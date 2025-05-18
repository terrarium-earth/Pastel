package de.dafuqs.spectrum.items.armor;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.Preenchanted;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import de.dafuqs.spectrum.render.armor.BedrockArmorModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BedrockArmorItem extends ArmorItem implements Preenchanted {
    @OnlyIn(Dist.CLIENT)
    private HumanoidModel<LivingEntity> model;

    public BedrockArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack_1, ItemStack itemStack_2) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    protected HumanoidModel<LivingEntity> provideArmorModelForSlot(EquipmentSlot slot) {
        var models = Minecraft.getInstance().getEntityModels();
        var root = models.bakeLayer(SpectrumModelLayers.MAIN_BEDROCK_LAYER);
        return new BedrockArmorModel(root, slot);
    }

    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<LivingEntity> getArmorModel() {
        if (model == null) {
            model = provideArmorModelForSlot(getEquipmentSlot());
        }
        return model;
    }

    // this takes the "unused" stack, so addons can mixin into it
    @SuppressWarnings("unused")
    public RenderType getRenderLayer(ItemStack stack) {
        return RenderType.entitySolid(SpectrumModelLayers.BEDROCK_ARMOR_MAIN_ID);
    }

    @NotNull
    @SuppressWarnings("unused")
    public ResourceLocation getArmorTexture(ItemStack stack, EquipmentSlot slot) {
        return SpectrumCommon.locate("textures/armor/bedrock_armor_main.png");
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of();
	}
}
