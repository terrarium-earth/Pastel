package earth.terrarium.pastel.compat.emi.recipes;

import com.cmdpro.databank.DatabankUtils;
import com.google.common.collect.Lists;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.widgets.DynamicStackWidget;
import earth.terrarium.pastel.compat.emi.widgets.DynamicTextWidget;
import earth.terrarium.pastel.compat.emi.widgets.SaneButtonWidget;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.recipe.GatedPastelRecipe;
import earth.terrarium.pastel.recipe.RecipeScaling;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentUpgradeEmiRecipeGated extends GatedSpectrumEmiRecipe<GatedPastelRecipe<?>> {
	
	private static final ResourceLocation BACKGROUND_TEXTURE = PastelCommon.locate("textures/gui/container/enchanter.png");
	private static final int NORMAL_COLOR = 0x4d3655;
	private static final int OVERCHANT_COLOR = 0xdb3564;
	
	private static final int SWITCH_TIME = 30;
	private static final int XP_INDEX = 8;
	private static final int BOOK_INDEXES_START = 9;
	
	private final Component transKey;
	private final int levelCap;
	private final int maxNormal;
	private final RecipeScaling.ScalingData itemScaling;
	private final RecipeScaling.ScalingData xpScaling;
	private int indexer = 1;
	
	public EnchantmentUpgradeEmiRecipeGated(EmiRecipeCategory category, EnchantmentUpgradeRecipe recipe) {
		super(category, recipe, 132, 90);
		this.itemScaling = recipe.getItemScaling();
		this.xpScaling = recipe.getXpScaling();
		
		inputs = Lists.newArrayList();
		
		var enchant = recipe.getEnchantment();
		levelCap = recipe.getLevelCap();
		maxNormal = enchant.value().getMaxLevel();
		transKey = enchant.value().description().copy().withStyle(s -> s.withItalic(true));
		
		
		// Pigments first due to funny bullshit
		for (int i = 0; i < 8; i++) {
			inputs.add(EmiStack.of(recipe.getBulkItem(), 1));
		}
		
		// Then the xp
		inputs.add(EmiStack.of(KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getBaseXPCost(), true)));
		
		this.outputs = new ArrayList<>();
		
		// Last the book
		for (int i = 1; i <= levelCap; i++) {
			var enchStack = new ItemStack(Items.ENCHANTED_BOOK);
			var builder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
			
			builder.set(enchant, i);
			enchStack.set(DataComponents.STORED_ENCHANTMENTS, builder.toImmutable());
			inputs.add(EmiStack.of(enchStack.copy()));
			
			if (i > 1) {
				enchStack.set(DataComponents.STORED_ENCHANTMENTS, builder.toImmutable());
				outputs.add(EmiStack.of(enchStack));
			}
		}
		
	}
	
	@Override
	public void addUnlockedWidgets(WidgetHolder widgets) {
		var overEnchant = DatabankUtils.hasAdvancement(Minecraft.getInstance().player, PastelAdvancements.OVERENCHANTING);
		
		// Reset the indexer
		indexer = 1;
		
		widgets.addTexture(BACKGROUND_TEXTURE, 13, 13, 54, 54, 0, 0);
		if (overEnchant && levelCap > maxNormal)
			widgets.addTexture(BACKGROUND_TEXTURE, 0, 0, 16, 16, 64, 0).tooltipText(List.of(Component.translatable(EnchanterBlockEntity.OVERCHANTING_TOOLTIP).withStyle(s -> s.withColor(OVERCHANT_COLOR))));
		
		// Knowledge Gem and Enchanter
		final var gem = new DynamicStackWidget(c -> {
			var xp = xpScaling.apply(indexer);
			return EmiStack.of(KnowledgeGemItem.getKnowledgeDropStackWithXP(xp, false));
		}, 0, 111, 5);
		widgets.add(gem);
		widgets.addSlot(EmiStack.of(PastelBlocks.ENCHANTER.get()), 111, 51).drawBack(false);
		
		var cap = overEnchant ? levelCap : maxNormal;
		// Indexing buttons
		var minus = new SaneButtonWidget(84, 18, 8, 8, 64, 16, BACKGROUND_TEXTURE,() -> false, (mX, mY, b) -> {
			indexer = Math.clamp(indexer - 1, 1, cap - 1);
		}).tooltipText(List.of(Component.translatable(EnchanterBlockEntity.CYCLING)));
		var plus = new SaneButtonWidget(94, 18, 8, 8, 72, 16, BACKGROUND_TEXTURE,() -> false, (mX, mY, b) -> {
			indexer = Math.clamp(indexer + 1, 1, cap - 1);
		}).tooltipText(List.of(Component.translatable(EnchanterBlockEntity.CYCLING)));
		
		widgets.add(minus);
		widgets.add(plus);
		
		// surrounding input slots
		widgets.addSlot(inputs.get(0), 18, 0);
		widgets.addSlot(inputs.get(1), 44, 0);
		widgets.addSlot(inputs.get(2), 62, 18);
		widgets.addSlot(inputs.get(3), 62, 44);
		widgets.addSlot(inputs.get(4), 44, 62);
		widgets.addSlot(inputs.get(5), 18, 62);
		widgets.addSlot(inputs.get(6), 0, 44);
		widgets.addSlot(inputs.get(7), 0, 18);
		
		// Center Slot
		final var in = new DynamicStackWidget(c -> inputs.get(BOOK_INDEXES_START + indexer - 1), 0, 31, 31);
		widgets.add(in);
		
		// Output
		final var out = new DynamicStackWidget(c -> inputs.get(BOOK_INDEXES_START + indexer), 0, 106, 26);
		out.large(true).recipeContext(this);
		widgets.add(out);
		widgets.addTexture(EmiTexture.EMPTY_ARROW, 80, 31);
		
		// Info
		final var lv = new DynamicTextWidget(c -> {
			var color = NORMAL_COLOR;
			if (indexer + 1 > maxNormal)
				color = OVERCHANT_COLOR;
			
			
			return new Tuple<>(Component.translatable(EnchanterBlockEntity.LEVEL_TRANS, indexer, indexer + 1).getVisualOrderText(), color);
		}, 67, 2, false);
		widgets.add(lv);
		
		
		final var itemUse = new DynamicTextWidget(c -> new Tuple<>(Component.translatable(EnchanterBlockEntity.ITEM_TRANS, itemScaling.apply(indexer)).getVisualOrderText(), NORMAL_COLOR), 67, 70, false);
		widgets.add(itemUse);
		
		widgets.addText(transKey, 3, 82, NORMAL_COLOR, false);
	}
	
	@Override
	public List<EmiStack> getOutputs() {
		return super.getOutputs();
	}
	
	@Override
	public List<EmiIngredient> getInputs() {
		return super.getInputs();
	}
}
