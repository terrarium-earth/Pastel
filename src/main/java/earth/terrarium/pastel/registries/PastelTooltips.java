package earth.terrarium.pastel.registries;

import com.mojang.serialization.DataResult;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.SignText;
import net.neoforged.neoforge.event.entity.player.*;

import java.util.List;
import java.util.Optional;

public class PastelTooltips {
	
	public static void register(ItemTooltipEvent event) {
		var context = event.getContext();
		var lines = event.getToolTip();
		var stack = event.getItemStack();

		DataComponentMap components = stack.getComponents();
		if (!components.isEmpty()) {
			if (stack.is(Blocks.SCULK_SHRIEKER.asItem())) {
				addSculkShriekerTooltips(lines, components);
			} else if (stack.is(ItemTags.SIGNS)) {
				addSignTooltips(lines, components);
			} else if (stack.is(Items.SPAWNER)) {
				addSpawnerTooltips(lines, components);
			}
		}
	}
	
	private static void addSculkShriekerTooltips(List<Component> lines, DataComponentMap components) {
		BlockItemStateProperties stateComponent = components.get(DataComponents.BLOCK_STATE);
		if (stateComponent != null && !stateComponent.isEmpty()) {
			if (Boolean.TRUE.equals(stateComponent.get(SculkShriekerBlock.CAN_SUMMON))) {
				lines.add(Component.translatable("pastel.tooltip.able_to_summon_warden").withStyle(ChatFormatting.GRAY));
			}
		}
	}
	
	private static void addSignTooltips(List<Component> lines, DataComponentMap components) {
		CustomData dataComponent = components.get(DataComponents.BLOCK_ENTITY_DATA);
		if (dataComponent == null) {
			return;
		}
		CompoundTag blockEntityTag = dataComponent.getUnsafe().getCompound("BlockEntityTag");
		addSignText(lines, SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, blockEntityTag.getCompound("front_text")));
		addSignText(lines, SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, blockEntityTag.getCompound("back_text")));
	}
	
	private static void addSignText(List<Component> lines, DataResult<SignText> signText) {
		if (signText.result().isPresent()) {
			SignText st = signText.result().get();
			Style style = Style.EMPTY.withColor(st.getColor().getTextColor());
			for (Component text : st.getMessages(false)) {
				lines.addAll(text.toFlatList(style));
			}
		}
	}
	
	public static void addSpawnerTooltips(List<Component> lines, DataComponentMap components) {
		CustomData dataComponent = components.get(DataComponents.BLOCK_ENTITY_DATA);
		if (dataComponent == null) {
			return;
		}
		
		CompoundTag blockEntityTag = dataComponent.copyTag();
		
		try {
			short spawnCount = blockEntityTag.getShort("SpawnCount");
			short minSpawnDelay = blockEntityTag.getShort("MinSpawnDelay");
			short maxSpawnDelay = blockEntityTag.getShort("MaxSpawnDelay");
			short spawnRange = blockEntityTag.getShort("SpawnRange");
			short requiredPlayerRange = blockEntityTag.getShort("RequiredPlayerRange");
			short maxNearbyEntities = blockEntityTag.getShort("MaxNearbyEntities");
			
			if (spawnCount > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.spawn_count", spawnCount).withStyle(ChatFormatting.GRAY));
			}
			if (minSpawnDelay > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.min_spawn_delay", minSpawnDelay).withStyle(ChatFormatting.GRAY));
			}
			if (maxSpawnDelay > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.max_spawn_delay", maxSpawnDelay).withStyle(ChatFormatting.GRAY));
			}
			if (spawnRange > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.spawn_range", spawnRange).withStyle(ChatFormatting.GRAY));
			}
			if (requiredPlayerRange > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.required_player_range", requiredPlayerRange).withStyle(ChatFormatting.GRAY));
			}
			if (maxNearbyEntities > 0) {
				lines.add(Component.translatable("item.pastel.spawner.tooltip.max_nearby_entities", maxNearbyEntities).withStyle(ChatFormatting.GRAY));
			}
		} catch (Exception e) {
			lines.add(Component.translatable("item.pastel.spawner.tooltip.unknown_mob"));
		}
	}
}
