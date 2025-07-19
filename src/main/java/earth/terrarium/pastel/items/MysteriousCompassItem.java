package earth.terrarium.pastel.items;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.items.magic_items.StructureCompassItem;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelStructureTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MysteriousCompassItem extends StructureCompassItem implements SlotBackgroundEffect {

    public MysteriousCompassItem(Properties settings) {
        super(settings, PastelStructureTags.MYSTERIOUS_COMPASS_LOCATED);
    }

    @Override
    public void inventoryTick(
        @NotNull ItemStack stack, @NotNull Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && world.getGameTime() % 200 == 0 && entity instanceof Player player)
            if (DatabankUtils.hasAdvancement(player, PastelAdvancements.MYSTERIOUS_LOCKET_SOCKETING)) {
                locateStructure(stack, world, entity);
            } else {
                removeStructurePos(stack);
            }
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return SlotEffect.FULL_PACKAGE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return 0xFFFFFF;
    }
}
