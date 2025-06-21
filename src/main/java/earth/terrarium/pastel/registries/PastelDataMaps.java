package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.*;
import earth.terrarium.pastel.api.interaction.projectile_behavior.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.datamaps.*;

public class PastelDataMaps {
	public static void register() {}
	
	public static final DataMapType<Item, ItemProjectileBehavior> PROJECTILE_BEHAVIOR = DataMapType.builder(
			PastelCommon.ofPastel("projectile_behavior"),
			Registries.ITEM,
			ItemProjectileBehaviorRegistry.CODEC
	).build();
}
