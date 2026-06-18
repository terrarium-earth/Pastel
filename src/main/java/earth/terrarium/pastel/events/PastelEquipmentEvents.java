package earth.terrarium.pastel.events;

import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.attachments.data.InertiaData;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PastelEquipmentEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, PastelEquipmentEvents::processRazingMod);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, PastelEquipmentEvents::processInertiaMod);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEquipmentEvents::processInexorable);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelEquipmentEvents::hookshotAntiAirSlowdown);
    }

    private static void hookshotAntiAirSlowdown(PlayerEvent.BreakSpeed event) {
        var player = event.getEntity();
        var speed = event.getNewSpeed();
        if (!player.onGround() && HookshotData.get(player).isAlreadyHooked() && speed <= event
            .getOriginalSpeed() + Mth.EPSILON)
            event.setNewSpeed(event.getNewSpeed() * 5);
    }

    private static void processRazingMod(PlayerEvent.BreakSpeed event) {
        var stack = event
            .getEntity()
            .getMainHandItem();
        var tool = stack.get(DataComponents.TOOL);
        var access = event
            .getEntity()
            .registryAccess();
        var state = event.getState();

        if (tool == null)
            return;

        var razing = Ench.getLevel(access, PastelEnchantments.RAZING, stack);
        if (razing > 0 && tool.isCorrectForDrops(state)) {
            float hardness = state
                .getBlock()
                .defaultDestroyTime();
            event.setNewSpeed((float) Math.max(1 + hardness, Math.pow(2, 1 + razing / 8F)));
        }
    }

    private static void processInertiaMod(PlayerEvent.BreakSpeed event) {
        var stack = event
            .getEntity()
            .getMainHandItem();
        var tool = stack.get(DataComponents.TOOL);
        var player = event.getEntity();
        var level = player.level();

        if (tool == null)
            return;

        var access = event
            .getEntity()
            .registryAccess();
        var state = event.getState();

        var ench = Ench.getLevel(access, PastelEnchantments.INERTIA, stack);
        if (ench > 0 && tool.isCorrectForDrops(state)) {
            var speed = event.getNewSpeed();
            var inertia = player.getData(InertiaData.ATTACHMENT);
            var strength = inertia.getPotency(level.isClientSide());

            if (strength > 0) {
                var eff = (strength * strength * ench - 1) / Math.sqrt(1 + Math.pow(strength, 2));
                event.setNewSpeed(speed * (float) (eff * 3 + 4));

            } else {
                event.setNewSpeed(speed / 4);
            }
        }
    }

    private static void processInexorable(PlayerEvent.BreakSpeed event) {
        var stack = event
            .getEntity()
            .getMainHandItem();
        var access = event
            .getEntity()
            .registryAccess();
        var state = event.getState();
        var tool = stack.get(DataComponents.TOOL);

        if (tool == null)
            return;

        if (Ench.hasEnchantment(access, PastelEnchantments.INEXORABLE, stack) && tool.isCorrectForDrops(state)) {
            event.setNewSpeed(Math.max(event.getNewSpeed(), tool.getMiningSpeed(state)));
        }
    }
}
