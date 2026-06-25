package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.data.recipe.builder.SpiritInstillerRecipeBuilder;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.HardcorePlayerRevivalRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.MemoryToHeadRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.*;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class SpiritInstillerRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "spirit_instiller");

        root(pfx);
        spawner(pfx.subPrefix("spawner"));
        secret(pfx.subPrefix("secret"));
        headFusion(pfx.subPrefix("head_fusion"));
    }

    private static void secret(PrefixHelper pfx) {
        // NOTE: this has a hint that isn't actually used due to the codec
        // not accepting it
        // lang string
        // recipe.pastel.spirit_instiller.germinated_jade_vine_crossbreeding.hint
        pfx.generateRecipe(
                "germiniated_jade_vine_crossbreeding",
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(PastelBlocks.NEPHRITE_BLOSSOM_BULB, 1),
                        SizedIngredient.of(PastelBlocks.JADEITE_LOTUS_BULB, 1),
                        new ItemStack(PastelItems.GERMINATED_JADE_VINE_BULB.asItem())
                )
                        .craftingTime(200)
                        .experience(2.0f)
                        .secret(true)
        );

        // Also has a unused hint:
        // recipe.pastel.spirit_instiller.chorus_flower.hint
        pfx.generateRecipe(
            "chorus_flower",
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(Items.ECHO_SHARD, 1),
                        SizedIngredient.of(PastelBlocks.RESONANT_LILY.asItem(), 1),
                        new ItemStack(Items.CHORUS_FLOWER)
                )
                        .craftingTime(1200)
                        .experience(10.0f)
                        .secret(true)
        );
    }

    private static void spawner(PrefixHelper pfx) {
        pfx.generateDynamicRecipe(
                "spawner_creature_change",
                new SpawnerCreatureChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_max_nearby_entities_change",
                new SpawnerMaxNearbyEntitiesChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_required_player_range_change",
                new SpawnerRequiredPlayerRangeChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_spawn_count_change",
                new SpawnerSpawnCountChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_spawn_delay_change",
                new SpawnerSpawnDelayChangeRecipe()
        );
    }

    private static void root(PrefixHelper pfx) {
        pfx.generateDynamicRecipe(
                "hardcore_player_revival",
                new HardcorePlayerRevivalRecipe()
        );

        pfx.generateDynamicRecipe(
                "memory_to_head",
                new MemoryToHeadRecipe()
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(PastelBlocks.RESONANT_LILY, 1),
                        SizedIngredient.of(PastelItems.BLOOD_ORCHID_PETAL, 1),
                        new ItemStack(PastelBlocks.BLOOD_ORCHID)
                )
                        .craftingTime(400)
                        .experience(4.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BLOOD_ORCHID)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelBlocks.ONYX_BLOCK, 1),
                        SizedIngredient.of(PastelItems.MIDNIGHT_ABERRATION, 1),
                        SizedIngredient.of(PastelItems.MOONSTRUCK_NECTAR, 1),
                        new ItemStack(PastelBlocks.BUDDING_ONYX, 1)
                )
                        .craftingTime(800)
                        .experience(4.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BUDDING_ONYX)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.HIBERNATING_JADE_VINE_BULB, 1),
                        SizedIngredient.of(PastelItems.LIQUID_CRYSTAL_BUCKET, 1),
                        SizedIngredient.of(PastelItems.VEGETAL, 16),
                        new ItemStack(PastelItems.GERMINATED_JADE_VINE_BULB.asItem())
                )
                        .craftingTime(600)
                        .experience(20.0f)
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_HIBERNATING_JADE_VINE_BULB)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(Items.NAUTILUS_SHELL, 1),
                        SizedIngredient.of(PastelItems.MOONSTRUCK_NECTAR, 1),
                        SizedIngredient.of(PastelItems.MERMAIDS_GEM, 1),
                        new ItemStack(Items.HEART_OF_THE_SEA)
                )
                        .craftingTime(200)
                        .experience(2.0f)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.KNOTTED_SWORD, 1),
                        SizedIngredient.of(PastelItems.EVERNECTAR, 1),
                        SizedIngredient.of(PastelItems.AETHER_VESTIGES, 1),
                        new ItemStack(PastelItems.NECTAR_LANCE.asItem())
                )
                        .craftingTime(1200)
                        .experience(16.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.NECTAR_LANCE)
        );
    }

    private static void headFusion(PrefixHelper pfx) {
        var h = new HeadGenerator(pfx, 4);
        h.generateHeadFusion(head(PastelSkullType.PHANTOM), SecondFusionIngredient.NIGHTDEW, head(PastelSkullType.GHAST));
        h.generateHeadFusion(head(PastelSkullType.SQUID),SecondFusionIngredient.basic(PastelItems.SHIMMERSTONE_GEM, 4), head(PastelSkullType.GLOW_SQUID));
        h.generateHeadFusion(Items.ZOMBIE_HEAD, SecondFusionIngredient.INCANDESCENT, head(PastelSkullType.HUSK));
        h.generateHeadFusion(head(PastelSkullType.SLIME), SecondFusionIngredient.INCANDESCENT, head(PastelSkullType.MAGMA_CUBE));
        h.generateHeadFusion(head(PastelSkullType.COW), SecondFusionIngredient.basic(Items.BROWN_MUSHROOM, 4), head(PastelSkullType.MOOSHROOM_BROWN));
        h.generateHeadFusion(head(PastelSkullType.COW), SecondFusionIngredient.basic(Items.RED_MUSHROOM, 4), head(PastelSkullType.MOOSHROOM_RED));
        h.generateHeadFusion(head(PastelSkullType.HORSE), SecondFusionIngredient.basic(head(PastelSkullType.DONKEY)), head(PastelSkullType.MULE));
        h.generateHeadFusion(head(PastelSkullType.BAT), SecondFusionIngredient.NIGHTDEW, head(PastelSkullType.PHANTOM));
        h.generateHeadFusion(head(PastelSkullType.PIG), SecondFusionIngredient.basic(head(PastelSkullType.PILLAGER)), Items.PIGLIN_HEAD);
        h.generateHeadFusion(head(PastelSkullType.HORSE), SecondFusionIngredient.STORM_STORM, head(PastelSkullType.SKELETON_HORSE));
        h.generateHeadFusion(Items.SKELETON_SKULL, SecondFusionIngredient.FROSTBITE, head(PastelSkullType.STRAY));
        h.generateHeadFusion(head(PastelSkullType.CAMEL), SecondFusionIngredient.INCANDESCENT, head(PastelSkullType.STRIDER));
        h.generateHeadFusion(head(PastelSkullType.VILLAGER), SecondFusionIngredient.STORM_STORM, head(PastelSkullType.WITCH));
        h.generateHeadFusion(Items.SKELETON_SKULL, SecondFusionIngredient.basic(Items.BLAZE_POWDER, 2), Items.WITHER_SKELETON_SKULL);
        h.generateHeadFusion(head(PastelSkullType.HOGLIN), SecondFusionIngredient.basic(Items.ZOMBIE_HEAD), head(PastelSkullType.ZOGLIN));
        h.generateHeadFusion(head(PastelSkullType.HORSE), SecondFusionIngredient.basic(Items.ZOMBIE_HEAD), head(PastelSkullType.ZOMBIE_HORSE));
        h.generateHeadFusion(head(PastelSkullType.VILLAGER), SecondFusionIngredient.basic(Items.ZOMBIE_HEAD), head(PastelSkullType.ZOMBIE_VILLAGER));
        h.generateHeadFusion(Items.PIGLIN_HEAD, SecondFusionIngredient.basic(Items.ZOMBIE_HEAD), head(PastelSkullType.ZOMBIFIED_PIGLIN));

        variantChangingHeadFusion(pfx.subPrefix("variant_changing"));
    }

    private record SecondFusionIngredient(
            ResourceLocation unlock,
            SizedIngredient ingredient

    ) {
        public static SecondFusionIngredient basic(ItemLike input, int count) {
            return new SecondFusionIngredient(PastelAdvancements.Unlocks.HeadFusion.BASIC_RECIPES, SizedIngredient.of(input, count));
        }

        public static SecondFusionIngredient basic(ItemLike input) {
            return basic(input, 1);
        }


        public static final SecondFusionIngredient NIGHTDEW = new SecondFusionIngredient(
                PastelAdvancements.Unlocks.HeadFusion.NIGHTDEW_RECIPES,
                SizedIngredient.of(PastelItems.NIGHTDEW_SPROUT, 2)
        );

        public static final SecondFusionIngredient INCANDESCENT = new SecondFusionIngredient(
                PastelAdvancements.Unlocks.HeadFusion.INCANDESCENT_RECIPES,
                SizedIngredient.of(PastelItems.INCANDESCENT_ESSENCE, 4)
        );

        public static final SecondFusionIngredient FROSTBITE = new SecondFusionIngredient(
                PastelAdvancements.Unlocks.HeadFusion.FROSTBITE_RECIPES,
                SizedIngredient.of(PastelItems.FROSTBITE_ESSENCE, 4)
        );

        public static final SecondFusionIngredient STORM_STORM = new SecondFusionIngredient(
                PastelAdvancements.Unlocks.HeadFusion.STORM_STONE_RECIPES,
                SizedIngredient.of(PastelItems.STORM_STONE, 4)
        );
    }

    private static void variantChangingHeadFusion(PrefixHelper pfx) {
        // Because God Hates Foxes, foxes are more expensive to convert
        var foxHelper = new HeadGenerator(pfx, 4);
        foxHelper.generateHeadFusion(head(PastelSkullType.FOX), SecondFusionIngredient.FROSTBITE, head(PastelSkullType.FOX_ARCTIC));
        foxHelper.generateHeadFusion(head(PastelSkullType.FOX_ARCTIC), SecondFusionIngredient.INCANDESCENT, head(PastelSkullType.FOX));

        var helper = new HeadGenerator(pfx, 1);
        var pigments = PastelItems.PIGMENTS.map(SecondFusionIngredient::basic);

        var axolotlTag = Ingredient.of(PastelItemTags.MobHeads.AXOLOTL_HEADS);
        helper.generateHeadFusion(axolotlTag, pigments.blue(), head(PastelSkullType.AXOLOTL_BLUE));
        // poop axolotl...........
        helper.generateHeadFusion(axolotlTag, pigments.brown(), head(PastelSkullType.AXOLOTL_WILD));
        helper.generateHeadFusion(axolotlTag, pigments.cyan(), head(PastelSkullType.AXOLOTL_CYAN));
        helper.generateHeadFusion(axolotlTag, pigments.yellow(), head(PastelSkullType.AXOLOTL_GOLD));
        helper.generateHeadFusion(axolotlTag, pigments.pink(), head(PastelSkullType.AXOLOTL_LEUCISTIC));

        var parrotTag = Ingredient.of(PastelItemTags.MobHeads.PARROT_HEADS);
        helper.generateHeadFusion(parrotTag, pigments.blue(), head(PastelSkullType.PARROT_BLUE));
        helper.generateHeadFusion(parrotTag, pigments.cyan(), head(PastelSkullType.PARROT_CYAN));
        helper.generateHeadFusion(parrotTag, pigments.gray(), head(PastelSkullType.PARROT_GRAY));
        helper.generateHeadFusion(parrotTag, pigments.green(), head(PastelSkullType.PARROT_GREEN));
        helper.generateHeadFusion(parrotTag, pigments.red(), head(PastelSkullType.PARROT_RED));

        var shulkerTag = Ingredient.of(PastelItemTags.MobHeads.SHULKER_HEADS);
        helper.generateHeadFusion(shulkerTag, pigments.black(), head(PastelSkullType.SHULKER_BLACK));
        helper.generateHeadFusion(shulkerTag, pigments.blue(), head(PastelSkullType.SHULKER_BLUE));
        helper.generateHeadFusion(shulkerTag, pigments.brown(), head(PastelSkullType.SHULKER_BROWN));
        helper.generateHeadFusion(shulkerTag, pigments.cyan(), head(PastelSkullType.SHULKER_CYAN));
        helper.generateHeadFusion(shulkerTag, pigments.gray(), head(PastelSkullType.SHULKER_GRAY));
        helper.generateHeadFusion(shulkerTag, pigments.green(), head(PastelSkullType.SHULKER_GREEN));
        helper.generateHeadFusion(shulkerTag, pigments.lightBlue(), head(PastelSkullType.SHULKER_LIGHT_BLUE));
        helper.generateHeadFusion(shulkerTag, pigments.lightGray(), head(PastelSkullType.SHULKER_LIGHT_GRAY));
        helper.generateHeadFusion(shulkerTag, pigments.lime(), head(PastelSkullType.SHULKER_LIME));
        helper.generateHeadFusion(shulkerTag, pigments.magenta(), head(PastelSkullType.SHULKER_MAGENTA));
        helper.generateHeadFusion(shulkerTag, pigments.orange(), head(PastelSkullType.SHULKER_ORANGE));
        helper.generateHeadFusion(shulkerTag, pigments.pink(), head(PastelSkullType.SHULKER_PINK));
        helper.generateHeadFusion(shulkerTag, pigments.purple(), head(PastelSkullType.SHULKER_PURPLE));
        helper.generateHeadFusion(shulkerTag, pigments.red(), head(PastelSkullType.SHULKER_RED));
        helper.generateHeadFusion(shulkerTag, pigments.white(), head(PastelSkullType.SHULKER_WHITE));
        helper.generateHeadFusion(shulkerTag, pigments.yellow(), head(PastelSkullType.SHULKER_YELLOW));


    }

    private static ItemLike head(PastelSkullType kind) {
        return PastelBlocks.MOB_HEADS.get(kind);
    }

    private record HeadGenerator(
        PrefixHelper pfx,
        int inputVegetal
    ) {
        public void generateHeadFusion(Ingredient head, SecondFusionIngredient second, ItemLike result) {

            var headName = BuiltInRegistries.ITEM.getKey(result.asItem()).getPath().replace("_head", "").replace("_skull", "");

            pfx.generateRecipe(
                    headName,
                    new SpiritInstillerRecipeBuilder(
                            SizedIngredient.of(PastelItems.VEGETAL, inputVegetal),
                            new SizedIngredient(head, 1),
                            second.ingredient,
                            new ItemStack(result)
                    )
                            .craftingTime(800)
                            .experience(4.0f)
                            .requiredAdvancement(second.unlock)
                            .group("head_fusion")
            );
        }
        public void generateHeadFusion(ItemLike head, SecondFusionIngredient second, ItemLike result) {
            generateHeadFusion(Ingredient.of(head), second, result);
        }
    }


}
