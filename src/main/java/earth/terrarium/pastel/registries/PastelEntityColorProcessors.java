package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.interaction.EntityColorProcessorRegistry;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PastelEntityColorProcessors {

    public static void register() {
        // VANILLA
        EntityColorProcessorRegistry.register(
            () -> EntityType.SHEEP, (entity, dyeColor, player) -> {
                if (dyeColor.isEmpty()) {
                    return false;
                }
                DyeColor color = dyeColor.get();

                if (entity.getColor() == color) {
                    return false;
                }
                entity.setColor(color);
                return true;
            }
        );
        EntityColorProcessorRegistry.register(
            () -> EntityType.WOLF, (entity, dyeColor, player) -> {
                if (dyeColor.isEmpty()) {
                    return false;
                }
                if (!entity.isTame() || !entity.isOwnedBy(player)) {
                    return false;
                }
                DyeColor color = dyeColor.get();
                if (entity.getCollarColor() == color) {
                    return false;
                }

                entity.setCollarColor(color);
                return true;
            }
        );
        EntityColorProcessorRegistry.register(
            () -> EntityType.CAT, (entity, dyeColor, player) -> {
                if (dyeColor.isEmpty()) {
                    return false;
                }
                if (!entity.isTame() || !entity.isOwnedBy(player)) {
                    return false;
                }
                DyeColor color = dyeColor.get();
                if (entity.getCollarColor() == color) {
                    return false;
                }

                entity.setCollarColor(color);
                return true;
            }
        );
        EntityColorProcessorRegistry.register(
            () -> EntityType.SHULKER, (entity, dyeColor, player) -> {
                @Nullable DyeColor shulkerColor = entity.getColor();
                if (shulkerColor == null && dyeColor.isEmpty()) {
                    return false;
                }
                if (Optional.ofNullable(shulkerColor) == dyeColor) {
                    return false;
                }
                entity.setVariant(dyeColor);
                return true;
            }
        );

        // SPECTRUM
        EntityColorProcessorRegistry.register(
            PastelEntityTypes.EGG_LAYING_WOOLY_PIG, (entity, dyeColor, player) -> {
                if (dyeColor.isEmpty()) {
                    return false;
                }
                DyeColor color = dyeColor.get();
                if (entity.getColor() == color) {
                    return false;
                }
                entity.setColor(color);
                return true;
            }
        );
        EntityColorProcessorRegistry.register(
            PastelEntityTypes.INK_PROJECTILE, (entity, dyeColor, player) -> {
                if (dyeColor.isEmpty()) {
                    return false;
                }
                @Nullable InkColor inkColor = entity.getInkColor();
                if (inkColor == null || entity.getInkColor() == inkColor) {
                    return false;
                }
                entity.setColor(inkColor);
                return true;
            }
        );
    }

}
