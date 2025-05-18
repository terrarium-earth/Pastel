package de.dafuqs.spectrum.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import de.dafuqs.spectrum.registries.client.SpectrumShaders;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.fml.util.thread.EffectiveSide;

public class ResetShadersCommand {
	
	public static void register(LiteralCommandNode<CommandSourceStack> root) {
		LiteralCommandNode<CommandSourceStack> config = Commands.literal("resetShaders").executes((context) -> {
			if (EffectiveSide.get().isClient())
				execute();
			return 0;
		}).build();
		root.addChild(config);
	}
	
	private static void execute() {
		SpectrumShaders.clearDimensionShaders();
	}
}
