package de.dafuqs.spectrum.api.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class DynamicRenderModel extends ForwardingBakedModel implements UnbakedModel {
    private static class WrappingOverridesList extends ItemOverrides {
        private final ItemOverrides wrapped;
        private WrappingOverridesList(ItemOverrides orig) {
            super(null, null, List.of());
            this.wrapped = orig;
        }

        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
            BakedModel newModel = wrapped.resolve(model, stack, world, entity, seed);
            return newModel == model ? model : new DynamicRenderModel(newModel);
        }
    }
    // only used pre-bake
    private UnbakedModel baseUnbaked;

    // could be used again if pre-bake model problems get figured out
    public DynamicRenderModel(UnbakedModel base) {
        this.baseUnbaked = base;
    }

    // post-bake post-override constructor
    public DynamicRenderModel(BakedModel base) {
        this.wrapped = base instanceof DynamicRenderModel fm ? fm.getWrappedModel() : base;
    }

    // avoid FAPI builtin model lookup
    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    private DynamicRenderModel wrap(BakedModel model) {
        this.wrapped = model instanceof DynamicRenderModel fm ? fm.getWrappedModel() : model;
        return this;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return this.baseUnbaked.getDependencies();
    }

    // override so wrap persists over override
    // ensures that renderer is called
    @Override
    public ItemOverrides getOverrides() {
        return new WrappingOverridesList(super.getOverrides());
    }

    // return empty transform to prevent double apply in render
    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelLoader) {
        this.baseUnbaked.resolveParents(modelLoader);
    }
    
    @Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        return this.wrap(this.baseUnbaked.bake(baker, textureGetter, rotationContainer));
    }
    
}
