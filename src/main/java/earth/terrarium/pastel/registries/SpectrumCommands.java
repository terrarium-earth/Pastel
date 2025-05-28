package earth.terrarium.pastel.registries;

import com.mojang.brigadier.tree.LiteralCommandNode;
import earth.terrarium.pastel.commands.DumpRegistriesCommand;
import earth.terrarium.pastel.commands.DumpTagsCommand;
import earth.terrarium.pastel.commands.PrimordialFireCommand;
import earth.terrarium.pastel.commands.PrintConfigCommand;
import earth.terrarium.pastel.commands.ResetShadersCommand;
import earth.terrarium.pastel.commands.SanityCommand;
import earth.terrarium.pastel.commands.ShootingStarCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class SpectrumCommands {

	public static void register(RegisterCommandsEvent event) {
        LiteralCommandNode<CommandSourceStack> spectrumNode = Commands.literal("pastel").build();
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
