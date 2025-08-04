package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.entity.entity.WireHookEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WireHookItem extends Item {

    public WireHookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);

        if (!level.isClientSide()) {
            var data = HookshotData.get(player);

            if (data.isAlreadyHooked()) {
                recallHook((ServerLevel) level, player, data);

            }
            else {
                var hook = new WireHookEntity(player, player.getX(), player.getY(), player.getZ(), level);
                hook.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
                level.addFreshEntity(hook);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static void recallHook(ServerLevel level, Player player, HookshotData data) {
        player.playNotifySound(PastelSounds.USE_FAIL, SoundSource.PLAYERS, 0.5F,
                               Support.varFloatCentered(player.getRandom(), 0.1F));

        var hook = level.getEntity(data.getLinkedHook().get());
        if (hook instanceof WireHookEntity wireHook)
            wireHook.recall();
    }
}
