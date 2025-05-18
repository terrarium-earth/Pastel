package de.dafuqs.spectrum.items.conditional;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Hashtable;
import java.util.Map;

public class CloakedBlockItem extends BlockItem implements RevelationAware {
	
	final ResourceLocation cloakAdvancementIdentifier;
	final Item cloakItem;
	
	public CloakedBlockItem(Block block, Properties settings, ResourceLocation cloakAdvancementIdentifier, Item cloakItem) {
		super(block, settings);
		this.cloakAdvancementIdentifier = cloakAdvancementIdentifier;
		this.cloakItem = cloakItem;
		RevelationAware.register(this);
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return cloakAdvancementIdentifier;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return new Hashtable<>();
	}
	
	@Override
	public Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this, cloakItem);
	}
	
}
