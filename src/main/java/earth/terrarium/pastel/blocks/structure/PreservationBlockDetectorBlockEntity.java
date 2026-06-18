package earth.terrarium.pastel.blocks.structure;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PreservationBlockDetectorBlockEntity extends BlockEntity implements CommandSource {

    protected @Nullable BlockState detectedState; // detect this block. Null: any block

    protected @Nullable BlockState changeIntoState;

    // change into this once triggered. Null: stay as is (can be used again and again)
    protected List<String> commands = List.of(); // get executed in order. First command that fails ends the chain

    public PreservationBlockDetectorBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.PRESERVATION_BLOCK_DETECTOR.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        if (this.changeIntoState != null) {
            nbt.putString("change_into_state", BlockStateParser.serialize(this.changeIntoState));
        }
        if (this.detectedState != null) {
            nbt.putString("detected_state", BlockStateParser.serialize(this.detectedState));
        }
        if (!this.commands.isEmpty()) {
            ListTag commandList = new ListTag();
            for (
                String s : this.commands
            ) {
                commandList.add(StringTag.valueOf(s));
            }
            nbt.put("commands", commandList);
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        this.commands = new ArrayList<>();
        this.changeIntoState = null;
        this.detectedState = null;
        if (nbt.contains("commands", Tag.TAG_LIST)) {
            for (
                Tag e : nbt.getList("commands", Tag.TAG_STRING)
            ) {
                this.commands.add(e.getAsString());
            }
        }
        if (nbt.contains("change_into_state", Tag.TAG_STRING)) {
            try {
                this.changeIntoState = BlockStateParser
                    .parseForBlock(
                        BuiltInRegistries.BLOCK.asLookup(),
                        nbt
                            .getString(
                                "change_into_state"
                            ),
                        false
                    )
                    .blockState();
            } catch (CommandSyntaxException ignored) {
            }
        }
        if (nbt.contains("detected_state", Tag.TAG_STRING)) {
            try {
                this.detectedState = BlockStateParser
                    .parseForBlock(
                        BuiltInRegistries.BLOCK.asLookup(),
                        nbt
                            .getString(
                                "detected_state"
                            ),
                        false
                    )
                    .blockState();
            } catch (CommandSyntaxException ignored) {
            }
        }
    }

    public void triggerForNeighbor(BlockState state) {
        if ((this.detectedState == null || state.equals(this.detectedState)) && this
            .getLevel() instanceof ServerLevel serverWorld) {
            this.execute(serverWorld);
        }
    }

    public void execute(ServerLevel serverWorld) {
        MinecraftServer minecraftServer = serverWorld.getServer();
        AtomicBoolean failed = new AtomicBoolean(false);
        if (!this.commands.isEmpty()) {
            CommandSourceStack serverCommandSource = new CommandSourceStack(
                this,
                Vec3.atCenterOf(PreservationBlockDetectorBlockEntity.this.worldPosition),
                Vec2.ZERO,
                serverWorld,
                2,
                "PreservationBlockDetector",
                this
                    .getLevel()
                    .getBlockState(this.worldPosition)
                    .getBlock()
                    .getName(),
                minecraftServer,
                null
            )
                .withCallback((success, returnValue) -> {
                    if (returnValue < 1) failed.set(true);
                });
            for (
                String command : this.commands
            ) {
                minecraftServer
                    .getCommands()
                    .performPrefixedCommand(serverCommandSource, command);
                if (failed.get()) {
                    break;
                }
            }
            if (this.changeIntoState != null) {
                serverWorld.setBlockAndUpdate(this.worldPosition, this.changeIntoState);
            }
        }
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
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
