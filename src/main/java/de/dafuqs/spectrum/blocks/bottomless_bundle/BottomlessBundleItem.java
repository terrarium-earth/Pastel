package de.dafuqs.spectrum.blocks.bottomless_bundle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.InventoryInsertionAcceptor;
import de.dafuqs.spectrum.api.render.DynamicItemRenderer;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.items.tooltip.BottomlessBundleTooltipData;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumEnchantmentTags;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.item.ItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Implementation Detail <p> While it may appear otherwise, the count a bottomless bundle can store may never exceed {@link Integer#MAX_VALUE}
 */
public class BottomlessBundleItem extends BlockItem implements InventoryInsertionAcceptor {
	
	private static final long MAX_STORED_AMOUNT_BASE = 20000;
	
	public BottomlessBundleItem(Block block, Item.Properties settings) {
		super(block, settings.component(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT));
	}
	
	public static long getMaxStoredAmount(int powerLevel) {
		return MAX_STORED_AMOUNT_BASE * (int) Math.pow(10, Math.min(5, powerLevel)); // to not exceed int max
	}
	
	private static boolean dropOneBundledStack(ItemStack stack, Player player) {
		var builder = BottomlessStack.Builder.of(player.level(), stack);
		var dropped = builder.removeFirstStack();
		if (dropped.isEmpty())
			return false;
		
		player.drop(dropped, true);
		builder.buildAndSet(stack);
		return true;
	}
	
	public static boolean isLocked(ItemStack itemStack) {
		return itemStack.has(DataComponents.LOCK);
	}
	
	public static ItemStack getTemplateVariant(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT).variant();
	}
	
	public static long getStoredAmount(ItemStack voidBundleStack) {
		return voidBundleStack
				.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT)
				.count();
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		if (user.isShiftKeyDown()) {
			ItemStack handStack = user.getItemInHand(hand);
			if (handStack.has(DataComponents.LOCK)) {
				handStack.remove(DataComponents.LOCK);
				if (world.isClientSide) {
					playZipSound(user, 0.8F);
				}
			} else {
				handStack.set(DataComponents.LOCK, LockCode.NO_LOCK);
				if (world.isClientSide) {
					playZipSound(user, 1.0F);
				}
			}
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else if (dropOneBundledStack(itemStack, user)) {
			this.playDropContentsSound(user);
			user.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemStack);
		}
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer().isShiftKeyDown())
			return super.useOn(context);
		return InteractionResult.PASS;
	}
	
	@Override
	public boolean canFitInsideContainerItems() {
		return false;
	}
	
	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack bundleStack) {
		ItemStack variant = getTemplateVariant(bundleStack);
		var storedAmount = getStoredAmount(bundleStack);
		
		return Optional.of(new BottomlessBundleTooltipData(variant, storedAmount));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		boolean locked = isLocked(stack);
		long storedAmount = getStoredAmount(stack);
		if (storedAmount == 0) {
			tooltip.add(Component.translatable("item.spectrum.bottomless_bundle.tooltip.empty").withStyle(ChatFormatting.GRAY));
			if (locked) {
				tooltip.add(
						Component.translatable("item.spectrum.bottomless_bundle.tooltip.locked").withStyle(ChatFormatting.GRAY));
			}
		} else {
			ItemStack variant = getTemplateVariant(stack);
			var powerLevel = context.registries()
					.lookup(Registries.ENCHANTMENT)
					.flatMap(impl -> impl.get(Enchantments.POWER))
					.map(ench -> EnchantmentHelper.getItemEnchantmentLevel(ench, stack))
					.orElse(0);
			String totalStacks = Support.getShortenedNumberString(storedAmount / (float) variant.getItem().getDefaultMaxStackSize());
			tooltip.add(Component.translatable("item.spectrum.bottomless_bundle.tooltip.count", storedAmount,
					getMaxStoredAmount(powerLevel), totalStacks).withStyle(ChatFormatting.GRAY));
			if (locked) {
				tooltip.add(Component.translatable("item.spectrum.bottomless_bundle.tooltip.locked").withStyle(ChatFormatting.GRAY));
			} else {
				tooltip.add(Component.translatable("item.spectrum.bottomless_bundle.tooltip.enter_inventory",
						variant.getItem().getDescription().getString()).withStyle(ChatFormatting.GRAY));
			}
		}
		if (EnchantmentHelper.hasTag(stack, SpectrumEnchantmentTags.DELETES_OVERFLOW)) {
			tooltip.add(Component.translatable("item.spectrum.bottomless_bundle.tooltip.voiding"));
		}
	}
	
	@Override
	public void onDestroyed(ItemEntity entity) {
		var bottomlessStack = entity.getItem().get(SpectrumDataComponentTypes.BOTTOMLESS_STACK);
		if (bottomlessStack != null) {
			entity.getItem().set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT);
			ItemUtils.onContainerDestroyed(entity, bottomlessStack.iterateCopy());
		}
	}
	
	/**
	 * When the bundle is clicked onto another stack
	 */
	@Override
	public boolean overrideStackedOnOther(ItemStack bundleStack, Slot slot, ClickAction clickType, Player player) {
		if (bundleStack.getCount() != 1 || clickType != ClickAction.SECONDARY) {
			return false;
		}
		
		ItemStack slotStack = slot.getItem();
		var builder = BottomlessStack.Builder.of(player.level(), bundleStack);
		if (slotStack.isEmpty()) {
			var removed = builder.removeFirstStack();
			if (!removed.isEmpty()) {
				this.playRemoveOneSound(player);
				var remainder = slot.safeInsert(removed);
				builder.add(remainder);
			}
		} else if (slotStack.getItem().canFitInsideContainerItems()) {
			var added = builder.add(slot, player);
			if (added > 0) {
				this.playInsertSound(player);
			}
		}
		
		builder.buildAndSet(bundleStack);
		return true;
	}
	
	/**
	 * When a stack is right-clicked onto the bundle
	 */
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack bundleStack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
		if (bundleStack.getCount() != 1 || clickType != ClickAction.SECONDARY || !slot.allowModification(player)) {
			return false;
		}
		
		var builder = BottomlessStack.Builder.of(player.level(), bundleStack);
		if (otherStack.isEmpty()) {
			var removed = builder.removeFirstStack();
			if (!removed.isEmpty()) {
				this.playRemoveOneSound(player);
				cursorStackReference.set(removed);
			}
		} else {
			var added = builder.add(otherStack);
			if (added > 0) {
				otherStack.shrink(added);
				this.playInsertSound(player);
			}
		}
		
		builder.buildAndSet(bundleStack);
		return true;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		// We unbundle, tick and then rebundle the stack, in case inventory tick would modify components, count or other properties
		// The slot isn't technically correct, since it's the slot of the bundle, not that of the bundled stack
		BottomlessStack.Builder builder = BottomlessStack.Builder.of(world, stack);
		ItemStack bundled = builder.getReference();
		ItemStack ticked = builder.getReference().copyWithCount((int) Math.min(Integer.MAX_VALUE, builder.count));
		long preTickCount = ticked.getCount();
		ticked.inventoryTick(world, entity, slot, selected);
		if (!ItemStack.isSameItemSameComponents(bundled, ticked) || ticked.getCount() != preTickCount) {
			builder.set(ticked, Math.min(builder.getMaxAllowed(ticked), builder.count + ticked.getCount() - preTickCount));
			builder.buildAndSet(stack);
		}
	}
	
	
	@Override
	public boolean acceptsItemStack(ItemStack inventoryInsertionAcceptorStack, ItemStack itemStackToAccept) {
		ItemStack reference = getTemplateVariant(inventoryInsertionAcceptorStack);
		return !reference.isEmpty() && ItemStack.isSameItemSameComponents(inventoryInsertionAcceptorStack, reference);
	}
	
	@Override
	public int acceptItemStack(ItemStack inventoryInsertionAcceptorStack, ItemStack itemStackToAccept, Player playerEntity) {
		if (isLocked(inventoryInsertionAcceptorStack)) {
			return itemStackToAccept.getCount();
		}
		
		var builder = BottomlessStack.Builder.of(playerEntity.level(), inventoryInsertionAcceptorStack);
		var added = builder.add(itemStackToAccept);
		builder.buildAndSet(inventoryInsertionAcceptorStack);
		return itemStackToAccept.getCount() - added;
	}
	
	private void playRemoveOneSound(Entity entity) {
		entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F,
				0.8F + entity.level().getRandom().nextFloat() * 0.4F);
	}
	
	private void playInsertSound(Entity entity) {
		entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
	}
	
	private void playDropContentsSound(Entity entity) {
		entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F,
				0.8F + entity.level().getRandom().nextFloat() * 0.4F);
	}
	
	private void playZipSound(Entity entity, float basePitch) {
		entity.playSound(SpectrumSoundEvents.BOTTOMLESS_BUNDLE_ZIP, 0.8F,
				basePitch + entity.level().getRandom().nextFloat() * 0.4F);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantmentValue() {
		return 5;
	}
	
	public static class BottomlessBundlePlacementDispenserBehavior extends OptionalDispenseItemBehavior {
		
		@Override
		@SuppressWarnings("resource")
		protected ItemStack execute(BlockSource pointer, ItemStack stack) {
			this.setSuccess(false);
			if (stack.getItem() instanceof BottomlessBundleItem bottomlessBundleItem) {
				Direction direction = pointer.state().getValue(DispenserBlock.FACING);
				BlockPos blockPos = pointer.pos().relative(direction);
				Direction direction2 = pointer.level().isEmptyBlock(blockPos.below()) ? direction : Direction.UP;
				
				try {
					this.setSuccess(bottomlessBundleItem.place(new DirectionalPlaceContext(pointer.level(), blockPos, direction, stack, direction2)).consumesAction());
				} catch (Exception e) {
					SpectrumCommon.logError("Error trying to place bottomless bundle at " + blockPos + " : " + e);
				}
			}
			return stack;
		}
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Renderer implements DynamicItemRenderer {
		public Renderer() {
		}
		
		@Override
		public void render(ItemRenderer renderer, ItemStack stack, ItemDisplayContext mode, boolean leftHanded,
						   PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay,
						   BakedModel model) {
			if (mode != ItemDisplayContext.GUI
					|| getStoredAmount(stack) <= 0)
				return;
			ItemStack bundledStack = BottomlessBundleItem.getTemplateVariant(stack);
			Minecraft client = Minecraft.getInstance();
			BakedModel bundledModel = renderer.getModel(bundledStack, client.level, client.player, 0);
			
			matrices.pushPose();
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0.5F, 0.5F, 0.5F);
			renderer.render(bundledStack, mode, leftHanded, matrices, vertexConsumers, light, overlay, bundledModel);
			matrices.popPose();
		}
	}
	
	public record BottomlessStack(ItemStack variant, long count, boolean locked) {
		
		public static BottomlessStack DEFAULT = new BottomlessStack(ItemStack.EMPTY, 0, false);
		
		public static Codec<BottomlessStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemStack.CODEC.fieldOf("variant").forGetter(BottomlessStack::variant),
				Codec.LONG.fieldOf("count").forGetter(BottomlessStack::count),
				Codec.BOOL.fieldOf("locked").forGetter(BottomlessStack::locked)
		).apply(instance, BottomlessStack::new));
		
		public static StreamCodec<RegistryFriendlyByteBuf, BottomlessStack> STREAM_CODEC = StreamCodec.composite(
				ItemStack.STREAM_CODEC, BottomlessStack::variant,
				ByteBufCodecs.VAR_LONG, BottomlessStack::count,
				ByteBufCodecs.BOOL, BottomlessStack::locked,
				BottomlessStack::new
		);
		
		public Iterable<ItemStack> iterateCopy() {
			return new Iterable<>() {
				
				@Override
				public @NotNull Iterator<ItemStack> iterator() {
					return new Iterator<>() {
						
						private final Builder builder = new Builder(BottomlessStack.this, Integer.MAX_VALUE, false, false);
						
						@Override
						public boolean hasNext() {
							return !builder.isEmpty();
						}
						
						@Override
						public ItemStack next() {
							return builder.removeFirstStack();
						}
						
					};
				}
				
			};
		}
		
		public static class Builder {
			
			private final boolean voiding, locked;
			private final long max;
			private long count;
			private ItemStack reference;
			
			public static Builder of(Level world, ItemStack stack) {
				var prev = stack.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT);
				var max = BottomlessBundleItem.getMaxStoredAmount(SpectrumEnchantmentHelper.getLevel(world.registryAccess(), Enchantments.POWER, stack));
				var voiding = EnchantmentHelper.hasTag(stack, SpectrumEnchantmentTags.DELETES_OVERFLOW_IN_INVENTORY);
				var locked = stack.has(DataComponents.LOCK);
				return new Builder(prev, max, voiding, locked);
			}
			
			public Builder(BottomlessStack prev, long max, boolean voiding, boolean locked) {
				this.reference = prev.variant();
				this.max = max;
				this.count = prev.count();
				this.voiding = voiding;
				this.locked = locked;
			}
			
			public Builder clear() {
				this.reference = ItemStack.EMPTY;
				this.count = 0;
				return this;
			}
			
			public int getMaxAllowed(ItemStack stack) {
				return (int) Math.min(getMaxAllowed(stack, stack.getCount()), Integer.MAX_VALUE);
			}
			
			public long getMaxAllowed(ItemStack variant, long amount) {
				if (isEmpty()) {
					return this.max;
				}
				if (variant.isEmpty() || amount <= 0 || !variant.getItem().canFitInsideContainerItems())
					return 0;
				return voiding ? Long.MAX_VALUE : this.max - this.count;
			}
			
			public int add(ItemStack stack) {
				int toAdd = Math.min(stack.getCount(), this.getMaxAllowed(stack));
				if (toAdd == 0)
					return 0;
				
				if (this.count == 0)
					this.reference = stack.copyWithCount(1);
				
				this.count += Math.min(this.max - this.count, toAdd);
				return toAdd;
			}
			
			public void setStack(ItemStack stack) {
				this.reference = stack.copyWithCount(1);
			}
			
			public void set(ItemStack stack, long count) {
				if (stack.isEmpty() || count == 0) {
					this.reference = ItemStack.EMPTY;
					this.count = 0;
				} else {
					this.reference = stack.copyWithCount(1);
					this.count = count;
				}
			}
			
			public long add(Slot slot, Player player) {
				var maxAllowed = this.getMaxAllowed(slot.getItem());
				return this.add(slot.safeTake(slot.getItem().getCount(), maxAllowed, player));
			}
			
			public ItemStack remove(int amount) {
				if (isEmpty())
					return ItemStack.EMPTY;
				
				var toRemove = Math.min((int) this.count, amount);
				var removed = this.reference.copyWithCount(toRemove);
				this.count -= toRemove;
				if (this.count == 0)
					this.reference = ItemStack.EMPTY;
				
				return removed;
			}
			
			public ItemStack removeFirstStack() {
				return remove(reference.getItem().getDefaultMaxStackSize());
			}
			
			public long getCount() {
				return count;
			}
			
			public ItemStack getReference() {
				return reference;
			}
			
			public boolean isEmpty() {
				return count == 0 || reference.isEmpty();
			}
			
			public void buildAndSet(ItemStack bottomlessBundleStack) {
				if (this.isEmpty()) {
					bottomlessBundleStack.remove(SpectrumDataComponentTypes.BOTTOMLESS_STACK);
				} else {
					bottomlessBundleStack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, new BottomlessStack(reference, count, locked));
				}
			}
		}
		
	}
	
	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.POWER) || enchantment.is(SpectrumEnchantments.VOIDING);
	}
	
}
