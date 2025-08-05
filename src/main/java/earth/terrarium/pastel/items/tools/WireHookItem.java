package earth.terrarium.pastel.items.tools;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.api.item.HasColorGradient;
import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.entity.entity.WireHookEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class WireHookItem extends Item implements HasColorGradient {

    private static final ColorGradient GRADIENT = new ColorGradient(
        new Color(0xFFCC49),
        new Color(0xF8840E)
    ).fadeAlpha(1, 0).fadeAlpha(0, 0, 1, 0.05f);

    public WireHookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        var data = HookshotData.get(player);
        if (data.isAlreadyHooked()) {
            recallHook(level, player, data);
        }
        else {
            if (!level.isClientSide()) {
                var hook = new WireHookEntity(player, level);
                hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
                level.addFreshEntity(hook);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static void recallHook(Level level, Player player, HookshotData data) {
        player.playNotifySound(PastelSounds.USE_FAIL, SoundSource.PLAYERS, 0.5F,
                               Support.varFloatCentered(player.getRandom(), 0.1F));

        var hook = data.getHookEntity(level);
        if (hook instanceof WireHookEntity wireHook)
            wireHook.recall();
    }

    @Override
    public ColorGradient getColorGradient(ResourceLocation gradient) {
        if (gradient.equals(HasColorGradient.LUNGE)) {
            return GRADIENT;
        }

        return null;
    }
}
