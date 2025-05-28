package earth.terrarium.pastel.mixin.client;

import de.dafuqs.arrowhead.api.ArrowheadCrossbow;
import earth.terrarium.pastel.registries.SpectrumItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	private static boolean isChargedCrossbow(ItemStack stack) {
		return false;
	}
	
	@Shadow
	private static ItemInHandRenderer.HandRenderSelection selectionUsingItemWhileHoldingBowLike(LocalPlayer clientPlayerEntity) {
		throw new AssertionError();
	}
	
	@Inject(method = "evaluateWhichHandsToRender",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
			cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void arrowhead$getHandRenderType(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir, ItemStack itemStack, ItemStack itemStack2) {
		Item item1 = itemStack.getItem();
		Item item2 = itemStack2.getItem();
		boolean bl = item1 == SpectrumItems.BEDROCK_BOW.get() || item2 == SpectrumItems.BEDROCK_BOW.get();
		boolean bl2 = item1 instanceof ArrowheadCrossbow || item2 instanceof ArrowheadCrossbow;
		if (!bl && !bl2) {
			// vanilla behavior
		} else if (player.isUsingItem()) {
			cir.setReturnValue(HeldItemRendererMixin.selectionUsingItemWhileHoldingBowLike(player));
		} else {
			cir.setReturnValue(isChargedCrossbow(itemStack) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
		}
	}
	
	@Inject(method = "selectionUsingItemWhileHoldingBowLike",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
			cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void arrowhead$getUsingItemHandRenderType(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir, ItemStack activeStack, InteractionHand activeHand) {
		ItemStack itemStack = player.getUseItem();
		InteractionHand hand = player.getUsedItemHand();
		if (itemStack.getItem() == SpectrumItems.BEDROCK_BOW.get() || itemStack.getItem() instanceof ArrowheadCrossbow) {
			cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(hand));
		}
	}
	
	@Inject(method = "isChargedCrossbow",
			at = @At(value = "HEAD"),
			cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void arrowhead$isChargedCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if(stack.getItem() instanceof ArrowheadCrossbow && CrossbowItem.isCharged(stack)) {
			cir.setReturnValue(true);
		}
	}
}
