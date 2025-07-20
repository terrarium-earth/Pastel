package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.interaction.projectile_behavior.ItemProjectileBehavior;
import earth.terrarium.pastel.api.interaction.projectile_behavior.ItemProjectileBehaviorRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class PastelDataMaps {
    public static void register() {
    }

    public static final DataMapType<Item, ItemProjectileBehavior> PROJECTILE_BEHAVIOR = DataMapType.builder(
                                                                                                       PastelCommon.ofPastel("projectile_behavior"),
                                                                                                       Registries.ITEM,
                                                                                                       ItemProjectileBehaviorRegistry.CODEC
                                                                                                   )
                                                                                                   .build();
}
