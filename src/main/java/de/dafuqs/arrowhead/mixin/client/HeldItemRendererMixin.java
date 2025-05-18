package de.dafuqs.arrowhead.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.dafuqs.arrowhead.api.ArrowheadBow;
import de.dafuqs.arrowhead.api.ArrowheadCrossbow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {
	
	@Shadow protected abstract void applyItemArmTransform(PoseStack matrices, HumanoidArm arm, float equipProgress);
	
	@Shadow protected abstract void applyItemArmAttackTransform(PoseStack matrices, HumanoidArm arm, float swingProgress);
	
	@Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light);
	
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
		boolean bl = item1 instanceof ArrowheadBow || item2 instanceof ArrowheadBow;
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
		if (itemStack.getItem() instanceof ArrowheadBow || itemStack.getItem() instanceof ArrowheadCrossbow) {
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
	
	
	@Inject(method = "renderArmWithItem",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1),
			cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void arrowhead$renderFirstPersonItem(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci, boolean bl, HumanoidArm arm) {
		if (item.getItem() instanceof ArrowheadCrossbow) {
			boolean bl2 = CrossbowItem.isCharged(item);
			boolean bl3 = arm == HumanoidArm.RIGHT;
			int i = bl3 ? 1 : -1;
			if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
				this.applyItemArmTransform(matrices, arm, equipProgress);
				matrices.translate(((float)i * -0.4785682F), -0.0943870022892952D, 0.05731530860066414D);
				matrices.mulPose(Axis.XP.rotationDegrees(-11.935F));
				matrices.mulPose(Axis.YP.rotationDegrees((float)i * 65.3F));
				matrices.mulPose(Axis.ZP.rotationDegrees((float)i * -9.785F));
				float f = (float)item.getUseDuration(player) - (player.getUseItemRemainingTicks() - tickDelta + 1.0F);
				float g = f / (float)CrossbowItem.getChargeDuration(item, player);
				if (g > 1.0F) {
					g = 1.0F;
				}
				
				if (g > 0.1F) {
					float h = Mth.sin((f - 0.1F) * 1.3F);
					float j = g - 0.1F;
					float k = h * j;
					matrices.translate((k * 0.0F), (k * 0.004F), (k * 0.0F));
				}
				
				matrices.translate((g * 0.0F), (g * 0.0F), (g * 0.04F));
				matrices.scale(1.0F, 1.0F, 1.0F + g * 0.2F);
				matrices.mulPose(Axis.YN.rotationDegrees((float)i * 45.0F));
			} else {
				float f = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * 3.1415927F);
				float g = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * 6.2831855F);
				float h = -0.2F * Mth.sin(swingProgress * 3.1415927F);
				matrices.translate(((float)i * f), g, h);
				this.applyItemArmTransform(matrices, arm, equipProgress);
				this.applyItemArmAttackTransform(matrices, arm, swingProgress);
				if (bl2 && swingProgress < 0.001F && bl) {
					matrices.translate(((float)i * -0.641864F), 0.0D, 0.0D);
					matrices.mulPose(Axis.YP.rotationDegrees((float)i * 10.0F));
				}
			}
			
			this.renderItem(player, item, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
			
			matrices.popPose();
			
			ci.cancel();
		}
	}
	
}
