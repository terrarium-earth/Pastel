package earth.terrarium.pastel.registries;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.components.BeverageComponent;
import earth.terrarium.pastel.components.CustomPotionDataComponent;
import earth.terrarium.pastel.components.EnderSpliceComponent;
import earth.terrarium.pastel.components.ExtendedBundleComponent;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.components.InkPoweredComponent;
import earth.terrarium.pastel.components.InkStorageComponent;
import earth.terrarium.pastel.components.JadeWineComponent;
import earth.terrarium.pastel.components.MemoryComponent;
import earth.terrarium.pastel.components.PairedFoodComponent;
import earth.terrarium.pastel.components.PairedItemComponent;
import earth.terrarium.pastel.components.ShootingStarComponent;
import earth.terrarium.pastel.components.WithMilkComponent;
import earth.terrarium.pastel.components.WorkstaffComponent;
import earth.terrarium.pastel.components.WrappedPresentComponent;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.fluids.*;
import net.neoforged.neoforge.registries.*;

import java.util.UUID;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class PastelDataComponentTypes {
	
	private static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PastelCommon.MOD_ID);
	
	// It seems like vanilla caches all components with collections (lists, maps, etc.), so we will too
	public static final DataComponentType<Unit> ACTIVATED = register("activated", builder -> builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	public static final DataComponentType<Integer> AOE = register("aoe", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
	public static final DataComponentType<BeverageComponent> BEVERAGE = register("beverage", builder -> builder.persistent(BeverageComponent.CODEC).networkSynchronized(BeverageComponent.STREAM_CODEC));
	public static final DataComponentType<ItemStorage.Component> ITEM_STORAGE = register("item_storage", builder -> builder.persistent(ItemStorage.Component.CODEC).networkSynchronized(ItemStorage.Component.STREAM_CODEC));
	public static final DataComponentType<ResourceLocation> BOUND_ITEM = register("bound_item", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
	public static final DataComponentType<ItemEnchantments> CANVAS_ENCHANTMENTS = register("canvas_enchantments", (builder) -> builder.persistent(ItemEnchantments.CODEC).networkSynchronized(ItemEnchantments.STREAM_CODEC).cacheEncoding());
	public static final DataComponentType<PairedFoodComponent> PAIRED_FOOD_COMPONENT = register("paired_food_component", builder -> builder.persistent(PairedFoodComponent.CODEC).networkSynchronized(PairedFoodComponent.STREAM_CODEC));
	public static final DataComponentType<CustomPotionDataComponent> CUSTOM_POTION_DATA = register("custom_potion_data", builder -> builder.persistent(CustomPotionDataComponent.CODEC).networkSynchronized(CustomPotionDataComponent.STREAM_CODEC));
	public static final DataComponentType<EnderSpliceComponent> ENDER_SPLICE = register("ender_splice", builder -> builder.persistent(EnderSpliceComponent.CODEC).networkSynchronized(EnderSpliceComponent.STREAM_CODEC));
	public static final DataComponentType<ExtendedBundleComponent> EXTENDED_BUNDLE = register("extended_bundle", builder -> builder.persistent(ExtendedBundleComponent.CODEC).networkSynchronized(ExtendedBundleComponent.STREAM_CODEC));
	public static final DataComponentType<Unit> HIDE_USAGE_TOOLTIP = register("hide_usage_tooltip", builder -> builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	public static final DataComponentType<InfusedBeverageComponent> INFUSED_BEVERAGE = register("infused_beverage", builder -> builder.persistent(InfusedBeverageComponent.CODEC).networkSynchronized(InfusedBeverageComponent.STREAM_CODEC));
	public static final DataComponentType<InkColor> INK_COLOR = register("ink_color", builder -> builder.persistent(InkColor.CODEC).networkSynchronized(InkColor.STREAM_CODEC));
	public static final DataComponentType<InkPoweredComponent> INK_POWERED = register("ink_powered", builder -> builder.persistent(InkPoweredComponent.CODEC).networkSynchronized(InkPoweredComponent.STREAM_CODEC).cacheEncoding());
	public static final DataComponentType<InkStorageComponent> INK_STORAGE = register("ink_storage", builder -> builder.persistent(InkStorageComponent.CODEC).networkSynchronized(InkStorageComponent.STREAM_CODEC).cacheEncoding());
	public static final DataComponentType<Unit> IS_PREVIEW_ITEM = register("is_preview_item", builder -> builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	public static final DataComponentType<JadeWineComponent> JADE_WINE = register("jade_wine", builder -> builder.persistent(JadeWineComponent.CODEC).networkSynchronized(JadeWineComponent.STREAM_CODEC));
	public static final DataComponentType<Long> LAST_COOLDOWN_START = register("last_cooldown_start", builder -> builder.persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG));
	public static final DataComponentType<MemoryComponent> MEMORY = register("memory", builder -> builder.persistent(MemoryComponent.CODEC).networkSynchronized(MemoryComponent.STREAM_CODEC).cacheEncoding());
	public static final DataComponentType<MobEffectInstance> CONCEALED_EFFECT = register("concealed_effect", builder -> builder.persistent(MobEffectInstance.CODEC).networkSynchronized(MobEffectInstance.STREAM_CODEC));
	public static final DataComponentType<Float> OVERCHARGED = register("overcharged", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));
	public static final DataComponentType<PairedItemComponent> PAIRED_ITEM = register("paired_item", builder -> builder.persistent(PairedItemComponent.CODEC).networkSynchronized(PairedItemComponent.STREAM_CODEC));
	public static final DataComponentType<Long> TIMESTAMP = register("timestamp", builder -> builder.persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG));
	public static final DataComponentType<ShootingStarComponent> SHOOTING_STAR = register("shooting_star", builder -> builder.persistent(ShootingStarComponent.CODEC).networkSynchronized(ShootingStarComponent.STREAM_CODEC));
	public static final DataComponentType<UUID> SLOT_RESERVER = register("slot_eserver", builder -> builder.persistent(UUIDUtil.STRING_CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC));
	public static final DataComponentType<Unit> SOCKETED = register("socketed", builder -> builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	public static final DataComponentType<Unit> STABLE = register("stable", builder -> builder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	public static final DataComponentType<ResourceLocation> STORED_BLOCK = register("stored_block", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
	public static final DataComponentType<Integer> STORED_EXPERIENCE = register("stored_experience", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
	public static final DataComponentType<ResourceLocation> STORED_RECIPE = register("stored_recipe", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
	public static final DataComponentType<GlobalPos> TARGETED_STRUCTURE = register("targeted_structure", builder -> builder.persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC));
	public static final DataComponentType<WrappedPresentComponent> WRAPPED_PRESENT = register("wrapped_present", builder -> builder.persistent(WrappedPresentComponent.CODEC).networkSynchronized(WrappedPresentComponent.STREAM_CODEC).cacheEncoding());
	public static final DataComponentType<WithMilkComponent> WITH_MILK = register("with_milk", builder -> builder.persistent(WithMilkComponent.CODEC).networkSynchronized(WithMilkComponent.STREAM_CODEC));
	public static final DataComponentType<WorkstaffComponent> WORKSTAFF = register("workstaff", builder -> builder.persistent(WorkstaffComponent.CODEC).networkSynchronized(WorkstaffComponent.STREAM_CODEC));
	public static final DataComponentType<SimpleFluidContent> MERMAIDS_GEM = register("mermaids_gem", builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));
	
	public static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
		var type = builderOperator.apply(DataComponentType.builder()).build();
		REGISTRAR.register(id, () -> type);
		return type;
	}
	
	public static void register(IEventBus bus) {
		REGISTRAR.register(bus);
	}
	
}
