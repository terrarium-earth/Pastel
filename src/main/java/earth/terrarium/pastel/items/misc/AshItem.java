package earth.terrarium.pastel.items.misc;

import earth.terrarium.pastel.helpers.level.BlockReference;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import earth.terrarium.pastel.items.ItemWithTooltip;
import net.minecraft.world.item.context.UseOnContext;

import java.util.Optional;

public class AshItem extends ItemWithTooltip {

    public AshItem(Properties settings,  String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var world = context.getLevel();
        var random = world.getRandom();
        var stack = context.getItemInHand();
        var reference = BlockReference.of(world, context.getClickedPos());

        if (!reference.isOf(PastelBlocks.BLACKSLAG.get()))
            return InteractionResult.FAIL;

        world.setBlockAndUpdate(
            reference.pos, PastelBlocks.ASHEN_BLACKSLAG.get()
                                                       .defaultBlockState()
        );

        if (!world.isClientSide()) {
            world.addDestroyBlockEffect(
                reference.pos, PastelBlocks.ASH.get()
                                               .defaultBlockState()
            );
            world.playLocalSound(
                reference.pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 0.5F, 0.5F + random.nextFloat() * 0.5F, true);
        }

        for (int i = 0; i < 4 + random.nextInt(4); i++) {
            world.addParticle(
                PastelParticleTypes.FALLING_ASH, reference.pos.getX() + random.nextFloat(),
                reference.pos.getY() + 1.1 + random.nextFloat() * 0.4F, reference.pos.getZ() + random.nextFloat(), 0, 0,
                0
            );
        }

        if (Optional.ofNullable(context.getPlayer())
                    .map(p -> !p.getAbilities().instabuild)
                    .orElse(true))
            stack.shrink(1);

        return InteractionResult.sidedSuccess(world.isClientSide());
    }
}
