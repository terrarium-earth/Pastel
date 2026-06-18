package earth.terrarium.pastel.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DumpRegistriesCommand {

    public static void register(LiteralCommandNode<CommandSourceStack> root) {
        LiteralCommandNode<CommandSourceStack> dumpRegistries = Commands
            .literal("dump_registries")
            .requires((source) -> source.hasPermission(2))
            .executes(
                (context) -> execute(context.getSource())
            )
            .build();
        root.addChild(dumpRegistries);
    }

    private static int execute(CommandSourceStack source) {
        File directory = FMLPaths.GAMEDIR
            .get()
            .resolve("registry_dump")
            .toFile();

        source
            .registryAccess()
            .registries()
            .forEach(registry -> {
                File file = new File(
                    directory,
                    registry
                        .key()
                        .location()
                        .toString()
                        .replace(":", "/") + ".txt"
                );
                file
                    .getParentFile()
                    .mkdirs();
                try {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    for (
                        ResourceKey<?> e : registry
                            .value()
                            .registryKeySet()
                    ) {
                        writer
                            .write(
                                e
                                    .location()
                                    .toString()
                            );
                        writer.write(System.lineSeparator());
                    }
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        source.sendSystemMessage(Component.literal("Registries exported to directory 'registry_dump'"));

        return 0;
    }

}
