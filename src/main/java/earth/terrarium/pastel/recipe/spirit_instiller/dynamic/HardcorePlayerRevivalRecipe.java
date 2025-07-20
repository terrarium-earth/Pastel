package earth.terrarium.pastel.recipe.spirit_instiller.dynamic;

import com.mojang.authlib.GameProfile;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.attachments.HardcoreDeathTracker;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.recipe.InstanceRecipeInput;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HardcorePlayerRevivalRecipe extends SpiritInstillerRecipe {

    public HardcorePlayerRevivalRecipe() {
        super(
            "", false, Optional.empty(),
            IngredientStack.ofItems(Blocks.PLAYER_HEAD.asItem()), IngredientStack.ofItems(Items.TOTEM_OF_UNDYING),
            IngredientStack.ofItems(Items.ENCHANTED_GOLDEN_APPLE),
            ItemStack.EMPTY, 1200, 100, true
        );
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.SPIRIT_INSTILLER_HARDCORE_PLAYER_REVIVAL;
    }

    @Override
    public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
        SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
        GameProfile gameProfile = getSkullOwner(recipeInput.getItem(SpiritInstillerRecipe.CENTER));
        if (gameProfile != null && PastelCommon.getSidedServer() != null) {
            ServerPlayer revivedPlayer = PastelCommon.getSidedServer()
                                                     .getPlayerList()
                                                     .getPlayerByName(gameProfile.getName());
            if (revivedPlayer != null) {
                HardcoreDeathTracker.removeHardcoreDeath(gameProfile);
                revivedPlayer.setGameMode(PastelCommon.getSidedServer()
                                                      .getDefaultGameType());

                Rotation blockRotation = spiritInstillerBlockEntity.getMultiblockRotation();
                float yaw = 0.0F;
                switch (blockRotation) {
                    case NONE -> yaw = -90.0F;
                    case CLOCKWISE_90 -> yaw = 0.0F;
                    case CLOCKWISE_180 -> yaw = 900.0F;
                    case COUNTERCLOCKWISE_90 -> yaw = 180.0F;
                }

                BlockPos pos = spiritInstillerBlockEntity.getBlockPos();
                revivedPlayer.teleportTo(
                    (ServerLevel) spiritInstillerBlockEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ(),
                    revivedPlayer.getYRot(), revivedPlayer.getXRot()
                );
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftWithStacks(RecipeInput inventory, Level level) {
        ItemStack instillerStack = inventory.getItem(0);
        if (instillerStack.is(Blocks.PLAYER_HEAD.asItem())) {
            GameProfile gameProfile = getSkullOwner(instillerStack);
            if (gameProfile == null || PastelCommon.getSidedServer() == null) {
                return false;
            }

            PlayerList playerManager = PastelCommon.getSidedServer()
                                                   .getPlayerList();
            ServerPlayer playerToRevive = gameProfile.getId() == null ? playerManager.getPlayerByName(
                gameProfile.getName()) : playerManager.getPlayer(gameProfile.getId());
            return playerToRevive != null && HardcoreDeathTracker.hasHardcoreDeath(gameProfile);
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
