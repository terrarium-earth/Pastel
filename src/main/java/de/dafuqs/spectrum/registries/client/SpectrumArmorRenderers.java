package de.dafuqs.spectrum.registries.client;

import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.render.armor.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.extensions.common.*;

public class SpectrumArmorRenderers {

	public static final Item[] BEDROCK_ARMOR = {
			SpectrumItems.BEDROCK_HELMET,
			SpectrumItems.BEDROCK_CHESTPLATE,
			SpectrumItems.BEDROCK_LEGGINGS,
			SpectrumItems.BEDROCK_BOOTS
	};

	public static void register(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			private HumanoidModel<LivingEntity> model;

			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if (model == null) {
					model = provideArmorModelForSlot(equipmentSlot);
				}

				return model;
			}

			private HumanoidModel<LivingEntity> provideArmorModelForSlot(EquipmentSlot slot) {
				var models = Minecraft.getInstance().getEntityModels();
				var root = models.bakeLayer(SpectrumModelLayers.MAIN_BEDROCK_LAYER);
				return new BedrockArmorModel(root, slot);
			}

		}, BEDROCK_ARMOR);
	}
	
}
