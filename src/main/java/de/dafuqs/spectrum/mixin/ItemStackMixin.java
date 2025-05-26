package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.api.gui.SlotWithOnClickAction;
import de.dafuqs.spectrum.api.item.Preenchanted;
import de.dafuqs.spectrum.api.item.TooltipExtensions;
import de.dafuqs.spectrum.items.ConcealingOilsItem;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumEnchantmentTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	
	@Shadow
	public abstract boolean is(TagKey<Item> tag);
	
	@Shadow
	public abstract boolean is(Item item);
	
	@Shadow
	public abstract Item getItem();
	
	@Shadow
	@Nullable
	public abstract <T> T remove(DataComponentType<? extends T> type);
	
	// Injecting into onStackClicked instead of onClicked because onStackClicked is called first
	@Inject(at = @At("HEAD"), method = "overrideStackedOnOther", cancellable = true)
	public void spectrum$onStackClicked(Slot slot, ClickAction clickType, Player player, CallbackInfoReturnable<Boolean> cir) {
		if (slot instanceof SlotWithOnClickAction slotWithOnClickAction) {
			if (slotWithOnClickAction.onClicked((ItemStack) (Object) this, clickType, player)) {
				cir.setReturnValue(true);
			}
		}
	}
	
	@ModifyReturnValue(method = "canBeHurtBy", at = @At(value = "RETURN"))
	public boolean spectrum$applyIndestructibleEnchantment(boolean original) {
		var stack = (ItemStack) (Object) this;
		
		return original && !EnchantmentHelper.hasTag(stack, SpectrumEnchantmentTags.INDESTRUCTIBLE_EFFECT);
	}
	
	// thank you so, so much @williewillus / @Botania for this snippet of code
	// https://github.com/VazkiiMods/Botania/blob/1.18.x/Fabric/src/main/java/vazkii/botania/fabric/mixin/FabricMixinItemStack.java
	@Inject(at = @At("HEAD"), method = "is(Lnet/minecraft/world/item/Item;)Z", cancellable = true)
	private void spectrum$isSpectrumShears(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item == Items.SHEARS) {
			if (is(SpectrumItems.BEDROCK_SHEARS)) {
				cir.setReturnValue(true);
			}
		}
	}
	
	// The enchantment table does not allow enchanting items that already have enchantments applied
	// This mixin changes items, that only got their DefaultEnchantments to still be enchantable
	@Inject(method = "isEnchantable()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments;isEmpty()Z"), cancellable = true)
	public void spectrum$isEnchantable(CallbackInfoReturnable<Boolean> cir) {
		var stack = (ItemStack) (Object) this;
		if (this.getItem() instanceof Preenchanted preenchanted && preenchanted.onlyHasPreEnchantments(stack)) {
			cir.setReturnValue(true);
		}
	}
	
	@Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"))
	public void spectrum$playerTooltip(Item.TooltipContext context, Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> tooltip) {
		var stack = (ItemStack) (Object) this;
		if (stack.getItem() instanceof TooltipExtensions expanded) {
			expanded.appendTooltipWithPlayer(stack, player, tooltip, context);
		}
	}
	
	@Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "net/minecraft/world/item/TooltipFlag.isAdvanced ()Z", shift = At.Shift.BEFORE, ordinal = 1))
	public void spectrum$expandTooltipPostDamage(Item.TooltipContext context, Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> tooltip) {
		var stack = (ItemStack) (Object) this;
		var oilEffect = stack.get(SpectrumDataComponentTypes.CONCEALED_EFFECT);
		var profile = stack.get(DataComponents.PROFILE);
		if (oilEffect != null && profile != null && player.getUUID().equals(profile.id().orElse(null))) {
			var subText = new ArrayList<Component>();
			PotionContents.addPotionTooltip(List.of(oilEffect), subText::add, 1f, context.tickRate());
			
			tooltip.add(Component.translatable("info.pastel.tooltip.adulterated.info").withStyle(s -> s.withColor(ConcealingOilsItem.POISONED_COLOUR)));
			tooltip.add(Component.translatable("info.pastel.tooltip.adulterated.effect", subText.getFirst()).withStyle(s -> s.withColor(ConcealingOilsItem.POISONED_COLOUR).withItalic(true)));
		}
		
		if (stack.getItem() instanceof TooltipExtensions expanded) {
			expanded.expandTooltipPostStats(stack, player, tooltip, context);
		}
	}
	
}