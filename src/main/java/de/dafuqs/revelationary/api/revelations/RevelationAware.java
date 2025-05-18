package de.dafuqs.revelationary.api.revelations;

import de.dafuqs.revelationary.RevelationRegistry;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Interface for defining a block/item/blockitem/... as revealable.
 * Using this interface will allow more functionality than using Revelationary's json api
 * <p>
 * Blocks and items with this interface will disguise themselves as other blocks/items
 * until the player gets a specific advancement. It's name will get obfuscated.
 * Disguised blocks will drop item stacks as if they were the block they are disguised as
 */
public interface RevelationAware {
	/**
	 * The advancement identifier that the player must have to see this block/item
	 */
	ResourceLocation getCloakAdvancementIdentifier();
	
	/**
	 * Register this object as revealable
	 * You can call this safely at the end of the objects' constructor.
	 */
	static void register(RevelationAware revelationAware) {
		RevelationRegistry.registerRevelationAware(revelationAware);
	}
	
	/**
	 * A map of all blockstates and the block states disguised as
	 * This can be a single entry like this.getDefaultState() => Blocks.STONE.getDefaultState()
	 * Or if your block has > 1 state a mapping for every state that should be disguised
	 * If you use this interface on an item without block representation (no BlockItem) return an emtpy map
	 */
	Map<BlockState, BlockState> getBlockStateCloaks();
	
	/**
	 * A pair consisting of this objects item representation and the item it should be disguised as
	 * If you are implementing an item without a block this will prob. look something like "new Pair<>(this, cloakItem);"
	 * If you are implementing a BlockItem something like "new Pair<>(this.asItem(), Blocks.OAK_LOG.asItem())"
	 * If you use this interace on a block without item representation (like vanilla end portal) return null
	 */
	@Nullable Tuple<Item, Item> getItemCloak();
	
	/**
	 * Optionally return a mapping of a revelation aware item and the text that should be used as translation
	 * If you return null (the default) it's name will be scattered unreadable instead
	 *
	 * @return the matching of the item and the text it will use when not revealed
	 */
	@Nullable
	default Tuple<Item, MutableComponent> getCloakedItemTranslation() {
		return null;
	}
	
	/**
	 * Optionally return a mapping of a revelation aware block and the text that should be used as translation
	 * If you return null (the default) it's name will be scattered unreadable instead
	 *
	 * @return the matching of the block and the text it will use when not revealed
	 */
	@Nullable
	default Tuple<Block, MutableComponent> getCloakedBlockTranslation() {
		return null;
	}
	
	/**
	 * Gets called when this object gets disguised (like when taking an advancement from the player)
	 */
	default void onCloak() {
	}
	
	/**
	 * Gets called when this object gets revealed (when the player gets the matching advancement)
	 */
	default void onUncloak() {
	}
	
	/**
	 * Helper method that checks, if the ShapeContext is of a player and if the player has the matching advancement
	 *
	 * @param context the ShapeContext to check
	 */
	default boolean isVisibleTo(CollisionContext context) {
		if (context instanceof EntityCollisionContext entityShapeContext && entityShapeContext.getEntity() instanceof Player player) {
			return this.isVisibleTo(player);
		}
		return false;
	}
	
	/**
	 * Helper method that checks, if the player has the matching advancement
	 *
	 * @param player the player to check
	 */
	default boolean isVisibleTo(@Nullable Player player) {
		return AdvancementHelper.hasAdvancement(player, getCloakAdvancementIdentifier());
	}
	
	
	/**
	 * Helper method that returns the player in a lootContextBuilder
	 *
	 * @param lootContextBuilderSet The loot context builder set to search a player in
	 * @return the player of that loot context builder. null if there is no player in that context
	 */
	@Nullable
	static Player getLootPlayerEntity(LootParams.Builder lootContextBuilderSet) {
		Entity entity = lootContextBuilderSet.getOptionalParameter(LootContextParams.THIS_ENTITY);
		if (entity instanceof Player player) {
			return player;
		}
		return null;
	}
}