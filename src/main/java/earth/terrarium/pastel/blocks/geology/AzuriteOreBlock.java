package earth.terrarium.pastel.blocks.geology;

import com.cmdpro.databank.hidden.types.BlockHiddenType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.mixin.accessors.ExperienceDroppingBlockAccessor;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class AzuriteOreBlock extends CloakedOreBlock {
    public static final MapCodec<AzuriteOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                                                                              IntProvider.codec(0, 10)
                                                                                                                         .fieldOf("experience")
                                                                                                                         .forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getXpRange()), propertiesCodec()
                                                                                                          )
                                                                                                          .apply(
                                                                                                              instance,
                                                                                                              AzuriteOreBlock::new
                                                                                                          ));

    public static final Property<Boolean> WARDED = BooleanProperty.create("warded");

    public AzuriteOreBlock(IntProvider experienceDropped, Properties settings) {
        super(experienceDropped, settings);
        this.registerDefaultState(getStateDefinition().any()
                                                      .setValue(WARDED, true));
    }

    @Override
    public MapCodec<? extends AzuriteOreBlock> codec() {
        return CODEC;
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        if (world.isClientSide() && !entity.isSteppingCarefully() && world.random.nextInt(3) == 0) {
            ParticleHelper.playParticleAroundBlockSides(
                world, PastelParticleTypes.AZURE_MOTE_SMALL, pos, new Direction[]{Direction.UP}, 1, Vec3.ZERO);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);

        if (world.isClientSide() && BlockHiddenType.isVisibleClient(state)) {
            ParticleHelper.playTriangulatedParticle(
                world, PastelParticleTypes.AZURE_AURA, 1, false, Vec3.ZERO, 0, true, Vec3.atCenterOf(pos),
                new Vec3(
                    0, 0.08D + world.getRandom()
                                    .nextDouble() * 0.04, 0
                )
            );
            ParticleHelper.playParticleAroundBlockSides(
                world, PastelParticleTypes.AZURE_MOTE_SMALL, pos, Direction.values(), 3, Vec3.ZERO);
        }

        return state;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        super.attack(state, world, pos, player);

        if (world.isClientSide() && BlockHiddenType.isVisible(state, player)) {
            ParticleHelper.playParticleAroundBlockSides(
                world, PastelParticleTypes.AZURE_MOTE, pos, Direction.values(), 1, Vec3.ZERO);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        if (!BlockHiddenType.isVisibleClient(state)) return;

        if (world.getRandom()
                 .nextFloat() >= 0.02) return;

        ParticleHelper.playTriangulatedParticle(
            world, PastelParticleTypes.AZURE_AURA, 5, false, new Vec3(2, 0, 2), 0,
            true, Vec3.atLowerCornerOf(pos),
            new Vec3(0, 0.07D + random.nextDouble() * 0.06, 0)
        );
        ParticleHelper.playParticleAroundBlockSides(
            world, PastelParticleTypes.AZURE_MOTE, pos, Direction.values(),
            random.nextIntBetweenInclusive(1, 3), Vec3.ZERO
        );
        world.playSound(null, pos, PastelSounds.SOFT_HUM, SoundSource.BLOCKS, 1F, random.nextFloat() * 0.5F + 1F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WARDED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateDefinition().any()
                                   .setValue(WARDED, false);
    }

}
