package earth.terrarium.pastel.capabilities;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.ItemPickupListener;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class PastelCapabilities {

    public static final class Ink {
        //TODO convert ink stuff to caps
    }

    public static final class Pickup {

        public static final ItemCapability<ItemPickupListener, Void> ITEM = ItemCapability.createVoid(
                PastelCommon.locate("pickup_listener"), ItemPickupListener.class
        );

        public static final EntityCapability<ItemPickupListener, Void> ENTITY = EntityCapability.createVoid(
                PastelCommon.locate("pickup_listener"), ItemPickupListener.class
        );
    }

    public static final class Misc {

        public static final ItemCapability<AreaMiningHandler, Void> MINING = ItemCapability.createVoid(
            PastelCommon.locate("area_mining"), AreaMiningHandler.class);

        public static final ItemCapability<SplitDamageHandler, Void> SPLIT_DAMAGE = ItemCapability.createVoid(
            PastelCommon.locate("split_damage"), SplitDamageHandler.class);

        public static final ItemCapability<ExperienceHandler, HolderLookup.Provider> XP = ItemCapability.create(
            PastelCommon.locate("experience"), ExperienceHandler.class, HolderLookup.Provider.class);
    }
}
