package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.api.gui.SlotWithOnClickAction;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.api.item.TooltipExtensions;
import earth.terrarium.pastel.items.ConcealingOilsItem;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelItems;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public void onStackClicked(Slot slot, ClickAction clickType, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (slot instanceof SlotWithOnClickAction slotWithOnClickAction) {
            if (slotWithOnClickAction.onClicked((ItemStack) (Object) this, clickType, player)) {
                cir.setReturnValue(true);
            }
        }
    }

    @ModifyReturnValue(method = "isDamageableItem", at = @At(value = "RETURN"))
    public boolean applyIndestructibleEnchantment(boolean original) {
        var stack = (ItemStack) (Object) this;

        return original && !EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.INDESTRUCTIBLE_EFFECT);
    }

    // thank you so, so much @williewillus / @Botania for this snippet of code
    // https://github.com/VazkiiMods/Botania/blob/1.18.x/Fabric/src/main/java/vazkii/botania/fabric/mixin/FabricMixinItemStack.java
    @Inject(at = @At("HEAD"), method = "is(Lnet/minecraft/world/item/Item;)Z", cancellable = true)
    private void isSpectrumShears(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item == Items.SHEARS) {
            if (is(PastelItems.BEDROCK_SHEARS.get())) {
                cir.setReturnValue(true);
            }
        }
    }

    // The enchantment table does not allow enchanting items that already have enchantments applied
    // This mixin changes items, that only got their DefaultEnchantments to still be enchantable
    @Inject(method = "isEnchantable()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments;isEmpty()Z"),
            cancellable = true)
    public void isEnchantable(CallbackInfoReturnable<Boolean> cir) {
        var stack = (ItemStack) (Object) this;
        if (this.getItem() instanceof Preenchanted preenchanted && preenchanted.onlyHasPreEnchantments(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE",
                                                 target = "Lnet/minecraft/world/item/Item;appendHoverText" +
                                                          "(Lnet/minecraft/world/item/ItemStack;" +
                                                          "Lnet/minecraft/world/item/Item$TooltipContext;" +
                                                          "Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"))
    public void playerTooltip(
        Item.TooltipContext context, Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir,
        @Local List<Component> tooltip
    ) {
        var stack = (ItemStack) (Object) this;
        if (stack.getItem() instanceof TooltipExtensions expanded) {
            expanded.appendTooltipWithPlayer(stack, player, tooltip, context);
        }
    }

    @Inject(method = "getTooltipLines",
            at = @At(value = "INVOKE", target = "net/minecraft/world/item/TooltipFlag.isAdvanced ()Z",
                     shift = At.Shift.BEFORE, ordinal = 1))
    public void expandTooltipPostDamage(
        Item.TooltipContext context, Player player, TooltipFlag type, CallbackInfoReturnable<List<Component>> cir,
        @Local List<Component> tooltip
    ) {
        var stack = (ItemStack) (Object) this;
        var oilEffect = stack.get(PastelDataComponentTypes.CONCEALED_EFFECT);
        var profile = stack.get(DataComponents.PROFILE);
        if (oilEffect != null && profile != null && player.getUUID()
                                                          .equals(profile.id()
                                                                         .orElse(null))) {
            var subText = new ArrayList<Component>();
            PotionContents.addPotionTooltip(List.of(oilEffect), subText::add, 1f, context.tickRate());

            tooltip.add(Component.translatable("info.pastel.tooltip.adulterated.info")
                                 .withStyle(s -> s.withColor(ConcealingOilsItem.POISONED_COLOUR)));
            tooltip.add(Component.translatable("info.pastel.tooltip.adulterated.effect", subText.getFirst())
                                 .withStyle(s -> s.withColor(ConcealingOilsItem.POISONED_COLOUR)
                                                  .withItalic(true)));
        }

        if (stack.getItem() instanceof TooltipExtensions expanded) {
            expanded.expandTooltipPostStats(stack, player, tooltip, context);
        }
    }

    @WrapOperation(method = "getTooltipLines", at = @At(value = "INVOKE",
                                                        target = "Lnet/minecraft/world/item/ItemStack;addToTooltip" +
                                                                 "(Lnet/minecraft/core/component/DataComponentType;" +
                                                                 "Lnet/minecraft/world/item/Item$TooltipContext;" +
                                                                 "Ljava/util/function/Consumer;" +
                                                                 "Lnet/minecraft/world/item/TooltipFlag;)V",
                                                        ordinal = 3))
    private void getTooltipWithEmpower(
        ItemStack instance, DataComponentType<ItemEnchantments> component, Item.TooltipContext context,
        Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag, Operation<Void> original
    ) {
        int empowerLevel = instance.getOrDefault(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, 0);
        if (empowerLevel == 0) {
            original.call(instance, component, context, tooltipAdder, tooltipFlag);
            return;
        }

        ItemEnchantments itemEnchantments = (ItemEnchantments) instance.get(component);
        if (itemEnchantments != null) {
            // if you are using this to grant more than 3 levels you are doing something wrong
            Component addComponent = Component.translatable("pastel.tooltip.crystal_armor_empowered")
                                              .withStyle(ChatFormatting.DARK_BLUE);
            if (itemEnchantments.showInTooltip) {
                HolderLookup.Provider registries = context.registries();
                HolderSet<Enchantment> orderedEnchants = ItemEnchantments.getTagOrEmpty(
                    registries, Registries.ENCHANTMENT, EnchantmentTags.TOOLTIP_ORDER);
                for (Holder<Enchantment> orderedEnchantment : orderedEnchants) {
                    var effectiveBoost = orderedEnchantment.value()
                                                           .getMaxLevel() == 1 ? 0 : empowerLevel;
                    int level = itemEnchantments.enchantments.getInt(orderedEnchantment);
                    if (level > 0 && Enchantment.getFullname(
                        orderedEnchantment, level - effectiveBoost) instanceof MutableComponent mutableComponent) {
                        tooltipAdder.accept(
                            mutableComponent.append(effectiveBoost == 0 ? Component.empty() : addComponent));
                    }
                }

                for (Object2IntMap.Entry<Holder<Enchantment>> enchantment :
                    itemEnchantments.enchantments.object2IntEntrySet()) {
                    var effectiveBoost = enchantment.getKey()
                                                    .value()
                                                    .getMaxLevel() == 1 ? 0 : empowerLevel;
                    Holder<Enchantment> enchantmentKey = (Holder<Enchantment>) enchantment.getKey();
                    if (!orderedEnchants.contains(enchantmentKey) && Enchantment.getFullname(
                        (Holder<Enchantment>) enchantment.getKey(),
                        enchantment.getIntValue() - effectiveBoost
                    ) instanceof MutableComponent mutableComponent) {
                        tooltipAdder.accept(
                            mutableComponent.append(effectiveBoost == 0 ? Component.empty() : addComponent));
                    }
                }
            }
        }
    }

}
