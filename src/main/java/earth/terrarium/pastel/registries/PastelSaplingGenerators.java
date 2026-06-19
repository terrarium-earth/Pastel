package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.PastelInkColorCollection;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

@SuppressWarnings(
    "unused"
)
public class PastelSaplingGenerators {
    public static final PastelInkColorCollection<ResourceLocation> COLORED_SAPLING_IDS =
            PastelInkColorCollection.NAMES.map(name -> PastelCommon.locate("colored_trees/" + name));

    public static final PastelInkColorCollection<TreeGrower> COLORED_SAPLING_GENERATORS =
            COLORED_SAPLING_IDS.map(PastelSaplingGenerators::registerRegular);



    public static final ResourceLocation WHITE_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.white();

    public static final TreeGrower WHITE_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.white();

    public static final ResourceLocation ORANGE_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.orange();

    public static final TreeGrower ORANGE_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.orange();

    public static final ResourceLocation MAGENTA_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.magenta();

    public static final TreeGrower MAGENTA_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.magenta();

    public static final ResourceLocation LIGHT_BLUE_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.lightBlue();

    public static final TreeGrower LIGHT_BLUE_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.lightBlue();

    public static final ResourceLocation YELLOW_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.yellow();

    public static final TreeGrower YELLOW_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.yellow();

    public static final ResourceLocation LIME_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.lime();

    public static final TreeGrower LIME_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.lime();

    public static final ResourceLocation PINK_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.pink();

    public static final TreeGrower PINK_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.pink();

    public static final ResourceLocation GRAY_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.gray();

    public static final TreeGrower GRAY_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.gray();

    public static final ResourceLocation LIGHT_GRAY_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.lightGray();

    public static final TreeGrower LIGHT_GRAY_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.lightGray();

    public static final ResourceLocation CYAN_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.cyan();

    public static final TreeGrower CYAN_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.cyan();

    public static final ResourceLocation PURPLE_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.purple();

    public static final TreeGrower PURPLE_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.purple();

    public static final ResourceLocation BLUE_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.blue();

    public static final TreeGrower BLUE_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.blue();

    public static final ResourceLocation BROWN_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.brown();

    public static final TreeGrower BROWN_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.brown();

    public static final ResourceLocation GREEN_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.green();

    public static final TreeGrower GREEN_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.green();

    public static final ResourceLocation RED_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.red();

    public static final TreeGrower RED_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.red();

    public static final ResourceLocation BLACK_COLORED_SAPLING_ID = COLORED_SAPLING_IDS.black();

    public static final TreeGrower BLACK_COLORED_SAPLING_GENERATOR = COLORED_SAPLING_GENERATORS.black();

    public static final ResourceLocation WEEPING_GALA_SAPLING_ID = PastelCommon.locate("weeping_gala");

    public static final TreeGrower WEEPING_GALA_SAPLING_GENERATOR = registerRegular(WEEPING_GALA_SAPLING_ID);

    public static TreeGrower registerRegular(ResourceLocation id) {
        return new TreeGrower(
            id.getPath(),
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, id)),
            Optional.empty()
        );
    }

}
