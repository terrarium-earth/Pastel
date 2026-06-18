package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.inventories.CraftingTabletScreenHandler;
import earth.terrarium.pastel.items.tooltip.CraftingTabletTooltipData;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.registries.PastelBannerPatterns;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.List;
import java.util.Optional;

public class CraftingTabletItem extends Item implements LoomPatternProvider {

    private static final Component TITLE = Component.translatable("item.pastel.crafting_tablet");

    public CraftingTabletItem(Properties settings) {
        super(settings);
    }

    public static void setStoredRecipe(ItemStack craftingTabletItemStack, RecipeHolder<?> recipe) {
        craftingTabletItemStack.set(PastelDataComponentTypes.STORED_RECIPE, recipe.id());
    }

    public static void clearStoredRecipe(ItemStack craftingTabletItemStack) {
        craftingTabletItemStack.remove(PastelDataComponentTypes.STORED_RECIPE);
    }

    public static RecipeHolder<?> getStoredRecipe(Level world, ItemStack itemStack) {
        if (world != null) {
            var id = itemStack.get(PastelDataComponentTypes.STORED_RECIPE);
            if (id != null)
                return world
                    .getRecipeManager()
                    .byKey(id)
                    .orElse(null);
        }
        return null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        var storedRecipe = getStoredRecipe(world, itemStack);
        if (storedRecipe == null || user.isShiftKeyDown()) {
            if (world.isClientSide) {
                return InteractionResultHolder.success(user.getItemInHand(hand));
            } else {
                user.openMenu(createScreenHandlerFactory(world, (ServerPlayer) user, itemStack));
                return InteractionResultHolder.consume(user.getItemInHand(hand));
            }
        } else {
            if (storedRecipe.value() instanceof PedestalRecipe) {
                return InteractionResultHolder.pass(user.getItemInHand(hand));
            } else {
                if (tryCraftRecipe(user, storedRecipe.value(), world)) {
                    if (world.isClientSide) {
                        return InteractionResultHolder.success(user.getItemInHand(hand));
                    } else {
                        return InteractionResultHolder.consume(user.getItemInHand(hand));
                    }
                }
                user.playSound(PastelSounds.USE_FAIL, 1.0F, 1.0F);
                return InteractionResultHolder.fail(user.getItemInHand(hand));
            }
        }
    }

    public MenuProvider createScreenHandlerFactory(Level world, ServerPlayer serverPlayerEntity, ItemStack itemStack) {
        return new SimpleMenuProvider(
            (syncId, inventory, player) -> new CraftingTabletScreenHandler(
                syncId,
                inventory,
                ContainerLevelAccess
                    .create(
                        world,
                        serverPlayerEntity.blockPosition()
                    ),
                itemStack
            ),
            TITLE
        );
    }

    public static boolean tryCraftRecipe(Player serverPlayerEntity, Recipe<?> recipe, Level world) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        IItemHandlerModifiable playerInventory = new InvWrapper(serverPlayerEntity.getInventory());
        boolean hasInInventory = InventoryHelper.hasInInventory(ingredients, playerInventory);
        if (world.isClientSide) {
            return hasInInventory;
        }

        if (InventoryHelper.hasInInventory(ingredients, playerInventory)) {
            List<ItemStack> remainders = InventoryHelper
                .removeFromInventoryWithRemainders(
                    ingredients,
                    playerInventory
                );

            ItemStack craftingResult = recipe
                .getResultItem(
                    serverPlayerEntity
                        .level()
                        .registryAccess()
                )
                .copy();
            serverPlayerEntity
                .getInventory()
                .placeItemBackInInventory(craftingResult);

            for (
                ItemStack remainder : remainders
            ) {
                serverPlayerEntity
                    .getInventory()
                    .placeItemBackInInventory(remainder);
            }
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        var recipe = getStoredRecipe(Minecraft.getInstance().level, stack);
        if (recipe == null) {
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.crafting_tablet.tooltip.no_recipe")
                        .withStyle(ChatFormatting.GRAY)
                );
        } else {
            if (recipe.value() instanceof PedestalRecipe) {
                tooltip
                    .add(
                        Component
                            .translatable("item.pastel.crafting_tablet.tooltip.pedestal_recipe")
                            .withStyle(ChatFormatting.GRAY)
                    );
            } else {
                tooltip
                    .add(
                        Component
                            .translatable("item.pastel.crafting_tablet.tooltip.crafting_recipe")
                            .withStyle(ChatFormatting.GRAY)
                    );
            }
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.crafting_tablet.tooltip.shift_to_view_gui")
                        .withStyle(ChatFormatting.GRAY)
                );
        }

        addBannerPatternProviderTooltip(tooltip);
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        Minecraft client = Minecraft.getInstance();
        var storedRecipe = CraftingTabletItem.getStoredRecipe(client.level, stack);
        if (storedRecipe != null) {
            return Optional.of(new CraftingTabletTooltipData(storedRecipe.value(), client.level));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ResourceKey<BannerPattern> getPattern() {
        return PastelBannerPatterns.CRAFTING_TABLET;
    }

}
