package de.dafuqs.revelationary.api.revelations;

import de.dafuqs.revelationary.ClientRevelationHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

/**
 * Interface to react to the event of blocks/items getting revealed after getting advancements
 */
public interface RevealingCallback {
	/**
	 * Gets called every time blocks or items get revealed.
	 * The block and item lists are complete and do contain all entries from all mods
	 * that use the revelation system. If you want to only trigger actions for your
	 * own mods entries you have to run a filter across those sets
	 *
	 * @param advancements the advancements that caused the revelations
	 * @param blocks       all blocks that are revealed
	 * @param items        all items that are revealed
	 * @param isJoinPacket true if the trigger is because of the revelation happens right at world join
	 *                     (when revealing all the blocks and items mapped to advancements gotten in previous play sessions)
	 */
	void trigger(Set<ResourceLocation> advancements, Set<Block> blocks, Set<Item> items, boolean isJoinPacket);
	
	/**
	 * Register this RevealingCallback
	 * It will now receive trigger events
	 *
	 * @param callback the callback to register
	 */
	static void register(RevealingCallback callback) {
		ClientRevelationHolder.callbacks.add(callback);
	}
}