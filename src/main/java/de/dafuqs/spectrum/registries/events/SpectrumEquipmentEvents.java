package de.dafuqs.spectrum.registries.events;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.component.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.player.*;

public class SpectrumEquipmentEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, SpectrumEquipmentEvents::processRazingMod);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, SpectrumEquipmentEvents::processInertiaMod);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, SpectrumEquipmentEvents::processInexorable);

    }

    private static void processRazingMod(PlayerEvent.BreakSpeed event) {
        var stack = event.getEntity().getMainHandItem();
        var tool = stack.get(DataComponents.TOOL);
        var access = event.getEntity().registryAccess();
        var state = event.getState();

        if (tool == null)
            return;

        var razing = SpectrumEnchantmentHelper.getLevel(access, SpectrumEnchantments.RAZING, stack);
        if (razing > 0 && tool.isCorrectForDrops(state)) {
            float hardness = state.getBlock().defaultDestroyTime();
            event.setNewSpeed((float) Math.max(1 + hardness, Math.pow(2, 1 + razing / 8F)));
        }
    }

    private static void processInertiaMod(PlayerEvent.BreakSpeed event) {
        var stack = event.getEntity().getMainHandItem();
        var tool = stack.get(DataComponents.TOOL);
        var access = event.getEntity().registryAccess();
        var state = event.getState();
        var original = event.getOriginalSpeed();

        if (tool == null)
            return;

        var inertia = SpectrumEnchantmentHelper.getLevel(access, SpectrumEnchantments.INERTIA, stack);
        if (inertia > 0) {
            var component = stack.getOrDefault(SpectrumDataComponentTypes.INERTIA, InertiaComponent.DEFAULT);
            if (state.is(component.lastMined())) {
                var additionalSpeedPercent = 2.0 * Math.log(component.count()) / Math.log((6 - inertia) * (6 - inertia) + 1);
                event.setNewSpeed((float) (original / 2 + additionalSpeedPercent));
            } else {
                event.setNewSpeed(original / 4);
            }
        }
    }

    private static void processInexorable(PlayerEvent.BreakSpeed event) {
        var stack = event.getEntity().getMainHandItem();
        var access = event.getEntity().registryAccess();
        var state = event.getState();
        var tool = stack.get(DataComponents.TOOL);
        var original = event.getOriginalSpeed();

        if (tool == null)
            return;

        if (SpectrumEnchantmentHelper.hasEnchantment(access, SpectrumEnchantments.INERTIA, stack) && tool.isCorrectForDrops(state)) {
            event.setNewSpeed(Math.max(original, tool.getMiningSpeed(state)));
        }
    }
}
