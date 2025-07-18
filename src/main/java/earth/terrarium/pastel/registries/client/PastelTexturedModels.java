package earth.terrarium.pastel.registries.client;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.function.UnaryOperator;

import static earth.terrarium.pastel.registries.client.PastelTextureKeys.BASE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.CASE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.CORE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.ENDS;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.FILAMENT;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.GEMSTONE;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.GLASS;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.INNER;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.KEY0;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.KEY1;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.SHELL;
import static earth.terrarium.pastel.registries.client.PastelTextureKeys.SHRINE;
import static net.minecraft.data.models.model.TextureMapping.getBlockTexture;
import static net.minecraft.data.models.model.TextureSlot.ALL;
import static net.minecraft.data.models.model.TextureSlot.BACK;
import static net.minecraft.data.models.model.TextureSlot.BOTTOM;
import static net.minecraft.data.models.model.TextureSlot.DIRT;
import static net.minecraft.data.models.model.TextureSlot.FRONT;
import static net.minecraft.data.models.model.TextureSlot.PARTICLE;
import static net.minecraft.data.models.model.TextureSlot.SIDE;
import static net.minecraft.data.models.model.TextureSlot.TOP;

public class PastelTexturedModels {

    public static final TexturedModel.Provider BASE_TRANS_LIGHT_CORE = TexturedModel.createDefault(
        b -> new TextureMapping().put(CASE, getBlockTexture(b))
                                 .put(BASE, getBlockTexture(b, "_base"))
                                 .put(GLASS, getBlockTexture(b, "_glass"))
                                 .put(SHELL, getBlockTexture(b, "_shell"))
                                 .put(FILAMENT, getBlockTexture(b, "_filament"))
                                 .put(ENDS, getBlockTexture(b, "_ends")), PastelModels.BASE_TRANS_LIGHT_CORE
    );
    public static final TexturedModel.Provider BOWL = TexturedModel.createDefault(
        b -> new TextureMapping().put(
                                     SIDE, getBlockTexture(
                                         b, "_side")
                                 )
                                 .put(
                                     TOP, getBlockTexture(
                                         b, "_top")
                                 )
                                 .put(
                                     BOTTOM, getBlockTexture(
                                         b, "_bottom")
                                 )
                                 .put(
                                     INNER, getBlockTexture(
                                         b, "_inner")
                                 ), PastelModels.BOWL
    );
    public static final TexturedModel.Provider CHIME = TexturedModel.createDefault(
        b -> new TextureMapping().put(BASE, PastelTextures.BALCITE_CHIME_BASE)
                                 .put(GEMSTONE, getBlockTexture(b)), PastelModels.CHIME
    );
    public static final TexturedModel.Provider CUBE_COLUMN_MIRRORED = TexturedModel.createDefault(
        TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN_MIRRORED);
    public static final TexturedModel.Provider CUSHION = TexturedModel.createDefault(
        b -> PastelTextureMaps.sideTopBottom(b, "_side", b, "_top", b, "_bottom"), PastelModels.CUSHION);
    public static final TexturedModel.Provider FUSION_SHRINE = TexturedModel.createDefault(
        b -> new TextureMapping().put(SHRINE, getBlockTexture(b))
                                 .put(PARTICLE, getBlockTexture(b, "_breaking")), PastelModels.FUSION_SHRINE
    );
    public static final TexturedModel.Provider ROUNDEL = TexturedModel.createDefault(
        b -> new TextureMapping().put(ALL, getBlockTexture(b)), PastelModels.ROUNDEL);
    public static final TexturedModel.Provider SHOOTING_STAR = TexturedModel.createDefault(
        b -> new TextureMapping().put(CORE, getBlockTexture(b))
                                 .put(SIDE, getBlockTexture(b, "_side")), PastelModels.SHOOTING_STAR
    );

    public static TexturedModel.Provider baseTransLantern(boolean diagonal, boolean tall) {
        return TexturedModel.createDefault(
            b -> new TextureMapping().put(GLASS, getBlockTexture(b, "_glass_on"))
                                     .put(CASE, getBlockTexture(b, "_case_on")),
            PastelModels.baseTransLantern(diagonal, tall)
        );
    }

    public static TexturedModel.Provider carpet(UnaryOperator<Block> woolBlock, String woolSuffix) {
        return TexturedModel.createDefault(
            b -> TextureMapping.wool(ModelLocationUtils.getModelLocation(woolBlock.apply(b), woolSuffix)),
            ModelTemplates.CARPET
        );
    }

    public static TexturedModel.Provider complexOrientable(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix,
        UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> frontBlock, String frontSuffix,
        UnaryOperator<Block> backBlock, String backSuffix, UnaryOperator<Block> particleBlock, String particleSuffix
    ) {
        return TexturedModel.createDefault(
            b -> new TextureMapping().put(
                                         SIDE, getBlockTexture(sideBlock.apply(b), sideSuffix))
                                     .put(
                                         TOP, getBlockTexture(topBlock.apply(b), topSuffix))
                                     .put(
                                         BOTTOM, getBlockTexture(bottomBlock.apply(b), bottomSuffix))
                                     .put(
                                         FRONT, getBlockTexture(frontBlock.apply(b), frontSuffix))
                                     .put(
                                         BACK, getBlockTexture(backBlock.apply(b), backSuffix))
                                     .put(
                                         PARTICLE, getBlockTexture(particleBlock.apply(b), particleSuffix)),
            PastelModels.COMPLEX_ORIENTABLE
        );
    }

    public static TexturedModel.Provider cross(UnaryOperator<Block> crossBlock, String crossSuffix) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.cross(crossBlock.apply(b), crossSuffix), ModelTemplates.CROSS);
    }

    public static TexturedModel.Provider cubeAll(UnaryOperator<Block> allBlock, String allSuffix) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.all(allBlock.apply(b), allSuffix), ModelTemplates.CUBE_ALL);
    }

    public static TexturedModel.Provider cubeBottomTop(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix,
        UnaryOperator<Block> bottomBlock, String bottomSuffix
    ) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.sideTopBottom(
                sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix,
                bottomBlock.apply(b), bottomSuffix
            ), ModelTemplates.CUBE_BOTTOM_TOP
        );
    }

    public static TexturedModel.Provider cubeBottomTopParticle(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix,
        UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> particleBlock, String particleSuffix
    ) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.sideTopBottomParticle(
                sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix,
                bottomBlock.apply(b), bottomSuffix, particleBlock.apply(b),
                particleSuffix
            ), PastelModels.CUBE_BOTTOM_TOP_PARTICLE
        );
    }

    public static TexturedModel.Provider cubeBottomTopWall(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix,
        UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> wallBlock, String wallSuffix
    ) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.sideTopBottomWall(
                sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix,
                bottomBlock.apply(b), bottomSuffix, wallBlock.apply(b), wallSuffix
            ), PastelModels.CUBE_BOTTOM_TOP_WALL
        );
    }

    public static TexturedModel.Provider cubeColumn(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> endBlock, String endSuffix) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.sideEnd(sideBlock.apply(b), sideSuffix, endBlock.apply(b), endSuffix),
            ModelTemplates.CUBE_COLUMN
        );
    }

    public static TexturedModel.Provider doubleCross(UnaryOperator<Block> crossBlock, String crossSuffix) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.cross(crossBlock.apply(b), crossSuffix), PastelModels.DOUBLE_CROSS);
    }

    public static TexturedModel.Provider farmland(
        UnaryOperator<Block> dirtBlock, String dirtSuffix, UnaryOperator<Block> topBlock, String topSuffix) {
        return TexturedModel.createDefault(
            b -> new TextureMapping().put(DIRT, getBlockTexture(dirtBlock.apply(b), dirtSuffix))
                                     .put(TOP, getBlockTexture(topBlock.apply(b), topSuffix)), ModelTemplates.FARMLAND
        );
    }

    public static TexturedModel.Provider flowerPotCross(
        UnaryOperator<Block> plantBlock, String plantSuffix, boolean tinted) {
        BlockModelGenerators.TintState tintType = tinted ? BlockModelGenerators.TintState.TINTED
                                                         : BlockModelGenerators.TintState.NOT_TINTED;
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.plant(plantBlock.apply(b), plantSuffix), tintType.getCrossPot());
    }

    public static TexturedModel.Provider leaves(UnaryOperator<Block> allBlock, String allSuffix) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.all(allBlock.apply(b), allSuffix), ModelTemplates.LEAVES);
    }

    public static TexturedModel.Provider overgrown(
        UnaryOperator<Block> sideBlock, String sideSuffix, UnaryOperator<Block> topBlock, String topSuffix,
        UnaryOperator<Block> bottomBlock, String bottomSuffix, UnaryOperator<Block> frondsBlock, String frondsSuffix
    ) {
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.sideTopBottomFronds(
                sideBlock.apply(b), sideSuffix, topBlock.apply(b), topSuffix,
                bottomBlock.apply(b), bottomSuffix, frondsBlock.apply(b),
                frondsSuffix
            ), PastelModels.OVERGROWN
        );
    }

    public static TexturedModel.Provider parented(ResourceLocation parentModelId) {
        return TexturedModel.createDefault(
            b -> new TextureMapping(), new ModelTemplate(Optional.of(parentModelId), Optional.empty()));
    }

    public static TexturedModel.Provider particle(UnaryOperator<Block> particleBlock, String particleSuffix) {
        return TexturedModel.createDefault(
            b -> TextureMapping.particle(getBlockTexture(particleBlock.apply(b), particleSuffix)),
            ModelTemplates.PARTICLE_ONLY
        );
    }

    public static TexturedModel.Provider particle(ResourceLocation particle) {
        return TexturedModel.createDefault(b -> TextureMapping.particle(particle), ModelTemplates.PARTICLE_ONLY);
    }

    public static TexturedModel.Provider sugarStick(int age, UnaryOperator<Block> sugarBlock) {
        return TexturedModel.createDefault(
            b -> new TextureMapping().put(KEY0, getBlockTexture(sugarBlock.apply(b)))
                                     .put(KEY1, getBlockTexture(Blocks.OAK_PLANKS))
                                     .put(PARTICLE, getBlockTexture(sugarBlock.apply(b))), PastelModels.sugarStick(age)
        );
    }

    public static TexturedModel.Provider tintableCross(
        UnaryOperator<Block> crossBlock, String crossSuffix, boolean tinted) {
        BlockModelGenerators.TintState tintType = tinted ? BlockModelGenerators.TintState.TINTED
                                                         : BlockModelGenerators.TintState.NOT_TINTED;
        return TexturedModel.createDefault(
            b -> PastelTextureMaps.cross(crossBlock.apply(b), crossSuffix), tintType.getCross());
    }

}
