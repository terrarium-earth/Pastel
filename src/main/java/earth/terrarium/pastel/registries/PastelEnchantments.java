package earth.terrarium.pastel.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

import static earth.terrarium.pastel.PastelCommon.locate;

@SuppressWarnings("unused")
public class PastelEnchantments {

    public static List<ResourceKey<Enchantment>> PASTEL_ENCHANTMENTS = new ArrayList<>();

    // Increase the chance to reel in entities instead of fishing loot
    public static final ResourceKey<Enchantment> BIG_CATCH = of("big_catch");

    // Increases drop chance of <1 loot drops
    public static final ResourceKey<Enchantment> CLOVERS_FAVOR = of("clovers_favor");

    // Drops mob equipment on hit (and players, but way less often)
    public static final ResourceKey<Enchantment> DISARMING = of("disarming");

    // Drops more XP on kill
    public static final ResourceKey<Enchantment> EXUBERANCE = of("exuberance");

    // Increased damage if the enemy has full health
    public static final ResourceKey<Enchantment> FIRST_STRIKE = of("first_strike");

    // applies smelting recipe before dropping items after mining
    public static final ResourceKey<Enchantment> FOUNDRY = of("foundry");

    // Increased damage when landing a critical hit
    public static final ResourceKey<Enchantment> IMPROVED_CRITICAL = of("improved_critical");

    // Make tools not use up durability
    public static final ResourceKey<Enchantment> INDESTRUCTIBLE = of("indestructible");

    // Decreases mining speed, but increases with each mined block of the same type
    public static final ResourceKey<Enchantment> INERTIA = of("inertia");

    // prevents mining & movement slowdowns
    public static final ResourceKey<Enchantment> INEXORABLE = of("inexorable");

    // don't drop items into the world, add to inv instead
    public static final ResourceKey<Enchantment> INVENTORY_INSERTION = of("inventory_insertion");

    // Kills silverfish when mining infested blocks
    public static final ResourceKey<Enchantment> PEST_CONTROL = of("pest_control");

    // increased mining speed for very hard blocks
    public static final ResourceKey<Enchantment> RAZING = of("razing");

    // Silk Touch, just for different blocks
    public static final ResourceKey<Enchantment> RESONANCE = of("resonance");

    // Increase luck when fishing
    public static final ResourceKey<Enchantment> SERENDIPITY_REEL = of("serendipity_reel");

    // ItemStacks with this enchantment are not destroyed by cactus, fire, lava, ...
    public static final ResourceKey<Enchantment> STEADFAST = of("steadfast");

    // Increases attack speed
    public static final ResourceKey<Enchantment> TIGHT_GRIP = of("tight_grip");

    // Drops mob heads
    public static final ResourceKey<Enchantment> TREASURE_HUNTER = of("treasure_hunter");

    // Voids all items mined
    public static final ResourceKey<Enchantment> VOIDING = of("voiding");

    private static ResourceKey<Enchantment> of(String id) {
        var result = ResourceKey.create(Registries.ENCHANTMENT, locate(id));
        PASTEL_ENCHANTMENTS.add(result);
        return result;
    }

}
