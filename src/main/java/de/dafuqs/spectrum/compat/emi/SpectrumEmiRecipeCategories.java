package de.dafuqs.spectrum.compat.emi;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class SpectrumEmiRecipeCategories {
	public static final EmiRecipeCategory PEDESTAL_CRAFTING = new SpectrumCategory(SpectrumCommon.locate("pedestal_crafting"), EmiStack.of(SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get()));
	public static final EmiRecipeCategory ANVIL_CRUSHING = new SpectrumCategory(SpectrumCommon.locate("anvil_crushing"), EmiStack.of(Blocks.ANVIL));
	public static final EmiRecipeCategory FUSION_SHRINE = new SpectrumCategory(SpectrumCommon.locate("fusion_shrine"), EmiStack.of(SpectrumBlocks.FUSION_SHRINE_CALCITE.get()), "block.pastel.fusion_shrine");
	public static final EmiRecipeCategory NATURES_STAFF = new SpectrumCategory(SpectrumCommon.locate("natures_staff_conversions"), EmiStack.of(SpectrumItems.NATURES_STAFF.get()), SpectrumItems.NATURES_STAFF.get().getDescriptionId());
	public static final EmiRecipeCategory ENCHANTER = new SpectrumCategory(SpectrumCommon.locate("enchanter"), EmiStack.of(SpectrumBlocks.ENCHANTER.get()), "container.pastel.rei.enchanting.title");
	public static final EmiRecipeCategory ENCHANTMENT_UPGRADE = new SpectrumCategory(SpectrumCommon.locate("enchantment_upgrade"), EmiStack.of(SpectrumBlocks.ENCHANTER.get()), "container.pastel.rei.enchantment_upgrading.title");
	public static final EmiRecipeCategory POTION_WORKSHOP_BREWING = new SpectrumCategory(SpectrumCommon.locate("potion_workshop_brewing"), EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
	public static final EmiRecipeCategory POTION_WORKSHOP_CRAFTING = new SpectrumCategory(SpectrumCommon.locate("potion_workshop_crafting"), EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
	public static final EmiRecipeCategory POTION_WORKSHOP_REACTING = new SpectrumCategory(SpectrumCommon.locate("potion_workshop_reacting"), EmiStack.of(SpectrumBlocks.POTION_WORKSHOP.get()));
	public static final EmiRecipeCategory SPIRIT_INSTILLER = new SpectrumCategory(SpectrumCommon.locate("spirit_instiller"), EmiStack.of(SpectrumBlocks.SPIRIT_INSTILLER.get()), SpectrumBlocks.SPIRIT_INSTILLER.get().getDescriptionId());
	public static final EmiRecipeCategory GOO_CONVERTING = new SpectrumCategory(SpectrumCommon.locate("goo_converting"), EmiStack.of(SpectrumItems.GOO_BUCKET.get()));
	public static final EmiRecipeCategory LIQUID_CRYSTAL_CONVERTING = new SpectrumCategory(SpectrumCommon.locate("liquid_crystal_converting"), EmiStack.of(SpectrumItems.LIQUID_CRYSTAL_BUCKET.get()));
	public static final EmiRecipeCategory MIDNIGHT_SOLUTION_CONVERTING = new SpectrumCategory(SpectrumCommon.locate("midnight_solution_converting"), EmiStack.of(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET.get()));
	public static final EmiRecipeCategory DRAGONROT_CONVERTING = new SpectrumCategory(SpectrumCommon.locate("dragonrot_converting"), EmiStack.of(SpectrumItems.DRAGONROT_BUCKET.get()), "container.pastel.rei.dragonrot_converting.title");
	public static final EmiRecipeCategory HEATING = new SpectrumCategory(SpectrumCommon.locate("heating"), EmiStack.of(SpectrumBlocks.BLAZE_IDOL.get()));
	public static final EmiRecipeCategory FREEZING = new SpectrumCategory(SpectrumCommon.locate("freezing"), EmiStack.of(SpectrumBlocks.POLAR_BEAR_IDOL.get()));
	public static final EmiRecipeCategory INK_CONVERTING = new SpectrumCategory(SpectrumCommon.locate("ink_converting"), EmiStack.of(SpectrumBlocks.COLOR_PICKER.get()));
	public static final EmiRecipeCategory CRYSTALLARIEUM = new SpectrumCategory(SpectrumCommon.locate("crystallarieum"), EmiStack.of(SpectrumBlocks.CRYSTALLARIEUM.get()), "block.pastel.crystallarieum");
	public static final EmiRecipeCategory CINDERHEARTH = new SpectrumCategory(SpectrumCommon.locate("cinderhearth"), EmiStack.of(SpectrumBlocks.CINDERHEARTH.get()), SpectrumBlocks.CINDERHEARTH.get().getDescriptionId());
	public static final EmiRecipeCategory TITRATION_BARREL = new SpectrumCategory(SpectrumCommon.locate("titration_barrel"), EmiStack.of(SpectrumBlocks.TITRATION_BARREL.get()), SpectrumBlocks.TITRATION_BARREL.get().getDescriptionId());
	public static final EmiRecipeCategory PRIMORDIAL_FIRE_BURNING = new SpectrumCategory(SpectrumCommon.locate("primordial_fire_burning"), EmiStack.of(SpectrumItems.DOOMBLOOM_SEED.get()), "container.pastel.rei.primordial_fire_burning.title");
	
	private static class SpectrumCategory extends EmiRecipeCategory {
		private final String key;
		
		public SpectrumCategory(ResourceLocation id, EmiRenderable icon) {
			this(id, icon, "container." + id.getNamespace() + ".rei." + id.getPath() + ".title");
		}

		public SpectrumCategory(ResourceLocation id, EmiRenderable icon, String key) {
			super(id, icon, icon, EmiRecipeSorting.compareOutputThenInput());
			this.key = key;
		}

		@Override
		public Component getName() {
			return Component.translatable(key);
		}
	}
}
