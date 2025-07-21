package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.items.tools.GlassCrestWorkstaffItem;
import earth.terrarium.pastel.items.tools.WorkstaffItem;
import earth.terrarium.pastel.networking.c2s_payloads.WorkstaffToggleSelectedPayload;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class WorkstaffScreen extends QuickNavigationGridScreen<WorkstaffScreenHandler> {

    private static final Grid RANGE_GRID = new Grid(
        GridEntry.EMPTY,
        GridEntry.text(
            Component.literal("1x1"), Component.translatable("item.pastel.workstaff.gui.1x1"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_1x1)
        ),
        GridEntry.text(
            Component.literal("5x5"), Component.translatable("item.pastel.workstaff.gui.5x5"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_5x5)
        ),
        GridEntry.BACK,
        GridEntry.text(
            Component.literal("3x3"), Component.translatable("item.pastel.workstaff.gui.3x3"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_3x3)
        )
    );

    private static final Grid ENCHANTMENT_GRID = new Grid(
        GridEntry.EMPTY,
        GridEntry.item(
            Items.FEATHER, Component.translatable("item.pastel.workstaff.gui.silk_touch"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_SILK_TOUCH)
        ),
        GridEntry.BACK,
        GridEntry.item(
            PastelItems.RESONANCE_SHARD.get(), Component.translatable("item.pastel.workstaff.gui.resonance"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_RESONANCE)
        ),
        GridEntry.item(
            PastelBlocks.FOUR_LEAF_CLOVER.get()
                                         .asItem(), Component.translatable("item.pastel.workstaff.gui.fortune"),
            (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_FORTUNE)
        )
    );

    public WorkstaffScreen(WorkstaffScreenHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title);

        GridEntry rightClickGridEntry;
        ItemStack mainHandStack = playerInventory.player.getMainHandItem();
        if (mainHandStack.getItem() instanceof WorkstaffItem workstaffItem && workstaffItem.itemAbilitiesEnabled(
            mainHandStack)) {
            rightClickGridEntry = GridEntry.item(
                Items.WOODEN_HOE, Component.translatable("item.pastel.workstaff.gui.disable_right_click_actions"),
                (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.DISABLE_RIGHT_CLICK_ACTIONS)
            );
        } else {
            rightClickGridEntry = GridEntry.item(
                PastelItems.MULTITOOL.get(), Component.translatable(
                    "item.pastel.workstaff.gui.enable_right_click_actions"), (screen) -> WorkstaffScreen.select(
                    WorkstaffItem.GUIToggle.ENABLE_RIGHT_CLICK_ACTIONS)
            );
        }

        if (mainHandStack.getItem() instanceof GlassCrestWorkstaffItem) {
            GridEntry projectileEntry = GlassCrestWorkstaffItem.canShoot(mainHandStack)
                                        ? GridEntry.item(
                Items.SPECTRAL_ARROW, Component.translatable("item.pastel.workstaff.gui.disable_projectiles"),
                (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.DISABLE_PROJECTILES)
            )
                                        : GridEntry.item(
                                            Items.ARROW, Component.translatable(
                                                "item.pastel.workstaff.gui.enable_projectiles"),
                                            (screen) -> WorkstaffScreen.select(
                                                WorkstaffItem.GUIToggle.ENABLE_PROJECTILES)
                                        );

            gridStack.push(new Grid(
                GridEntry.CLOSE,
                GridEntry.item(
                    Items.STONE, Component.translatable("item.pastel.workstaff.gui.range_group"),
                    (screen) -> selectGrid(RANGE_GRID)
                ),
                rightClickGridEntry,
                projectileEntry,
                GridEntry.item(
                    Items.ENCHANTED_BOOK, Component.translatable("item.pastel.workstaff.gui.enchantment_group"),
                    (screen) -> screen.selectGrid(ENCHANTMENT_GRID)
                )
            ));
        } else {
            var drm = Minecraft.getInstance().player.registryAccess();
            GridEntry enchantmentEntry = Ench.hasEnchantment(drm, Enchantments.FORTUNE, mainHandStack)
                                         ? GridEntry.item(
                Items.FEATHER, Component.translatable("item.pastel.workstaff.gui.silk_touch"),
                (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_SILK_TOUCH)
            )
                                         : GridEntry.item(
                                             PastelBlocks.FOUR_LEAF_CLOVER.get()
                                                                          .asItem(),
                                             Component.translatable("item.pastel.workstaff.gui.fortune"),
                                             (screen) -> WorkstaffScreen.select(WorkstaffItem.GUIToggle.SELECT_FORTUNE)
                                         );

            gridStack.push(new Grid(
                GridEntry.CLOSE,
                GridEntry.item(
                    Items.STONE, Component.translatable("item.pastel.workstaff.gui.range_group"),
                    (screen) -> screen.selectGrid(RANGE_GRID)
                ),
                rightClickGridEntry,
                GridEntry.EMPTY,
                enchantmentEntry
            ));
        }

    }

    protected static void select(WorkstaffItem.GUIToggle toggle) {
        PacketDistributor.sendToServer(new WorkstaffToggleSelectedPayload(toggle.ordinal()));
        Minecraft client = Minecraft.getInstance();
        client.level.playSound(
            null, client.player.blockPosition(), PastelSoundEvents.PAINTBRUSH_SELECT, SoundSource.NEUTRAL, 0.6F, 1.0F);
        client.player.closeContainer();
    }

}
