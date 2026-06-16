package earth.terrarium.pastel.helpers.interaction;

import earth.terrarium.pastel.api.interaction.ItemProvider;
import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// Is this wise?
public class InventoryHelper {

    public static int getItemCountInInventory(IItemHandler inventory, Item item) {
        int count = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.is(item)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static int getStackCountInInventory(IItemHandler inventory, ItemStack reference) {
        int count = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (ItemStack.isSameItemSameComponents(stack, reference)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static boolean removeFromInventoryWithRemainders(
        @NotNull Player playerEntity, @NotNull ItemStack stackToRemove) {
        if (playerEntity.isCreative()) {
            return true;
        }

        // count how many we have in the inv
        Inventory playerInventory = playerEntity.getInventory();
        List<ItemStack> matchingStacks = new ArrayList<>();
        int paymentStackItemCount = 0;
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            ItemStack currentStack = playerInventory.getItem(i);

            ItemProvider itemProvider = currentStack.getCapability(ItemProvider.CAPABILITY);
            if (itemProvider == null) {
                if (ItemStack.isSameItem(currentStack, stackToRemove)) {
                    matchingStacks.add(currentStack);
                    paymentStackItemCount += currentStack.getCount();
                }
            } else {
                matchingStacks.add(currentStack);
                paymentStackItemCount += itemProvider.getItemCount(playerEntity, currentStack, stackToRemove.getItem());
            }

            if (paymentStackItemCount >= stackToRemove.getCount()) {
                break;
            }
        }

        // did we find enough?
        if (paymentStackItemCount < stackToRemove.getCount()) {
            return false;
        }

        // decrement the inventory
        int amountToRemove = stackToRemove.getCount();
        for (ItemStack matchingStack : matchingStacks) {
            ItemProvider itemProvider = matchingStack.getCapability(ItemProvider.CAPABILITY);
            if (itemProvider != null) {
                amountToRemove -= itemProvider.provideItems(
                    playerEntity, matchingStack, stackToRemove.getItem(), amountToRemove);
            } else {
                int currentRemove = Math.min(matchingStack.getCount(), amountToRemove);
                matchingStack.shrink(currentRemove);
                amountToRemove -= currentRemove;
                if (amountToRemove <= 0) {
                    return true;
                }
            }
        }
        return true;
    }

    public static boolean isItemCountInInventory(
        IItemHandlerModifiable inventory, ItemReference reference, int maxSearchAmount) {
        int count = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (reference.permits(stack)) {
                count += stack.getCount();
                if (count >= maxSearchAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Tuple<Integer, List<ItemStack>> getStackCountInInventory(
        ItemStack reference, IItemHandlerModifiable inventory, int maxSearchAmount) {
        List<ItemStack> foundStacks = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (ItemStack.isSameItemSameComponents(stack, reference)) {
                foundStacks.add(stack);
                count += stack.getCount();
                if (count >= maxSearchAmount) {
                    return new Tuple<>(count, foundStacks);
                }
            }
        }
        return new Tuple<>(count, foundStacks);
    }

    /**
     * Adds a single itemstack to an inventory
     *
     * @param itemStack the itemstack to add. The stack can have a size >
     *                  maxStackSize and will be split accordingly
     * @param inventory the inventory to add to
     * @return The remaining stack that could not be added
     */
    public static ItemStack smartAddToInventory(
        ItemStack itemStack, IItemHandlerModifiable inventory, @Nullable Direction side) {
        if (inventory instanceof WorldlyContainer sidedInventory && side != null) {
            int[] acceptableSlots = sidedInventory.getSlotsForFace(side);
            for (int acceptableSlot : acceptableSlots) {
                if (sidedInventory.canPlaceItemThroughFace(acceptableSlot, itemStack, side)) {
                    itemStack = setOrCombineStack(inventory, acceptableSlot, itemStack);
                    if (itemStack.isEmpty()) {
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < inventory.getSlots(); i++) {
                itemStack = setOrCombineStack(inventory, i, itemStack);
                if (itemStack.isEmpty()) {
                    break;
                }
            }
        }
        return itemStack;
    }

    public static ItemStack setOrCombineStack(IItemHandlerModifiable inventory, int slot, ItemStack addingStack) {
        ItemStack existingStack = inventory.getStackInSlot(slot);
        CrystalArmorItem.removeEmpowered(addingStack);
        if (existingStack.isEmpty()) {
            if (addingStack.getCount() > addingStack.getMaxStackSize()) {
                int amount = addingStack.getMaxStackSize();
                amount = Math.min(amount, inventory.getSlotLimit(slot));
                ItemStack newStack = addingStack.copy();
                newStack.setCount(amount);
                addingStack.shrink(amount);
                inventory.setStackInSlot(slot, newStack);
            } else {
                inventory.setStackInSlot(slot, addingStack);
                return ItemStack.EMPTY;
            }
        } else {
            combineStacks(existingStack, addingStack);
        }
        return addingStack;
    }

    public static void combineStacks(ItemStack originalStack, ItemStack addingStack) {
        if (ItemStack.isSameItemSameComponents(originalStack, addingStack)) {
            int leftOverAmountInExistingStack = originalStack.getMaxStackSize() - originalStack.getCount();
            if (leftOverAmountInExistingStack > 0) {
                int addAmount = Math.min(leftOverAmountInExistingStack, addingStack.getCount());
                originalStack.grow(addAmount);
                addingStack.shrink(addAmount);
            }
        }
    }

    /**
     * Adds a single stacks to an inventory in a given slot range
     *
     * @param inventory  the inventory to add to
     * @param stackToAdd the stack to add to the inventory
     * @param rangeStart the start insert slot
     * @param rangeEnd   the last insert slot
     * @return false if the stack could not be completely added
     */

    public static boolean addToInventory(
        IItemHandlerModifiable inventory, ItemStack stackToAdd, int rangeStart, int rangeEnd) {
        for (int i = rangeStart; i < rangeEnd; i++) {
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (currentStack.isEmpty()) {
                inventory.setStackInSlot(i, stackToAdd);
                return true;
            } else if (stackToAdd.isStackable()) {
                combineStacks(currentStack, stackToAdd);
                if (stackToAdd.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean addToInventory(
        IItemHandlerModifiable inventory, List<ItemStack> stacksToAdd, int rangeStart, int rangeEnd) {
        for (int i = rangeStart; i < rangeEnd; i++) {
            ItemStack inventoryStack = inventory.getStackInSlot(i);
            if (inventoryStack.isEmpty()) {
                inventory.setStackInSlot(i, stacksToAdd.get(0));
                stacksToAdd.removeFirst();
                if (stacksToAdd.isEmpty()) {
                    return true;
                }
            }
            for (int j = 0; j < stacksToAdd.size(); j++) {
                ItemStack stackToAdd = stacksToAdd.get(j);
                if (stackToAdd.isStackable()) {
                    combineStacks(inventoryStack, stackToAdd);
                    if (stackToAdd.isEmpty()) {
                        stacksToAdd.remove(j);
                        if (stacksToAdd.isEmpty()) {
                            return true;
                        }
                        j--;
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack offerToInventory(
        IItemHandlerModifiable inventory, ItemStack itemStack, int rangeStart, int rangeEnd) {
        for (int i = rangeStart; i < rangeEnd; i++) {
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (currentStack.isEmpty()) {
                inventory.setStackInSlot(i, itemStack);
                return ItemStack.EMPTY;
            } else if (itemStack.isStackable()) {
                combineStacks(currentStack, itemStack);
                if (itemStack.isEmpty()) {
                    return itemStack;
                }
            }
        }

        return itemStack;
    }

    // TODO: lots of code overlap with hasInInventory()
    public static boolean hasIngredientStacksInInventory(
        List<IngredientStack> ingredients, IItemHandlerModifiable inventory) {
        List<Ingredient> ingredientsToFind = new ArrayList<>();
        List<Integer> requiredIngredientAmounts = new ArrayList<>();
        for (IngredientStack ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }

            ingredientsToFind.add(ingredient.getIngredient());
            requiredIngredientAmounts.add(ingredient.getCount());
        }

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (ingredientsToFind.isEmpty()) {
                break;
            }
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                int amount = currentStack.getCount();
                for (int j = 0; j < ingredientsToFind.size(); j++) {
                    if (ingredientsToFind.get(j)
                                         .test(currentStack)) {
                        int ingredientCount = requiredIngredientAmounts.get(j);
                        if (amount >= ingredientCount) {
                            ingredientsToFind.remove(j);
                            requiredIngredientAmounts.remove(j);
                            j--;
                        } else {
                            requiredIngredientAmounts.set(j, requiredIngredientAmounts.get(j) - amount);
                        }

                        amount -= ingredientCount;
                        if (amount < 1) {
                            break;
                        }
                    }
                }
            }
        }

        return ingredientsToFind.isEmpty();
    }

    public static boolean hasInInventory(List<Ingredient> ingredients, IItemHandlerModifiable inventory) {
        List<Ingredient> ingredientsToFind = new ArrayList<>();
        List<Integer> requiredIngredientAmounts = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }

            ingredientsToFind.add(ingredient);
            if (ingredient.getItems().length > 0) {
                requiredIngredientAmounts.add(ingredient.getItems()[0].getCount());
            } else {
                requiredIngredientAmounts.add(1);
            }
        }

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (ingredientsToFind.isEmpty()) {
                break;
            }
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                int amount = currentStack.getCount();
                for (int j = 0; j < ingredientsToFind.size(); j++) {
                    if (ingredientsToFind.get(j)
                                         .test(currentStack)) {
                        int ingredientCount = requiredIngredientAmounts.get(j);
                        if (amount >= ingredientCount) {
                            ingredientsToFind.remove(j);
                            requiredIngredientAmounts.remove(j);
                            j--;
                        } else {
                            requiredIngredientAmounts.set(j, requiredIngredientAmounts.get(j) - amount);
                        }

                        amount -= ingredientCount;
                        if (amount < 1) {
                            break;
                        }
                    }
                }
            }
        }

        return ingredientsToFind.isEmpty();
    }

    // return are the recipe remainders
    public static List<ItemStack> removeFromInventoryWithRemainders(
        List<Ingredient> ingredients, IItemHandlerModifiable inventory) {
        List<ItemStack> remainders = new ArrayList<>();

        List<Ingredient> requiredIngredients = new ArrayList<>();
        List<Integer> requiredIngredientAmounts = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }

            requiredIngredients.add(ingredient);
            if (ingredient.getItems().length > 0) {
                requiredIngredientAmounts.add(ingredient.getItems()[0].getCount());
            } else {
                requiredIngredientAmounts.add(1);
            }
        }

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (requiredIngredients.isEmpty()) {
                break;
            }

            ItemStack currentStack = inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                for (int j = 0; j < requiredIngredients.size(); j++) {
                    int currentStackCount = currentStack.getCount();
                    if (requiredIngredients.get(j)
                                           .test(currentStack)) {
                        int ingredientCount = requiredIngredientAmounts.get(j);
                        ItemStack remainder = currentStack.getCraftingRemainingItem();
                        if (currentStackCount >= ingredientCount) {
                            if (!remainder.isEmpty()) {
                                remainder.setCount(requiredIngredientAmounts.get(j));
                                remainders.add(remainder);
                            }
                            requiredIngredients.remove(j);
                            requiredIngredientAmounts.remove(j);
                            j--;
                        } else {
                            if (!remainder.isEmpty()) {
                                remainder.setCount(currentStackCount);
                                remainders.add(remainder);
                            }

                            requiredIngredientAmounts.set(j, requiredIngredientAmounts.get(j) - currentStackCount);
                        }

                        currentStack.setCount(currentStackCount - ingredientCount);
                    }
                }
            }
        }

        return remainders;
    }

    // return are the recipe remainders
    // TODO lots of code overlap with removeFromInventoryWithRemainders()
    public static List<ItemStack> removeIngredientStacksFromInventoryWithRemainders(
        List<IngredientStack> ingredients, IItemHandlerModifiable inventory) {
        List<ItemStack> remainders = new ArrayList<>();

        List<Ingredient> requiredIngredients = new ArrayList<>();
        List<Integer> requiredIngredientAmounts = new ArrayList<>();
        for (IngredientStack ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }

            requiredIngredients.add(ingredient.getIngredient());
            requiredIngredientAmounts.add(ingredient.getCount());
        }

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (requiredIngredients.isEmpty()) {
                break;
            }

            ItemStack currentStack = inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                for (int j = 0; j < requiredIngredients.size(); j++) {
                    int currentStackCount = currentStack.getCount();
                    if (requiredIngredients.get(j)
                                           .test(currentStack)) {
                        int ingredientCount = requiredIngredientAmounts.get(j);
                        ItemStack remainder = currentStack.getCraftingRemainingItem();
                        if (currentStackCount >= ingredientCount) {
                            if (!remainder.isEmpty()) {
                                remainder.setCount(requiredIngredientAmounts.get(j));
                                remainders.add(remainder);
                            }
                            requiredIngredients.remove(j);
                            requiredIngredientAmounts.remove(j);
                            j--;
                        } else {
                            if (!remainder.isEmpty()) {
                                remainder.setCount(currentStackCount);
                                remainders.add(remainder);
                            }

                            requiredIngredientAmounts.set(j, requiredIngredientAmounts.get(j) - currentStackCount);
                        }

                        currentStack.setCount(currentStackCount - ingredientCount);
                    }
                }
            }
        }

        return remainders;
    }

    public static boolean canFitStacks(IItemHandlerModifiable inventory, List<ItemStack> stacks) {
        if (stacks.isEmpty())
            return true;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty())
                continue;

            if (!ItemHandlerHelper.insertItemStacked(inventory, stack, true)
                                  .isEmpty())
                return false;
        }

        return true;
    }

    public static List<ItemStack> getRemainders(List<Ingredient> ingredients) {
        List<ItemStack> remainders = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }

            if (ingredient.getItems().length > 0) {
                remainders.add(ingredient.getItems()[0].getCraftingRemainingItem());
            }
        }

        return remainders;
    }

    // returns recipe remainders
    public static List<ItemStack> removeFromInventoryWithRemainders(
        ItemStack removeItemStack, IItemHandlerModifiable inventory) {
        List<ItemStack> remainders = new ArrayList<>();

        int removeItemStackCount = removeItemStack.getCount();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (ItemStack.isSameItemSameComponents(currentStack, removeItemStack)) {
                ItemStack remainder = currentStack.getCraftingRemainingItem();

                int amountAbleToDecrement = Math.min(currentStack.getCount(), removeItemStackCount);
                currentStack.shrink(amountAbleToDecrement);
                removeItemStackCount -= amountAbleToDecrement;

                if (!remainder.isEmpty()) {
                    remainder.setCount(amountAbleToDecrement);
                    remainders.add(remainder);
                }
            }
            if (removeItemStackCount == 0) {
                return remainders;
            }
        }
        return remainders;
    }

    public static boolean canCombineItemStacks(ItemStack currentItemStack, ItemStack additionalItemStack) {
        return currentItemStack.isEmpty() || additionalItemStack.isEmpty() || (ItemStack.isSameItemSameComponents(
            currentItemStack, additionalItemStack)
                                                                               && (currentItemStack.getCount() +
                                                                                   additionalItemStack.getCount() <=
                                                                                   currentItemStack.getMaxStackSize()));
    }

    public static Optional<ItemStack> extractLastStack(IItemHandlerModifiable inventory) {
        ItemStack currentStack;
        for (int i = inventory.getSlots() - 1; i >= 0; i--) {
            currentStack = inventory.getStackInSlot(i);
            if (!currentStack.isEmpty()) {
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                return Optional.of(currentStack);
            }
        }
        return Optional.empty();
    }

    public static ItemStack addToInventoryUpToSingleStackWithMaxTotalCount(
        ItemStack itemStack, IItemHandlerModifiable inventory, int maxTotalCount) {
        // check if a stack that can be combined is in the inventory already
        int itemCount = 0;
        int firstEmptySlot = -1;
        ItemStack matchingStack = null;
        int matchingSlot = -1;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack slotStack = inventory.getStackInSlot(i);

            if (slotStack.isEmpty()) {
                if (firstEmptySlot == -1) {
                    firstEmptySlot = i;
                }
            } else {
                itemCount += slotStack.getCount();
                if (ItemStack.isSameItemSameComponents(itemStack, slotStack)) {
                    matchingStack = slotStack;
                    matchingSlot = i;
                }
            }
        }

        int storageLeft = maxTotalCount - itemCount;
        if (storageLeft <= 0) {
            return itemStack;
        }

        if (matchingStack != null) {
            int addedCount = Math.min(matchingStack.getMaxStackSize() - matchingStack.getCount(), itemStack.getCount());
            addedCount = Math.min(storageLeft, addedCount);
            if (addedCount > 0) {
                matchingStack.setCount(matchingStack.getCount() + addedCount);
                itemStack.shrink(addedCount);
                inventory.setStackInSlot(matchingSlot,matchingStack); // needed so that listeners are called
            }
            return itemStack;
        }

        if (firstEmptySlot == -1) {
            return itemStack;
        }

        inventory.setStackInSlot(firstEmptySlot, itemStack.split(storageLeft));
        return itemStack;
    }

    public static int countItemsInInventory(IItemHandlerModifiable inventory) {
        int contentCount = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            contentCount += inventory.getStackInSlot(i)
                                     .getCount();
        }
        return contentCount;
    }

    public static ItemStack extractFromInventory(IItemHandler inventory, ItemReference reference, int amount) {
        int extracted = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (reference.permits(inventory.getStackInSlot(i)))
                extracted += inventory.extractItem(i, amount - extracted, false)
                                      .getCount();

            if (extracted == amount)
                break;
        }

        if (extracted == 0)
            return ItemStack.EMPTY;

        return reference.asStack(extracted);
    }

    public static List<ItemStorage> getAvailableItems(IItemHandler handler) {
        var available = new HashMap<ItemReference, ItemStorage>();

        for (int i = 0; i < handler.getSlots(); i++) {
            var stack = handler.getStackInSlot(i);
            var ref = ItemReference.of(stack);

            if (ref.isEmpty())
                continue;

            available.computeIfAbsent(ref, ItemStorage::new)
                     .increment(stack.getCount());
        }

        return new ArrayList<>(available.values());
    }
}
