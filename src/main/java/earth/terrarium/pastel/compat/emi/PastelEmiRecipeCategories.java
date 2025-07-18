package earth.terrarium.pastel.compat.emi;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class PastelEmiRecipeCategories {
    public static final EmiRecipeCategory PEDESTAL_CRAFTING = new PastelCategory(
        PastelCommon.locate("pedestal_crafting"), EmiStack.of(PastelBlocks.PEDESTAL_BASIC_AMETHYST.get()));
    public static final EmiRecipeCategory ANVIL_CRUSHING = new PastelCategory(
        PastelCommon.locate("anvil_crushing"), EmiStack.of(Blocks.ANVIL));
    public static final EmiRecipeCategory FUSION_SHRINE = new PastelCategory(
        PastelCommon.locate("fusion_shrine"), EmiStack.of(PastelBlocks.FUSION_SHRINE_CALCITE.get()),
        "block.pastel.fusion_shrine"
    );
    public static final EmiRecipeCategory NATURES_STAFF = new PastelCategory(
        PastelCommon.locate("natures_staff_conversions"), EmiStack.of(PastelItems.NATURES_STAFF.get()),
        PastelItems.NATURES_STAFF.get()
                                 .getDescriptionId()
    );
    public static final EmiRecipeCategory ENCHANTER = new PastelCategory(
        PastelCommon.locate("enchanter"), EmiStack.of(PastelBlocks.ENCHANTER.get()),
        "container.pastel.rei.enchanting.title"
    );
    public static final EmiRecipeCategory ENCHANTMENT_UPGRADE = new PastelCategory(
        PastelCommon.locate("enchantment_upgrade"), EmiStack.of(PastelBlocks.ENCHANTER.get()),
        "container.pastel.rei.enchantment_upgrading.title"
    );
    public static final EmiRecipeCategory POTION_WORKSHOP_BREWING = new PastelCategory(
        PastelCommon.locate("potion_workshop_brewing"), EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
    public static final EmiRecipeCategory POTION_WORKSHOP_CRAFTING = new PastelCategory(
        PastelCommon.locate("potion_workshop_crafting"), EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
    public static final EmiRecipeCategory POTION_WORKSHOP_REACTING = new PastelCategory(
        PastelCommon.locate("potion_workshop_reacting"), EmiStack.of(PastelBlocks.POTION_WORKSHOP.get()));
    public static final EmiRecipeCategory SPIRIT_INSTILLER = new PastelCategory(
        PastelCommon.locate("spirit_instiller"), EmiStack.of(PastelBlocks.SPIRIT_INSTILLER.get()),
        PastelBlocks.SPIRIT_INSTILLER.get()
                                     .getDescriptionId()
    );
    public static final EmiRecipeCategory HUMUS_CONVERTING = new PastelCategory(
        PastelCommon.locate("humus_converting"), EmiStack.of(PastelItems.HUMUS_BUCKET.get()));
    public static final EmiRecipeCategory LIQUID_CRYSTAL_CONVERTING = new PastelCategory(
        PastelCommon.locate("liquid_crystal_converting"), EmiStack.of(PastelItems.LIQUID_CRYSTAL_BUCKET.get()));
    public static final EmiRecipeCategory MIDNIGHT_SOLUTION_CONVERTING = new PastelCategory(
        PastelCommon.locate("midnight_solution_converting"), EmiStack.of(PastelItems.MIDNIGHT_SOLUTION_BUCKET.get()));
    public static final EmiRecipeCategory DRAGONROT_CONVERTING = new PastelCategory(
        PastelCommon.locate("dragonrot_converting"), EmiStack.of(PastelItems.DRAGONROT_BUCKET.get()),
        "container.pastel.rei.dragonrot_converting.title"
    );
    public static final EmiRecipeCategory HEATING = new PastelCategory(
        PastelCommon.locate("heating"), EmiStack.of(PastelBlocks.BLAZE_IDOL.get()));
    public static final EmiRecipeCategory FREEZING = new PastelCategory(
        PastelCommon.locate("freezing"), EmiStack.of(PastelBlocks.POLAR_BEAR_IDOL.get()));
    public static final EmiRecipeCategory INK_CONVERTING = new PastelCategory(
        PastelCommon.locate("ink_converting"), EmiStack.of(PastelBlocks.COLOR_PICKER.get()));
    public static final EmiRecipeCategory CRYSTALLARIEUM = new PastelCategory(
        PastelCommon.locate("crystallarieum"), EmiStack.of(PastelBlocks.CRYSTALLARIEUM.get()),
        "block.pastel.crystallarieum"
    );
    public static final EmiRecipeCategory CINDERHEARTH = new PastelCategory(
        PastelCommon.locate("cinderhearth"), EmiStack.of(PastelBlocks.CINDERHEARTH.get()),
        PastelBlocks.CINDERHEARTH.get()
                                 .getDescriptionId()
    );
    public static final EmiRecipeCategory TITRATION_BARREL = new PastelCategory(
        PastelCommon.locate("titration_barrel"), EmiStack.of(PastelBlocks.TITRATION_BARREL.get()),
        PastelBlocks.TITRATION_BARREL.get()
                                     .getDescriptionId()
    );
    public static final EmiRecipeCategory PRIMORDIAL_FIRE_BURNING = new PastelCategory(
        PastelCommon.locate("primordial_fire_burning"), EmiStack.of(PastelItems.DOOMBLOOM_SEED.get()),
        "container.pastel.rei.primordial_fire_burning.title"
    );

    private static class PastelCategory extends EmiRecipeCategory {
        private final String key;

        public PastelCategory(ResourceLocation id, EmiRenderable icon) {
            this(id, icon, "container." + id.getNamespace() + ".rei." + id.getPath() + ".title");
        }

        public PastelCategory(ResourceLocation id, EmiRenderable icon, String key) {
            super(id, icon, icon, EmiRecipeSorting.compareOutputThenInput());
            this.key = key;
        }

        @Override
        public Component getName() {
            return Component.translatable(key);
        }
    }
}
