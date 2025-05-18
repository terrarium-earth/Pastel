package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic;

import com.mojang.authlib.GameProfile;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import de.dafuqs.spectrum.cca.HardcoreDeathComponent;
import de.dafuqs.spectrum.recipe.InstanceRecipeInput;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HardcorePlayerRevivalRecipe extends SpiritInstillerRecipe {
	
	public HardcorePlayerRevivalRecipe() {
		super("", false, Optional.empty(),
				IngredientStack.ofItems(Blocks.PLAYER_HEAD.asItem()), IngredientStack.ofItems(Items.TOTEM_OF_UNDYING), IngredientStack.ofItems(Items.ENCHANTED_GOLDEN_APPLE),
				ItemStack.EMPTY, 1200, 100, true);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLER_HARDCORE_PLAYER_REVIVAL;
	}
	
	@Override
	public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		GameProfile gameProfile = getSkullOwner(recipeInput.getItem(SpiritInstillerRecipe.CENTER_INGREDIENT));
		if (gameProfile != null && SpectrumCommon.minecraftServer != null) {
			ServerPlayer revivedPlayer = SpectrumCommon.minecraftServer.getPlayerList().getPlayerByName(gameProfile.getName());
			if (revivedPlayer != null) {
				HardcoreDeathComponent.removeHardcoreDeath(gameProfile);
				revivedPlayer.setGameMode(SpectrumCommon.minecraftServer.getDefaultGameType());
				
				Rotation blockRotation = spiritInstillerBlockEntity.getMultiblockRotation();
				float yaw = 0.0F;
				switch (blockRotation) {
					case NONE -> yaw = -90.0F;
					case CLOCKWISE_90 -> yaw = 0.0F;
					case CLOCKWISE_180 -> yaw = 900.0F;
					case COUNTERCLOCKWISE_90 -> yaw = 180.0F;
				}
				
				BlockPos pos = spiritInstillerBlockEntity.getBlockPos();
				revivedPlayer.teleportTo((ServerLevel) spiritInstillerBlockEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ(), revivedPlayer.getYRot(), revivedPlayer.getXRot());
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canCraftWithStacks(RecipeInput inventory) {
		ItemStack instillerStack = inventory.getItem(0);
		if (instillerStack.is(Blocks.PLAYER_HEAD.asItem())) {
			GameProfile gameProfile = getSkullOwner(instillerStack);
			if (gameProfile == null || SpectrumCommon.minecraftServer == null) {
				return false;
			}
			
			PlayerList playerManager = SpectrumCommon.minecraftServer.getPlayerList();
			ServerPlayer playerToRevive = gameProfile.getId() == null ? playerManager.getPlayerByName(gameProfile.getName()) : playerManager.getPlayer(gameProfile.getId());
			return playerToRevive != null && HardcoreDeathComponent.hasHardcoreDeath(gameProfile);
		}
		return false;
	}
	
	@Override
	public boolean canPlayerCraft(Player playerEntity) {
		return true;
	}
	
	@Nullable
	private GameProfile getSkullOwner(ItemStack instillerStack) {
		var profile = instillerStack.get(DataComponents.PROFILE);
		return profile == null ? null : profile.gameProfile();
	}
	
}
