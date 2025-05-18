package de.dafuqs.spectrum.blocks.gravity;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Hashtable;
import java.util.Map;

public class CloakedFloatItem extends FloatItem implements RevelationAware {
	
	final ResourceLocation cloakAdvancementIdentifier;
	final Item cloakItem;
	
	public CloakedFloatItem(Properties settings, float gravityMod, ResourceLocation cloakAdvancementIdentifier, Item cloakItem) {
		super(settings, gravityMod);
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
