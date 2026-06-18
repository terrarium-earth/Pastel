package earth.terrarium.pastel.inventories;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.block.InkColorSelectedPacketReceiver;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.items.magic_items.PaintbrushItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class PaintbrushScreenHandler extends QuickNavigationGridScreenHandler implements
    InkColorSelectedPacketReceiver {

    private final Player player;

    private final ItemStack paintBrushStack;

    private final boolean hasAccessToWhites;

    public PaintbrushScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ItemStack.EMPTY);
    }

    public PaintbrushScreenHandler(int syncId, Inventory playerInventory, ItemStack paintBrushStack) {
        super(PastelScreenHandlerTypes.PAINTBRUSH, syncId);
        this.player = playerInventory.player;
        this.paintBrushStack = paintBrushStack;
        this.hasAccessToWhites = DatabankUtils
            .hasAdvancement(playerInventory.player, InkColors.WHITE.getRequiredAdvancement());
    }

    @Override
    public boolean stillValid(Player player) {
        for (
            ItemStack itemStack : player.getHandSlots()
        ) {
            if (itemStack == paintBrushStack) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccessToWhites() {
        return hasAccessToWhites;
    }

    @Override
    public void onInkColorSelectedPacket(Optional<Holder<InkColor>> inkColor) {
        PaintbrushItem.setColor(paintBrushStack, inkColor.map(Holder::value).orElse(null));
        removed(player);
    }

    @Override
    public BlockEntity getBlockEntity() {
        return null;
    }

}
