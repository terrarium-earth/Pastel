package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.render.armor.BedrockArmorModel;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;

public class PastelArmorRenderers {

    public static final DeferredItem<?>[] BEDROCK_ARMOR = {
        PastelItems.BEDROCK_HELMET,
        PastelItems.BEDROCK_CHESTPLATE,
        PastelItems.BEDROCK_LEGGINGS,
        PastelItems.BEDROCK_BOOTS
    };

    public static void register(RegisterClientExtensionsEvent event) {
        event.registerItem(
            new IClientItemExtensions() {
                private Map<EquipmentSlot, HumanoidModel<LivingEntity>> models = new Object2ObjectArrayMap<>();

                @Override
                public HumanoidModel<?> getHumanoidArmorModel(
                    LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot,
                    HumanoidModel<?> original
                ) {
                    return models.computeIfAbsent(equipmentSlot, this::provideArmorModelForSlot);
                }

                private HumanoidModel<LivingEntity> provideArmorModelForSlot(EquipmentSlot slot) {
                    var models = Minecraft.getInstance()
                                          .getEntityModels();
                    var root = models.bakeLayer(PastelModelLayers.MAIN_BEDROCK_LAYER);
                    return new BedrockArmorModel(root, slot);
                }

            }, BEDROCK_ARMOR
        );
    }

}
