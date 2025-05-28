package earth.terrarium.pastel.blocks.geology;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.mixin.accessors.ExperienceDroppingBlockAccessor;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleAroundBlockSidesPayload;
import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ShimmerstoneOreBlock extends CloakedOreBlock {
	
	public static final MapCodec<ShimmerstoneOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			IntProvider.codec(0, 10).fieldOf("experience").forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getXpRange()),
			propertiesCodec(),
			ResourceLocation.CODEC.fieldOf("advancement").forGetter(CloakedOreBlock::getCloakAdvancementIdentifier),
			BlockState.CODEC.fieldOf("cloak").forGetter(b -> b.getBlockStateCloaks().get(b.defaultBlockState()))
	).apply(instance, ShimmerstoneOreBlock::new));
	
	public ShimmerstoneOreBlock(IntProvider experienceDropped, Properties settings, ResourceLocation cloakAdvancementIdentifier, BlockState cloakBlockState) {
		super(experienceDropped, settings, cloakAdvancementIdentifier, cloakBlockState);
	}
	
	@Override
	public MapCodec<? extends ShimmerstoneOreBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		var random = world.getRandom();
		if (!world.isClientSide() && !entity.isSteppingCarefully() && random.nextInt(3) == 0) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerLevel) world, 1, pos, new Vec3(0, 0.05, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.UP);
			if (random.nextInt(3) == 0) {
				PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerLevel) world, 1, pos, new Vec3(0, 0.025, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.values());
				
			}
		}
		super.stepOn(world, pos, state, entity);
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClientSide()) {
			var random = world.getRandom();
			if (random.nextBoolean()) {
				var amount = (int) Math.ceil(Mth.clamp(fallDistance / 2, 1, 10));
				PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerLevel) world, amount, pos, new Vec3(0, 0.05 + amount / 30.0, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, this::isVisibleTo, Direction.UP);
			}
		}
		super.fallOn(world, state, pos, entity, fallDistance);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		
		if (isVisibleTo(Minecraft.getInstance().player)) {
			ParticleHelper.playParticleAroundBlockSides(world, SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, pos, Direction.values(), 1, new Vec3(0, 0.025, 0));
		}
	}
	
	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		if (!world.isClientSide()) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerLevel) world, 3, pos, new Vec3(0, 0.05, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, serverPlayer -> isVisibleTo(serverPlayer), Direction.values());
		}
		return super.playerWillDestroy(world, pos, state, player);
	}
	
	@Override
	public void attack(BlockState state, Level world, BlockPos pos, Player player) {
		super.attack(state, world, pos, player);
		if (!world.isClientSide()) {
			PlayParticleAroundBlockSidesPayload.playParticleAroundBlockSides((ServerLevel) world, 1, pos, new Vec3(0, 0.01, 0), SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, serverPlayer -> isVisibleTo(serverPlayer), Direction.values());
		}
	}
}
