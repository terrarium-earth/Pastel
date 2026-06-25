package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.components.MemoryComponent;
import earth.terrarium.pastel.data.recipe.builder.SpiritInstillerRecipeBuilder;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.HardcorePlayerRevivalRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.MemoryToHeadRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.Locale;

public class SpiritInstillerRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "spirit_instiller");

        root(pfx);
        spawner(pfx.subPrefix("spawner"));
        secret(pfx.subPrefix("secret"));
        headFusion(pfx.subPrefix("head_fusion"));
        memories(pfx.subPrefix("memories"));
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

    private static void memories(PrefixHelper pfx) {
        for (MemoryKind kind : MemoryKind.values()) {
            var name = kind.name().toLowerCase(Locale.ROOT);
            var patch =
                    DataComponentPatch.builder()
                        .set(DataComponents.ENTITY_DATA, CustomData.of(kind.extra))
                        .set(PastelDataComponentTypes.MEMORY, new MemoryComponent.Builder(MemoryComponent.DEFAULT).ticksToManifest(kind.timeToManifest).build())
                        .build();
            pfx.generateRecipe(
                    name,
                    new SpiritInstillerRecipeBuilder(
                            SizedIngredient.of(PastelItemTags.MEMORY_BONDING_AGENTS, 4),
                            SizedIngredient.of(kind.head, 1),
                            new SizedIngredient(kind.preferredItems,  1),
                            new ItemStack(PastelBlocks.MEMORY.asItem().builtInRegistryHolder(), 1, patch)
                    )
                            .group("memories")
                            .craftingTime(800)
                            .experience(4.0f)
                            // use memory via mobheads instead...?
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.MEMORIES)
            );
        }
    }


    private enum MemoryKind {
        ALLAY(head(PastelSkullType.ALLAY), Ingredient.of(Items.AMETHYST_SHARD), 4, basic(EntityType.ALLAY)),
        ARMADILLO(head(PastelSkullType.ARMADILLO), Ingredient.of(Items.SPIDER_EYE), 4, basic(EntityType.ARMADILLO)),
        AXOLOTL_BLUE(head(PastelSkullType.AXOLOTL_BLUE), Ingredient.of(Items.WATER_BUCKET), 4, axolotl(4)),
        AXOLOTL_WILD(head(PastelSkullType.AXOLOTL_WILD), Ingredient.of(Items.WATER_BUCKET), 4, axolotl(1)),
        AXOLOTL_CYAN(head(PastelSkullType.AXOLOTL_CYAN), Ingredient.of(Items.WATER_BUCKET), 4, axolotl(3)),
        AXOLOTL_GOLD(head(PastelSkullType.AXOLOTL_GOLD), Ingredient.of(Items.WATER_BUCKET), 4, axolotl(2)),
        AXOLOTL_LEUCISTIC(head(PastelSkullType.AXOLOTL_LEUCISTIC), Ingredient.of(Items.WATER_BUCKET), 4, axolotl(0)),
        BAT(head(PastelSkullType.BAT), Ingredient.of(Items.GLOW_BERRIES), 8, basic(EntityType.BAT)),
        BEE(head(PastelSkullType.BEE), Ingredient.of(Items.HONEYCOMB), 8, basic(EntityType.BEE)),
        BLAZE(head(PastelSkullType.BLAZE), Ingredient.of(Items.BLAZE_ROD), 16, basic(EntityType.BLAZE)),
        BOGGED(head(PastelSkullType.BOGGED), Ingredient.of(Tags.Items.MUSHROOMS), 16, basic(EntityType.BOGGED)),
        BREEZE(head(PastelSkullType.BREEZE), Ingredient.of(Items.FEATHER), 16, basic(EntityType.BREEZE)),
        CAMEL(head(PastelSkullType.CAMEL), Ingredient.of(Items.CACTUS), 8, basic(EntityType.CAMEL)),
        CAT(head(PastelSkullType.CAT), Ingredient.of(Items.STRING), 4, basic(EntityType.CAT)),
        CAVE_SPIDER(head(PastelSkullType.CAVE_SPIDER), Ingredient.of(Items.SPIDER_EYE), 16, basic(EntityType.CAVE_SPIDER)),
        CHICKEN(head(PastelSkullType.CHICKEN), Ingredient.of(Items.WHEAT_SEEDS), 4, basic(EntityType.CHICKEN)),
        COW(head(PastelSkullType.COW), Ingredient.of(Items.WHEAT), 4, basic(EntityType.COW)),
        CREEPER(Items.CREEPER_HEAD, Ingredient.of(Items.GUNPOWDER), 16, basic(EntityType.CREEPER)),
        DOLPHIN(head(PastelSkullType.DOLPHIN), Ingredient.of(ItemTags.FISHES), 4, basic(EntityType.DOLPHIN)),
        DONKEY(head(PastelSkullType.DONKEY), Ingredient.of(Items.WHEAT), 4, basic(EntityType.DONKEY)),
        DROWNED(head(PastelSkullType.DROWNED), Ingredient.of(Items.WATER_BUCKET), 16, basic(EntityType.DROWNED)),
        // skip egg laying wooly pig as it has a seperate unlock...
        // skip elder guardian
        // skip ender dragon
        ENDERMAN(head(PastelSkullType.ENDERMAN), Ingredient.of(Items.ENDER_PEARL), 16, basic(EntityType.ENDERMAN)),
        ENDERMITE(head(PastelSkullType.ENDERMITE), Ingredient.of(Items.ENDER_PEARL), 16, basic(EntityType.ENDERMITE)),
        ERASER(head(PastelSkullType.ERASER), Ingredient.of(Items.SPIDER_EYE), 16, basic(PastelEntityTypes.ERASER.get())),
        EVOKER(head(PastelSkullType.EVOKER), Ingredient.of(Items.WATER_BUCKET), 16, basic(EntityType.EVOKER)),
        FOX(head(PastelSkullType.FOX), Ingredient.of(ItemTags.FOX_FOOD), 8, fox("red")),
        FOX_ARCTIC(head(PastelSkullType.FOX_ARCTIC), Ingredient.of(ItemTags.FOX_FOOD), 8, fox("snow")),
        FROG_COLD(head(PastelSkullType.FROG_COLD), Ingredient.of(Items.SLIME_BALL), 4, frog(FrogVariant.COLD)),
        FROG_TEMPERATE(head(PastelSkullType.FROG_TEMPERATE), Ingredient.of(Items.SLIME_BALL), 4, frog(FrogVariant.TEMPERATE)),
        FROG_WARM(head(PastelSkullType.FROG_WARM), Ingredient.of(Items.SLIME_BALL), 4, frog(FrogVariant.WARM)),
        GHAST(head(PastelSkullType.GHAST), Ingredient.of(Items.PHANTOM_MEMBRANE), 16, basic(EntityType.GHAST)),
        GLOW_SQUID(head(PastelSkullType.GLOW_SQUID), Ingredient.of(Items.WATER_BUCKET), 8, basic(EntityType.GLOW_SQUID)),
        GOAT(head(PastelSkullType.GOAT), Ingredient.of(Items.WHEAT), 8, basic(EntityType.GOAT)),
        GUARDIAN(head(PastelSkullType.GUARDIAN), Ingredient.of(Items.PRISMARINE), 16, basic(EntityType.GUARDIAN)),
        HOGLIN(head(PastelSkullType.HOGLIN), Ingredient.of(Items.CRIMSON_FUNGUS), 8, basic(EntityType.HOGLIN)),
        HORSE(head(PastelSkullType.HORSE), Ingredient.of(Items.HAY_BLOCK), 4, basic(EntityType.HORSE)),
        HUSK(head(PastelSkullType.HUSK), Ingredient.of(Items.ROTTEN_FLESH), 16, basic(EntityType.HUSK)),
        ILLUSIONER(head(PastelSkullType.ILLUSIONER), Ingredient.of(ItemTags.BANNERS), 16, basic(EntityType.ILLUSIONER)),
        IRON_GOLEM(head(PastelSkullType.IRON_GOLEM), Ingredient.of(Items.IRON_BLOCK), 8, basic(EntityType.IRON_GOLEM)),
        // kindling is also seperate...
        // TODO Lizard
        LLAMA(head(PastelSkullType.LLAMA), Ingredient.of(Items.HAY_BLOCK), 4, basic(EntityType.LLAMA)),
        MAGMA_CUBE(head(PastelSkullType.MAGMA_CUBE), Ingredient.of(Items.MAGMA_CREAM), 16, basic(EntityType.MAGMA_CUBE)),
        // TODO Mooshroom
        MULE(head(PastelSkullType.MULE), Ingredient.of(Items.WHEAT), 4, basic(EntityType.MULE)),
        OCELOT(head(PastelSkullType.OCELOT), Ingredient.of(Items.STRING), 4, basic(EntityType.OCELOT)),
        PANDA(head(PastelSkullType.PANDA), Ingredient.of(Items.BAMBOO), 4, basic(EntityType.PANDA)),
        // TODO parrots
        PHANTOM(head(PastelSkullType.PHANTOM), Ingredient.of(Items.PHANTOM_MEMBRANE), 16, basic(EntityType.PHANTOM)),
        PIG(head(PastelSkullType.PIG), Ingredient.of(Items.CARROT), 4, basic(EntityType.PIG)),
        PIGLIN(Items.PIGLIN_HEAD, Ingredient.of(Items.GOLD_INGOT), 8, basic(EntityType.PIGLIN)),
        PIGLIN_BRUTE(head(PastelSkullType.PIGLIN_BRUTE), Ingredient.of(Items.GOLD_BLOCK), 16, basic(EntityType.PIGLIN_BRUTE)),
        PILLAGER(head(PastelSkullType.PILLAGER), Ingredient.of(Tags.Items.GEMS_EMERALD), 8, basic(EntityType.PILLAGER)),
        POLAR_BEAR(head(PastelSkullType.POLAR_BEAR), Ingredient.of(ItemTags.FISHES), 8, basic(EntityType.POLAR_BEAR)),
        PRESERVATION_TURRET(head(PastelSkullType.PRESERVATION_TURRET), Ingredient.of(PastelItems.MOONSTONE_SHARD), 16, basic(PastelEntityTypes.PRESERVATION_TURRET.get())),
        PUFFERFISH(head(PastelSkullType.PUFFERFISH), Ingredient.of(Items.WATER_BUCKET), 4, basic(EntityType.PUFFERFISH)),
        RABBIT(head(PastelSkullType.RABBIT), Ingredient.of(Items.CARROT), 4, basic(EntityType.RABBIT)),
        RAVAGER(head(PastelSkullType.RAVAGER), Ingredient.of(ItemTags.BANNERS), 32, basic(EntityType.RAVAGER)),
        SALMON(head(PastelSkullType.SALMON), Ingredient.of(Items.WATER_BUCKET), 4, basic(EntityType.SALMON)),
        // SHEEP TODO (but it easy)
        // TODO Shulkers
        SILVERFISH(head(PastelSkullType.SILVERFISH), Ingredient.of(Items.STONE_BRICKS), 16, basic(EntityType.SILVERFISH)),
        SKELETON(Items.SKELETON_SKULL, Ingredient.of(Items.BONE), 16, basic(EntityType.SKELETON)),
        SKELETON_HORSE(head(PastelSkullType.SKELETON_HORSE), Ingredient.of(Items.HAY_BLOCK), 16, basic(EntityType.SKELETON_HORSE)),
        SLIME(head(PastelSkullType.SLIME), Ingredient.of(Items.SLIME_BALL), 16, basic(EntityType.SLIME)),
        // dont be sniffphobic
        SNIFFER(head(PastelSkullType.SNIFFER), Ingredient.of(Items.TORCHFLOWER_SEEDS), 16, basic(EntityType.SNIFFER)),
        SNOW_GOLEM(head(PastelSkullType.SNOW_GOLEM), Ingredient.of(Items.SNOWBALL), 8, basic(EntityType.SNOW_GOLEM)),
        SPIDER(head(PastelSkullType.SPIDER), Ingredient.of(Items.SPIDER_EYE), 16, basic(EntityType.SPIDER)),
        SQUID(head(PastelSkullType.SQUID), Ingredient.of(Items.WATER_BUCKET), 4, basic(EntityType.SQUID)),
        STRAY(head(PastelSkullType.STRAY), Ingredient.of(Items.ROTTEN_FLESH), 16, basic(EntityType.STRAY)),
        STRIDER(head(PastelSkullType.STRIDER), Ingredient.of(Items.WARPED_FUNGUS), 8, basic(EntityType.STRIDER)),
        TADPOLE(head(PastelSkullType.TADPOLE), Ingredient.of(Items.SLIME_BALL), 8, basic(EntityType.TADPOLE)),
        TROPICAL_FISH(head(PastelSkullType.TROPICAL_FISH), Ingredient.of(Items.WATER_BUCKET), 8, basic(EntityType.TROPICAL_FISH)),
        TURTLE(head(PastelSkullType.TURTLE), Ingredient.of(Items.SEAGRASS), 4, basic(EntityType.TURTLE)),
        VEX(head(PastelSkullType.VEX), Ingredient.of(ItemTags.BANNERS), 16, basic(EntityType.VEX)),
        VILLAGER(head(PastelSkullType.VILLAGER), Ingredient.of(Items.EMERALD), 32, basic(EntityType.VILLAGER)),
        VINDICATOR(head(PastelSkullType.VINDICATOR), Ingredient.of(ItemTags.BANNERS), 16, basic(EntityType.VINDICATOR)),
        WANDERING_TRADER(head(PastelSkullType.WANDERING_TRADER), Ingredient.of(Items.EMERALD), 4, basic(EntityType.WANDERING_TRADER)),
        // skip warden in here
        // skip wither
        WITHER_SKELETON(Items.WITHER_SKELETON_SKULL, Ingredient.of(Items.COAL), 16, basic(EntityType.WITHER_SKELETON)),
        WOLF(head(PastelSkullType.WOLF), Ingredient.of(Items.BONE), 8, basic(EntityType.WOLF)),
        ZOGLIN(head(PastelSkullType.ZOGLIN), Ingredient.of(Items.ROTTEN_FLESH), 16, basic(EntityType.ZOGLIN)),
        ZOMBIE(Items.ZOMBIE_HEAD, Ingredient.of(Items.ROTTEN_FLESH), 16, basic(EntityType.ZOMBIE)),
        ZOMBIE_HORSE(head(PastelSkullType.ZOMBIE_HORSE), Ingredient.of(Items.HAY_BLOCK), 16, basic(EntityType.ZOMBIE_HORSE)),
        ZOMBIE_VILLAGER(head(PastelSkullType.ZOMBIE_VILLAGER), Ingredient.of(Items.ROTTEN_FLESH), 32, basic(EntityType.ZOMBIE_VILLAGER)),
        ZOMBIFIED_PIGLIN(head(PastelSkullType.ZOMBIFIED_PIGLIN), Ingredient.of(Items.ROTTEN_FLESH), 16, basic(EntityType.ZOMBIFIED_PIGLIN))
        ;

        final Item head;
        final Ingredient preferredItems;
        final int timeToManifest;
        final CompoundTag extra;

        private MemoryKind(ItemLike head, Ingredient preferredItems, int timeToManifest, CompoundTag tag) {
            this.head = head.asItem();
            this.preferredItems = preferredItems;
            this.timeToManifest = timeToManifest;
            this.extra = tag;
        }

        private static CompoundTag basic(EntityType<?> kind) {
            var id = EntityType.getKey(kind);
            var tag = new CompoundTag();
            tag.putString("id", id.toString());
            return tag;
        }
        // ???
        private static CompoundTag axolotl(int variant) {
            var tag = basic(EntityType.AXOLOTL);
            tag.putInt("Variant", variant);
            return tag;
        }

        private static CompoundTag fox(String name) {
            var tag = basic(EntityType.FOX);
            tag.putString("Type", name);
            return tag;
        }

        private static CompoundTag frog(ResourceKey<FrogVariant> variant) {
            var tag = basic(EntityType.FROG);
            tag.putString("variant", variant.location().toString());
            return tag;
        }
    }


}
