package earth.terrarium.pastel.progression;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import earth.terrarium.pastel.progression.toast.MessageToast;
import earth.terrarium.pastel.progression.toast.UnlockedRecipeToast;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class UnlockToastManager {
    // Advancement Identifier + Recipe Variant => Recipe
    public static final Map<ResourceLocation, Map<RecipeType<?>, Set<GatedRecipe<?>>>> gatedRecipes = new HashMap<>();

    public static final Map<ResourceLocation, Tuple<ItemStack, String>> MESSAGE_TOASTS = new HashMap<>() {{
        put(
            PastelAdvancements.Milestones.UNLOCK_SHOOTING_STARS,
            new Tuple<>(Items.SPYGLASS.getDefaultInstance(), "shooting_stars_unlocked")
        );
        put(
            PastelAdvancements.Milestones.UNLOCK_OVERENCHANTING_WITH_ENCHANTER, new Tuple<>(
                PastelBlocks.ENCHANTER.get()
                                      .asItem()
                                      .getDefaultInstance(), "overchanting_unlocked"
            )
        );
        put(
            PastelAdvancements.Milestones.UNLOCK_CONFLICTED_ENCHANTING_WITH_ENCHANTER, new Tuple<>(
                PastelBlocks.ENCHANTER.get()
                                      .asItem()
                                      .getDefaultInstance(), "enchant_conflicting_enchantments_unlocked"
            )
        );
        put(
            PastelAdvancements.Milestones.UNLOCK_FOURTH_POTION_WORKSHOP_REAGENT_SLOT, new Tuple<>(
                PastelBlocks.POTION_WORKSHOP.get()
                                            .asItem()
                                            .getDefaultInstance(), "fourth_potion_reagent_unlocked"
            )
        );
        put(
            PastelAdvancements.Midgame.PASTEL_MIDGAME, new Tuple<>(
                PastelBlocks.PEDESTAL_ONYX.get()
                                          .asItem()
                                          .getDefaultInstance(), "second_advancement_tree_unlocked"
            )
        );
        put(
            PastelAdvancements.Lategame.PASTEL_LATEGAME, new Tuple<>(
                PastelBlocks.PEDESTAL_MOONSTONE.get()
                                               .asItem()
                                               .getDefaultInstance(), "third_advancement_tree_unlocked"
            )
        );
        put(
            PastelAdvancements.ASCEND_KINDLING, new Tuple<>(
                PastelBlocks.PEDESTAL_MOONSTONE.get()
                                               .asItem()
                                               .getDefaultInstance(), "ascend_kindling"
            )
        );
        put(PastelAdvancements.Hidden.GET_DENIED_BY_MANXI,new Tuple<>(
            PastelItems.NECTARDEW_BURGEON.get().getDefaultInstance(), "get_denied_by_manxi"
        ));
        put(
            PastelCommon.locate("mod_integration/neepmeat/vivisect_kindling"), new Tuple<>(
                PastelItems.DIVINATION_HEART.get()
                                            .getDefaultInstance(), "vivisect_kindling"
            )
        );
        put(
            PastelAdvancements.COLLECT_PIGMENT, new Tuple<>(
                PastelItems.PAINTBRUSH.get()
                                      .getDefaultInstance(), "block_coloring_unlocked"
            )
        );
        put(
            PastelAdvancements.Midgame.FILL_INK_CONTAINER, new Tuple<>(
                PastelItems.PAINTBRUSH.get()
                                      .getDefaultInstance(), "ink_slinging_unlocked"
            )
        );
        put(
            PastelAdvancements.Milestones.UNLOCK_PASTEL_NODE_COLORING, new Tuple<>(
                PastelBlocks.SENDER_NODE.get()
                                        .asItem()
                                        .getDefaultInstance(), "pastel_node_coloring"
            )
        );
    }};

    public static void clear() {
        gatedRecipes.clear();
    }

    public static void registerGatedRecipe(RecipeType<?> recipeType, GatedRecipe<?> gatedRecipe) {
        ResourceLocation requiredAdvancementIdentifier = gatedRecipe.advancementID()
                                                                    .orElse(null);

        // secret recipes should not have a popup
        if (gatedRecipe.isSecret()) {
            return;
        }

        if (gatedRecipes.containsKey(requiredAdvancementIdentifier)) {
            Map<RecipeType<?>, Set<GatedRecipe<?>>> recipeTypeListMap = gatedRecipes.get(requiredAdvancementIdentifier);
            if (recipeTypeListMap.containsKey(recipeType)) {
                Set<GatedRecipe<?>> existingSet = recipeTypeListMap.get(recipeType);
                existingSet.add(gatedRecipe);
            } else {
                Set<GatedRecipe<?>> newList = new ObjectArraySet<>();
                newList.add(gatedRecipe);
                recipeTypeListMap.put(recipeType, newList);
            }
        } else {
            Map<RecipeType<?>, Set<GatedRecipe<?>>> recipeTypeListSet = new HashMap<>();
            Set<GatedRecipe<?>> newSet = new ObjectArraySet<>();
            newSet.add(gatedRecipe);
            recipeTypeListSet.put(recipeType, newSet);
            gatedRecipes.put(requiredAdvancementIdentifier, recipeTypeListSet);
        }
    }

    public static void processAdvancements(Set<ResourceLocation> doneAdvancements) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return;
        RegistryAccess registryManager = client.level.registryAccess();

        int unlockedRecipeCount = 0;
        HashMap<RecipeType<?>, List<GatedRecipe<?>>> unlockedRecipesByType = new HashMap<>();
        List<Tuple<ItemStack, String>> specialToasts = new ArrayList<>();

        for (ResourceLocation doneAdvancement : doneAdvancements) {
            if (gatedRecipes.containsKey(doneAdvancement)) {
                Map<RecipeType<?>, Set<GatedRecipe<?>>> recipesGatedByAdvancement = gatedRecipes.get(doneAdvancement);

                for (Map.Entry<RecipeType<?>, Set<GatedRecipe<?>>> recipesByType :
                    recipesGatedByAdvancement.entrySet()) {
                    List<GatedRecipe<?>> newRecipes;
                    if (unlockedRecipesByType.containsKey(recipesByType.getKey())) {
                        newRecipes = unlockedRecipesByType.get(recipesByType.getKey());
                    } else {
                        newRecipes = new ArrayList<>();
                    }

                    for (GatedRecipe<?> unlockedRecipe : recipesByType.getValue()) {
                        if (unlockedRecipe.canPlayerCraft(client.player)) {
                            if (!newRecipes.contains((unlockedRecipe))) {
                                newRecipes.add(unlockedRecipe);
                                unlockedRecipeCount++;
                            }
                        }
                    }
                    unlockedRecipesByType.put(recipesByType.getKey(), newRecipes);
                }
            }

            Optional<PedestalTier> newlyUnlockedRecipeTier = PedestalTier.hasJustUnlockedANewRecipeTier(
                doneAdvancement);
            if (newlyUnlockedRecipeTier.isPresent()) {
                List<GatedRecipe<?>> unlockedPedestalRecipes;
                if (unlockedRecipesByType.containsKey(PastelRecipeTypes.PEDESTAL)) {
                    unlockedPedestalRecipes = unlockedRecipesByType.get(PastelRecipeTypes.PEDESTAL);
                } else {
                    unlockedPedestalRecipes = new ArrayList<>();
                }
                List<GatedRecipe<?>> pedestalRecipes = new ArrayList<>();
                for (Map<RecipeType<?>, Set<GatedRecipe<?>>> recipesByType : gatedRecipes.values()) {
                    if (recipesByType.containsKey(PastelRecipeTypes.PEDESTAL)) {
                        pedestalRecipes.addAll(recipesByType.get(PastelRecipeTypes.PEDESTAL));
                    }
                }

                for (PedestalRecipe alreadyUnlockedRecipe : getRecipesForTierWithAllConditionsMet(
                    newlyUnlockedRecipeTier.get(), pedestalRecipes)) {
                    if (!unlockedPedestalRecipes.contains(alreadyUnlockedRecipe)) {
                        unlockedPedestalRecipes.add(alreadyUnlockedRecipe);
                    }
                }
            }

            if (UnlockToastManager.MESSAGE_TOASTS.containsKey(doneAdvancement)) {
                specialToasts.add(UnlockToastManager.MESSAGE_TOASTS.get(doneAdvancement));
            }
        }

        if (unlockedRecipeCount > 50) {
            // the player unlocked a LOT of recipes at the same time (via command?)
            // => show a single toast. Nobody's going to remember all that stuff.
            // At that point it would be overwhelming / annoying
            List<ItemStack> allStacks = new ArrayList<>();
            for (List<GatedRecipe<?>> recipes : unlockedRecipesByType.values()) {
                for (GatedRecipe<?> recipe : recipes) {
                    allStacks.add(recipe.getResultItem(client.level.registryAccess()));
                }
            }
            UnlockedRecipeToast.showLotsOfRecipesToast(Minecraft.getInstance(), allStacks);
        } else {
            for (List<GatedRecipe<?>> unlockedRecipeList : unlockedRecipesByType.values()) {
                showGroupedRecipeUnlockToasts(registryManager, unlockedRecipeList);
            }
        }

        for (Tuple<ItemStack, String> messageToast : specialToasts) {
            MessageToast.showMessageToast(Minecraft.getInstance(), messageToast.getA(), messageToast.getB());
        }
    }

    private static void showGroupedRecipeUnlockToasts(
        RegistryAccess registryManager, List<GatedRecipe<?>> unlockedRecipes) {
        if (unlockedRecipes.isEmpty()) {
            return;
        }


        Component singleText = unlockedRecipes.getFirst()
                                              .getSingleUnlockToastString();
        Component multipleText = unlockedRecipes.getFirst()
                                                .getMultipleUnlockToastString();

        List<ItemStack> singleRecipes = new ArrayList<>();
        HashMap<String, List<ItemStack>> groupedRecipes = new HashMap<>();

        for (GatedRecipe<?> recipe : unlockedRecipes) {
            if (!recipe.getResultItem(registryManager)
                       .isEmpty()) { // weather recipes
                // FIXME - Better place to log this?
                //if (recipe.getGroup() == null) {
                //PastelCommon.logWarning("Found a recipe with null group: " + recipe.getId().toString() + " Please
                // report this. If you are DaFuqs and you are reading this: you messed up big time.");
                //}

                if (recipe.getGroup()
                          .isEmpty()) {
                    singleRecipes.add(recipe.getResultItem(registryManager));
                } else {
                    if (groupedRecipes.containsKey(recipe.getGroup())) {
                        groupedRecipes.get(recipe.getGroup())
                                      .add(recipe.getResultItem(registryManager));
                    } else {
                        List<ItemStack> newList = new ArrayList<>();
                        newList.add(recipe.getResultItem(registryManager));
                        groupedRecipes.put(recipe.getGroup(), newList);
                    }
                }
            }
        }

        // show grouped recipes
        if (!groupedRecipes.isEmpty()) {
            for (Map.Entry<String, List<ItemStack>> group : groupedRecipes.entrySet()) {
                List<ItemStack> groupedList = group.getValue();
                if (groupedList.size() == 1) {
                    UnlockedRecipeToast.showRecipeToast(Minecraft.getInstance(), groupedList.getFirst(), singleText);
                } else {
                    UnlockedRecipeToast.showRecipeGroupToast(
                        Minecraft.getInstance(), group.getKey(), groupedList, multipleText);
                }
            }
        }

        // show singular recipes
        for (ItemStack singleStack : singleRecipes) {
            UnlockedRecipeToast.showRecipeToast(Minecraft.getInstance(), singleStack, singleText);
        }
    }

    /**
     * When the player upgraded their pedestal and built the new structure
     * show toasts for all recipes that he already meets the requirements for
     *
     * @param pedestalRecipeTier The new pedestal recipe tier the player unlocked
     */
    private static @NotNull List<PedestalRecipe> getRecipesForTierWithAllConditionsMet(
        PedestalTier pedestalRecipeTier, List<GatedRecipe<?>> pedestalRecipes) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;

        List<PedestalRecipe> alreadyUnlockedRecipesAtNewTier = new ArrayList<>();
        for (GatedRecipe<?> recipe : pedestalRecipes) {
            PedestalRecipe pedestalRecipe = (PedestalRecipe) recipe;
            if (pedestalRecipe.getTier() == pedestalRecipeTier && !alreadyUnlockedRecipesAtNewTier.contains(recipe) &&
                recipe.canPlayerCraft(player)) {
                alreadyUnlockedRecipesAtNewTier.add(pedestalRecipe);
            }
        }
        return alreadyUnlockedRecipesAtNewTier;
    }

}
