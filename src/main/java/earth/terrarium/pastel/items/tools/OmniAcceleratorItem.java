package earth.terrarium.pastel.items.tools;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.interaction.OmniAcceleratorProjectile;
import earth.terrarium.pastel.api.render.DynamicItemRenderer;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class OmniAcceleratorItem extends BundleItem implements InkPowered, ExtendedItemBar, SlotBackgroundEffect {

    protected static final InkCost COST = new InkCost(InkColors.YELLOW, 20);
    protected static final int CHARGE_TIME = 10;

    public OmniAcceleratorItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, user, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return CHARGE_TIME;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!(user instanceof ServerPlayer player)) return stack;

        Optional<ItemStack> shootStackOptional = getFirstStack(world.registryAccess(), stack);
        if (shootStackOptional.isEmpty()) {
            world.playSound(
                null, user.getX(), user.getY(), user.getZ(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 1.0F,
                1.0F
            );
            return stack;
        }

        if (!InkPowered.tryDrainEnergy(player, COST)) {
            world.playSound(
                null, user.getX(), user.getY(), user.getZ(), PastelSounds.USE_FAIL, SoundSource.PLAYERS, 1.0F,
                1.0F
            );
            return stack;
        }

        ItemStack shootStack = shootStackOptional.get();
        OmniAcceleratorProjectile projectile = OmniAcceleratorProjectile.get(shootStack);
        if (projectile.createProjectile(shootStack, user, world, stack) != null) {
            world.playSound(
                null, user.getX(), user.getY(), user.getZ(), projectile.getSoundEffect(), SoundSource.PLAYERS, 0.5F,
                0.4F / (world.getRandom()
                             .nextFloat() * 0.4F + 0.8F)
            );
            if (!player.isCreative()) {
                decrementFirstItem(stack);
            }
        }

        return stack;
    }

    public static void decrementFirstItem(ItemStack acceleratorStack) {
        var comp = acceleratorStack.get(DataComponents.BUNDLE_CONTENTS);
        if (comp == null) return;

        var builder = new BundleContents.Mutable(BundleContents.EMPTY);
        var first = true;
        for (var stack : comp.itemsCopy()) {
            if (first) {
                stack.shrink(1);
                first = false;
            }
            if (!stack.isEmpty())
                builder.tryInsert(stack);
        }

        acceleratorStack.set(DataComponents.BUNDLE_CONTENTS, builder.toImmutable());
    }

    public static Optional<ItemStack> getFirstStack(HolderLookup.Provider wrapperLookup, ItemStack stack) {
        var contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        return contents.isEmpty() ? Optional.empty() : Optional.of(contents.getItemUnsafe(0)
                                                                           .copy());
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(COST.color());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        addInkPoweredTooltip(tooltip);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer implements DynamicItemRenderer {
        public Renderer() {
        }

        @Override
        public void render(
            ItemRenderer renderer, ItemStack stack, ItemDisplayContext mode, boolean leftHanded, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light, int overlay
        ) {
            Minecraft client = Minecraft.getInstance();
            if (mode != ItemDisplayContext.GUI || client.level == null) return;

            Optional<ItemStack> optionalStack = getFirstStack(client.level.registryAccess(), stack);
            if (optionalStack.isEmpty()) {
                return;
            }
            ItemStack bundledStack = optionalStack.get();

            BakedModel bundledModel = renderer.getModel(bundledStack, client.level, client.player, 0);

            matrices.pushPose();
            matrices.scale(0.5F, 0.5F, 0.5F);
            matrices.translate(0.5F, 0.5F, 0.5F);
            renderer.render(bundledStack, mode, leftHanded, matrices, vertexConsumers, light, overlay, bundledModel);
            matrices.popPose();
        }
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        var usable = InkPowered.hasAvailableInk(player, COST);
        return usable ? SlotEffect.BORDER_FADE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return 0xFFFFFF;
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean allowVanillaDurabilityBarRendering(@Nullable Player player, ItemStack stack) {
        if (player == null || player.getItemInHand(player.getUsedItemHand()) != stack)
            return true;

        return !player.isUsingItem();
    }

    @Override
    public ExtendedItemBar.BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        if (player == null || !player.isUsingItem())
            return ExtendedItemBar.PASS;

        var activeStack = player.getItemInHand(player.getUsedItemHand());
        if (activeStack != stack)
            return ExtendedItemBar.PASS;

        var progress = Math.round(Mth.clampedLerp(0, 13, ((float) player.getTicksUsingItem() / CHARGE_TIME)));
        return new ExtendedItemBar.BarSignature(
            2, 13, 13, progress, 1, 0xFFFFFFFF, 2, ExtendedItemBar.DEFAULT_BACKGROUND_COLOR);
    }
}
