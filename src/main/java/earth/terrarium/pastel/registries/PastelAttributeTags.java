package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class PastelAttributeTags {

    public static final TagKey<Attribute> INEXORABLE_ARMOR_EFFECTIVE = TagKey.create(BuiltInRegistries.ATTRIBUTE.key(),
                                                                                     PastelCommon.locate(
                                                                                         "inexorable_armor_effective")
    );
    public static final TagKey<Attribute> INEXORABLE_HANDHELD_EFFECTIVE = TagKey.create(
        BuiltInRegistries.ATTRIBUTE.key(), PastelCommon.locate("inexorable_handheld_effective"));

}
