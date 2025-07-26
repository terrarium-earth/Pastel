package earth.terrarium.pastel.api.energy;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.progression.PastelCriteria;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.stream.Stream;

public interface InkPowered {

	/**
	 * The advancement the player needs to have in order to use ink powered tools
	 */
	ResourceLocation REQUIRED_ADVANCEMENT = PastelCommon.locate("milestones/unlock_ink_use");

	@OnlyIn(Dist.CLIENT)
    static boolean canUseClient() {
		Minecraft client = Minecraft.getInstance();
		return canUse(client.player);
	}

	static boolean canUse(Player playerEntity) {
		return DatabankUtils.hasAdvancement(playerEntity, InkPowered.REQUIRED_ADVANCEMENT);
	}

	/**
	 * The colors that the object requires for working.
	 * These are added as the player facing tooltip
	 **/
	List<InkColor> getUsedColors();

	/**
	 * The colors that the object requires for working.
	 * These are added as the player facing tooltip
	 **/
	@OnlyIn(Dist.CLIENT)
	default void addInkPoweredTooltip(List<Component> tooltip) {
		if (canUseClient()) {
			if (getUsedColors().size() > 1) {
				tooltip.add(Component.translatable("pastel.tooltip.ink_powered.prefix").withStyle(ChatFormatting.GRAY));
				for (InkColor color : getUsedColors()) {
					tooltip.add(color.getColoredInkName().withStyle(ChatFormatting.GRAY));
				}
			} else {
				tooltip.add(Component.translatable("pastel.tooltip.ink_powered.consume", getUsedColors().get(0).getColoredInkName()).withStyle(ChatFormatting.GRAY));
			}
		}
	}

    private static Stream<ItemStack> getCurioInkSlots(Player player) {
        return CuriosApi
                .getCuriosInventory(player)
                .stream()
                .flatMap(inventory -> inventory
                        .findCurios(itemStack -> itemStack.getItem() instanceof InkStorageItem<?>)
                        .stream()
                )
                .map(SlotResult::stack);
    }

	private static long tryDrainEnergy(@NotNull ItemStack stack, InkColor color, long amount, @Nullable Player player) {
		if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			if (!inkStorageItem.getDrainability().canDrain(player != null)) {
				return 0;
			}

			InkStorage inkStorage = inkStorageItem.getEnergyStorage(stack);
			long drained = inkStorage.drainEnergy(color, amount);
			if (drained > 0) {
				if (player instanceof ServerPlayer serverPlayerEntity) {
					PastelCriteria.INK_CONTAINER_INTERACTION.trigger(serverPlayerEntity, stack, inkStorage, color, -amount);
				}

				inkStorageItem.setEnergyStorage(stack, inkStorage);
			}
			return drained;
		} else {
			return 0;
		}
	}

	private static long tryGetEnergy(@NotNull ItemStack stack, InkColor color) {
		if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			InkStorage inkStorage = inkStorageItem.getEnergyStorage(stack);
			return inkStorage.getEnergy(color);
		} else {
			return 0;
		}
	}

	/**
	 * Searches an inventory for InkEnergyStorageItems and tries to drain the color energy.
	 * If enough could be drained returns true, else false.
	 * If not enough energy is available it will be drained as much as is available
	 * but return will still be false
	 **/
	static boolean tryDrainEnergy(@NotNull Container inventory, InkColor color, long amount) {
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack currentStack = inventory.getItem(i);
			if (!currentStack.isEmpty()) { // fast fail
				amount -= tryDrainEnergy(currentStack, color, amount, null);
				if (amount <= 0) {
					return true;
				}
			}
		}
		return false;
	}

	static boolean tryDrainEnergy(@NotNull Player player, @NotNull InkCost inkCost) {
		return tryDrainEnergy(player, inkCost.color(), inkCost.cost());
	}

	static boolean tryDrainEnergy(@NotNull Player player, @NotNull InkCost inkCost, float costModifier) {
		return tryDrainEnergy(player, inkCost.color(), Support.chanceRound(inkCost.cost() * costModifier, player.getRandom()));
	}

	/**
	 * Searches the players Trinkets for energy storage first and inventory second
	 * for PigmentEnergyStorageItem and tries to drain the color energy.
	 * If enough could be drained returns true, else false.
	 * If not enough energy is available it will be drained as much as is available
	 * but return will still be false
	 * <p>
	 * Check Order:
	 * - Offhand
	 * - Trinket Slots
	 * - Inventory
	 **/
	static boolean tryDrainEnergy(@NotNull Player player, @NotNull InkColor color, long amount) {
		if (player.isCreative()) {
			return true;
		}
		if (!canUse(player)) {
			return false;
		}

		// hands (main hand, too, if someone uses the staff from the offhand)
		for (ItemStack itemStack : player.getHandSlots()) {
			amount -= tryDrainEnergy(itemStack, color, amount, player);
			if (amount <= 0) {
				return true;
			}
		}

		// trinket slots
		var trinketInkStorages = getCurioInkSlots(player).toList();

        for (ItemStack trinketEnergyStorageStack : trinketInkStorages) {
            amount -= tryDrainEnergy(trinketEnergyStorageStack, color, amount, player);
            if (amount <= 0) {
                return true;
            }
		}

		// inventory
		for (ItemStack itemStack : player.getInventory().items) {
			amount -= tryDrainEnergy(itemStack, color, amount, player);
			if (amount <= 0) {
				return true;
			}
		}

		return false;
	}

	static long getAvailableInk(@NotNull Player player, InkColor color) {
		if (player.isCreative()) {
			return Long.MAX_VALUE;
		}
		if (!canUse(player)) {
			return 0;
		}

		long available = 0;

		// hands
		for (ItemStack itemStack : player.getHandSlots()) {
			available += tryGetEnergy(itemStack, color);
		}

		// trinket slots
        available += getCurioInkSlots(player).mapToLong(stack -> tryGetEnergy(stack, color)).sum();

		// inventory
		for (ItemStack itemStack : player.getInventory().items) {
			available += tryGetEnergy(itemStack, color);
		}
		return available;
	}

	static boolean hasAvailableInk(Player player, InkCost inkCost) {
		return hasAvailableInk(player, inkCost.color(), inkCost.cost());
	}

	static boolean hasAvailableInk(Player player, InkColor color, long amount) {
		if (!canUse(player)) {
			return false;
		}

		if (player.isCreative()) {
			return true;
		}

		// hands
		for (ItemStack itemStack : player.getHandSlots()) {
			amount -= tryGetEnergy(itemStack, color);
			if (amount <= 0) {
				return true;
			}
		}

        // trinket slots
        var trinketInkStorages = getCurioInkSlots(player).toList();

        for (ItemStack trinketEnergyStorageStack : trinketInkStorages) {
            amount -= tryGetEnergy(trinketEnergyStorageStack, color);
            if (amount <= 0) {
                return true;
            }
        }

		// inventory
		for (ItemStack itemStack : player.getInventory().items) {
			amount -= tryGetEnergy(itemStack, color);
			if (amount <= 0) {
				return true;
			}
		}

		return false;
	}

}
