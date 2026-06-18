package earth.terrarium.pastel.blocks.imbrifer;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.imbrifer.flora.WeepingGalaFrondsBlock;
import earth.terrarium.pastel.helpers.level.BlockReference;
import earth.terrarium.pastel.registries.PastelLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WeepingGalaFrondsTipBlock extends WeepingGalaFrondsBlock {

    public static final MapCodec<WeepingGalaFrondsTipBlock> CODEC = simpleCodec(WeepingGalaFrondsTipBlock::new);

    public static final EnumProperty<Form> FORM = EnumProperty.create("form", Form.class);

    public WeepingGalaFrondsTipBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FORM, Form.TIP));
    }

    @Override
    public MapCodec<? extends WeepingGalaFrondsTipBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(FORM) != Form.TIP;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1F) {
            var reference = BlockReference.of(state, pos);
            var form = reference.getProperty(FORM);

            if (form == Form.SPRIG) {
                reference.setProperty(FORM, Form.RESIN);
                reference.update(world);
            } else {
                for (
                    ItemStack rareStack : getResinStacks(
                        state,
                        world,
                        pos,
                        ItemStack.EMPTY,
                        PastelLootTables.WEEPING_GALA_SPRIG_RESIN
                    )
                ) {
                    popResource(world, pos, rareStack);
                }
                world
                    .playSound(
                        null,
                        pos,
                        SoundEvents.BEEHIVE_DRIP,
                        SoundSource.BLOCKS,
                        1,
                        0.9F + random.nextFloat() * 0.2F
                    );
                reference.setProperty(FORM, Form.SPRIG);
                reference.update(world);
            }
        }
    }

    @Override
    public InteractionResult useWithoutItem(
        BlockState state,
        Level world,
        BlockPos pos,
        Player player,
        BlockHitResult hit
    ) {
        var reference = BlockReference.of(state, pos);
        if (reference.getProperty(FORM) == Form.RESIN) {
            if (!world.isClientSide()) {
                for (
                    ItemStack rareStack : getResinStacks(
                        state,
                        (ServerLevel) world,
                        pos,
                        player.getMainHandItem(),
                        PastelLootTables.WEEPING_GALA_SPRIG_RESIN
                    )
                ) {
                    popResource(world, pos, rareStack);
                }
            }
            world
                .playSound(
                    null,
                    pos,
                    SoundEvents.BEEHIVE_SHEAR,
                    SoundSource.BLOCKS,
                    1,
                    0.9F + world
                        .getRandom()
                        .nextFloat() * 0.2F
                );
            reference.setProperty(FORM, Form.SPRIG);
            reference.update(world);

            return InteractionResult.sidedSuccess(world.isClientSide());
        }

        return InteractionResult.PASS;
    }

    public static List<ItemStack> getResinStacks(
        BlockState state,
        ServerLevel world,
        BlockPos pos,
        ItemStack stack,
        ResourceKey<LootTable> lootTableKey
    ) {
        var builder = (new LootParams.Builder(world))
            .withParameter(LootContextParams.BLOCK_STATE, state)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .withParameter(LootContextParams.TOOL, stack);

        LootTable lootTable = world
            .getServer()
            .reloadableRegistries()
            .getLootTable(lootTableKey);
        return lootTable.getRandomItems(builder.create(LootContextParamSets.BLOCK));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORM);
    }

    public enum Form implements StringRepresentable {
        TIP("tip", 0),
        SPRIG("sprig", 11),
        RESIN("resin", 12);

        private final String name;

        private final int luminance;

        Form(String name, int luminance) {
            this.name = name;
            this.luminance = luminance;
        }

        public int getLuminance() {
            return this.luminance;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
