package earth.terrarium.pastel.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class PrintConfigCommand {

    public static void register(LiteralCommandNode<CommandSourceStack> root) {
        LiteralCommandNode<CommandSourceStack> config = Commands
            .literal("config")
            .executes((context) -> execute(context.getSource()))
            .build();
        root.addChild(config);
    }

    private static int execute(CommandSourceStack source) {
        send(
            source,
            "EndermanHoldingEnderTreasureChance: " + PastelCommon.CONFIG.EndermanHoldingEnderTreasureChance + " (" + PastelCommon.CONFIG.EndermanHoldingEnderTreasureInEndChance + " in the End)"
        );

        send(source, "ShootingStarWorlds: " + PastelCommon.CONFIG.ShootingStarWorlds);
        send(source, "StormStonesWorlds: " + PastelCommon.CONFIG.StormStonesWorlds);
        send(source, "StormStonesChance: " + PastelCommon.CONFIG.StormStonesChance);
        send(source, "ShootingStarChance: " + PastelCommon.CONFIG.ShootingStarChance);
        send(source, "VanillaRecipeCraftingTimeTicks: " + PastelCommon.CONFIG.VanillaRecipeCraftingTimeTicks);

        send(
            source,
            "Decay tick rates: " + PastelCommon.CONFIG.FadingDecayTickRate + ", " + PastelCommon.CONFIG.FailingDecayTickRate + ", " + PastelCommon.CONFIG.RuinDecayTickRate + ", " + PastelCommon.CONFIG.ForfeitureDecayTickRate
        );
        send(
            source,
            "Decay pickup ability: " + PastelCommon.CONFIG.CanBottleUpFading + ", " + PastelCommon.CONFIG.CanBottleUpFailing + ", " + PastelCommon.CONFIG.CanBottleUpRuin + ", " + PastelCommon.CONFIG.CanBottleUpForfeiture
        );
        send(
            source,
            "Decay can destroy block entities: " + PastelCommon.CONFIG.FadingCanDestroyBlockEntities + ", " + PastelCommon.CONFIG.FailingCanDestroyBlockEntities + ", " + PastelCommon.CONFIG.RuinCanDestroyBlockEntities + ", " + PastelCommon.CONFIG.ForfeitureCanDestroyBlockEntities
        );

        send(source, "GlowVisionGogglesDuration: " + PastelCommon.CONFIG.GlowVisionGogglesDuration);
        send(source, "OmniAcceleratorPvP: " + PastelCommon.CONFIG.OmniAcceleratorPvP);

        send(
            source,
            "Bedrock Armor Protection: " + PastelCommon.CONFIG.BedrockArmorHelmetProtection + ", " + PastelCommon.CONFIG.BedrockArmorLeggingsProtection + ", " + PastelCommon.CONFIG.BedrockArmorChestplateProtection + ", " + PastelCommon.CONFIG.BedrockArmorBootsProtection + " (Toughness: " + PastelCommon.CONFIG.BedrockArmorToughness + ", Knockback Resistance: " + PastelCommon.CONFIG.BedrockArmorKnockbackResistance + ")"
        );
        send(
            source,
            "Crystal Armor Protection: " + PastelCommon.CONFIG.CrystalArmorHelmetProtection + ", " + PastelCommon.CONFIG.CrystalArmorChestplateProtection + ", " + PastelCommon.CONFIG.CrystalArmorLeggingsProtection + ", " + PastelCommon.CONFIG.CrystalArmorBootsProtection + " (Toughness: " + PastelCommon.CONFIG.CrystalArmorToughness + ", Knockback Resistance: " + PastelCommon.CONFIG.CrystalArmorKnockbackResistance + ")"
        );

        return 0;
    }

    private static void send(CommandSourceStack source, String s) {
        source.sendSuccess(() -> Component.literal(s), false);
    }

}
