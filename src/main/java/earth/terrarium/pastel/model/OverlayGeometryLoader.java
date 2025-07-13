package earth.terrarium.pastel.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.client.model.IModelBuilder;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.SimpleUnbakedGeometry;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class OverlayGeometryLoader implements IGeometryLoader<OverlayGeometryLoader.OverlayGeometry> {

    public static final OverlayGeometryLoader INSTANCE = new OverlayGeometryLoader();
    public static final ResourceLocation ID = PastelCommon.locate("overlay");

    private OverlayGeometryLoader() {}

    @Override
    public OverlayGeometry read(JsonObject json, JsonDeserializationContext context) throws JsonParseException {
        json.remove("loader");

        BlockModel base = context.deserialize(json, BlockModel.class);
        Map<BlockElementFace, String> overlayRefs = resolveOverlayReferences(json, base);

        return new OverlayGeometry(base, overlayRefs);
    }

    private static @NotNull Map<BlockElementFace, String> resolveOverlayReferences(JsonObject json, BlockModel base) {
        Map<BlockElementFace, String> overlayRefs = new HashMap<>();
        for (int i = 0; i < base.getElements().size(); i++) {
            var rawElements = GsonHelper.getAsJsonArray(json, "elements");

            var element = base.getElements().get(i);
            var rawElement = rawElements.get(i).getAsJsonObject();
            var rawFaces = rawElement.getAsJsonObject("faces");

            for (Direction dir : element.faces.keySet()) {
                var face = element.faces.get(dir);
                var raw = rawFaces.getAsJsonObject(dir.getName());

                if (!raw.has("overlay"))
                    throw new JsonParseException("Every overlay face must have an \"overlay\" member");

                overlayRefs.put(face, raw.get("overlay").getAsString());
            }
        }
        return overlayRefs;
    }

    public static class OverlayGeometry extends SimpleUnbakedGeometry<OverlayGeometry> {

        private final BlockModel base;
        private final Map<BlockElementFace, String> overlayRefs;

        private OverlayGeometry(BlockModel base, Map<BlockElementFace, String> overlayRefs) {
            this.base = base;
            this.overlayRefs = overlayRefs;
        }

        @Override
        protected void addQuads(IGeometryBakingContext context, IModelBuilder<?> builder, ModelBaker modelBaker, Function<Material, TextureAtlasSprite> sprites, ModelState modelState) {
            Transformation rootTransform = context.getRootTransform();
            if (!rootTransform.isIdentity()) {
                modelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform);
            }

            for (BlockElement element : base.getElements()) {
                for (Direction direction : element.faces.keySet()) {
                    var face = element.faces.get(direction);

                    var sprite = sprites.apply(context.getMaterial(face.texture()));
                    var overlay = sprites.apply(context.getMaterial(overlayRefs.get(face)));

                    var baseQuad = BlockModel.bakeFace(element, face, sprite, direction, modelState);
                    var finalQuad = new QuadWithOverlay(baseQuad, overlay);

                    if (face.cullForDirection() == null) {
                        builder.addUnculledFace(finalQuad);
                    } else {
                        builder.addCulledFace(modelState.getRotation().rotateTransform(face.cullForDirection()), finalQuad);
                    }
                }
            }
        }

        @Override
        public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
            base.resolveParents(modelGetter);
        }
    }
}
