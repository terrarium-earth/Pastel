package earth.terrarium.pastel.blocks.conditional.amaranth;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Map;

public class AmaranthBushelBlock extends FlowerBlock implements RevelationAware {

	public static final MapCodec<AmaranthBushelBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects),
			propertiesCodec()
	).apply(instance, AmaranthBushelBlock::new));

	public AmaranthBushelBlock(Holder<MobEffect> stewEffect, float effectLengthInSeconds, BlockBehaviour.Properties settings) {
		this(makeEffectList(stewEffect, effectLengthInSeconds), settings);
	}

	public AmaranthBushelBlock(SuspiciousStewEffects stewEffects, BlockBehaviour.Properties settings) {
		super(stewEffects, settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends AmaranthBushelBlock> codec() {
		return CODEC;
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return PastelAdvancements.REVEAL_AMARANTH;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		map.put(this.defaultBlockState(), Blocks.FERN.defaultBlockState());
		return map;
	}
	
	@Override
	public @Nullable Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this.asItem(), Blocks.FERN.asItem());
	}
	
	@Override
	public void onUncloak() {
		if (PastelColorProviders.amaranthBushelBlockColorProvider != null && PastelColorProviders.amaranthBushelItemColorProvider != null) {
			PastelColorProviders.amaranthBushelBlockColorProvider.setShouldApply(false);
			PastelColorProviders.amaranthBushelItemColorProvider.setShouldApply(false);
		}
	}
	
	@Override
	public void onCloak() {
		if (PastelColorProviders.amaranthBushelBlockColorProvider != null && PastelColorProviders.amaranthBushelItemColorProvider != null) {
			PastelColorProviders.amaranthBushelBlockColorProvider.setShouldApply(true);
			PastelColorProviders.amaranthBushelItemColorProvider.setShouldApply(true);
		}
	}

}
