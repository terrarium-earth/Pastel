package earth.terrarium.pastel.blocks;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.sound.WorldAttenuation;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TeaTable extends Block implements EntityBlock {
    // Please for the love of all that is sacred, someone find a better way to do this
    public static final ResourceLocation[] upToOnyx = new ResourceLocation[]{
        PastelAdvancements.COLLECT_RADIATING_ENDER, PastelAdvancements.ENTER_ENDER_GLASS,
        PastelAdvancements.BREAK_CRACKED_DRAGONBONE, PastelAdvancements.REMEMBER_KINDLING,
        PastelAdvancements.ASCEND_KINDLING, PastelAdvancements.PLUCK_RESPLENDENT_FEATHER,
        PastelAdvancements.COLLECT_ANY_BASIC_SHARD, PastelAdvancements.COLLECT_ALL_BASIC_SHARDS,
        PastelAdvancements.BREAK_GEMSTONE_ORE, PastelAdvancements.CRAFT_USING_PEDESTAL,
        PastelAdvancements.COLLECT_GEMSTONE_POWDER, PastelAdvancements.COLLECT_MERMAIDS_GEM,
        PastelAdvancements.FISH_AN_ENTITY, PastelAdvancements.FAIL_TO_TAKE_ITEM_OUT_OF_PEDESTAL,
        PastelAdvancements.PLACE_PEDESTAL, PastelAdvancements.GIFT_WET_LAVA_SPONGE,
        PastelAdvancements.START_CRAFTING_TIME_CONSUMING_PEDESTAL_RECIPE, PastelAdvancements.CRAFT_CMY_PEDESTAL,
        PastelAdvancements.COLLECT_QUITOXIC_REEDS, PastelAdvancements.USE_OBLIVION_PICKAXE_WITH_EFFICIENCY,
        PastelAdvancements.FILL_BOTTOMLESS_BUNDLE, PastelAdvancements.FILL_BOTTOMLESS_BUNDLE_FOR_REAL_THIS_TIME,
        PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE,
        PastelAdvancements.GET_HIT_WHILE_WEARING_FULL_SUIT_OF_GEMSTONE_ARMOR, PastelAdvancements.DARK_RADIANCE_STAFF,
        PastelAdvancements.COLLECT_SHIMMERSTONE, PastelAdvancements.GIFT_OR_OPEN_PRESENT,
        PastelAdvancements.CRAFT_BOTTLE_OF_FADING, PastelAdvancements.COLLECT_VEGETAL,
        PastelAdvancements.USE_GLISTERING_MELON_SEEDS, PastelAdvancements.COLLECT_AMARANTH,
        PastelAdvancements.COLLECT_AMARANTH_BUSHEL, PastelAdvancements.DRINK_TEA_WITH_SCONE,
        PastelAdvancements.CRAFT_SALTED_JARAMEL_TRIFLE_OR_TART, PastelAdvancements.EAT_EACH_PASTRY,
        PastelAdvancements.CONSUME_ENCHANTED_STAR_CANDY, PastelAdvancements.CRAFT_COLORED_SAPLING,
        PastelAdvancements.COLLECT_PIGMENT, PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER,
        PastelAdvancements.COLLECT_ALL_BASIC_PIGMENTS_BESIDES_BROWN, PastelAdvancements.REVIVE_DEAD_BUSH,
        PastelAdvancements.COLLECT_STAR_FRAGMENT, PastelAdvancements.CATCH_SHOOTING_STAR,
        PastelAdvancements.COLLECT_ALL_SHOOTING_STAR_VARIANTS, PastelAdvancements.TAP_TITRATION_BARREL,
        PastelAdvancements.TAP_POISONOUS_VODKA, PastelAdvancements.TAP_PURE_ALCOHOL, PastelAdvancements.TAP_AGED_AIR,
        PastelAdvancements.PLACE_INCANDESCENT_AMALGAM, PastelAdvancements.SURVIVE_DRINKING_INCANDESCENT_AMALGAM,
        PastelAdvancements.TAP_MERMAIDS_JAM, PastelAdvancements.TAP_SUSPICIOUS_BREW, PastelAdvancements.FISH_IN_LAVA,
        PastelAdvancements.HOOK_ENTITY_WITH_MOLTEN_ROD, PastelAdvancements.BUILD_FUSION_SHRINE,
        PastelAdvancements.COLLECT_NIGHTDEW, PastelAdvancements.FIND_PRESERVATION_RUINS,
        PastelAdvancements.FAIL_TO_GLITCH_INTO_PRESERVATION_RUIN,
        PastelAdvancements.ENTER_WIRELESS_REDSTONE_PRESERVATION_RUIN,
        PastelAdvancements.SOLVE_WIRELESS_REDSTONE_PRESERVATION_RUIN,
        PastelAdvancements.ENTER_DIKE_GATE_PRESERVATION_RUIN, PastelAdvancements.SOLVE_DIKE_GATE_PRESERVATION_RUIN,
        PastelAdvancements.ENTER_COLOR_MIXING_PRESERVATION_RUIN,
        PastelAdvancements.SOLVE_COLOR_MIXING_PRESERVATION_RUIN,
        PastelAdvancements.PLACE_MOONSTONE_IN_PRESERVATION_RUINS,
        PastelAdvancements.Unlocks.ColoredLamps.LIGHT_GRAY_LAMP, PastelAdvancements.Unlocks.ColoredLamps.GRAY_LAMP,
        PastelAdvancements.Unlocks.ColoredLamps.GREEN_LAMP, PastelAdvancements.Unlocks.ColoredLamps.ANY_COLORED_LAMP,
        PastelAdvancements.Unlocks.ColoredLamps.YELLOW_LAMP, PastelAdvancements.Unlocks.ColoredLamps.PINK_LAMP,
        PastelAdvancements.Unlocks.ColoredLamps.BLACK_LAMP, PastelAdvancements.Unlocks.ColoredLamps.LIGHT_BLUE_LAMP,
        PastelAdvancements.Unlocks.ColoredLamps.ORANGE_LAMP, PastelAdvancements.Unlocks.ColoredLamps.CYAN_LAMP,
        PastelAdvancements.Unlocks.ColoredLamps.MAGENTA_LAMP, PastelAdvancements.Unlocks.Equipment.TENDER_PICKAXE,
        PastelAdvancements.Unlocks.Equipment.LAGOON_ROD, PastelAdvancements.Unlocks.Equipment.LUCKY_PICKAXE,
        PastelAdvancements.Unlocks.Equipment.RAZOR_FALCHION, PastelAdvancements.Unlocks.Equipment.MOLTEN_ROD,
        PastelAdvancements.Unlocks.Equipment.GEMSTONE_ARMOR_CATEGORY,
        PastelAdvancements.Unlocks.Equipment.ANY_PREENCHANTED_TOOL,
        PastelAdvancements.Unlocks.Equipment.OBLIVION_PICKAXE, PastelAdvancements.Unlocks.Blocks.RADIANT_GLASS,
        PastelAdvancements.Unlocks.Blocks.CMY_PEDESTAL, PastelAdvancements.Unlocks.Blocks.COLORED_SPORE_BLOSSOMS,
        PastelAdvancements.Unlocks.Blocks.ETHEREAL_PLATFORM, PastelAdvancements.Unlocks.Blocks.HEARTBOUND_CHEST,
        PastelAdvancements.Unlocks.Blocks.SHIMMERSTONE_LIGHTS, PastelAdvancements.Unlocks.Blocks.BLOCK_FLOODER,
        PastelAdvancements.Unlocks.Blocks.LAVA_SPONGE, PastelAdvancements.Unlocks.Blocks.ITEM_ROUNDEL,
        PastelAdvancements.Unlocks.Blocks.COMPACTING_CHEST, PastelAdvancements.Unlocks.Blocks.ENDER_GLASS,
        PastelAdvancements.Unlocks.Blocks.TITRATION_BARREL, PastelAdvancements.Unlocks.Blocks.ENDER_BLOCKS,
        PastelAdvancements.Unlocks.Blocks.PRESENT, PastelAdvancements.Unlocks.Blocks.FUSION_SHRINE,
        PastelAdvancements.Unlocks.Trinkets.GLOW_VISION_GOGGLES, PastelAdvancements.Hidden.CollectVanilla.QUARTZ,
        PastelAdvancements.Hidden.CollectVanilla.ENDER_EYE, PastelAdvancements.Hidden.CollectVanilla.DRAGON_BREATH,
        PastelAdvancements.Hidden.CollectVanilla.ECHO_SHARD, PastelAdvancements.Hidden.CollectVanilla.PHANTOM_MEMBRANE,
        PastelAdvancements.Hidden.CollectVanilla.GLOW_LICHEN,
        PastelAdvancements.Hidden.CollectVanilla.WITHER_SKELETON_SKULL,
        PastelAdvancements.Hidden.CollectVanilla.GHAST_TEAR, PastelAdvancements.Hidden.CollectVanilla.GLOWSTONE_DUST,
        PastelAdvancements.Hidden.CollectVanilla.NETHERITE_SCRAP,
        PastelAdvancements.Hidden.CollectVanilla.NAUTILUS_SHELL, PastelAdvancements.Hidden.CollectVanilla.NETHER_STAR,
        PastelAdvancements.Hidden.EntityInteract.KINDLING, PastelAdvancements.Hidden.CollectPigment.LIME,
        PastelAdvancements.Hidden.CollectPigment.MAGENTA, PastelAdvancements.Hidden.CollectPigment.PINK,
        PastelAdvancements.Hidden.CollectPigment.YELLOW, PastelAdvancements.Hidden.CollectPigment.CYAN,
        PastelAdvancements.Hidden.CollectPigment.PURPLE, PastelAdvancements.Hidden.CollectPigment.GREEN,
        PastelAdvancements.Hidden.CollectPigment.RED, PastelAdvancements.Hidden.CollectPigment.ORANGE,
        PastelAdvancements.Hidden.CollectPigment.LIGHT_BLUE, PastelAdvancements.Hidden.CollectPigment.BLUE,
        PastelAdvancements.Hidden.CollectShards.TOPAZ, PastelAdvancements.Hidden.CollectShards.AMETHYST,
        PastelAdvancements.Hidden.CollectShards.CITRINE, PastelAdvancements.Hidden.COLLECT_STARDUST,
        PastelAdvancements.Hidden.COLLECT_PRIMORDIAL_TORCH, PastelAdvancements.Hidden.COLLECT_CRAWFISH,
        PastelAdvancements.Hidden.COLLECT_WEEPING_GALA, PastelAdvancements.Hidden.BREAK_NIGHTDEW_VINE,
        PastelAdvancements.Unlocks.Pylons.ANY_PYLON, PastelAdvancements.Unlocks.Pylons.TOPAZ_PYLON,
        PastelAdvancements.Unlocks.Pylons.AMETHYST_PYLON, PastelAdvancements.Unlocks.Pylons.CITRINE_PYLON,
        PastelAdvancements.Unlocks.Items.RADIANCE_STAFF, PastelAdvancements.Unlocks.Items.NATURES_STAFF,
        PastelAdvancements.Unlocks.Items.BOTTLE_OF_FADING, PastelAdvancements.Unlocks.Items.ENCHANTMENT_CANVAS,
        PastelAdvancements.Unlocks.Items.PHANTOM_FRAMES, PastelAdvancements.Unlocks.Items.ARTISANS_ATLAS,
        PastelAdvancements.Unlocks.Items.CRAFTING_TABLET, PastelAdvancements.Unlocks.Items.BAG_OF_HOLDING,
        PastelAdvancements.Unlocks.Items.BOTTOMLESS_BUNDLE, PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM,
        PastelAdvancements.Unlocks.GemstoneLights.TOPAZ, PastelAdvancements.Unlocks.GemstoneLights.AMETHYST,
        PastelAdvancements.Unlocks.GemstoneLights.CITRINE, PastelAdvancements.Unlocks.GemstoneLights.ANY,
        PastelAdvancements.Unlocks.Enchantments.VOIDING_USAGE, PastelAdvancements.Unlocks.Weather.RAIN,
        PastelAdvancements.Unlocks.Weather.CLEAR, PastelAdvancements.Unlocks.Glowblocks.ANY_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.LIME_GLOWBLOCK, PastelAdvancements.Unlocks.Glowblocks.YELLOW_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.PURPLE_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.LIGHT_BLUE_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.ORANGE_GLOWBLOCK, PastelAdvancements.Unlocks.Glowblocks.GREEN_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.PINK_GLOWBLOCK, PastelAdvancements.Unlocks.Glowblocks.RED_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.CYAN_GLOWBLOCK, PastelAdvancements.Unlocks.Glowblocks.BLUE_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.MAGENTA_GLOWBLOCK, PastelAdvancements.Unlocks.Resources.FERTILIZER,
        PastelAdvancements.Unlocks.Resources.NETHERITE_INGOT, PastelAdvancements.Unlocks.Redstone.BLOCK_PLACER,
        PastelAdvancements.Unlocks.Redstone.REDSTONE_TIMER, PastelAdvancements.Unlocks.Redstone.ITEM_DETECTOR,
        PastelAdvancements.Unlocks.Redstone.REDSTONE_SAND, PastelAdvancements.Unlocks.Redstone.WEATHER_DETECTOR,
        PastelAdvancements.Unlocks.Redstone.LIGHT_LEVEL_DETECTOR,
        PastelAdvancements.Unlocks.Redstone.REDSTONE_CALCULATOR, PastelAdvancements.Unlocks.Food.BODACIOUS_BERRY_BAR,
        PastelAdvancements.Unlocks.Food.TRIPLE_MEAT_POT_PIE, PastelAdvancements.Unlocks.Food.STAR_CANDY,
        PastelAdvancements.Unlocks.Food.CAMOMILLESQUE, PastelAdvancements.Unlocks.Food.TARTS,
        PastelAdvancements.Unlocks.Food.LUCKY_ROLL, PastelAdvancements.Unlocks.Food.REPRISE,
        PastelAdvancements.Unlocks.Food.SUSPICIOUS_BREW, PastelAdvancements.Unlocks.Food.CHEONG,
        PastelAdvancements.Unlocks.Food.GIN, PastelAdvancements.Unlocks.Food.MINT_BEVERAGES,
        PastelAdvancements.Unlocks.ColoredSaplings.GREEN_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.LIGHT_BLUE_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.CYAN_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.LIME_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.MAGENTA_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.YELLOW_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.ORANGE_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.PURPLE_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.RED_SAPLING, PastelAdvancements.Unlocks.ColoredSaplings.BLUE_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.PINK_SAPLING, PastelAdvancements.Milestones.REVEAL_COLORED_TREES_CMY,
        PastelAdvancements.Milestones.UNLOCK_SHOOTING_STARS, PastelAdvancements.Milestones.REVEAL_MERMAIDS_BRUSH,
        PastelAdvancements.Milestones.REVEAL_SHIMMERSTONE, PastelAdvancements.Milestones.REVEAL_QUITOXIC_REEDS,
        PastelAdvancements.Milestones.REVEAL_COLORED_SAPLINGS_CMY, PastelAdvancements.Milestones.REVEAL_DRAGONBONE,
        PastelAdvancements.Milestones.REVEAL_AMARANTH, PastelAdvancements.Milestones.REVEAL_FOUR_LEAF_CLOVER,
        PastelAdvancements.Milestones.REVEAL_RADIATING_ENDER, PastelAdvancements.Hidden.StatusEffects.SWIFTNESS,
        PastelAdvancements.Hidden.StatusEffects.CALMING, PastelAdvancements.Hidden.StatusEffects.SOMNOLENCE,
        PastelAdvancements.Hidden.StatusEffects.DEADLY_POISON, PastelAdvancements.Hidden.StatusEffects.ANOTHER_ROLL,
        PastelAdvancements.Hidden.StatusEffects.LAVA_GLIDING, PastelAdvancements.Hidden.StatusEffects.ANY_SLEEP,
        };

    public static final ResourceLocation[] onyxToAbberation = new ResourceLocation[]{
        PastelAdvancements.CRAFT_ONYX_PEDESTAL, PastelAdvancements.Unlocks.HeadFusion.STORM_STONE_RECIPES,
        PastelAdvancements.Unlocks.HeadFusion.BASIC_RECIPES, PastelAdvancements.Unlocks.HeadFusion.FROSTBITE_RECIPES,
        PastelAdvancements.Unlocks.HeadFusion.NIGHTDEW_RECIPES,
        PastelAdvancements.Unlocks.HeadFusion.INCANDESCENT_RECIPES, PastelAdvancements.Unlocks.Equipment.KNOTTED_SWORD,
        PastelAdvancements.Unlocks.Blocks.MEMORIES, PastelAdvancements.Unlocks.Blocks.KINDLING_MEMORY,
        PastelAdvancements.Unlocks.Blocks.MEMORIES_VIA_MOB_HEAD, PastelAdvancements.Unlocks.Blocks.MIDNIGHT_ABERRATION,
        PastelAdvancements.Unlocks.Blocks.BLACK_HOLE_CHEST, PastelAdvancements.Unlocks.Blocks.CINDERHEARTH,
        PastelAdvancements.Unlocks.Blocks.BUDDING_ONYX, PastelAdvancements.Unlocks.Blocks.FABRICATION_CHEST,
        PastelAdvancements.Unlocks.Blocks.PASTEL_NETWORK, PastelAdvancements.Unlocks.Blocks.ENCHANTER,
        PastelAdvancements.Unlocks.Blocks.SPIRIT_INSTILLER, PastelAdvancements.Unlocks.Blocks.ITEM_BOWL,
        PastelAdvancements.Unlocks.Blocks.LIQUID_CRYSTAL, PastelAdvancements.Unlocks.Blocks.MOB_HEADS,
        PastelAdvancements.Unlocks.Blocks.IDOLS, PastelAdvancements.Unlocks.Blocks.POTION_WORKSHOP,
        PastelAdvancements.Unlocks.Blocks.PARTICLE_SPAWNER, PastelAdvancements.Unlocks.Blocks.CRYSTAL_APOTHECARY,
        PastelAdvancements.Unlocks.Blocks.COLOR_PICKER, PastelAdvancements.Unlocks.Trinkets.LESSER_POTION_PENDANT,
        PastelAdvancements.Unlocks.Trinkets.FANCIFUL_PENDANT, PastelAdvancements.Unlocks.Trinkets.PIGMENT_PALETTE,
        PastelAdvancements.Unlocks.Trinkets.FANCIFUL_TUFF_RING, PastelAdvancements.Unlocks.Trinkets.WHISPY_CIRCLET,
        PastelAdvancements.Unlocks.Trinkets.JEOPARDANT, PastelAdvancements.Unlocks.Trinkets.SHIELDGRASP_AMULET,
        PastelAdvancements.Unlocks.Trinkets.RING_OF_DENSER_STEPS,
        PastelAdvancements.Unlocks.Trinkets.SEVEN_LEAGUE_BOOTS, PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_BELT,
        PastelAdvancements.Unlocks.Trinkets.ANY_BASE_TRINKET,
        PastelAdvancements.Unlocks.Trinkets.ANY_AZURE_DIKE_EQUIPMENT, PastelAdvancements.Unlocks.Trinkets.TOTEM_PENDANT,
        PastelAdvancements.Unlocks.Trinkets.GLEAMING_PIN, PastelAdvancements.Unlocks.Trinkets.GLOVES_OF_DAWNS_GRASP,
        PastelAdvancements.Unlocks.Trinkets.HEARTSINGERS_REWARD, PastelAdvancements.Unlocks.Trinkets.FANCIFUL_GLOVES,
        PastelAdvancements.Unlocks.Trinkets.COTTON_CLOUD_BOOTS, PastelAdvancements.Unlocks.Trinkets.FANCIFUL_BELT,
        PastelAdvancements.Unlocks.Trinkets.TAKE_OFF_BELT, PastelAdvancements.Unlocks.Trinkets.WEEPING_CIRCLET,
        PastelAdvancements.Unlocks.Trinkets.FANCIFUL_CIRCLET, PastelAdvancements.Unlocks.Trinkets.RADIANCE_PIN,
        PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_RING, PastelAdvancements.Unlocks.Trinkets.ASHEN_CIRCLET,
        PastelAdvancements.Unlocks.MEMORY_TO_HEAD, PastelAdvancements.Hidden.CollectPigment.BROWN,
        PastelAdvancements.Hidden.CollectPigment.BLACK, PastelAdvancements.Hidden.COLLECT_ANY_CRYSTALLIZED_BLOCK,
        PastelAdvancements.Hidden.COLLECT_ALL_JADE_VINE_PRODUCTS, PastelAdvancements.Hidden.COLLECT_KOI,
        PastelAdvancements.Hidden.GET_SUBSTANTIAL_AZURE_DIKE_CHARGE, PastelAdvancements.Hidden.COLLECT_STRATINE_GEM,
        PastelAdvancements.Hidden.COLLECT_FROSTBITE_CRYSTAL,
        PastelAdvancements.Hidden.COLLECT_HIBERNATING_JADE_VINE_BULB, PastelAdvancements.Hidden.COLLECT_GILDED_BOOK,
        PastelAdvancements.Hidden.INSTILLED_GERMINATED_JADE_VINE_BULB, PastelAdvancements.Hidden.UNLOCK_EXPLARD_HINT,
        PastelAdvancements.Hidden.COLLECT_BLAZING_CRYSTAL, PastelAdvancements.Midgame.COLLECT_RESONANT_LILY,
        PastelAdvancements.Midgame.COLLECT_MOB_HEAD_USING_TREASURE_HUNTER,
        PastelAdvancements.Midgame.CREATE_PASTEL_NETWORK, PastelAdvancements.Midgame.POTION_MASTERY,
        PastelAdvancements.Midgame.SMELT_IN_CINDERHEARTH_WITH_YIELD_UPGRADES,
        PastelAdvancements.Midgame.CARRY_TOO_MANY_HEAVY_GRAVITY_BLOCKS,
        PastelAdvancements.Midgame.FILL_KNOWLEDGE_GEM_FOR_REAL_THIS_TIME,
        PastelAdvancements.Midgame.BUILD_CINDERHEARTH_STRUCTURE_WITHOUT_LAVA,
        PastelAdvancements.Midgame.CRAFT_SOMETHING_BROWN, PastelAdvancements.Midgame.BUILD_WITHER_USING_WITHER_HEADS,
        PastelAdvancements.Midgame.CRAFT_PIGMENT_PALETTE, PastelAdvancements.Midgame.DRINK_TEA_WITH_MILK,
        PastelAdvancements.Midgame.ENTER_LIQUID_CRYSTAL, PastelAdvancements.Midgame.BUILD_SPIRIT_INSTILLER_STRUCTURE,
        PastelAdvancements.Midgame.FILL_POTION_PENDANT, PastelAdvancements.Midgame.CRAFT_BLACKLISTED_MEMORY_FAIL,
        PastelAdvancements.Midgame.FILL_PIGMENT_PALETTE, PastelAdvancements.Midgame.BUILD_CINDERHEARTH_STRUCTURE,
        PastelAdvancements.Midgame.USE_ALL_PEDESTAL_UPGRADES,
        PastelAdvancements.Midgame.COLLECT_WITHER_HEAD_USING_TREASURE_HUNTER,
        PastelAdvancements.Midgame.COLLECT_WARDEN_HEAD_USING_TREASURE_HUNTER,
        PastelAdvancements.Midgame.MANIFEST_MEMORY, PastelAdvancements.Midgame.UPGRADE_PASTEL_NODE,
        PastelAdvancements.Midgame.CRAFT_BOTTLE_OF_DECAY_AWAY, PastelAdvancements.Midgame.CRAFT_AND_GET_25_XP,
        PastelAdvancements.Midgame.CREATE_HUGE_SLIME, PastelAdvancements.Midgame.UPGRADE_PASTEL_NODE_THROUGHPUT,
        PastelAdvancements.Midgame.DRINK_JADE_WINE, PastelAdvancements.Midgame.FILL_INK_CONTAINER,
        PastelAdvancements.Midgame.FISH_CHARGED_CREEPER, PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP,
        PastelAdvancements.Midgame.CREATE_BIG_PASTEL_NETWORK, PastelAdvancements.Midgame.COLLECT_AZURITE,
        PastelAdvancements.Midgame.UPGRADE_PASTEL_NODE_LAMP,
        PastelAdvancements.Midgame.DIP_SOMETHING_INTO_LIQUID_CRYSTAL, PastelAdvancements.Midgame.COLLECT_ALL_MOB_HEADS,
        PastelAdvancements.Midgame.CRAFT_SEMI_PERMEABLE_GLASS,
        PastelAdvancements.Midgame.COLLECT_GEMSTONE_SHARD_USING_CRYSTAL_APOTHECARY,
        PastelAdvancements.Midgame.HIGH_JUMP_WITH_TAKE_OFF_BELT, PastelAdvancements.Midgame.COLLECT_STORM_STONE,
        PastelAdvancements.Midgame.GROW_AZURITE_IN_CRYSTALLARIEUM, PastelAdvancements.Midgame.PLACE_COLOR_PICKER,
        PastelAdvancements.Midgame.FILL_KNOWLEDGE_GEM, PastelAdvancements.Midgame.TAP_CHRYSOCOLLA,
        PastelAdvancements.Midgame.COLLECT_NEOLITH, PastelAdvancements.Midgame.GET_AZURE_DIKE_CHARGE,
        PastelAdvancements.Midgame.REMEMBER_EGG_LAYING_WOOLY_PIG,
        PastelAdvancements.Midgame.CRAFT_BLACKLISTED_MEMORY_SUCCESS,
        PastelAdvancements.Midgame.BUILD_ENCHANTING_STRUCTURE,
        PastelAdvancements.Midgame.KILL_ENTITY_WITH_JEOPARDANT_AND_HALF_A_HEART,
        PastelAdvancements.Midgame.BUILD_ADVANCED_PEDESTAL_STRUCTURE,
        PastelAdvancements.Midgame.USE_DECAY_AWAY_ON_SCULK, PastelAdvancements.Midgame.HARVEST_MOONSTRUCK_NECTAR,
        PastelAdvancements.Midgame.CREATE_BUDDING_ONYX, PastelAdvancements.Midgame.CRAFT_BOTTLE_OF_FAILING,
        PastelAdvancements.Midgame.COLLECT_ENDER_DRAGON_HEAD_USING_TREASURE_HUNTER,
        PastelAdvancements.Midgame.COLLECT_STRATINE, PastelAdvancements.Midgame.TAKE_OFF_BELT_OVERCHARGED,
        PastelAdvancements.Midgame.BREW_NO_POTION_IN_POTION_WORKSHOP,
        PastelAdvancements.Midgame.TAP_SWEETENED_JADE_WINE, PastelAdvancements.Midgame.PLANT_JADE_VINES,
        PastelAdvancements.Midgame.BREW_LASTING_POTION_IN_POTION_WORKSHOP,
        PastelAdvancements.Midgame.COLLECT_JADE_JELLY,
        PastelAdvancements.Midgame.GET_TOOL_WITH_INERTIA_500_BLOCKS_BROKEN,
        PastelAdvancements.Midgame.KILL_ENTITY_WITH_INK_PROJECTILE,
        PastelAdvancements.Midgame.BREW_POWERFUL_POTION_IN_POTION_WORKSHOP, PastelAdvancements.Midgame.ENCHANT_BOOK,
        PastelAdvancements.Midgame.BREW_POTION_WITH_FOUR_EFFECTS_IN_POTION_WORKSHOP,
        PastelAdvancements.Midgame.ENCHANT_SEVEN_LEAGUE_BOOTS,
        PastelAdvancements.Midgame.CREATE_PASTEL_NETWORK_USING_EVERY_NODE_TYPE,
        PastelAdvancements.Midgame.ENCHANT_ITEM_WORTH_2500_XP,
        PastelAdvancements.Midgame.SMELT_PURE_RESOURCE_IN_CINDERHEARTH, PastelAdvancements.Unlocks.Pylons.ONYX_PYLON,
        PastelAdvancements.Unlocks.Items.NIGHT_SALTS, PastelAdvancements.Unlocks.Items.DRAGON_BREATH,
        PastelAdvancements.Unlocks.Items.BASIC_INK_STORAGE_ITEMS, PastelAdvancements.Unlocks.Items.ENDER_SPLICE,
        PastelAdvancements.Unlocks.Items.INK_ASSORTMENT, PastelAdvancements.Unlocks.Items.KNOWLEDGE_GEM,
        PastelAdvancements.Unlocks.Items.CONSTRUCTORS_STAFF, PastelAdvancements.Unlocks.Items.EXPERIENCE_BOTTLE,
        PastelAdvancements.Unlocks.Items.BOTTLE_OF_FAILING, PastelAdvancements.Unlocks.Items.CRESCENT_CLOCK,
        PastelAdvancements.Unlocks.Items.BOTTLE_OF_DECAY_AWAY, PastelAdvancements.Unlocks.GemstoneLights.ONYX,
        PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED, PastelAdvancements.Unlocks.Upgrades.UPGRADE_EXPERIENCE,
        PastelAdvancements.Unlocks.Upgrades.UPGRADE_EFFICIENCY, PastelAdvancements.Unlocks.GemstoneChimes.ONYX_CHIME,
        PastelAdvancements.Unlocks.GemstoneChimes.TOPAZ_CHIME, PastelAdvancements.Unlocks.GemstoneChimes.ANY_CHIME,
        PastelAdvancements.Unlocks.GemstoneChimes.AMETHYST_CHIME,
        PastelAdvancements.Unlocks.GemstoneChimes.CITRINE_CHIME,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL_TREASURE,
        PastelAdvancements.Unlocks.Enchantments.STEADFAST, PastelAdvancements.Unlocks.Enchantments.INERTIA,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER_LUCK,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_SILK_TOUCH,
        PastelAdvancements.Unlocks.Enchantments.CLOVERS_FAVOR, PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL,
        PastelAdvancements.Unlocks.Enchantments.FOUNDRY, PastelAdvancements.Unlocks.Enchantments.TIGHT_GRIP,
        PastelAdvancements.Unlocks.Enchantments.DISARMING, PastelAdvancements.Unlocks.Enchantments.TREASURE_HUNTER,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_LUCK,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE_INFINITY,
        PastelAdvancements.Unlocks.Enchantments.IMPROVED_CRITICAL,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT_CHANNELING,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_SWIFT_SNEAK,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER, PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_QUITOXIC,
        PastelAdvancements.Unlocks.Enchantments.INVENTORY_INSERTION, PastelAdvancements.Unlocks.Enchantments.SNIPING,
        PastelAdvancements.Unlocks.Enchantments.BIG_CATCH, PastelAdvancements.Unlocks.Enchantments.FIRST_STRIKE,
        PastelAdvancements.Unlocks.Enchantments.EXUBERANCE, PastelAdvancements.Unlocks.Enchantments.VOIDING_CRAFTING,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE, PastelAdvancements.Unlocks.Weather.THUNDER,
        PastelAdvancements.Unlocks.Potions.WEAK_SLEEP_EFFECTS, PastelAdvancements.Unlocks.Potions.DARKNESS,
        PastelAdvancements.Unlocks.Potions.DEADLY_POISON, PastelAdvancements.Unlocks.Potions.LUCK,
        PastelAdvancements.Unlocks.Potions.SCARRED, PastelAdvancements.Unlocks.Glowblocks.BLACK_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.BROWN_GLOWBLOCK, PastelAdvancements.Unlocks.Redstone.BLOCK_DETECTOR,
        PastelAdvancements.Unlocks.Redstone.PLAYER_DETECTOR, PastelAdvancements.Unlocks.Food.AZALEA_TEA,
        PastelAdvancements.Unlocks.Food.RESTORATION_TEA, PastelAdvancements.Unlocks.Food.KARAK_CHAI,
        PastelAdvancements.Unlocks.Food.JADE_WINE, PastelAdvancements.Unlocks.Food.JARAMEL,
        PastelAdvancements.Unlocks.Food.VERDIGRIS_WINE, PastelAdvancements.Unlocks.Food.TRIFLES,
        PastelAdvancements.Unlocks.Food.MERMAIDS_POPCORN, PastelAdvancements.Unlocks.Food.GLISTERING_JELLY_TEA,
        PastelAdvancements.Unlocks.ColoredSaplings.BROWN_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.BLACK_SAPLING, PastelAdvancements.Milestones.REVEAL_STORM_STONES,
        PastelAdvancements.Milestones.UNLOCK_FOURTH_POTION_WORKSHOP_REAGENT_SLOT,
        PastelAdvancements.Milestones.REVEAL_AZURITE, PastelAdvancements.Milestones.REVEAL_STRATINE,
        PastelAdvancements.Milestones.REVEAL_COLORED_TREES_K, PastelAdvancements.Milestones.UNLOCK_INK_USE,
        PastelAdvancements.Hidden.StatusEffects.IMMUNITY, PastelAdvancements.Hidden.StatusEffects.DENSITY,
        };
    public static final ResourceLocation[] abberationToImbrifer = new ResourceLocation[]{
        PastelAdvancements.Unlocks.Equipment.BEDROCK_TOOLS, PastelAdvancements.Unlocks.Equipment.DRACONIC_TWINSWORD,
        PastelAdvancements.Unlocks.Blocks.UNIVERSE_SPYHOLE, PastelAdvancements.Unlocks.Blocks.BLOOD_ORCHID,
        PastelAdvancements.Unlocks.Blocks.MIDNIGHT_SOLUTION,
        PastelAdvancements.Unlocks.Blocks.EGG_LAYING_WOOLY_PIG_HEAD,
        PastelAdvancements.Unlocks.Trinkets.CIRCLET_OF_ARROGANCE, PastelAdvancements.Hidden.BREAK_BLACK_MATERIA,
        PastelAdvancements.Hidden.SOLVE_EVERY_PRESERVATION_RUIN, PastelAdvancements.Midgame.COLLECT_MIDNIGHT_CHIP,
        PastelAdvancements.Midgame.KILL_A_MOB_WITH_FRENZY3, PastelAdvancements.Midgame.HAVE_HALF_A_HEART_WITH_DIVINITY,
        PastelAdvancements.Midgame.CRUMBLE_MIDNIGHT_ABERRATION, PastelAdvancements.Midgame.APPLY_DIVINITY,
        PastelAdvancements.Midgame.CRAFT_BOTTLE_OF_RUIN, PastelAdvancements.Midgame.OPEN_DEEPER_DOWN_PORTAL,
        PastelAdvancements.Midgame.FISH_IN_MANY_LIQUIDS, PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK,
        PastelAdvancements.Midgame.PLUCK_BLOOD_ORCHID_PETAL, PastelAdvancements.Midgame.ENTER_DIMENSION,
        PastelAdvancements.Midgame.COLLECT_JADE_VINE_PETALS, PastelAdvancements.Midgame.COLLECT_BLOOD_ORCHID_PETAL,
        PastelAdvancements.Midgame.WEAR_COMPLETE_SET_OF_BEDROCK_ARMOR,
        PastelAdvancements.Midgame.COLLECT_MOONSTONE_SHARD, PastelAdvancements.Unlocks.Items.BOTTLE_OF_RUIN,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_UNBREAKING,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL_BREACHING,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION, PastelAdvancements.Unlocks.Potions.WITHER,
        PastelAdvancements.Unlocks.Potions.BAD_OMEN, PastelAdvancements.Unlocks.Food.DEMON_TRIFLE,
        PastelAdvancements.Unlocks.Food.RABBIT_POISON, PastelAdvancements.Unlocks.Food.DEMON_TEA,
        PastelAdvancements.Unlocks.Food.FREIGEIST, PastelAdvancements.Milestones.REVEAL_BLOOD_ORCHID_PETALS,
        PastelAdvancements.Hidden.StatusEffects.ASCENSION, PastelAdvancements.Hidden.StatusEffects.DIVINITY,
        PastelAdvancements.Hidden.StatusEffects.FRENZY,
        };
    public static final ResourceLocation[] imbriferToEndgame = new ResourceLocation[]{
        PastelAdvancements.Unlocks.Ampoules.PURE_AZURITE_AMPOULE,
        PastelAdvancements.Unlocks.Ampoules.RAW_BLOODSTONE_AMPOULE,
        PastelAdvancements.Unlocks.Ampoules.PURE_BLOODSTONE_AMPOULE,
        PastelAdvancements.Unlocks.Ampoules.RAW_AZURITE_AMPOULE,
        PastelAdvancements.Unlocks.Ampoules.RAW_MALACHITE_AMPOULE,
        PastelAdvancements.Unlocks.Ampoules.PURE_MALACHITE_AMPOULE, PastelAdvancements.Unlocks.Equipment.NECTAR_LANCE,
        PastelAdvancements.Unlocks.Equipment.DRAGONRENDING_PICKAXE, PastelAdvancements.Unlocks.Equipment.DREAMFLAYER,
        PastelAdvancements.Unlocks.Equipment.NIGHTFALLS_BLADE, PastelAdvancements.Unlocks.Equipment.OMNI_ACCELERATOR,
        PastelAdvancements.Unlocks.Equipment.SOOTHING_BOUQUET, PastelAdvancements.Unlocks.Blocks.CRYSTALLARIEUM,
        PastelAdvancements.Unlocks.Blocks.MOONSTONE_SEMI_PERMEABLE_GLASS,
        PastelAdvancements.Unlocks.Blocks.PURE_PASTEL_NETWORK,
        PastelAdvancements.Unlocks.Blocks.BLACKSLAG_SHIMMERSTONE_LIGHT,
        PastelAdvancements.Unlocks.Blocks.DRAGONROT_BUCKET, PastelAdvancements.Unlocks.Trinkets.RING_OF_PURSUIT,
        PastelAdvancements.Unlocks.Trinkets.NEAT_RING, PastelAdvancements.Unlocks.Trinkets.PUFF_CIRCLET,
        PastelAdvancements.Unlocks.Trinkets.GREATER_POTION_PENDANT,
        PastelAdvancements.Unlocks.Trinkets.AZURESQUE_DIKE_CORE,
        PastelAdvancements.Unlocks.Trinkets.FANCIFUL_BISMUTH_RING,
        PastelAdvancements.Unlocks.Trinkets.RING_OF_AERIAL_GRACE,
        PastelAdvancements.Unlocks.Trinkets.LAURELS_OF_SERENITY,
        PastelAdvancements.Lategame.TRIGGER_UNENCHANTED_WORKSTAFF, PastelAdvancements.Lategame.COLLECT_BISMUTH,
        PastelAdvancements.Lategame.COLLECT_DD_RESOURCES,
        PastelAdvancements.Lategame.BREAK_BUDDING_BLOCK_WITH_RESONANCE_TOOL,
        PastelAdvancements.Lategame.EQUIP_NEAT_RING, PastelAdvancements.Lategame.NO_RGB,
        PastelAdvancements.Lategame.CREATE_MALACHITE_TOOL, PastelAdvancements.Lategame.COLLECT_ALL_COOKBOOKS,
        PastelAdvancements.Lategame.EAT_EACH_COOKBOOK_HEAVY_MEAL, PastelAdvancements.Lategame.USE_PERTURBED_EYE,
        PastelAdvancements.Lategame.CARRY_TOO_MANY_LOW_GRAVITY_BLOCKS,
        PastelAdvancements.Lategame.SHOOT_FULLY_OVERCHARGED_CROSSBOW, PastelAdvancements.Lategame.COLLECT_NECTARDEW,
        PastelAdvancements.Lategame.COLLECT_AETHER_GRACED_NECTAR_GLOVES,
        PastelAdvancements.Lategame.FIND_EXCAVATION_SITE,
        PastelAdvancements.Lategame.GET_KILLED_WHILE_OUT_OF_DEEPER_DOWN_BOUNDS,
        PastelAdvancements.Lategame.COLLECT_ENOUGH_HINTS, PastelAdvancements.Lategame.COLLECT_AETHER_VESTIGES,
        PastelAdvancements.Lategame.COLLECT_BISMUTH_CRYSTAL, PastelAdvancements.Lategame.COLLECT_ALL_LEGENDARY_WEAPONS,
        PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PastelAdvancements.Lategame.COLLECT_NOXWOOD,
        PastelAdvancements.Lategame.ENCHANT_EXCHANGING_STAFF, PastelAdvancements.Lategame.WEAR_BOTH_GRAVITY_TRINKETS,
        PastelAdvancements.Lategame.CRAFT_RESONANT_TOOL, PastelAdvancements.Lategame.CREATE_JADE_VINE,
        PastelAdvancements.Lategame.GROW_BISMUTH_IN_CRYSTALLARIEUM, PastelAdvancements.Lategame.SURVIVE_FATAL_SLUMBER,
        PastelAdvancements.Lategame.COLLECT_GRAYSCALE_PIGMENTS, PastelAdvancements.Lategame.COLLECT_ONE_COOKBOOK,
        PastelAdvancements.Lategame.SOLVE_STRANGE_PRESERVATION_RUIN, PastelAdvancements.Lategame.COLLECT_DOOMBLOOM_SEED,
        PastelAdvancements.Lategame.COLLECT_POISONERS_HANDBOOK, PastelAdvancements.Lategame.COLLECT_MYCEYLON,
        PastelAdvancements.Lategame.CREATE_GLASS_CREST_TOOL,
        PastelAdvancements.Lategame.PUT_TOO_MANY_LOW_GRAVITY_BLOCKS_INTO_ANIMAL,
        PastelAdvancements.Lategame.COLLECT_PRICKLY_BAYLEAF, PastelAdvancements.Lategame.REVIVE_BLACK_SLUDGE_PLANT,
        PastelAdvancements.Lategame.COLLECT_PALTAERIA, PastelAdvancements.Lategame.COLLECT_MOONSTONE_CORE,
        PastelAdvancements.Lategame.CREATE_EVERNECTAR,
        PastelAdvancements.Lategame.BREAK_INFESTED_BLOCK_WITH_RESONANCE_TOOL,
        PastelAdvancements.Lategame.REACHED_DD_FLOOR, PastelAdvancements.Lategame.BREAK_SPAWNER_WITH_RESONANCE_TOOL,
        PastelAdvancements.Lategame.COLLECT_MOONSTONE, PastelAdvancements.Lategame.ENTER_STRANGE_PRESERVATION_RUIN,
        PastelAdvancements.Lategame.FIND_UNDERGROWTH_MANOR, PastelAdvancements.Lategame.COLLECT_MALACHITE,
        PastelAdvancements.Lategame.COLLECT_HOVER_BLOCK, PastelAdvancements.Lategame.PASTEL_LATEGAME,
        PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE,
        PastelAdvancements.Lategame.STRIKE_UP_HUMMINGSTONE_HYMN, PastelAdvancements.Lategame.COLLECT_MYSTERIOUS_LOCKET,
        PastelAdvancements.Lategame.VISIT_ALL_DEEPER_DOWN_BIOMES, PastelAdvancements.Lategame.COLLECT_HUMMINGSTONE,
        PastelAdvancements.Lategame.TAME_LIZARD, PastelAdvancements.Lategame.COLLECT_DOWNSTONE_FRAGMENTS,
        PastelAdvancements.Lategame.CRAFT_MOONSTONE_PEDESTAL,
        PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE_WITHOUT_MOONSTONE,
        PastelAdvancements.Lategame.GROW_MALACHITE_IN_CRYSTALLARIEUM, PastelAdvancements.Lategame.FIND_FORGOTTEN_CITY,
        PastelAdvancements.Hidden.VisitDDBiomes.BLACK_LANGAST, PastelAdvancements.Hidden.VisitDDBiomes.NOXSHROOM_FOREST,
        PastelAdvancements.Hidden.VisitDDBiomes.DEEP_BARRENS, PastelAdvancements.Hidden.VisitDDBiomes.CRYSTAL_GARDENS,
        PastelAdvancements.Hidden.VisitDDBiomes.DRAGONROT_SWAMP,
        PastelAdvancements.Hidden.VisitDDBiomes.DEEP_DRIPSTONE_CAVES,
        PastelAdvancements.Hidden.VisitDDBiomes.HOWLING_SPIRES, PastelAdvancements.Hidden.VisitDDBiomes.RAZOR_EDGE,
        PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK,
        PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK,
        PastelAdvancements.Hidden.CollectCookbooks.IMBRIFER_COOKBOOK,
        PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2,
        PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_1,
        PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK, PastelAdvancements.Hidden.EntityInteract.ERASER,
        PastelAdvancements.Hidden.EntityInteract.LIZARD, PastelAdvancements.Hidden.EntityInteract.PRESERVATION_TURRET,
        PastelAdvancements.Hidden.CollectPigment.WHITE, PastelAdvancements.Hidden.CollectPigment.GRAY,
        PastelAdvancements.Hidden.CollectPigment.LIGHT_GRAY, PastelAdvancements.Hidden.COLLECT_JADEITE,
        PastelAdvancements.Hidden.COLLECT_BLACKSLAG, PastelAdvancements.Hidden.COLLECT_BASAL_MARBLE,
        PastelAdvancements.Hidden.INTERACT_WITH_DRAGONROT, PastelAdvancements.Hidden.COLLECT_SAWBLADE_HOLLY_BERRY,
        PastelAdvancements.Hidden.INTERACT_WITH_DOOMBLOOM, PastelAdvancements.Hidden.COLLECT_ALL_CRYSTAL_FLOWERS,
        PastelAdvancements.Hidden.COLLECT_PALTAERIA_GEM, PastelAdvancements.Hidden.COLLECT_ROCK_CRYSTAL,
        PastelAdvancements.Hidden.DISCOVER_ROCK_CRYSTAL, PastelAdvancements.Hidden.COLLECT_ASH_AND_SLUSH,
        PastelAdvancements.Hidden.COLLECT_ASH, PastelAdvancements.Hidden.COLLECT_SHALE_CLAY,
        PastelAdvancements.Hidden.COLLECT_PYRITE, PastelAdvancements.Hidden.COLLECT_FISSURE_PLUM,
        PastelAdvancements.Hidden.COLLECT_SLUSH, PastelAdvancements.Hidden.COLLECT_GLASS_PEACH,
        PastelAdvancements.Hidden.COLLECT_ALOE, PastelAdvancements.Hidden.COLLECT_DRAGONJAG,
        PastelAdvancements.Hidden.BUILD_ANY_COMPLEX_STRUCTURE, PastelAdvancements.Unlocks.Pylons.MOONSTONE_PYLON,
        PastelAdvancements.Unlocks.Items.EVERPROMISE_RIBBON_GUIDEBOOK,
        PastelAdvancements.Unlocks.Items.STAFF_OF_REMEMBRANCE, PastelAdvancements.Unlocks.Items.GLASS_AMPOULES,
        PastelAdvancements.Unlocks.Items.PERTURBED_EYE, PastelAdvancements.Unlocks.Items.HEAVY_CORE,
        PastelAdvancements.Unlocks.Items.CELESTIAL_POCKETWATCH, PastelAdvancements.Unlocks.Items.EXCHANGING_STAFF,
        PastelAdvancements.Unlocks.Items.EVERPROMISE_RIBBON_RECIPE,
        PastelAdvancements.Unlocks.Items.BOTTLE_OF_FORFEITURE, PastelAdvancements.Unlocks.Items.PIPE_BOMB,
        PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM_DOOM, PastelAdvancements.Unlocks.GemstoneLights.MOONSTONE,
        PastelAdvancements.Unlocks.Upgrades.UPGRADE_YIELD, PastelAdvancements.Unlocks.Upgrades.UPGRADE_EXPERIENCE2,
        PastelAdvancements.Unlocks.Upgrades.UPGRADE_EFFICIENCY2, PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED2,
        PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED3, PastelAdvancements.Unlocks.Upgrades.UPGRADE_YIELD2,
        PastelAdvancements.Unlocks.GemstoneChimes.MOONSTONE_CHIME, PastelAdvancements.Unlocks.Enchantments.RAZING_USAGE,
        PastelAdvancements.Unlocks.Enchantments.RESONANCE_USAGE, PastelAdvancements.Unlocks.Enchantments.VANILLA_CURSES,
        PastelAdvancements.Unlocks.Enchantments.RESONANCE_CRAFTING, PastelAdvancements.Unlocks.Enchantments.INEXORABLE,
        PastelAdvancements.Unlocks.Enchantments.RAZING_CRAFTING,
        PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE,
        PastelAdvancements.Unlocks.Enchantments.SERENDIPITY_REEL,
        PastelAdvancements.Unlocks.Enchantments.INDESTRUCTIBLE, PastelAdvancements.Unlocks.Enchantments.PEST_CONTROL,
        PastelAdvancements.Unlocks.Potions.RESISTANCE, PastelAdvancements.Unlocks.Potions.WITHER,
        PastelAdvancements.Unlocks.Potions.STRONG_SLEEP_EFFECTS, PastelAdvancements.Unlocks.Potions.LIFE_DRAIN,
        PastelAdvancements.Unlocks.Potions.LEVITATION, PastelAdvancements.Unlocks.Glowblocks.LIGHT_GRAY_GLOWBLOCK,
        PastelAdvancements.Unlocks.Glowblocks.GRAY_GLOWBLOCK, PastelAdvancements.Unlocks.Glowblocks.WHITE_GLOWBLOCK,
        PastelAdvancements.Unlocks.Resources.PURE_AZURITE, PastelAdvancements.Unlocks.Resources.BLOODSTONE,
        PastelAdvancements.Unlocks.Redstone.CREATURE_DETECTOR, PastelAdvancements.Unlocks.Redstone.BLOCK_BREAKER,
        PastelAdvancements.Unlocks.Malachite.GLASS_ARROWS,
        PastelAdvancements.Unlocks.Malachite.FRACTAL_GLASS_CREST_BIDENT,
        PastelAdvancements.Unlocks.Malachite.GLASS_CREST_CROSSBOW, PastelAdvancements.Unlocks.Malachite.MALACHITE_TOOLS,
        PastelAdvancements.Unlocks.Malachite.FEROCIOUS_GLASS_CREST_BIDENT,
        PastelAdvancements.Unlocks.Malachite.GLASS_CREST_WORKSTAFF,
        PastelAdvancements.Unlocks.Malachite.GLASS_CREST_ULTRA_GREATSWORD,
        PastelAdvancements.Unlocks.Malachite.GLASS_CREST_TOOLS, PastelAdvancements.Unlocks.Food.MYCEYLON_PASTRIES,
        PastelAdvancements.Unlocks.Food.MOONSHINE, PastelAdvancements.Unlocks.Food.FISSURE_PLUM_TITRATION,
        PastelAdvancements.Unlocks.Food.GLASS_PEACH_TITRATION, PastelAdvancements.Unlocks.Food.TRIPLE_MEAT_POT_STEW,
        PastelAdvancements.Unlocks.Food.HONEY, PastelAdvancements.Unlocks.Food.SPIKED_MULLET_WINE,
        PastelAdvancements.Unlocks.Food.DRAGONBONE_BROTH, PastelAdvancements.Unlocks.Food.SAWBLADE_HOLLY_TITRATION,
        PastelAdvancements.Unlocks.Food.NECTARDEW_TITRATION, PastelAdvancements.Unlocks.Food.MYCEYLON_LIQUOR,
        PastelAdvancements.Unlocks.Food.GATORWINE, PastelAdvancements.Unlocks.ColoredSaplings.BLACK_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.LIGHT_GRAY_SAPLING,
        PastelAdvancements.Unlocks.ColoredSaplings.GRAY_SAPLING,
        PastelAdvancements.Milestones.UNLOCK_SPAWNER_MANIPULATION, PastelAdvancements.Milestones.REVEAL_COLORED_TREES_W,
        PastelAdvancements.Milestones.UNLOCK_PASTEL_NODE_UPGRADING, PastelAdvancements.Milestones.REVEAL_MALACHITE,
        PastelAdvancements.Milestones.REVEAL_PALTAERIA, PastelAdvancements.Milestones.UNLOCK_PASTEL_NODE_COLORING,
        PastelAdvancements.Milestones.UNLOCK_SPAWNER_CREATURE_CHANGE,
        PastelAdvancements.Hidden.StatusEffects.VULNERABILITY, PastelAdvancements.Hidden.StatusEffects.STIFFNESS,
        PastelAdvancements.Hidden.StatusEffects.FATAL_SLUMBER, PastelAdvancements.Hidden.StatusEffects.TOUGHNESS,
        PastelAdvancements.Hidden.StatusEffects.PROJECTILE_REBOUND,
        PastelAdvancements.Hidden.StatusEffects.ADVANCED_SLEEP, PastelAdvancements.Hidden.StatusEffects.ETERNAL_SLUMBER,
        PastelAdvancements.Hidden.StatusEffects.SCARRED, PastelAdvancements.Hidden.StatusEffects.EFFECT_PROLONGING,
        PastelAdvancements.Hidden.StatusEffects.LIGHTWEIGHT, PastelAdvancements.Hidden.StatusEffects.MAGIC_ANNULATION,
        };
    public static final ResourceLocation[][] advancementTiers = new ResourceLocation[][]{
        upToOnyx, onyxToAbberation, abberationToImbrifer, imbriferToEndgame
    };

    private static final VoxelShape SHAPE = box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    public TeaTable(Properties properties) {
        super(properties);
    }

    // Move this somewhere more sensible later (maybe to DatabankUtils)
    public boolean anyPlayerHas(ResourceLocation advancement, List<ServerPlayer> players) {
        for (Player player : players) {
            if (DatabankUtils.hasAdvancement(player, advancement)) return true;
        }
        return false;
    }

    @Override
    public ItemInteractionResult useItemOn(
        ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult hitResult
    ) {
        if (!stack.is(PastelItemTags.TEA_TABLE_DRINKS)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (world.isClientSide) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            // do the thing
            ArrayList<ServerPlayer> seatedPlayers = new ArrayList<>();
            for (Direction dir : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
                BlockPos potentialCushion = pos.relative(dir);
                if (world.getBlockState(potentialCushion)
                         .is(PastelBlockTags.TEA_TABLE_SEATS)) {
                    for (ServerPlayer seatedPlayer : world.getEntitiesOfClass(
                        ServerPlayer.class, new AABB(potentialCushion))) {
                        if (seatedPlayer.getMainHandItem()
                                        .is(PastelItemTags.TEA_TABLE_DRINKS)) {
                            seatedPlayers.add(seatedPlayer);
                        }
                    }
                }
            }
            if (seatedPlayers.isEmpty()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else if (seatedPlayers.size() == 1) {
                player.displayClientMessage(Component.translatable("block.pastel.tea_table_lonely_party"), true);
            } else {
                // We have at least two players seated on cushions and with a hot drink. Time to study
                int lowest_common_stage = 4;
                // 3 = sync everything before compassquest, 2 = sync up to imbrifer,
                // 1 = sync up to 'perfect compound', 0 = sync up to onyx
                // Endgame advancements are never synced—experience that yourself
                for (Player seatedPlayer : seatedPlayers) {
                    seatedPlayer.getMainHandItem()
                                .setCount(seatedPlayer.getMainHandItem()
                                                      .getCount() -
                                          1); // todo: make the player actually get effects from this
                    if (DatabankUtils.hasAdvancement(seatedPlayer, PastelAdvancements.Midgame.ENTER_DIMENSION)) {
                        lowest_common_stage = Math.min(lowest_common_stage, 3);
                    } else if (DatabankUtils.hasAdvancement(
                        seatedPlayer, PastelAdvancements.Midgame.CREATE_MIDNIGHT_ABERRATION)) {
                        lowest_common_stage = Math.min(lowest_common_stage, 2);
                    } else if (DatabankUtils.hasAdvancement(seatedPlayer, PastelAdvancements.CREATE_ONYX_SHARD)) {
                        lowest_common_stage = Math.min(lowest_common_stage, 1);
                    } else {
                        lowest_common_stage = 0;
                    }
                }
                MinecraftServer server = world.getServer();
                if (server == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                ServerAdvancementManager sam = server.getAdvancements();
                for (int i = 0; i <= lowest_common_stage; i++) {
                    for (ResourceLocation advancement : advancementTiers[i]) {
                        if (!anyPlayerHas(advancement, seatedPlayers)) continue;
                        AdvancementHolder advancementHolder = sam.get(advancement);
                        if (advancementHolder == null) continue;
                        for (ServerPlayer victim : seatedPlayers) {
                            AdvancementProgress progress = victim.getAdvancements()
                                                                 .getOrStartProgress(advancementHolder);
                            if (progress.isDone()) continue;
                            for (String criterion : progress.getRemainingCriteria())
                                victim.getAdvancements()
                                      .award(advancementHolder, criterion);

                        }

                    }
                }

                return ItemInteractionResult.CONSUME;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeaTableBlockEntity(pos, state);
    }
}
