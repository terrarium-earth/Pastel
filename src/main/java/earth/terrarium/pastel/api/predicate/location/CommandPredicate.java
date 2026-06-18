package earth.terrarium.pastel.api.predicate.location;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicBoolean;

public record CommandPredicate(String command) implements CommandSource {

    public static final Codec<CommandPredicate> CODEC = Codec.STRING
        .xmap(
            CommandPredicate::new,
            CommandPredicate::command
        );

    public static final StreamCodec<ByteBuf, CommandPredicate> STREAM_CODEC = ByteBufCodecs.STRING_UTF8
        .map(
            CommandPredicate::new,
            CommandPredicate::command
        );

    public boolean test(ServerLevel world, BlockPos pos) {
        AtomicBoolean passed = new AtomicBoolean(false);
        MinecraftServer minecraftServer = world.getServer();
        CommandSourceStack serverCommandSource = new CommandSourceStack(
            this,
            Vec3.atCenterOf(pos),
            Vec2.ZERO,
            world,
            2,
            "SpectrumCommandWorldCondition",
            world
                .getBlockState(pos)
                .getBlock()
                .getName(),
            minecraftServer,
            null
        )
            .withCallback((successful, returnValue) -> passed.set(returnValue > 0));
        minecraftServer
            .getCommands()
            .performPrefixedCommand(serverCommandSource, command);
        return passed.get();
    }

    @Override
    public void sendSystemMessage(Component message) {
    }

    @Override
    public boolean acceptsSuccess() {
        return false;
    }

    @Override
    public boolean acceptsFailure() {
        return false;
    }

    @Override
    public boolean shouldInformAdmins() {
        return false;
    }

}
