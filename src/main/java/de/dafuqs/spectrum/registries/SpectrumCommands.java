package de.dafuqs.spectrum.registries;

import com.mojang.brigadier.tree.LiteralCommandNode;
import de.dafuqs.spectrum.commands.DumpRegistriesCommand;
import de.dafuqs.spectrum.commands.DumpTagsCommand;
import de.dafuqs.spectrum.commands.PrimordialFireCommand;
import de.dafuqs.spectrum.commands.PrintConfigCommand;
import de.dafuqs.spectrum.commands.ResetShadersCommand;
import de.dafuqs.spectrum.commands.SanityCommand;
import de.dafuqs.spectrum.commands.ShootingStarCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class SpectrumCommands {

	public static void register(RegisterCommandsEvent event) {
        LiteralCommandNode<CommandSourceStack> spectrumNode = Commands.literal("spectrum").build();
        ShootingStarCommand.register(spectrumNode);
        SanityCommand.register(spectrumNode);
        PrintConfigCommand.register(spectrumNode);
        PrimordialFireCommand.register(spectrumNode);
        DumpRegistriesCommand.register(spectrumNode);
        DumpTagsCommand.register(spectrumNode);
        ResetShadersCommand.register(spectrumNode);

        event.getDispatcher().getRoot().addChild(spectrumNode);
	}
}
