package de.dafuqs.spectrum.items.armor;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.Preenchanted;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import de.dafuqs.spectrum.render.armor.BedrockArmorModel;
import net.minecraft.world.entity.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.*;

import java.util.Map;

public class BedrockArmorItem extends ArmorItem implements Preenchanted {

    public BedrockArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return SpectrumCommon.locate("textures/armor/bedrock_armor_main.png");
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack_1, ItemStack itemStack_2) {
        return false;
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
