package earth.terrarium.pastel.registries;

import com.google.common.base.Suppliers;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static earth.terrarium.pastel.PastelCommon.locate;

public class PastelArmorMaterials {

    private static final DeferredRegister<ArmorMaterial> REGISTER = DeferredRegister
        .create(
            Registries.ARMOR_MATERIAL,
            PastelCommon.MOD_ID
        );

    public static Holder<ArmorMaterial> CRYSTAL;

    public static Holder<ArmorMaterial> BEDROCK;

    public static void register(IEventBus bus) {
        CRYSTAL = register(
            "crystal",
            Util
                .make(
                    new EnumMap<>(ArmorItem.Type.class),
                    map -> {
                        map.put(ArmorItem.Type.BOOTS, PastelCommon.CONFIG.CrystalArmorBootsProtection);
                        map.put(ArmorItem.Type.LEGGINGS, PastelCommon.CONFIG.CrystalArmorLeggingsProtection);
                        map.put(ArmorItem.Type.CHESTPLATE, PastelCommon.CONFIG.CrystalArmorChestplateProtection);
                        map.put(ArmorItem.Type.HELMET, PastelCommon.CONFIG.CrystalArmorHelmetProtection);
                    }
                ),
            15,
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.AMETHYST_BLOCK_CHIME),
            PastelCommon.CONFIG.CrystalArmorToughness,
            PastelCommon.CONFIG.CrystalArmorKnockbackResistance,
            () -> Ingredient.of(PastelItemTags.GEMSTONE_SHARDS)
        );

        BEDROCK = register(
            "bedrock",
            Util
                .make(
                    new EnumMap<>(ArmorItem.Type.class),
                    map -> {
                        map.put(ArmorItem.Type.BOOTS, PastelCommon.CONFIG.BedrockArmorBootsProtection);
                        map.put(ArmorItem.Type.LEGGINGS, PastelCommon.CONFIG.BedrockArmorLeggingsProtection);
                        map.put(ArmorItem.Type.CHESTPLATE, PastelCommon.CONFIG.BedrockArmorChestplateProtection);
                        map.put(ArmorItem.Type.HELMET, PastelCommon.CONFIG.BedrockArmorHelmetProtection);
                    }
                ),
            5,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            PastelCommon.CONFIG.BedrockArmorToughness,
            PastelCommon.CONFIG.BedrockArmorKnockbackResistance,
            () -> Ingredient.of(PastelItems.BEDROCK_DUST.get())
        );

        REGISTER.register(bus);
    }

    public static Holder<ArmorMaterial> register(
        String id,
        EnumMap<ArmorItem.Type, Integer> defense,
        int enchantability,
        Holder<SoundEvent> equipSound,
        float toughness,
        float knockbackResistance,
        Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(locate(id)));

        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

        for (
            ArmorItem.Type type : ArmorItem.Type.values()
        ) {
            enumMap.put(type, defense.get(type));
        }

        return REGISTER
            .register(
                id,
                () -> new ArmorMaterial(
                    enumMap,
                    enchantability,
                    equipSound,
                    Suppliers.memoize(repairIngredient::get),
                    layers,
                    toughness,
                    knockbackResistance
                )
            );
    }

}
