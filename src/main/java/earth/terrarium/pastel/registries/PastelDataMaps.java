package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.*;
import earth.terrarium.pastel.api.interaction.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.registries.datamaps.*;

public class PastelDataMaps {
	public static final DataMapType<Item, ItemProjectileBehavior> PROJECTILE_BEHAVIOR = DataMapType.builder(
			SpectrumCommon.ofSpectrumDefaulted("projectile_behavior"),
			Registries.ITEM,
			
	)
}
