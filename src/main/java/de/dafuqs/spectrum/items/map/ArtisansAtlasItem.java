package de.dafuqs.spectrum.items.map;

import com.mojang.datafixers.util.Pair;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.item.map.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.structure.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ArtisansAtlasItem extends FilledMapItem {
	
	public static final int COOLDOWN_DURATION_TICKS = 20;
	
	public ArtisansAtlasItem(Settings settings) {
		super(settings);
	}
	
	private static void createAndSetState(ItemStack stack, ServerWorld world, int centerX, int centerZ, @Nullable StructureStart target, @Nullable Identifier targetId) {
		ArtisansAtlasState state = new ArtisansAtlasState(centerX, centerZ, (byte) 1, true, true, false, world.getRegistryKey());
		MapIdComponent id = world.increaseAndGetMapId();
		
		state.setTargetId(targetId);
		if (targetId != null) {
			state.startLocator(world);
			if (target != null) {
				state.addTarget(world, target.getBoundingBox().getCenter());
			}
		} else {
			state.cancelLocator();
		}
		
		world.putMapState(id, state);
		stack.set(DataComponentTypes.MAP_ID, id);
	}
	
	@Override
	public void updateColors(World world, Entity entity, MapState state) {
		if (world.getRegistryKey() != state.dimension || !(entity instanceof PlayerEntity playerEntity) || !(state instanceof ArtisansAtlasState atlasState)) {
			return;
		}
		
		int sampleSize = 1 << state.scale;
		MapState.PlayerUpdateTracker playerUpdateTracker = state.getPlayerSyncData(playerEntity);
		playerUpdateTracker.field_131++;
		
		Vec3i delta = atlasState.getDisplayDelta();
		if (delta == null) {
			// Delta is null when the state is first created, so update the whole thing
			delta = entity.getBlockPos().subtract(atlasState.getDisplayedCenter());
			int deltaX = delta.getX() / sampleSize;
			int deltaZ = delta.getZ() / sampleSize;
			
			for (int x = 0; x <= 127; x++) {
				updateVerticalStrip(world, atlasState, deltaX, deltaZ, x, 0, 127);
			}
			
			atlasState.clearDisplayDelta();
			return;
		}
		
		// Re-render the part that's moved, and copy the rest
		int deltaX = delta.getX() / sampleSize;
		int deltaZ = delta.getZ() / sampleSize;
		
		if (deltaX < 0) {
			for (int x = 127; x >= -deltaX; x--) {
				updateOrCopyVerticalStrip(world, atlasState, deltaX, deltaZ, x, playerUpdateTracker.field_131);
			}
			for (int x = 0; x <= Math.min(127, -deltaX - 1); x++) {
				updateVerticalStrip(world, atlasState, deltaX, deltaZ, x, 0, 127);
			}
		} else {
			for (int x = 0; x <= 127 - deltaX; x++) {
				updateOrCopyVerticalStrip(world, atlasState, deltaX, deltaZ, x, playerUpdateTracker.field_131);
			}
			for (int x = Math.max(0, 127 - deltaX + 1); x <= 127; x++) {
				updateVerticalStrip(world, atlasState, deltaX, deltaZ, x, 0, 127);
			}
		}
		
		if (deltaX != 0 || deltaZ != 0) {
			atlasState.clearDisplayDelta();
		}
	}
	
	private void updateOrCopyVerticalStrip(World world, ArtisansAtlasState state, int deltaX, int deltaZ, int x, int tick) {
		if (deltaX > 127 || deltaX < -127 || deltaZ > 127 || deltaZ < -127 || (x & 15) == (tick & 15)) {
			updateVerticalStrip(world, state, deltaX, deltaZ, x, 0, 127);
		} else if (deltaZ < 0) {
			copyVerticalStrip(state, deltaX, deltaZ, x, 127, -deltaZ);
			updateVerticalStrip(world, state, deltaX, deltaZ, x, 0, -deltaZ - 1);
		} else if (deltaZ > 0) {
			copyVerticalStrip(state, deltaX, deltaZ, x, 0, 127 - deltaZ);
			updateVerticalStrip(world, state, deltaX, deltaZ, x, 127 - deltaZ + 1, 127);
		} else if (deltaX != 0) {
			copyVerticalStrip(state, deltaX, deltaZ, x, 0, 127);
		}
	}
	
	private void copyVerticalStrip(ArtisansAtlasState state, int deltaX, int deltaZ, int x, int startZ, int endZ) {
		if (startZ > endZ) {
			for (int z = startZ; z >= endZ; z--) {
				state.setColor(x, z, state.colors[(x + deltaX) + (z + deltaZ) * 128]);
			}
		} else {
			for (int z = startZ; z <= endZ; z++) {
				state.setColor(x, z, state.colors[(x + deltaX) + (z + deltaZ) * 128]);
			}
		}
	}
	
	private void updateVerticalStrip(World world, ArtisansAtlasState state, int deltaX, int deltaZ, int x, int startZ, int endZ) {
		double previousHeight = getHeight(world, state, deltaX, deltaZ, x, startZ - 1);
		for (int z = startZ; z <= endZ; z++) {
			previousHeight = updateColor(world, state, deltaX, deltaZ, x, z, previousHeight);
		}
	}
	
	private double updateColor(World world, ArtisansAtlasState state, int deltaX, int deltaZ, int x, int z, double previousHeight) {
		int sampleSize = 1 << state.scale;
		int sampleArea = sampleSize * sampleSize;
		int sampleMask = sampleSize - 1;
		
		boolean hasCeiling = world.getDimension().hasCeiling();
		
		int blockX = ((state.getDisplayedCenter().getX() >> state.scale) + deltaX + x - 64) * sampleSize;
		int blockZ = ((state.getDisplayedCenter().getZ() >> state.scale) + deltaZ + z - 64) * sampleSize;
		
		WorldChunk chunk = world.getChunk(ChunkSectionPos.getSectionCoord(blockX), ChunkSectionPos.getSectionCoord(blockZ));
		if (chunk.isEmpty()) {
			return previousHeight;
		}
		
		
		int[] multiset = new int[64];
		
		int fluidDepth = 0;
		double height;
		if (hasCeiling) {
			int hash = blockX + blockZ * 231871;
			hash = hash * hash * 31287121 + hash * 11;
			if ((hash >> 20 & 1) == 0) {
				multiset[Blocks.DIRT.getDefaultState().getMapColor(world, BlockPos.ORIGIN).id] += 10;
			} else {
				multiset[Blocks.STONE.getDefaultState().getMapColor(world, BlockPos.ORIGIN).id] += 100;
			}
			
			height = 100.0;
		} else {
			height = 0.0;
			int bottomY = world.getBottomY();
			BlockPos.Mutable samplePos = new BlockPos.Mutable(0, 0, 0);
			
			for (int sample = 0; sample < sampleArea; sample++) {
				
				int posX = blockX + (sample >> state.scale);
				int posZ = blockZ + (sample & sampleMask);
				samplePos.setX(posX);
				samplePos.setZ(posZ);
				
				int sampleY = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, posX, posZ) + 1;
				
				BlockState blockState;
				MapColor mapColor;
				if (sampleY <= bottomY + 1) {
					blockState = Blocks.BEDROCK.getDefaultState();
					mapColor = blockState.getMapColor(world, samplePos);
				} else {
					do {
						samplePos.setY(--sampleY);
						blockState = chunk.getBlockState(samplePos);
						mapColor = blockState.getMapColor(world, samplePos);
					} while (mapColor == MapColor.CLEAR && sampleY > bottomY);
					
					if (sampleY > bottomY && !blockState.getFluidState().isEmpty()) {
						int fluidY = sampleY - 1;
						BlockPos.Mutable fluidPos = samplePos.mutableCopy();
						
						BlockState fluidBlockState;
						do {
							fluidPos.setY(fluidY--);
							fluidBlockState = chunk.getBlockState(fluidPos);
						} while (fluidY > bottomY && !fluidBlockState.getFluidState().isEmpty());
						
						fluidDepth += sampleY - 1 - fluidY;
						
						FluidState fluidState = blockState.getFluidState();
						blockState = !fluidState.isEmpty() && !blockState.isSideSolidFullSquare(world, samplePos, Direction.UP)
								? fluidState.getBlockState() : blockState;
						
						mapColor = blockState.getMapColor(world, samplePos);
					}
				}
				
				state.removeBanner(world, posX, posZ);
				
				height += sampleY;
				multiset[mapColor.id]++;
			}
			
			height /= sampleArea;
			fluidDepth /= sampleArea;
		}
		
		int maxCount = 0;
		MapColor color = MapColor.CLEAR;
		for (int i = 0; i < multiset.length; i++) {
			if (multiset[i] > maxCount) {
				maxCount = multiset[i];
				color = MapColor.get(i);
			}
		}
		
		MapColor.Brightness brightness;
		int odd = ((blockX ^ blockZ) / sampleSize) & 1;
		if (color == MapColor.WATER_BLUE) {
			double depth = (double) fluidDepth * 0.1 + (double) odd * 0.2;
			if (depth < 0.5) {
				brightness = MapColor.Brightness.HIGH;
			} else if (depth > 0.9) {
				brightness = MapColor.Brightness.LOW;
			} else {
				brightness = MapColor.Brightness.NORMAL;
			}
		} else {
			double f = (height - previousHeight) * 4.0 / (double) (sampleSize + 4) + ((double) odd - 0.5) * 0.4;
			if (f > 0.6) {
				brightness = MapColor.Brightness.HIGH;
			} else if (f < -0.6) {
				brightness = MapColor.Brightness.LOW;
			} else {
				brightness = MapColor.Brightness.NORMAL;
			}
		}
		
		state.setColor(x, z, color.getRenderColorByte(brightness));
		
		return height;
	}
	
	private double getHeight(World world, ArtisansAtlasState state, int deltaX, int deltaZ, int x, int z) {
		int sampleSize = 1 << state.scale;
		int sampleArea = sampleSize * sampleSize;
		int sampleMask = sampleSize - 1;
		
		int blockX = ((state.getDisplayedCenter().getX() >> state.scale) + deltaX + x - 64) * sampleSize;
		int blockZ = ((state.getDisplayedCenter().getZ() >> state.scale) + deltaZ + z - 64) * sampleSize;
		
		WorldChunk chunk = world.getChunk(ChunkSectionPos.getSectionCoord(blockX), ChunkSectionPos.getSectionCoord(blockZ));
		if (chunk.isEmpty())
			return 0;
		
		double height;
		if (world.getDimension().hasCeiling()) {
			height = 100.0;
		} else {
			height = 0.0;
			int bottomY = world.getBottomY();
			
			for (int sample = 0; sample < sampleArea; sample++) {
				int posX = blockX + (sample >> state.scale);
				int posZ = blockZ + (sample & sampleMask);
				
				BlockPos.Mutable samplePos = new BlockPos.Mutable(posX, 0, posZ);
				int sampleY = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, posX, posZ) + 1;
				
				if (sampleY > bottomY + 1) {
					BlockState blockState;
					do {
						samplePos.setY(--sampleY);
						blockState = chunk.getBlockState(samplePos);
					} while (blockState.getMapColor(world, samplePos) == MapColor.CLEAR && sampleY > bottomY);
				}
				
				height += sampleY;
			}
			
			height /= sampleArea;
		}
		
		return height;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!world.isClient) {
			MapState state = getMapState(stack, world);
			if (state instanceof ArtisansAtlasState atlasState) {
				atlasState.updateDimension(world.getRegistryKey());
			}
		}
		
		super.inventoryTick(stack, world, entity, slot, selected);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient() && context.getWorld() instanceof ServerWorld serverWorld && context.getPlayer() instanceof ServerPlayerEntity serverPlayerEntity) {
			ItemStack stack = serverPlayerEntity.getStackInHand(context.getHand());
			if (serverPlayerEntity.isSneaking()) {
				Vec3d hitPos = context.getHitPos();
				BlockPos blockPos = BlockPos.ofFloored(hitPos.getX(), hitPos.getY(), hitPos.getZ());
				Pair<Identifier, StructureStart> pair = ArtisansAtlasState.locateAnyStructureAtBlock(serverWorld, blockPos);
				if (pair != null) {
					Identifier structureId = pair.getFirst();
					if (SpectrumStructureTags.isIn(serverWorld, structureId, SpectrumStructureTags.UNLOCATABLE)) { // TODO: use c: tag
						serverPlayerEntity.sendMessage(Text.translatable("item.spectrum.artisans_atlas.unlocatable"), true);
					} else {
						serverPlayerEntity.sendMessage(Text.translatable("item.spectrum.artisans_atlas.set_structure").append(Text.translatable(structureId.toTranslationKey("structure"))), true);
						createAndSetState(stack, serverWorld, (int) serverPlayerEntity.getX(), (int) serverPlayerEntity.getZ(), pair.getSecond(), pair.getFirst());
					}
				}
				
				serverPlayerEntity.getItemCooldownManager().set(stack.getItem(), COOLDOWN_DURATION_TICKS);
			}
		}
		
		return ActionResult.success(context.getWorld().isClient());
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		
		if (!world.isClient() && world instanceof ServerWorld serverWorld && user instanceof ServerPlayerEntity serverPlayerEntity) {
			if (user.isSneaking())
				createAndSetState(stack, serverWorld, (int) serverPlayerEntity.getX(), (int) serverPlayerEntity.getZ(), null, null);
		}
		
		return TypedActionResult.success(stack, world.isClient());
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		
		var world = MinecraftClient.getInstance().world;
		if (world == null) return;
		
		if (getMapState(stack, world) instanceof ArtisansAtlasState atlasState) {
			Identifier structureId = atlasState.getTargetId();
			if (structureId == null)
				tooltip.add(Text.translatable("item.spectrum.artisans_atlas.empty"));
			else
				tooltip.add(Text.translatable("item.spectrum.artisans_atlas.locates_structure").append(Text.translatable(structureId.toTranslationKey("structure"))));
		}
		
	}
	
}
