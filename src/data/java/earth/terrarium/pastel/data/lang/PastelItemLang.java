package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;

import java.util.Map;

import static java.util.Map.entry;

public class PastelItemLang {

    // Most of these either have apostrophes or other special characters that won't be in the registeredName
    private static final Map<String, String> specialCasedItemTranslations = Map.ofEntries(
        entry(PastelItems.ARTISANS_ATLAS.getRegisteredName(), "Artisan's Atlas"),
        entry(PastelItems.ARTISTS_PALETTE.getRegisteredName(), "Artist's Palette"),
        entry(PastelItems.BREWERS_HANDBOOK.getRegisteredName(), "Brewer's Handbook"),
        entry(PastelItems.CONSTRUCTORS_STAFF.getRegisteredName(), "Constructor's Staff"),
        entry(PastelItems.AETHER_GRACED_NECTAR_GLOVES.getRegisteredName(), "Aether-Graced Nectar Gloves"),
        entry(PastelItems.GLOVES_OF_DAWNS_GRASP.getRegisteredName(), "Gloves of Dawn's Grasp"),
        entry(PastelItems.GUIDEBOOK.getRegisteredName(), "Colorful World"),
        entry(PastelItems.HEARTSINGERS_REWARD.getRegisteredName(), "Heartsinger's Reward"),
        entry(PastelItems.MALACHITE_GLASS_ARROW.getRegisteredName(), "Glass Arrow"),
        entry(PastelItems.MERMAIDS_GEM.getRegisteredName(), "Mermaid's Gem"),
        entry(PastelItems.MERMAIDS_JAM.getRegisteredName(), "Mermaid's Jam"),
        entry(PastelItems.MERMAIDS_POPCORN.getRegisteredName(), "Mermaid's Popcorn"),
        entry(PastelItems.NATURES_STAFF.getRegisteredName(), "Nature's Staff"),
        entry(PastelItems.NIGHTFALLS_BLADE.getRegisteredName(), "Nightfall's Blade"),
        entry(PastelItems.OMNI_ACCELERATOR.getRegisteredName(), "Omni-Accelerator"),
        entry(PastelItems.PEACHES_FLAMBE.getRegisteredName(), "Peaches Flambé"),
        entry(PastelItems.PEDESTAL_TIER_1_STRUCTURE_PLACER.getRegisteredName(), "Pastel Focus Structure Placer"),
        entry(PastelItems.PEDESTAL_TIER_2_STRUCTURE_PLACER.getRegisteredName(), "Pastel Temple Structure Placer"),
        entry(PastelItems.PEDESTAL_TIER_3_STRUCTURE_PLACER.getRegisteredName(), "Pastel Palace Structure Placer"),
        entry(PastelItems.POISONERS_HANDBOOK.getRegisteredName(), "Poisoner's Handbook"),
        entry(PastelItems.SEVEN_LEAGUE_BOOTS.getRegisteredName(), "Seven-League Boots")
    );

    public static void addTranslations(PastelLanguageProvider provider) {

        for (var item : PastelItems.ITEM_REGISTRAR.getEntries()) {
            if (item.get() instanceof BlockItem && !(item.get() instanceof ItemNameBlockItem)) continue; // prevent duplicate keys
            if (specialCasedItemTranslations.containsKey(item.getRegisteredName())) {
                provider.addItem(item, specialCasedItemTranslations.get(item.getRegisteredName()));
                continue;
            }
            var name = item.getRegisteredName()
                           .substring(7);
            if (name.endsWith("_banner_pattern")) {
                provider.addItem(item, "Banner Pattern");
                continue;
            }
            if (name.startsWith("music_disc_")) {
                provider.addItem(item, "Music Disc");
                continue;
            }

            var prettifiedName = PastelLanguageProvider.prettifyRegisteredName(item.getRegisteredName()
                                                                                   .substring(7));
            provider.addItem(item, prettifiedName);
        }

        // EMI aliases
        provider.add("item.pastel.crystal_armor.alias_0","Gemstone Armor");
        provider.add("item.pastel.crystal_armor.alias_1","Crystal Armor");

        provider.add("item.pastel.wire_hook.alias_0", "Hookshot");
        provider.add("item.pastel.wire_hook.alias_1", "Grappling Hook");
        provider.add("item.pastel.wire_hook.alias_2", "Grapple");

        // tooltips and such
        provider.add("item.pastel.onyx_helmet.tooltip", "Unity");
        provider.add("item.pastel.amethyst_chestplate.tooltip", "Time heals all wounds");
        provider.add("item.pastel.topaz_leggings.tooltip", "Sturdy legs");
        provider.add("item.pastel.citrine_boots.tooltip", "Put a spring in your step");

        provider.add("item.pastel.amethyst_cluster_banner_pattern.desc", "Amethyst Cluster");
        provider.add("item.pastel.amethyst_glass_arrow.tooltip", "-aggressive homing-");
        provider.add("item.pastel.amethyst_shard_banner_pattern.desc", "Amethyst Shard");
        provider.add("item.pastel.aqua_regia.tooltip.preview", "Brew it with a combination of Bulbs & Petals");
        provider.add(
            "item.pastel.aqua_regia.tooltip.preview2", "Petals ferment slower than Bulbs, but reduce negative effects");
        provider.add("item.pastel.tooltip.could_use_some_sweetener", "Could use some sweetener");
        provider.add("item.pastel.artisans_atlas.empty", "Use in a structure to locate others of its kind");
        provider.add("item.pastel.artisans_atlas.locates_structure", "Locates the closest ");
        provider.add("item.pastel.artisans_atlas.set_structure", "Now locates ");
        provider.add(
            "item.pastel.artisans_atlas.unlocatable", "The aura of this structure seems to be of a special kind");
        provider.add("item.pastel.artists_palette.tooltip", "§7Stores up to %d§7 of elemental Ink in total");
        provider.add("item.pastel.artists_palette.tooltip.mix_on_demand", "§7Mixes other types of Ink on demand");
        provider.add("item.pastel.ash_flakes.tooltip", "§O it's ash.");
        provider.add("item.pastel.ashen_circlet.tooltip", "§7Protects you from Fire Damage and");
        provider.add("item.pastel.ashen_circlet.tooltip.cooldown_full", "§2Ready to absorb immense heat");
        provider.add("item.pastel.ashen_circlet.tooltip.cooldown_seconds", "%d§7 Seconds until recharged");
        provider.add("item.pastel.ashen_circlet.tooltip2", "§7grants better Vision and Speed in Lava");
        provider.add("item.pastel.azalea_tea.tooltip", "Lets you sleep through the day");
        provider.add(
            "item.pastel.azure_dike_belt.tooltip", "§7Greatly increases §9Azure Dike§7 recovery after getting hit");
        provider.add("item.pastel.azure_dike_provider.tooltip", "§7Grants §a%d§7 units of §9Azure Dike");
        provider.add("item.pastel.azure_dike_ring.tooltip", "§7Increases §9Azure Dike§7 charging");
        provider.add("item.pastel.azuresque_dike_core.tooltip", "§7Empowers other sources of §9Azure Dike");
        provider.add(
            "item.pastel.azuresque_dike_core.tooltip2", "§7Increases §9Azure Dike§7 charging and recovery speed");
        provider.add("item.pastel.azuresque_dike_core.tooltip3", "§7Doubles damage that cannot be absorbed");
        provider.add("item.pastel.bedrock_fishing_rod.tooltip", "Able to fish even in the most aggressive of fluids");
        provider.add("item.pastel.bident.postToolTip.ap", " %d%% Armor Piercing");
        provider.add("item.pastel.bident.postToolTip.disable", "Right Click in inventory to toggle ability");
        provider.add("item.pastel.bident.postToolTip.pp", " %d%% Enchantment Piercing");
        provider.add("item.pastel.bident.toolTip.disabled", "throwing effects disabled");
        provider.add("item.pastel.blackest_lotus.tooltip", "Its magical vibes almost make you vibe, too");
        provider.add("item.pastel.block_flooder.tooltip", "Fills empty spaces (caves, ravines, pits…)");
        provider.add("item.pastel.block_flooder.tooltip2", "Throw to use; Radius of 10 blocks");
        provider.add("item.pastel.block_flooder.tooltip3", "Takes matching blocks right out of your inventory");
        provider.add("item.pastel.block_flooder.tooltip4", "Alternatively uses Cobblestone");
        provider.add("item.pastel.bottle_of_decay_away.tooltip", "§2Apply directly to corrupted area");
        provider.add("item.pastel.bottle_of_fading.tooltip", "§2Glibbery");
        provider.add("item.pastel.bottle_of_failing.tooltip", "§e⚠ Hungry");
        provider.add("item.pastel.bottle_of_forfeiture.tooltip", "§4⚠ I hope I know what I am doing");
        provider.add("item.pastel.bottle_of_ruin.tooltip", "§4⚠ I will probably regret this, won't I?");
        provider.add("item.pastel.bottomless_bundle.tooltip.count", "%d / %d (%s stacks)");
        provider.add("item.pastel.bottomless_bundle.tooltip.count_of", "%d / %d of ");
        provider.add("item.pastel.bottomless_bundle.tooltip.empty", "Empty");
        provider.add(
            "item.pastel.bottomless_bundle.tooltip.enter_inventory", "%s that you pick up will get put in here");
        provider.add("item.pastel.bottomless_bundle.tooltip.locked", "Locked (Crouch-Use to unlock)");
        provider.add("item.pastel.bottomless_bundle.tooltip.voiding", "§7Additional items will be §cvoided");
        provider.add("item.pastel.brewers_handbook.tooltip", "§6§oBrewer's Handbook, extended edition");
        provider.add(
            "item.pastel.celestial_pocketwatch.tooltip.use_blocked_fixed_time",
            "Your clock seems dysfunctional in this strange dimension"
        );
        provider.add(
            "item.pastel.celestial_pocketwatch.tooltip.use_blocked_gamerule",
            "A great power prevents the use of your watch"
        );
        provider.add("item.pastel.celestial_pocketwatch.tooltip.working", "Gives you reign over Day and Night");
        provider.add("item.pastel.cheong.tooltip", "A fruit-based sweetener");
        provider.add(
            "item.pastel.circlet_of_arrogance.tooltip", "§7Grants you immense stat boosts at great cost if you die");
        provider.add("item.pastel.citrine_glass_arrow.tooltip", "-infinite piercing-");
        provider.add("item.pastel.clotted_cream.tooltip", "Removes all Status Effects");
        provider.add("item.pastel.clotted_cream.tooltip2", "Creamy and sweet");
        provider.add("item.pastel.constructors_staff.tooltip.crouch", "Crouch to match block instead of block state");
        provider.add("item.pastel.constructors_staff.tooltip.missing_ink", "missing Ink");
        provider.add("item.pastel.constructors_staff.tooltip.none_in_inventory", "none in inventory");
        provider.add("item.pastel.constructors_staff.tooltip.range", "Extends a block structure up to %s blocks");
        provider.add("item.pastel.cotton_cloud_boots.tooltip", "Creates soft clouds under your feet that enable you");
        provider.add("item.pastel.cotton_cloud_boots.tooltip2", "to walk in the air, as long as you are sprinting");
        provider.add("item.pastel.crafting_tablet.tooltip.crafting_recipe", "Press Use to Craft");
        provider.add(
            "item.pastel.crafting_tablet.tooltip.no_recipe", "Put items into the Crafting Grid to save a Recipe");
        provider.add("item.pastel.crafting_tablet.tooltip.pedestal_recipe", "Use in a Pedestal for Autocrafting");
        provider.add("item.pastel.crafting_tablet.tooltip.recipe", "%d %s");
        provider.add("item.pastel.crafting_tablet.tooltip.shift_to_view_gui", "Crouch-Use to open Crafting Grid");
        provider.add("item.pastel.creative_ink_assortment.tooltip", "§7A never ending well of Ink");
        provider.add("item.pastel.creative_only", "Creative Exclusive");
        provider.add("item.pastel.crescent_clock.tooltip", "Tells the current moon phase");
        provider.add("item.pastel.draconic_twinsword.tooltip", "Can be thrown - Crits airborne targets");
        provider.add("item.pastel.draconic_twinsword.tooltip2", "Swap hands to split");
        provider.add("item.pastel.draconic_twinsword.tooltip3", "On Miss: Hit yourself. Idiot.");
        provider.add("item.pastel.dragon_talon.tooltip", "Ignores §fInvincibility Frames");
        provider.add("item.pastel.dragon_talon.tooltip2", "Use offhand to throw and recall");
        provider.add("item.pastel.dragon_talon.tooltip3", "Swap hands to merge");
        provider.add("item.pastel.dreamflayer.tooltip", "The more armored the enemy is");
        provider.add("item.pastel.dreamflayer.tooltip2", "compared to you, the more damage it deals");
        provider.add("item.pastel.dreamflayer.tooltip.activated", "Overpowered. Crouch-Use to power down");
        provider.add("item.pastel.dreamflayer.tooltip.deactivated", "Crouch-Use to overpower (%s)");
        provider.add("item.pastel.enchantment_canvas.tooltip.bound_to", "§7Bound to ");
        provider.add(
            "item.pastel.enchantment_canvas.tooltip.not_bound", "§7Right click onto an item to swap Enchantments");
        provider.add(
            "item.pastel.enchantment_canvas.tooltip.not_bound2", "§7The Canvas will be bound to items of that type");
        provider.add("item.pastel.ender_splice.tooltip.bound_player", "§7Bound to %s§7. Use to teleport");
        provider.add("item.pastel.ender_splice.tooltip.bound_pos", "§7Bound to %d %d %d §7in %s§7. Use to teleport");
        provider.add("item.pastel.ender_splice.tooltip.unbound", "§7Use to bind to your current position");
        provider.add(
            "item.pastel.ender_splice.wrong_dimension",
            "This Ender Splices energy is not strong enough to teleport you across dimensions"
        );
        provider.add("item.pastel.ender_canvas.tooltip.portrait", "§7Portrait of %s§7.");
        provider.add("item.pastel.ender_canvas.tooltip.large_landscape", "§7Landscape painting of %d %d %d §7in %s§7.");
        provider.add(
            "item.pastel.ender_canvas.tooltip.small_landscape", "§7Small landscape painting of %d %d %d §7in %s§7.");
        provider.add("item.pastel.evernectar.tooltip", "You probably shouldn't drink this");
        provider.add(
            "item.pastel.everpromise_ribbon.tooltip", "Name it in an Anvil and color it using Pigment via Crafting");
        provider.add(
            "item.pastel.everpromise_ribbon.tooltip2", "then use it on an animal to promise it never ending affection");
        provider.add("item.pastel.exchanging_staff.tooltip.range", "Exchanges blocks up to %s blocks around it");
        provider.add("item.pastel.exchanging_staff.tooltip.target", "Target: %s");
        provider.add("item.pastel.ferocious_glass_crest_bident.tooltip", "Does not require Water to §fRiptide§r");
        provider.add(
            "item.pastel.ferocious_glass_crest_bident.tooltip2", "Tap §fRiptide§r when §fRiptide§r to §fRiptide§r");
        provider.add("item.pastel.ferocious_glass_crest_bident.tooltip3", "Damages creatures around you while flying");
        provider.add("item.pastel.fissure_plum.tooltip", "§onutty and aromatic");
        provider.add("item.pastel.fractal_glass_crest_bident.tooltip", "Will not leave your hands when thrown");
        provider.add(
            "item.pastel.fractal_glass_crest_bident.tooltip2", "Instead, creates a §fMirror Image§r of itself");
        provider.add("item.pastel.fractal_glass_crest_bident.tooltip3", "Shatters on impact");
        provider.add("item.pastel.freigeist.tooltip", "§4♥§r You wish you were an Angel? §4♡§r");
        provider.add("item.pastel.laurels_of_serenity.tooltip", "Reduces enemy aggression");
        provider.add("item.pastel.aether_graced_nectar_gloves.tooltip", "§7Uses §9Azure Dike§7 to protect you");
        provider.add("item.pastel.aether_graced_nectar_gloves.tooltip2", "§7from harmful status effects");
        provider.add("item.pastel.germinated_jade_vine_bulb.tooltip", "§7Grows on the bottom of Wooden Fences");
        provider.add("item.pastel.germinated_jade_vine_bulb.tooltip2", "§7Only grows when exposed to §fMoonlight");
        provider.add("item.pastel.germinated_jade_vine_bulb.tooltip3", "§e⚠ Dies in Sunlight");
        provider.add("item.pastel.gilded_book.tooltip.copy_enchantments", "Use in the Enchanter to copy Enchantments");
        provider.add("item.pastel.gilded_book.tooltip.copy_enchantments2", "from any desired item onto this book");
        provider.add("item.pastel.gilded_book.tooltip.enchantability", "High Enchantability");
        provider.add("item.pastel.glass_crest_crossbow.message.charge", "%d%% Overcharge active");
        provider.add(
            "item.pastel.glass_crest_crossbow.tooltip.how_to_overcharge",
            "§7Use while loaded and sneaking to §fOVERCHARGE"
        );
        provider.add("item.pastel.glass_crest_crossbow.tooltip.overcharged", "§7Overcharge: %d§7%%");
        provider.add("item.pastel.glass_crest_ultra_greatsword.tooltip", "§7Deals §f%d%%§7 of it's damage via Magic");
        provider.add("item.pastel.glass_crest_ultra_greatsword.tooltip2", "§7Charge to perform a ground slam");
        provider.add("item.pastel.glass_peach.tooltip", "§o§7Crunchy!");
        provider.add("item.pastel.gleaming_pin.tooltip", "§7Reveals nearby Mobs when hit");
        provider.add("item.pastel.glistering_jelly_tea.tooltip", "Temporarily increases your Hit Points");
        provider.add("item.pastel.gloves_of_dawns_grasp.tooltip", "Increases your Reach");
        provider.add("item.pastel.priscillent_spectacles.tooltip", "§7Powered by §bGlow Ink Sacs");
        provider.add("item.pastel.priscillent_spectacles.tooltip_with_ink", "§7Powered by %s or §bGlow Ink Sacs§r");
        provider.add("item.pastel.heartsingers_reward.tooltip", "Grants you extra Hearts");
        provider.add("item.pastel.hibernating_jade_vine_bulb.tooltip", "Kind of... deadish?");
        provider.add("item.pastel.imbrifer_cookbook.tooltip", "§9§oCookbook of Imbrifer - Blue glass and Aqua regia");
        provider.add(
            "item.pastel.imperial_cookbook.tooltip", "§c§oFeeding an Imperator - Imperial recipes third edition");
        provider.add("item.pastel.infused_beverage.tooltip.age", "%d days aged - %d %% Alc.");
        provider.add("item.pastel.infused_beverage.tooltip.age_composite", "%d years, %d days aged - %d %% Alc.");
        provider.add("item.pastel.infused_beverage.tooltip.age_years", "%d years aged - %d %% Alc.");
        provider.add("item.pastel.infused_beverage.tooltip.variant.camomillesque", "Camomillesque");
        provider.add("item.pastel.infused_beverage.tooltip.variant.verdigris_wine", "Verdigris Wine");
        provider.add("item.pastel.infused_beverage.tooltip.variant.night_cream", "Crème de la Mort");
        provider.add("item.pastel.infused_beverage.tooltip.variant.velvet_brandy", "Velvet Brandy");
        provider.add("item.pastel.infused_beverage.tooltip.variant.gatorwine", "Gatorwine");
        provider.add("item.pastel.infused_beverage.tooltip.variant.plum_cider", "Plum Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.plum_liquor", "Plum Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.advocaat", "Advocaat");
        provider.add("item.pastel.infused_beverage.tooltip.variant.ale", "Pale Ale");
        provider.add("item.pastel.infused_beverage.tooltip.variant.apple_cider", "Apple Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.apple_liquor", "Apple Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.artemisa", "Artemisa");
        provider.add("item.pastel.infused_beverage.tooltip.variant.beer", "Beer");
        provider.add("item.pastel.infused_beverage.tooltip.variant.berry_cider", "Berry Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.berry_liquor", "Berry Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.bloodlust_brew", "Bloodlust Brew");
        provider.add("item.pastel.infused_beverage.tooltip.variant.damassine", "Damassine");
        provider.add("item.pastel.infused_beverage.tooltip.variant.enchanted_apple_cider", "Enchanted Apple Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.fruit_shnaps", "Fruit Shnaps");
        provider.add("item.pastel.infused_beverage.tooltip.variant.gilded_bloom_liquor", "Gilded Bloom Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.gin", "Gin");
        provider.add("item.pastel.infused_beverage.tooltip.variant.glass_peach_cider", "Glass Peach Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.glass_peach_liquor", "Glass Peach Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.glow_berry_cider", "Glow Berry Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.glow_berry_liquor", "Glow Berry Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.golden_apple_cider", "Golden Apple Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.greener_grasses", "Greener Grasses");
        provider.add("item.pastel.infused_beverage.tooltip.variant.hare_bane_creme", "Hare Bane Creme");
        provider.add("item.pastel.infused_beverage.tooltip.variant.incubus_cream", "Incubus' Cream");
        provider.add("item.pastel.infused_beverage.tooltip.variant.lager", "Lager");
        provider.add("item.pastel.infused_beverage.tooltip.variant.malt_beer", "Malt Beer");
        provider.add("item.pastel.infused_beverage.tooltip.variant.mead", "Mead");
        provider.add("item.pastel.infused_beverage.tooltip.variant.mint_cream", "Crème de Menthe");
        provider.add("item.pastel.infused_beverage.tooltip.variant.mint_schnapps", "Mint Schnapps");
        provider.add("item.pastel.infused_beverage.tooltip.variant.moonshine", "Moonshine");
        provider.add("item.pastel.infused_beverage.tooltip.variant.myceylon_apple_juice", "Myceylon Apple Juice");
        provider.add("item.pastel.infused_beverage.tooltip.variant.myceylon_liquor", "Myceylon Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.poisonous_vodka", "Vodka");
        provider.add("item.pastel.infused_beverage.tooltip.variant.porter", "Porter");
        provider.add("item.pastel.infused_beverage.tooltip.variant.rabbit_poison", "Rabbit Poison");
        provider.add("item.pastel.infused_beverage.tooltip.variant.rum", "Rum");
        provider.add("item.pastel.infused_beverage.tooltip.variant.sake", "Sake");
        provider.add("item.pastel.infused_beverage.tooltip.variant.sarsaparilla", "Sarsaparilla");
        provider.add("item.pastel.infused_beverage.tooltip.variant.sawblade_holly_cider", "Sawblade Holly Cider");
        provider.add("item.pastel.infused_beverage.tooltip.variant.sawblade_holly_liquor", "Sawblade Holly Liquor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.spiked_mullet_wine", "Spiked Mullet Wine");
        provider.add("item.pastel.infused_beverage.tooltip.variant.tequila", "Tequila");
        provider.add("item.pastel.infused_beverage.tooltip.variant.unknown", "Undefinable flavor");
        provider.add("item.pastel.infused_beverage.tooltip.variant.vodka", "Vodka");
        provider.add("item.pastel.infused_beverage.tooltip.variant.crown_jewel", "Crown Jewel");
        provider.add("item.pastel.ink_storage.stores_ink_per_type", "§7Stores up to %d§7 Ink per type");
        provider.add("item.pastel.ink_storage.stores_up_to_ink_per_type", "§7Stores up to %d§7 of one type of Ink");
        provider.add("item.pastel.jade_jelly.tooltip", "Nutritious, as well as tasty");
        provider.add("item.pastel.jade_wine.tooltip.bloominess", "%d %% Bloominess");
        provider.add("item.pastel.jade_wine.tooltip.bloominess_sweetened", "%d %% Bloominess - Sweetened");
        provider.add("item.pastel.jade_wine.tooltip.preview", "Brew it with a combination of Bulbs & Petals");
        provider.add(
            "item.pastel.jade_wine.tooltip.preview2", "Petals ferment slower than Bulbs but reduce negative effects");
        provider.add("item.pastel.jeopardant.tooltip.damage", "§7Increased Damage on low HP (currently §a+%d%%§7)");
        provider.add("item.pastel.jeopardant.tooltip.damage_zero", "§7Increased Damage on low HP (currently §80%%§7)");
        provider.add("item.pastel.knowledge_gem.tooltip.stored_experience", "/ %d Experience stored");
        provider.add(
            "item.pastel.knowledge_gem.tooltip.use", "Use to drain, or crouch-use to store experience (%d/tick)");
        provider.add("item.pastel.lagoon_rod.tooltip", "The bobber indicates fishing in open waters");
        provider.add("item.pastel.least_black_lotus.tooltip", "Washed one too many times?");
        provider.add("item.pastel.logo_banner_pattern.desc", "Color Theory");
        provider.add("item.pastel.astrologer_banner_pattern.desc", "Astrologer");
        provider.add("item.pastel.velvet_astrologer_banner_pattern.desc", "Velvet Astrologer");
        provider.add("item.pastel.poisonbloom_banner_pattern.desc", "Poisonbloom");
        provider.add("item.pastel.deep_light_banner_pattern.desc", "Deep Light");
        provider.add("item.pastel.melochites_cookbook_vol_1.tooltip", "§3§oMelochites recipe compendium - Volume 1");
        provider.add("item.pastel.melochites_cookbook_vol_2.tooltip", "§3§oMelochites recipe compendium - Volume 2");
        provider.add("item.pastel.memory.tooltip.does_not_manifest", "Will not manifest in your lifetime");
        provider.add("item.pastel.memory.tooltip.entity_type", "§7Remembers a §f%s");
        provider.add("item.pastel.memory.tooltip.entity_type_broken_promise", "§7 Broken Promise of a §f%s");
        provider.add("item.pastel.memory.tooltip.extra_long_time_to_manifest", "Manifesting will take weeks");
        provider.add("item.pastel.memory.tooltip.long_time_to_manifest", "Manifesting will take quite a while");
        provider.add("item.pastel.memory.tooltip.medium_time_to_manifest", "May manifest the next few days");
        provider.add("item.pastel.memory.tooltip.named", "§7Remembers ");
        provider.add("item.pastel.memory.tooltip.named_broken_promise", "§7Broken Promise of ");
        provider.add("item.pastel.memory.tooltip.short_time_to_manifest", "Seems to manifest soon!");
        provider.add("item.pastel.memory.tooltip.unrecognizable_entity_type", "The memory is blurred");
        provider.add("item.pastel.memory.tooltip.unset_entity_type", "Forgotten");
        provider.add("item.pastel.mermaids_popcorn.tooltip", "Sweet & Salty");
        provider.add("item.pastel.midnight_aberration.tooltip.stable", "§oHeld together by mysterious force");
        provider.add("item.pastel.mob_head.tooltip.designer", "§7Head design by %s");
        provider.add("item.pastel.molten_rod.tooltip", "Its Bobber is hot enough to withstand Lava");
        provider.add("item.pastel.molten_rod.tooltip2", "Sets your catch on fire - or anything else");
        provider.add("item.pastel.moonstone_glass_arrow.tooltip", "-light speed-");
        provider.add("item.pastel.moonstruck_nectar.tooltip", "Repairs any damaged tool");
        provider.add("item.pastel.moonstruck_nectar.tooltip2", "Can be drunk for a burst of Regeneration");
        provider.add("item.pastel.mysterious_locket.tooltip", "Softly glows with pale white color");
        provider.add("item.pastel.mysterious_locket.tooltip_empty", "Features a small empty notch");
        provider.add("item.pastel.mysterious_locket.tooltip_socketed", "Socketed with a §fMoonstone Core");
        provider.add("item.pastel.natures_staff.tooltip", "§7Uses §aVegetal§7 to grow all sorts of plants");
        provider.add("item.pastel.natures_staff.tooltip_lure", "§7Also lures in nearby animals");
        provider.add(
            "item.pastel.natures_staff.tooltip_with_chance", "§7Uses §aVegetal§7 to grow all sorts of plants (%d%%§7)");
        provider.add(
            "item.pastel.natures_staff.tooltip_with_ink", "§7Uses %s or §aVegetal§7 to grow all sorts of plants");
        provider.add(
            "item.pastel.natures_staff.tooltip_with_ink_and_chance",
            "§7Uses %s or §aVegetal§7 to grow all sorts of plants (%d%%§7)"
        );
        provider.add("item.pastel.neat_ring.tooltip", "§7§oIt's neat");
        provider.add("item.pastel.nectardew_burgeon.tooltip", "§oExceedingly rare");
        provider.add("item.pastel.nectered_viognier.tooltip.preview", "Brew it with a combination of Bulbs & Peaches");
        provider.add(
            "item.pastel.nectered_viognier.tooltip.preview2",
            "Peaches ferment slower than Bulbs, but reduce negative effects"
        );
        provider.add("item.pastel.night_salts.tooltip", "Break to sleep");
        provider.add("item.pastel.soothing_bouquet.tooltip", "Smell to sleep");
        provider.add("item.pastel.concealing_oils.tooltip", "Apply by right-clicking unto food in your inventory");
        provider.add("item.pastel.concealing_oils.when_poisoned", "When Poisoned:");
        provider.add("item.pastel.nightdew_sprout.tooltip", "§oFragrant like tea");
        provider.add("item.pastel.nightfalls_blade.when_struck", "When Struck:");
        provider.add("item.pastel.onyx_glass_arrow.tooltip", "-gravity well-");
        provider.add("item.pastel.paintbrush.ability.block_coloring", "- Block Coloring");
        provider.add("item.pastel.paintbrush.ability.header", "Abilities:");
        provider.add("item.pastel.paintbrush.ability.ink_slinging", "- Ink Slinging");
        provider.add("item.pastel.paintbrush.ability.pedestal_triggering", "- Pedestal Triggering");
        provider.add("item.pastel.paintbrush.tooltip.select_color", "Crouch-Use to select a Color");
        provider.add("item.pastel.tuning_stamp.controls", "Right Click to start/apply links");
        provider.add("item.pastel.tuning_stamp.controls2", "Strike a block to clear the stamp");
        provider.add("item.pastel.tuning_stamp.controls3", "Shift to remain tuned to the last node");
        provider.add("item.pastel.tuning_stamp.tooltip", "Impresses links");
        provider.add("item.pastel.tuning_stamp.tooltip.linked", "Impressing %d");
        provider.add("item.pastel.tuning_stamp.tooltip.missing", "Impressing... nothing?");
        provider.add("item.pastel.tuning_stamp.tooltip2", "tuned to %d %d %d");
        provider.add("item.pastel.pedestal.tooltip.all_basic", "§bC§dM§eY§7 Variant");
        provider.add("item.pastel.pedestal.tooltip.basic_amethyst", "§dAmethyst§7 Variant");
        provider.add("item.pastel.pedestal.tooltip.basic_citrine", "§eCitrine§7 Variant");
        provider.add("item.pastel.pedestal.tooltip.basic_topaz", "§bTopaz§7 Variant");
        provider.add("item.pastel.pedestal.tooltip.moonstone", "§fMoonstone§7 Variant");
        provider.add("item.pastel.pedestal.tooltip.onyx", "§8Onyx§7 Variant");
        provider.add("item.pastel.perturbed_eye.tooltip", "Socket into an End Portal Frame");
        provider.add("item.pastel.pigment_palette.tooltip.target", "Supplies your Tools and Trinkets with Ink");
        provider.add("item.pastel.pipe_bomb.tooltip", "Highly damaging to creatures - does not damage blocks");
        provider.add("item.pastel.pipe_bomb.tooltip2", "Sets surroundings on fire");
        provider.add("item.pastel.pipe_bomb.tooltip3", "Use to Prime. Explodes after a few seconds!");
        provider.add("item.pastel.poisoners_handbook.tooltip", "A Blasphemer's Journal");
        provider.add("item.pastel.potion.faster_to_drink", "Faster to drink");
        provider.add("item.pastel.potion.slower_to_drink", "Slower to drink");
        provider.add("item.pastel.potion.tooltip.unidentifiable", "§0Blackened beyond recognition");
        provider.add("item.pastel.potion.tooltip.invalid", "§5[?]");
        provider.add("item.pastel.potion.tooltip.incurable", "§5[Incurable]");
        provider.add("item.pastel.potion_pendant.tooltip_max_level", "§7Supports Effects up to level ");
        provider.add(
            "item.pastel.potion_pendant.tooltip_not_full_count",
            "§7Fill with up to §f%d§7 Effects in the §fPotion Workshop"
        );
        provider.add(
            "item.pastel.potion_pendant.tooltip_not_full_one", "§7Fill with an Effect in the §fPotion Workshop");
        provider.add("item.pastel.potion_pendant.when_worn", "When Worn:");
        provider.add("item.pastel.primordial_lighter.tooltip", "Sets the world on fire");
        provider.add("item.pastel.puff_circlet.tooltip", "§7Uses §9Azure Dike§7 to protect you");
        provider.add("item.pastel.puff_circlet.tooltip2", "§7from Projectiles and Fall Damage");
        provider.add("item.pastel.enchanted_star_candy.tooltip", "Cures all negative status effects");
        provider.add("item.pastel.enchanted_star_candy.tooltip2", "Fully heals & fills hunger");
        provider.add("item.pastel.rabbit_cream_pie.tooltip", "§oGreat for sharing");
        provider.add("item.pastel.radiance_pin.tooltip", "§7Places temporary lights when in the Dark");
        provider.add("item.pastel.radiance_staff.tooltip", "§7Uses §eShimmerstone Gems§7 to place lights");
        provider.add("item.pastel.radiance_staff.tooltip.ink", "§7Uses %s or §eShimmerstone Gems§7 to place lights");
        provider.add("item.pastel.radiance_staff.tooltip2", "§7Use it on lights to toggle their light level");
        provider.add("item.pastel.radiance_staff.tooltip3", "§7Hold and aim at darkness to §eFloodlight§7");
        provider.add("item.pastel.reprise.tooltip.teleport", "Teleports up to %d blocks in a random direction");
        provider.add("item.pastel.aether_vestiges.tooltip", "§O ???");
        provider.add("item.pastel.restoration_tea.tooltip", "Cures all negative Status Effects and");
        provider.add("item.pastel.restoration_tea.tooltip2", "makes you immune to them for a short time");
        provider.add("item.pastel.restoration_tea.tooltip_milk", "With Milk");
        provider.add("item.pastel.ring_of_aetherial_grace.tooltip", "Lowers your gravity, but increases knockback");
        provider.add("item.pastel.ring_of_aetherial_grace.tooltip2", "Enables you to walk on water");
        provider.add(
            "item.pastel.ring_of_denser_steps.tooltip", "Grants you knockback resistance, but increases your gravity");
        provider.add("item.pastel.ring_of_denser_steps.tooltip2", "Enables you to sprint underwater");
        provider.add("item.pastel.ring_of_pursuit.tooltip", "Increases your Mining Speed");
        provider.add("item.pastel.sedatives.tooltip", "Medicine for frayed minds");
        provider.add("item.pastel.shooting_star.tooltip.hardened", "Hardened - will not break");
        provider.add("item.pastel.slushslide.tooltip", "Great for sharing!");
        provider.add("item.pastel.spawner.tooltip.max_nearby_entities", "Max Nearby Entities: %d");
        provider.add("item.pastel.spawner.tooltip.max_spawn_delay", "Max Spawn Delay: %d");
        provider.add("item.pastel.spawner.tooltip.min_spawn_delay", "Min Spawn Delay: %d");
        provider.add("item.pastel.spawner.tooltip.required_player_range", "Required Player Range: %d");
        provider.add("item.pastel.spawner.tooltip.spawn_count", "Spawn Count: %d");
        provider.add("item.pastel.spawner.tooltip.spawn_range", "Spawn Range: %d");
        provider.add("item.pastel.spawner.tooltip.unknown_mob", "Unknown Mob");
        provider.add("item.pastel.pastel_fishing_rods.tooltip", "Sometimes reels in living animals");
        provider.add("item.pastel.staff_of_remembrance.tooltip", "Tap a creature to recollect it into a §fMemory§r");
        provider.add("item.pastel.star_candy.tooltip", "Cures a single negative status effect");
        provider.add("item.pastel.suspicious_brew.tooltip", "What might be mixed together here?");
        provider.add("item.pastel.suspicious_brew.tooltip.preview", "Exponentially longer duration the higher Alc. %");
        provider.add("item.pastel.suspicious_brew.tooltip.preview2", "Effects depend on the flowers used");
        provider.add("item.pastel.takeoff_belt.tooltip", "Crouch to charge a High-Jump");
        provider.add("item.pastel.tooltip.explosives.modifiers", "Use the Pigment Pedestal to add modifiers");
        provider.add("item.pastel.tooltip.explosives.remaining_slots", "Modifiers used: %d/%d");
        provider.add("item.pastel.tooltip.loom_pattern_available", "✿ Usable as Banner Pattern");
        provider.add("item.pastel.topaz_glass_arrow.tooltip", "-impaling knockback-");
        provider.add(
            "item.pastel.total_capped_simple_pigment_energy_storage.tooltip", "§7Stores up to %d§7 Ink in total");
        provider.add("item.pastel.totem_pendant.tooltip", "§7One-time Protection from Death");
        provider.add("item.pastel.upgrade_efficiency.tooltip", "Slight chance for ingredients to not get used up");
        provider.add("item.pastel.upgrade_efficiency2.tooltip", "Noticeable chance for ingredients to not get used up");
        provider.add(
            "item.pastel.upgrade_experience.tooltip", "Slightly increases experience gain / decreases consumption");
        provider.add(
            "item.pastel.upgrade_experience2.tooltip", "Greatly increases experience gain / decreases consumption");
        provider.add("item.pastel.upgrade_speed.tooltip", "Slightly increases crafting speed");
        provider.add("item.pastel.upgrade_speed2.tooltip", "Noticeably increases crafting speed");
        provider.add("item.pastel.upgrade_speed3.tooltip", "Greatly increases crafting speed");
        provider.add("item.pastel.upgrade_yield.tooltip", "Slight chance to increase output");
        provider.add("item.pastel.upgrade_yield2.tooltip", "Noticeable chance to increase output");
        provider.add("item.pastel.varia_cookbook.tooltip", "TODO");
        provider.add("item.pastel.weeping_circlet.tooltip", "§7Filters oxygen out of the water and");
        provider.add("item.pastel.weeping_circlet.tooltip2", "§7greatly increases underwater maneuverability");
        provider.add("item.pastel.weeping_circlet.tooltip3", "§7Heals your nearby §fAxolotls");
        provider.add("item.pastel.wet_lava_sponge.tooltip", "§e⚠ Caution: Hot surface ⚠");
        provider.add("item.pastel.whispy_circlet.tooltip", "§7Prevents Spawning of Phantoms");
        provider.add("item.pastel.whispy_circlet.tooltip2", "§7Reduces the duration of Negative Effects");
        provider.add("item.pastel.whispy_circlet.tooltip3", "§7Sleeping fully heals");
        provider.add("item.pastel.workstaff.gui.1x1", "Select 1x1 mining mode");
        provider.add("item.pastel.workstaff.gui.3x3", "Select 3x3 mining mode");
        provider.add("item.pastel.workstaff.gui.5x5", "Select 5x5 mining mode");
        provider.add("item.pastel.workstaff.gui.disable_projectiles", "Disable Ranged Mining");
        provider.add("item.pastel.workstaff.gui.disable_right_click_actions", "Disable Right-Click");
        provider.add("item.pastel.workstaff.gui.enable_projectiles", "Enable Ranged Mining");
        provider.add("item.pastel.workstaff.gui.enable_right_click_actions", "Enable Right-Click");
        provider.add("item.pastel.workstaff.gui.enchantment_group", "Choose Enchantment");
        provider.add("item.pastel.workstaff.gui.fortune", "Switch to Fortune");
        provider.add("item.pastel.workstaff.gui.range_group", "Choose AoE");
        provider.add("item.pastel.workstaff.gui.resonance", "Switch to Resonance");
        provider.add("item.pastel.workstaff.gui.silk_touch", "Switch to Silk Touch");
        provider.add("item.pastel.workstaff.message.1x1", "Selected 1x1 mining mode");
        provider.add("item.pastel.workstaff.message.3x3", "Selected 3x3 mining mode");
        provider.add("item.pastel.workstaff.message.5x5", "Selected 5x5 mining mode");
        provider.add("item.pastel.workstaff.message.disabled_projectiles", "Disabled Projectiles");
        provider.add(
            "item.pastel.workstaff.message.disabled_right_click_actions", "Disabled Tilling, Stripping and Flattening");
        provider.add("item.pastel.workstaff.message.enabled_projectiles", "Enabled Projectiles");
        provider.add(
            "item.pastel.workstaff.message.enabled_right_click_actions", "Enabled Tilling, Stripping and Flattening");
        provider.add("item.pastel.workstaff.message.fortune", "Switched Enchantment to Fortune");
        provider.add("item.pastel.workstaff.message.resonance", "Switched Enchantment to Resonance");
        provider.add("item.pastel.workstaff.message.silk_touch", "Switched Enchantment to Silk Touch");
        provider.add(
            "item.pastel.workstaff.message.would_result_in_conflicting_enchantments",
            "Switching enchantments would result in a conflict"
        );
        provider.add("item.pastel.workstaff.message.already_has_the_enchantment", "Enchantment is already present");
        provider.add("item.pastel.workstaff.tooltip.mining_range", "%dx%d area mining active (§fWhite Ink§r)");
        provider.add("item.pastel.workstaff.tooltip.projectile", "Ranged mining active (§fWhite Ink§r)");
        provider.add("item.pastel.workstaff.tooltip.projectiles_disabled", "Ranged Mining disabled");
        provider.add("item.pastel.workstaff.tooltip.right_click_actions", "Able to Till, Strip and Flatten");
        provider.add(
            "item.pastel.workstaff.tooltip.right_click_actions_disabled", "Tilling, Stripping and Flattening disabled");
        provider.add("item.pastel.workstaff", "Workstaff");

        provider.add("item.pastel.midnight_aberration.cloaked", "The Perfect Compound");
        provider.add("item.pastel.music_disc_credits.desc", "Radiarc - Irrelevance Fading");
        provider.add("item.pastel.music_disc_divinity.desc", "Proper Motions - Everreflective");
        provider.add("item.pastel.music_disc_discovery.desc", "DaveJF - The Mist");
    }
}
