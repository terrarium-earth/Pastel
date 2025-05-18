package de.dafuqs.revelationary.mixin;

import de.dafuqs.revelationary.RevelationRegistry;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {
	@Shadow public abstract ResourceKey<LootTable> getLootTable();

	@Redirect(
			method = "getDrops",
			at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/state/BlockBehaviour.getLootTable ()Lnet/minecraft/resources/ResourceKey;")
	)
	private ResourceKey<LootTable> revelationary$switchLootTableForCloakedBlock(BlockBehaviour instance, BlockState state, LootParams.Builder builder) {
		BlockState cloakState = RevelationRegistry.getCloak(state);
		if (cloakState != null) {
			Player lootPlayerEntity = RevelationAware.getLootPlayerEntity(builder);
			if (!RevelationRegistry.isVisibleTo(state, lootPlayerEntity)) {
				return cloakState.getBlock().getLootTable();
			}
		}
		return getLootTable();
	}
}
