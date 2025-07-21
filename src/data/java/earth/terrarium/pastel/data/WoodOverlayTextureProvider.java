package earth.terrarium.pastel.data;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.common.io.ByteStreams;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class WoodOverlayTextureProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final PackOutput.PathProvider pathProvider;
    private final String modId;
    private final ExistingFileHelper existingFileHelper;
    private final List<WoodOverlayTint> overlayTints = new ArrayList<>();

    public WoodOverlayTextureProvider(
        PackOutput output,
        String modId,
        ExistingFileHelper existingFileHelper
    ) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, ModelProvider.TEXTURE.getPrefix());
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        addOverlays();

        return CompletableFuture.allOf(overlayTints
            .stream()
            .map((tint) -> writeTexture(tint, output))
            .toArray(CompletableFuture[]::new)
        );
    }

    private NativeImage getTexture(ResourceLocation texture) {
        ExistingFileHelper.ResourceType resourceType = ModelProvider.TEXTURE;

        Resource textureResource;

        try {
            textureResource = existingFileHelper.getResource(texture, resourceType.getPackType(), resourceType.getSuffix(), resourceType.getPrefix());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Texture " + texture + " does not exist in any known resource pack", e);
        }

        try {
            return NativeImage.read(textureResource.open());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private float blend(Function<Color, Float> channel, Color base, Color overlay, Color tint) {
        return Math.min((channel.apply(base) * base.alpha() * (1 - overlay.alpha()) * channel.apply(tint) * tint.alpha()) + (channel.apply(overlay) * overlay.alpha()), 1.0f);
    }

    private NativeImage generateTexture(WoodOverlayTint overlayTint) {
        NativeImage base = getTexture(overlayTint.base());
        NativeImage overlay = getTexture(overlayTint.overlay());

        if (base.getWidth() != overlay.getWidth() || base.getHeight() != overlay.getHeight()) {
            throw new IllegalArgumentException("Textures " + base + " and " + overlay + " have differing sizes, " +
                base.getWidth() + "x" + base.getHeight() + " != " + overlay.getWidth() + "x" + overlay.getHeight()
            );
        }

        var output = new NativeImage(base.getWidth(), base.getHeight(), false);

        try {
            var tint = new Color(overlayTint.tint());

            for (int x = 0; x < base.getWidth(); x++) {
                for (int y = 0; y < base.getHeight(); y++) {
                    var baseColor = new Color(base.getPixelRGBA(x, y));
                    var overlayColor = new Color(overlay.getPixelRGBA(x, y));

                    float red = blend(Color::red, baseColor, overlayColor, tint);
                    float green = blend(Color::green, baseColor, overlayColor, tint);
                    float blue = blend(Color::blue, baseColor, overlayColor, tint);

                    int finalColor = FastColor.ABGR32.color(
                        (int) (baseColor.alpha() * 255),
                        (int) (red * 255),
                        (int) (green * 255),
                        (int) (blue * 255)
                    );

                    output.setPixelRGBA(x, y, finalColor);
                }
            }
        } catch (Exception e) {
            output.close();

            throw e;
        }

        return output;
    }

    @SuppressWarnings({"deprecation", "UnstableApiUsage"})
    private CompletableFuture<Void> writeTexture(WoodOverlayTint overlayTint, CachedOutput output) {
        return CompletableFuture.runAsync(() -> {
            ResourceLocation outputLocation = overlayTint.output();

            Path path = this.pathProvider.file(outputLocation, "png");

            try (NativeImage generated = generateTexture(overlayTint)) {
                Path temp = Files.createTempFile("generated", ".png");
                generated.writeToFile(temp);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                HashingOutputStream hasher = new HashingOutputStream(Hashing.sha1(), outputStream);

                ByteStreams.copy(Files.newInputStream(temp), hasher);

                output.writeIfNeeded(path, outputStream.toByteArray(), hasher.hash());
            } catch (IOException e) {
                LOGGER.error("Failed to save file to {}", path, e);
            }
        });
    }

    protected void addOverlay(ResourceLocation base, ResourceLocation overlay, ResourceLocation output, int tint) {
        overlayTints.add(new WoodOverlayTint(base, overlay, output, tint));
        existingFileHelper.trackGenerated(output, ModelProvider.TEXTURE);
    }

    protected void addOverlay(String type, ResourceLocation output, int tint) {
        String baseBlankName = "block/blank/blank_" + type;
        ResourceLocation base = ResourceLocation.fromNamespaceAndPath(PastelCommon.MOD_ID, baseBlankName);
        ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(PastelCommon.MOD_ID, baseBlankName + "_overlay");

        addOverlay(base, overlay, output, tint);
    }

    protected void addOverlay(String type, InkColor color) {
        ResourceLocation output = ResourceLocation.fromNamespaceAndPath(modId, "block/" + type + "/" + color.getLootName());

        addOverlay(type, output, color.getColorInt());
    }

    protected abstract void addOverlays();

    public record WoodOverlayTint(ResourceLocation base, ResourceLocation overlay, ResourceLocation output, int tint) {
    }

    private static class Color {
        private final float alpha;
        private final float red;
        private final float green;
        private final float blue;

        private Color(int packedColor) {
            this.alpha = FastColor.ABGR32.alpha(packedColor) / 255f;
            this.red = FastColor.ABGR32.red(packedColor) / 255f;
            this.green = FastColor.ABGR32.green(packedColor) / 255f;
            this.blue = FastColor.ABGR32.blue(packedColor) / 255f;
        }

        public float alpha() {
            return alpha;
        }

        public float red() {
            return red;
        }

        public float green() {
            return green;
        }

        public float blue() {
            return blue;
        }
    }
}
