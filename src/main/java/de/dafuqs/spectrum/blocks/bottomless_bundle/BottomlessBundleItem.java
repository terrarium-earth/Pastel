package de.dafuqs.spectrum.blocks.bottomless_bundle;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.items.tooltip.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.fabricmc.fabric.api.transfer.v1.storage.base.*;
import net.fabricmc.fabric.api.transfer.v1.transaction.*;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.*;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.component.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;
import net.minecraft.screen.slot.*;
import net.minecraft.sound.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BottomlessBundleItem extends BlockItem implements InventoryInsertionAcceptor {

	private static final long MAX_STORED_AMOUNT_BASE = 20000;

	public BottomlessBundleItem(Block block, Item.Settings settings) {
		super(block, settings.component(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT));
	}

	public static long getMaxStoredAmount(int powerLevel) {
		return MAX_STORED_AMOUNT_BASE * (int) Math.pow(10, Math.min(5, powerLevel)); // to not exceed int max
	}

	private static boolean dropOneBundledStack(ItemStack stack, PlayerEntity player) {
		var builder = BottomlessStack.Builder.of(player.getWorld(), stack);
		var dropped = builder.removeFirstStack();
		if (dropped.isEmpty())
			return false;

		player.dropItem(dropped, true);
		stack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
		return true;
	}

	public static boolean isLocked(ItemStack itemStack) {
		return itemStack.contains(DataComponentTypes.LOCK);
	}

	public static ItemStack getTemplateStack(ItemStack stack) {
		return stack
				.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT)
				.template();
	}

	public static long getStoredAmount(ItemStack voidBundleStack) {
		return voidBundleStack
				.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT)
				.count();
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (user.isSneaking()) {
			ItemStack handStack = user.getStackInHand(hand);
			if (handStack.contains(DataComponentTypes.LOCK)) {
				handStack.remove(DataComponentTypes.LOCK);
				if (world.isClient) {
					playZipSound(user, 0.8F);
				}
			} else {
				handStack.set(DataComponentTypes.LOCK, ContainerLock.EMPTY);
				if (world.isClient) {
					playZipSound(user, 1.0F);
				}
			}
			return TypedActionResult.success(itemStack, world.isClient());
		} else if (dropOneBundledStack(itemStack, user)) {
			this.playDropContentsSound(user);
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getPlayer().isSneaking())
			return super.useOnBlock(context);
		return ActionResult.PASS;
	}

	@Override
	public boolean canBeNested() {
		return false;
	}
	
	@Override
	public Optional<TooltipData> getTooltipData(ItemStack voidBundleStack) {
		ItemStack itemStack = getTemplateStack(voidBundleStack);
		var storedAmount = getStoredAmount(voidBundleStack);
		
		return Optional.of(new BottomlessBundleTooltipData(itemStack, storedAmount));
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		boolean locked = isLocked(stack);
		long storedAmount = getStoredAmount(stack);
		if (storedAmount == 0) {
			tooltip.add(Text.translatable("item.spectrum.bottomless_bundle.tooltip.empty").formatted(Formatting.GRAY));
			if (locked) {
				tooltip.add(
						Text.translatable("item.spectrum.bottomless_bundle.tooltip.locked").formatted(Formatting.GRAY));
			}
		} else {
			ItemStack template = getTemplateStack(stack);
			var powerLevel = context.getRegistryLookup()
					.getOptionalWrapper(RegistryKeys.ENCHANTMENT)
					.flatMap(impl -> impl.getOptional(Enchantments.POWER))
					.map(ench -> EnchantmentHelper.getLevel(ench, stack))
					.orElse(0);
			String totalStacks = Support.getShortenedNumberString(storedAmount / (float) template.getMaxCount());
			tooltip.add(Text.translatable("item.spectrum.bottomless_bundle.tooltip.count", storedAmount,
					getMaxStoredAmount(powerLevel), totalStacks).formatted(Formatting.GRAY));
			if (locked) {
				tooltip.add(
						Text.translatable("item.spectrum.bottomless_bundle.tooltip.locked").formatted(Formatting.GRAY));
			} else {
				tooltip.add(Text.translatable("item.spectrum.bottomless_bundle.tooltip.enter_inventory",
						template.getName().getString()).formatted(Formatting.GRAY));
			}
		}
		if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, SpectrumEnchantmentTags.DELETES_OVERFLOW)) {
			tooltip.add(Text.translatable("item.spectrum.bottomless_bundle.tooltip.voiding"));
		}
	}
	
	@Override
	public void onItemEntityDestroyed(ItemEntity entity) {
		var bottomlessStack = entity.getStack().get(SpectrumDataComponentTypes.BOTTOMLESS_STACK);
		if (bottomlessStack != null) {
			entity.getStack().set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT);
			ItemUsage.spawnItemContents(entity, bottomlessStack.iterateCopy());
		}
	}
	
	/**
	 * When the bundle is clicked onto another stack
	 */
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if (clickType == ClickType.RIGHT) {
			ItemStack itemStack = slot.getStack();
			var builder = BottomlessStack.Builder.of(player.getWorld(), stack);
			if (itemStack.isEmpty()) {
				playRemoveOneSound(player);
				var removed = builder.removeFirstStack();
				if (!removed.isEmpty()) {
					var remainder = slot.insertStack(removed);
					builder.add(remainder);
				}
			} else if (itemStack.getItem().canBeNested()) {
				var added = builder.add(slot, player);
				if (added > 0) {
					this.playInsertSound(player);
				}
			}

			stack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
			return true;
		}
		return false;
	}

	/**
	 * When an itemStack is right-clicked onto the bundle
	 */
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
			var builder = BottomlessStack.Builder.of(player.getWorld(), stack);
			if (otherStack.isEmpty()) {
				var removed = builder.removeFirstStack();
				if (!removed.isEmpty()) {
					this.playRemoveOneSound(player);
					cursorStackReference.set(removed);
				}
			} else {
				var added = builder.add(otherStack);
				if (added > 0) {
					this.playInsertSound(player);
				}
			}

			stack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
			return true;
		}
		return false;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		//TODO: Do we need to modify the stack, or can we just test without using the builder?
		var builder = BottomlessStack.Builder.of(world, stack);
		var template = builder.getTemplate();
		// The slot isn't technically correct
		template.getItem().inventoryTick(template, world, entity, slot, selected);
		stack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
	}
	
	@Override
	public boolean acceptsItemStack(ItemStack inventoryInsertionAcceptorStack, ItemStack itemStackToAccept) {
		ItemStack template = getTemplateStack(inventoryInsertionAcceptorStack);
		return !template.isEmpty() && ItemStack.areItemsAndComponentsEqual(template, itemStackToAccept);
	}
	
	@Override
	public int acceptItemStack(ItemStack inventoryInsertionAcceptorStack, ItemStack itemStackToAccept, PlayerEntity playerEntity) {
		if (isLocked(inventoryInsertionAcceptorStack)) {
			return itemStackToAccept.getCount();
		}
		
		var builder = BottomlessStack.Builder.of(playerEntity.getWorld(), inventoryInsertionAcceptorStack);
		var added = builder.add(itemStackToAccept);
		inventoryInsertionAcceptorStack.set(SpectrumDataComponentTypes.BOTTOMLESS_STACK, builder.build());
		return itemStackToAccept.getCount() - added;
	}
	
	private void playRemoveOneSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F,
				0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}
	
	private void playInsertSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}
	
	private void playDropContentsSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8F,
				0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}
	
	private void playZipSound(Entity entity, float basePitch) {
		entity.playSound(SpectrumSoundEvents.BOTTOMLESS_BUNDLE_ZIP, 0.8F,
				basePitch + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantability() {
		return 5;
	}

	public static class BottomlessBundlePlacementDispenserBehavior extends FallibleItemDispenserBehavior {
		
        @Override
		@SuppressWarnings("resource")
		protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
			this.setSuccess(false);
			if (stack.getItem() instanceof BottomlessBundleItem bottomlessBundleItem) {
				Direction direction = pointer.state().get(DispenserBlock.FACING);
				BlockPos blockPos = pointer.pos().offset(direction);
				Direction direction2 = pointer.world().isAir(blockPos.down()) ? direction : Direction.UP;
				
				try {
					this.setSuccess(bottomlessBundleItem.place(new AutomaticItemPlacementContext(pointer.world(), blockPos, direction, stack, direction2)).isAccepted());
				} catch (Exception e) {
					SpectrumCommon.logError("Error trying to place bottomless bundle at " + blockPos + " : " + e);
				}
			}
			return stack;
		}

	}

	@Environment(EnvType.CLIENT)
	public static class Renderer implements DynamicItemRenderer {
		public Renderer() {
		}

		@Override
		public void render(ItemRenderer renderer, ItemStack stack, ModelTransformationMode mode, boolean leftHanded,
						   MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay,
						   BakedModel model) {
			renderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, model);
			if (mode != ModelTransformationMode.GUI
					|| getStoredAmount(stack) <= 0)
				return;
			ItemStack bundledStack = BottomlessBundleItem.getTemplateStack(stack);
			MinecraftClient client = MinecraftClient.getInstance();
			BakedModel bundledModel = renderer.getModel(bundledStack, client.world, client.player, 0);

			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0.5F, 0.5F, 0.5F);
			renderer.renderItem(bundledStack, mode, leftHanded, matrices, vertexConsumers, light, overlay,
					bundledModel);
			matrices.pop();
		}
	}

	public record BottomlessStack(ItemStack template, long count) {

		public static BottomlessStack DEFAULT = new BottomlessStack(ItemStack.EMPTY, 0);

		public static Codec<BottomlessStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("template").forGetter(BottomlessStack::template),
				Codec.LONG.fieldOf("count").forGetter(BottomlessStack::count)
		).apply(instance, BottomlessStack::new));

		public static PacketCodec<RegistryByteBuf, BottomlessStack> PACKET_CODEC = PacketCodec.tuple(
				ItemStack.PACKET_CODEC, BottomlessStack::template,
				PacketCodecs.VAR_LONG, BottomlessStack::count,
				BottomlessStack::new
		);

		public BottomlessStack(ItemStack template, long count) {
			this.template = template.copyAndEmpty();
			this.count = count;
		}

		public Iterable<ItemStack> iterateCopy() {
			return new Iterable<>() {

				@Override
				public @NotNull Iterator<ItemStack> iterator() {
					return new Iterator<>() {

						private final Builder builder = new Builder(BottomlessStack.this, Integer.MAX_VALUE, false);

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

			private final boolean voiding;
			private final long max;
			private long count;
			private ItemStack template;

			public static Builder of(World world, ItemStack stack) {
				var prev = stack.getOrDefault(SpectrumDataComponentTypes.BOTTOMLESS_STACK, BottomlessStack.DEFAULT);
				var max = BottomlessBundleItem.getMaxStoredAmount(SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), Enchantments.POWER, stack));
				var voiding = EnchantmentHelper.hasAnyEnchantmentsIn(stack, SpectrumEnchantmentTags.DELETES_OVERFLOW_IN_INVENTORY);
				return new Builder(prev, max, voiding);
			}

			public Builder(BottomlessStack prev, long max, boolean voiding) {
				this.template = prev.template();
				this.max = max;
				this.count = prev.count();
				this.voiding = voiding;
			}

			public Builder clear() {
				this.template = ItemStack.EMPTY;
				this.count = 0;
				return this;
			}

			public int getMaxAllowed(ItemStack stack) {
				return (int) Math.min(getMaxAllowed(ItemVariant.of(stack), stack.getCount()), Integer.MAX_VALUE);
			}

			public long getMaxAllowed(ItemVariant variant, long amount) {
				if (isEmpty() || variant.isBlank() || amount <= 0 || !variant.getItem().canBeNested())
					return 0;
				return voiding ? Long.MAX_VALUE : this.max - this.count;
			}

			public int add(ItemStack stack) {
				int toAdd = Math.min(stack.getCount(), this.getMaxAllowed(stack));
				if (toAdd == 0)
					return 0;

				if (this.count == 0)
					this.template = stack.copyAndEmpty();

				this.count += Math.min(this.max - this.count, toAdd);
				stack.decrement(toAdd);
				return toAdd;
			}

			public long add(SingleVariantStorage<ItemVariant> storage) {
				try (Transaction transaction = Transaction.openOuter()) {
					var max = getMaxAllowed(storage.variant, storage.amount);
					var toAdd = storage.extract(storage.variant, max, transaction);
					if (toAdd == 0)
						return 0;

					if (this.count == 0)
						this.template = storage.variant.toStack(0);

					this.count += Math.min(this.max - this.count, toAdd);
					transaction.commit();
					return toAdd;
				}
			}

			public long add(Slot slot, PlayerEntity player) {
				var i = this.getMaxAllowed(slot.getStack());
				return this.add(slot.takeStackRange(slot.getStack().getCount(), i, player));
			}

			public ItemStack remove(int amount) {
				if (isEmpty())
					return ItemStack.EMPTY;

				var toRemove = Math.min((int) this.count, amount);
				var removed = this.template.copyWithCount(toRemove);
				this.count -= toRemove;
				if (this.count == 0)
					this.template = ItemStack.EMPTY;

				return removed;
			}

			public ItemStack removeFirstStack() {
				return remove(template.getMaxCount());
			}

			public long getCount() {
				return count;
			}

			public ItemStack getTemplate() {
				return template.copy();
			}

			public boolean isEmpty() {
				return count == 0
						|| template == ItemStack.EMPTY
						|| template.getItem() == Items.AIR;
			}

			public BottomlessStack build() {
				return new BottomlessStack(template, count);
			}

		}

	}

}
