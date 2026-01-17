package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.data.lang.blocks.compat.AlloyForgeryBlockLang;
import earth.terrarium.pastel.registries.PastelBlocks;

import java.util.Arrays;
import java.util.List;

public class PastelBlockLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        for (var block : PastelBlocks.COMMON_REGISTRAR.getEntries()) {
            String name = block.getRegisteredName()
                               .substring(7);
            // manxi gets special treatment. only partially because opening the structure files to change her id
            // would be annoying
            if (name.equals("manxi")) {
                provider.addBlock(block, "Stargazer Manxi");
                continue;
            }
            // pastel nodes have fancy formatting
            if (name.endsWith("node")) continue;
            // all pedestals bear the same name, and the fusion shrines are twins; item bowls, likewise
            if (name.startsWith("pedestal")) name = "pigment_pedestal";
            if (name.startsWith("fusion_shrine")) name = "fusion_shrine";
            if (name.startsWith("item_bowl")) name = "item_bowl";
            // flowing liquids use their parent name
            if (name.startsWith("flowing_")) name = name.substring(7);
            // polished gem blocks don't have 'block' in the name
            if (List.of(
                        "polished_moonstone_block", "polished_onyx_block", "polished_amethyst_block",
                        "polished_topaz_block",
                        "polished_citrine_block"
                    )
                    .contains(name)) name = name.substring(0, name.length() - 6);
            // handle storage blocks
            if (name.endsWith("_block") && !name.endsWith("_noxcap_block") && !name.equals("resplendent_block"))
                name = "block_of_" + name.substring(0, name.length() - 6);
            // semi-permeable glass has a hyphen and is in a weird order
            if (name.contains("semi_permeable")) name = "Semi-Permeable_" + name.replace("_semi_permeable", "");
            // upgrade names are also fairly unique
            if (name.startsWith("upgrade_")) continue;
            // avoid duplicate keys
            if (name.equals("primordial_wall_torch") || name.endsWith("_wall_head")) continue;

            var formattedName = PastelLanguageProvider.prettifyRegisteredName(name);

            provider.add(block.get(), formattedName);
        }

        // Special cased blocks
        provider.add(PastelBlocks.BUFFER_NODE.get(), "§aPastel Network Buffer Node");
        provider.add(PastelBlocks.CONNECTION_NODE.get(), "§7Pastel Network Connection Node");
        provider.add(PastelBlocks.GATHER_NODE.get(), "§8Pastel Network Gather Node");
        provider.add(PastelBlocks.PROVIDER_NODE.get(), "§dPastel Network Provider Node");
        provider.add(PastelBlocks.SENDER_NODE.get(), "§ePastel Network Sender Node");
        provider.add(PastelBlocks.STORAGE_NODE.get(), "§bPastel Network Storage Node");
        provider.add(PastelBlocks.UPGRADE_EFFICIENCY.get(), "Efficiency Slope");
        provider.add(PastelBlocks.UPGRADE_EFFICIENCY2.get(), "Efficiency Slope T2");
        provider.add(PastelBlocks.UPGRADE_EXPERIENCE.get(), "Knowledge Focus");
        provider.add(PastelBlocks.UPGRADE_EXPERIENCE2.get(), "Knowledge Focus T2");
        provider.add(PastelBlocks.UPGRADE_SPEED.get(), "Crafting Accelerator");
        provider.add(PastelBlocks.UPGRADE_SPEED2.get(), "Crafting Accelerator T2");
        provider.add(PastelBlocks.UPGRADE_SPEED3.get(), "Crafting Accelerator T3");
        provider.add(PastelBlocks.UPGRADE_YIELD.get(), "Production Surge");
        provider.add(PastelBlocks.UPGRADE_YIELD2.get(), "Production Surge T2");

        // Tooltips and such
        provider.add("block.pastel.fusion_shrine", "Fusion Shrine");
        provider.add("block.pastel.longing_chimera.tooltip", "Unknown Author - The Longing");
        provider.add("block.pastel.manxi.nope", "Powerful magic repels you");
        provider.add("block.pastel.bonemealing_idol.tooltip", "§7Fertilises a plant next to it");
        provider.add("block.pastel.buffer_node.tooltip", "Stocks Items for Gather Nodes");
        provider.add("block.pastel.color_picker.tooltip", "Converts §2P§3i§cg§6m§be§en§dt§7 into §bI§dn§ek");
        provider.add("block.pastel.compacting_chest.toggle_crafting_mode", "Toggle Crafting Mode");
        provider.add("block.pastel.connection_node.tooltip", "Extends the range of your Network");
        provider.add("block.pastel.creative_particle_spawner.tooltip", "Unbreakable - does not require Redstone");
        provider.add("block.pastel.crystal_apothecary.owner", "%s's Crystal Apothecary");
        provider.add("block.pastel.crystal_apothecary.tooltip", "Place in a Geode to harvest Gemstone Shards");

        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle0", "In Memory of a Nameless Astrologer");
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle1", "The thief. The teacher. The dreamer");
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle2",
            "Twice graced with Aether, he sought deeper truth"
        );
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle3",
            "In pursuit of deep light, he sailed unto barren waters"
        );
        provider.add("block.pastel.deep_light_chiseled_preservation_stone.puzzle4", "Enlightenment.");
        provider.add("block.pastel.deep_light_chiseled_preservation_stone.puzzle5", "Dived too deep.");
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle6", "A soul lost unto the endless sea.");
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle7",
            "Would you get too close to becoming lost just for truth?"
        );
        provider.add(
            "block.pastel.deep_light_chiseled_preservation_stone.puzzle8", "Just to see that which is beyond sight?");
        provider.add("block.pastel.echolocating_idol.tooltip", "§7Echolocates creatures in a %d block radius");
        provider.add("block.pastel.ender_dropper.owner", "%s's Ender Dropper");
        provider.add("block.pastel.ender_hopper.owner", "%s's Ender Hopper");
        provider.add("block.pastel.entity_summoning_idol.tooltip", "§7Summons a %s");
        provider.add("block.pastel.explosion_idol.tooltip", "§7Causes a %d§7 block large explosion");
        provider.add("block.pastel.fall_damage_negating_idol.tooltip", "§7Negates all fall damage when landing on it");
        provider.add("block.pastel.fall_damage_negating_idol.tooltip2", "§7Will also temporarily hold a fall");
        provider.add(
            "block.pastel.feeding_idol.tooltip", "§7Feeds animals in a range of %d§7 blocks using food on the ground");
        provider.add("block.pastel.freezing_idol.tooltip", "§7Freezes blocks around it");
        provider.add("block.pastel.gather_node.tooltip", "Gathers items from Sender, Provider and Storage Nodes");
        provider.add("block.pastel.heartbound_chest.owner", "%s's Heartbound Chest");
        provider.add(
            "block.pastel.incandescent_amalgam.tooltip", "§6⚠§r High yield magical explosive, handle with care");
        provider.add("block.pastel.incandescent_amalgam.tooltip.preview", "Gets more explosive with age");
        provider.add("block.pastel.incandescent_amalgam.tooltip_power", "Explosion Power: %d");
        provider.add("block.pastel.insomnia_idol.tooltip", "§7Causes Insomnia");
        provider.add("block.pastel.knockback_idol.tooltip", "§7Causes knockback");
        provider.add(
            "block.pastel.line_teleporting_idol.tooltip", "§7Teleports you on top of the next block of this type ");
        provider.add("block.pastel.line_teleporting_idol.tooltip2", "§7in a straight line within %d blocks");
        provider.add("block.pastel.milking_idol.tooltip", "§7Milks close-by creatures in a %d block radius");
        provider.add("block.pastel.milking_idol.tooltip2", "§7Requires §fBuckets§7 or §fBowls§7 close to the animals");
        provider.add("block.pastel.mob_block.tooltip", "When triggered via use/stepping on/projectile hit:");
        provider.add("block.pastel.particle_spawner.collisions", "Collisions");
        provider.add("block.pastel.particle_spawner.duration", "Duration");
        provider.add("block.pastel.particle_spawner.gravity", "Gravity");
        provider.add("block.pastel.particle_spawner.offset", "Offset");
        provider.add("block.pastel.particle_spawner.particle_count", "Particles / Second");
        provider.add("block.pastel.particle_spawner.scale", "Scale");
        provider.add("block.pastel.particle_spawner.tooltip", "Redstone powered, highly configurable particle display");
        provider.add("block.pastel.particle_spawner.variance", "Variance");
        provider.add("block.pastel.particle_spawner.velocity", "Velocity");
        provider.add("block.pastel.pastel_network_nodes.connection_debug", "This node is connected to:");
        provider.add("block.pastel.pastel_network_nodes.tooltip.placing", "Place against some form of inventory");
        provider.add("block.pastel.pastel_network_nodes.tooltip.range", "Connects to other nodes in a %d block radius");
        provider.add("block.pastel.pastel_node", "Pastel Node");
        provider.add("block.pastel.pedestal", "Pigment Pedestal");
        provider.add("block.pastel.piglin_trade_idol.tooltip", "§7Trades §fGold Ingots§7 with you");
        provider.add("block.pastel.player_detector.owner", "%s's Player Detector");
        provider.add("block.pastel.potion_effect_idol.tooltip", "§7Triggers %s");
        provider.add("block.pastel.present.tooltip.description", "Click it on items to add them to the Present");
        provider.add(
            "block.pastel.present.tooltip.description2", "Then put it in a crafting grid and add Pigment to wrap");
        provider.add("block.pastel.present.tooltip.wrapped", "Wrapped (place and sneak-use to open)");
        provider.add("block.pastel.present.tooltip.wrapped.giver", "Wrapped by %s (place and sneak-use to open)");
        provider.add("block.pastel.present.tooltip.wrapped_placed", "A present! Sneak-use to open");
        provider.add(
            "block.pastel.present.tooltip.wrapped_placed.giver", "A present wrapped by %s! (sneak-use to open)");
        provider.add("block.pastel.projectile_idol.tooltip", "§7Shoots a %s");
        provider.add("block.pastel.provider_node.tooltip", "Supplies Gather Nodes with items");
        provider.add(
            "block.pastel.random_teleporting_idol.tooltip", "§7Teleports you up to %d blocks in a random direction");
        provider.add("block.pastel.redstone_calculator.mode.addition", "Addition");
        provider.add("block.pastel.redstone_calculator.mode.division", "Division");
        provider.add("block.pastel.redstone_calculator.mode.max", "Maximum");
        provider.add("block.pastel.redstone_calculator.mode.min", "Minimum");
        provider.add("block.pastel.redstone_calculator.mode.modulo", "Modulo");
        provider.add("block.pastel.redstone_calculator.mode.multiplication", "Multiplication");
        provider.add("block.pastel.redstone_calculator.mode.subtraction", "Subtraction");
        provider.add("block.pastel.redstone_calculator.mode_set", "Set to mode: ");
        provider.add("block.pastel.redstone_timer.setting.active", "Active time set to: ");
        provider.add("block.pastel.redstone_timer.setting.four_ticks", "4 ticks");
        provider.add("block.pastel.redstone_timer.setting.inactive", "Inactive time set to: ");
        provider.add("block.pastel.redstone_timer.setting.one_minute", "1 minute");
        provider.add("block.pastel.redstone_timer.setting.one_second", "1 second");
        provider.add("block.pastel.redstone_timer.setting.ten_minutes", "10 minutes");
        provider.add("block.pastel.redstone_timer.setting.ten_seconds", "10 seconds");
        provider.add("block.pastel.sender_node.tooltip", "Sends items to Gather and Storage Nodes");
        provider.add("block.pastel.shearing_idol.tooltip", "§7Shears close-by Entities");
        provider.add("block.pastel.silverfish_inserting_idol.tooltip", "§7Invades neighboring Blocks with Silverfish");
        provider.add("block.pastel.slime_sizing_idol.tooltip", "§7Increases close-by Slime's sizes");
        provider.add("block.pastel.storage_node.tooltip", "Serves as storage for Sender and Gather Nodes");
        provider.add("block.pastel.tea_table_lonely_party", "I can't have a tea party without friends!");
        provider.add(
            "block.pastel.titration_barrel.content_count_with_fluid",
            "Contains %s and %d items. Seal with a Colored Plank to start fermenting."
        );
        provider.add(
            "block.pastel.titration_barrel.content_count_with_fluid_full",
            "Contains %s and %d items (full). Seal with a Colored Plank to start fermenting."
        );
        provider.add(
            "block.pastel.titration_barrel.content_count_without_fluid",
            "Contains %d items and no liquid. Seal with a Colored Plank to start fermenting."
        );
        provider.add(
            "block.pastel.titration_barrel.content_count_without_fluid_full",
            "Contains %d items (full) and no fluid. Seal with a Colored Plank to start fermenting."
        );
        provider.add(
            "block.pastel.titration_barrel.days_of_sealing_after_opened_with_extractable_amount",
            "%s was fermenting for %d days (%s real days)"
        );
        provider.add(
            "block.pastel.titration_barrel.days_of_sealing_before_opened",
            "Sealed up %d days ago (%s real days). Sneak-Use to unseal"
        );
        provider.add(
            "block.pastel.titration_barrel.debug_added_day",
            "(Debug Function) Added a real life day of fermentation time"
        );
        provider.add("block.pastel.titration_barrel.empty_when_tapping", "It... was empty. Huh");
        provider.add(
            "block.pastel.titration_barrel.invalid_recipe",
            "This does not seem like it would produce something useful..."
        );
        provider.add(
            "block.pastel.titration_barrel.invalid_recipe_when_tapping",
            "This barrel only contained an unusable mixture. Eww"
        );
        provider.add(
            "block.pastel.titration_barrel.missing_liquid_when_tapping",
            "It seems to have lacked liquid, so the content has gone bad. Pity"
        );
        provider.add(
            "block.pastel.titration_barrel.not_yet_ready", "Sealed up %d days ago (%s real days). Not yet finished.");
        provider.add(
            "block.pastel.titration_barrel.recipe_not_unlocked",
            "You have no idea what this is inside. Better leave it be."
        );
        provider.add("block.pastel.titration_barrel.tapping_item_required", "Tapping requires a ");
        provider.add("block.pastel.villager_converting_idol.tooltip", "§7Converts Villagers to Zombie Villagers");


        // this compat is weird
        AlloyForgeryBlockLang.addTranslations(provider);
    }


}
