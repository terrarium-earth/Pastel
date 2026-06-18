package earth.terrarium.pastel.entity.variants;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;

public enum LizardHornVariant implements StringRepresentable {

    HORNY("horny", "textures/entity/lizard/horns_horny.png"),
    STRAIGHT("straight", "textures/entity/lizard/horns_straight.png"),
    FLEXIBLE("flexible", "textures/entity/lizard/horns_flexible.png"),
    QUEER("queer", "textures/entity/lizard/horns_queer.png"),
    POLY("poly", "textures/entity/lizard/horns_poly.png"),
    ONLY_LIKES_YOU_AS_A_FRIEND("friendzoned", "textures/entity/lizard/horns_friendzoned.png");

    public static Codec<LizardHornVariant> CODEC = StringRepresentable.fromEnum(LizardHornVariant::values);

    private final String name;

    private final ResourceLocation id;

    private final ResourceLocation texture;

    LizardHornVariant(String name, String texture) {
        this.name = name;
        this.id = PastelCommon.locate(name);
        this.texture = PastelCommon.locate(texture);
        Registry.register(PastelRegistries.LIZARD_HORN_VARIANT, id, this);
    }

    public TagKey<LizardHornVariant> getReference() {
        return TagKey.create(PastelRegistries.LIZARD_HORN_VARIANT.key(), id);
    }

    public ResourceLocation getTextureLocation() {
        return texture;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

}
