package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.item.ItemDamageImmunity;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;

public class PastelItemDamageImmunities {

    public static void registerDefaultItemStackImmunities() {
        ItemDamageImmunity.registerImmunity(Items.NETHER_STAR, DamageTypeTags.IS_FIRE);
        ItemDamageImmunity.registerImmunity(Items.NETHER_STAR, DamageTypeTags.IS_EXPLOSION);

        ItemDamageImmunity.registerImmunity(PastelBlocks.CRACKED_END_PORTAL_FRAME, DamageTypeTags.IS_EXPLOSION);
        ItemDamageImmunity.registerImmunity(PastelItems.DOOMBLOOM_SEED, DamageTypeTags.IS_EXPLOSION);
    }

}
