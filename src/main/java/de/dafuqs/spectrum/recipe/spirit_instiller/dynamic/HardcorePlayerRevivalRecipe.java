package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic;

import com.mojang.authlib.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.spirit_instiller.*;
import de.dafuqs.spectrum.cca.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.component.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.server.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public class HardcorePlayerRevivalRecipe extends SpiritInstillerRecipe {
	
	public HardcorePlayerRevivalRecipe() {
		super("", false, null,
				IngredientStack.ofItems(1, Blocks.PLAYER_HEAD.asItem()), IngredientStack.ofItems(1, Items.TOTEM_OF_UNDYING), IngredientStack.ofItems(1, Items.ENCHANTED_GOLDEN_APPLE),
				ItemStack.EMPTY, 1200, 100, true);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLER_HARDCORE_PLAYER_REVIVAL;
	}
	
	@Override
	public ItemStack craft(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, RegistryWrapper.WrapperLookup drm) {
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		GameProfile gameProfile = getSkullOwner(recipeInput.getStackInSlot(SpiritInstillerRecipe.CENTER_INGREDIENT));
		if (gameProfile != null && SpectrumCommon.minecraftServer != null) {
			ServerPlayerEntity revivedPlayer = SpectrumCommon.minecraftServer.getPlayerManager().getPlayer(gameProfile.getName());
			if (revivedPlayer != null) {
				HardcoreDeathComponent.removeHardcoreDeath(gameProfile);
				revivedPlayer.changeGameMode(SpectrumCommon.minecraftServer.getDefaultGameMode());
				
				BlockRotation blockRotation = spiritInstillerBlockEntity.getMultiblockRotation();
				float yaw = 0.0F;
				switch (blockRotation) {
					case NONE -> yaw = -90.0F;
					case CLOCKWISE_90 -> yaw = 0.0F;
					case CLOCKWISE_180 -> yaw = 900.0F;
					case COUNTERCLOCKWISE_90 -> yaw = 180.0F;
				}
				
				BlockPos pos = spiritInstillerBlockEntity.getPos();
				revivedPlayer.teleport((ServerWorld) spiritInstillerBlockEntity.getWorld(), pos.getX(), pos.getY(), pos.getZ(), revivedPlayer.getYaw(), revivedPlayer.getPitch());
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canCraftWithStacks(RecipeInput inventory) {
		ItemStack instillerStack = inventory.getStackInSlot(0);
		if (instillerStack.isOf(Blocks.PLAYER_HEAD.asItem())) {
			GameProfile gameProfile = getSkullOwner(instillerStack);
			if (gameProfile == null || SpectrumCommon.minecraftServer == null) {
				return false;
			}
			
			PlayerManager playerManager = SpectrumCommon.minecraftServer.getPlayerManager();
			ServerPlayerEntity playerToRevive = gameProfile.getId() == null ? playerManager.getPlayer(gameProfile.getName()) : playerManager.getPlayer(gameProfile.getId());
			return playerToRevive != null && HardcoreDeathComponent.hasHardcoreDeath(gameProfile);
		}
		return false;
	}
	
	@Override
	public boolean canPlayerCraft(PlayerEntity playerEntity) {
		return true;
	}
	
	@Nullable
	private GameProfile getSkullOwner(ItemStack instillerStack) {
		var profile = instillerStack.get(DataComponentTypes.PROFILE);
		return profile == null ? null : profile.gameProfile();
	}
	
}
