package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.entity.entity.GlassArrowEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassArrowItem extends ArrowItem {

    public final GlassArrowVariant variant;

    public GlassArrowItem(Item.Properties settings, GlassArrowVariant variant, ParticleOptions particleEffect) {
        super(settings);
        this.variant = variant;
        variant.setData(this, particleEffect);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        GlassArrowEntity entity = new GlassArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
        entity.setVariant(variant);
        return entity;
    }

    @Override
    public AbstractArrow asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        GlassArrowEntity arrowEntity = new GlassArrowEntity(
            world,
            pos.x(),
            pos.y(),
            pos.z(),
            stack.copyWithCount(1),
            null
        );
        arrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
        arrowEntity.setVariant(variant);
        return arrowEntity;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (variant != GlassArrowVariant.MALACHITE) {
            tooltip
                .add(
                    Component
                        .translatable(getDescriptionId() + ".tooltip")
                        .withStyle(ChatFormatting.GRAY)
                );
        }
    }

}
