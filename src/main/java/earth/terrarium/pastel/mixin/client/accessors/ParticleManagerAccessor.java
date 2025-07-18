package earth.terrarium.pastel.mixin.client.accessors;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ParticleEngine.class)
public interface ParticleManagerAccessor {

    @Accessor
    Map<ResourceLocation, SpriteSet> getSpriteSets();

    @Accessor
    TextureAtlas getTextureAtlas();
}
