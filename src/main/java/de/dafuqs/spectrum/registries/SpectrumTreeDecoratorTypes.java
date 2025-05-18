package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.worldgen.tree_decorators.FrondsDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class SpectrumTreeDecoratorTypes {
	
	public static final TreeDecoratorType<?> FRONDS = register("fronds", new TreeDecoratorType<>(FrondsDecorator.CODEC));
	
	public static void register() {
	
	}
	
	private static TreeDecoratorType<?> register(String id, TreeDecoratorType<?> treeDecoratorType) {
		return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, SpectrumCommon.locate(id), treeDecoratorType);
	}
	
}
