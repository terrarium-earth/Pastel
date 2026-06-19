package earth.terrarium.pastel.recipe.pedestal;

import earth.terrarium.pastel.api.collection.GroupedCollection;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public record PastelGemstoneColorCollection<T>(
        T cyan,
        T magenta,
        T yellow,
        T black,
        T white
) {

    public static final PastelGemstoneColorCollection<PastelGemstoneColor> VALUES =
            new PastelGemstoneColorCollection<>(
                    PastelGemstoneColor.CYAN,
                    PastelGemstoneColor.MAGENTA,
                    PastelGemstoneColor.YELLOW,
                    PastelGemstoneColor.BLACK,
                    PastelGemstoneColor.WHITE
            );

    public static final PastelGemstoneColorCollection<String> NAMES = VALUES.map(PastelGemstoneColor::getSerializedName);

    public static final PastelGemstoneColorCollection<String> GEMSTONE_NAMES =
            new PastelGemstoneColorCollection<>(
                    "topaz",
                    "amethyst",
                    "citrine",
                    "onyx",
                    "moonstone"
            );
    public static final PastelGemstoneColorCollection<InkColor> COLORS =
            VALUES.map(PastelGemstoneColor::getInkColor);

    public static final PastelGemstoneColorCollection<MapColor> MAP_COLORS =
            new PastelGemstoneColorCollection<>(
                    MapColor.COLOR_CYAN,
                    MapColor.COLOR_MAGENTA,
                    MapColor.COLOR_YELLOW,
                    MapColor.COLOR_BLACK,
                    MapColor.SNOW
            );



    public T pick(PastelGemstoneColor color) {
        return switch (color) {
            case CYAN -> this.cyan;
            case MAGENTA -> this.magenta;
            case YELLOW -> this.yellow;
            case BLACK -> this.black;
            case WHITE -> this.white;
        };
    }

    public <U> PastelGemstoneColorCollection<U> map(Function<T, U> mapper) {
        return new PastelGemstoneColorCollection<>(
                mapper.apply(this.cyan),
                mapper.apply(this.magenta),
                mapper.apply(this.yellow),
                mapper.apply(this.black),
                mapper.apply(this.white)
        );
    }

    public static <T, U, R> PastelGemstoneColorCollection<R> zipMap(
            PastelGemstoneColorCollection<T> first,
            PastelGemstoneColorCollection<U> second,
            BiFunction<T, U, R> operation
    ) {
        return new PastelGemstoneColorCollection<>(
                operation.apply(first.cyan(), second.cyan()),
                operation.apply(first.magenta(), second.magenta()),
                operation.apply(first.yellow(), second.yellow()),
                operation.apply(first.black(), second.black()),
                operation.apply(first.white(), second.white())
        );
    }

    public static <B extends Block, Id> PastelGemstoneColorCollection<DeferredBlock<Block>> registerBlocks(
            PastelGemstoneColorCollection<Id> ids,
            GroupedCollection.QuadFunction<PastelGemstoneColor, Id, Function<BlockBehaviour.Properties, Block>, Supplier<BlockBehaviour.Properties>, DeferredBlock<Block>> register,
            BiFunction<PastelGemstoneColor, BlockBehaviour.Properties, B> blockFactory,
            Function<PastelGemstoneColor, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return zipMap(
                VALUES,
                ids,
                (key, id) -> register.apply(key, id, p -> blockFactory.apply(key, p), () -> propertiesSupplier.apply(key))
        );
    }

    public static <Id> PastelGemstoneColorCollection<DeferredItem<Item>> registerItems(
            PastelGemstoneColorCollection<Id> ids,
            BiFunction<Id, PastelGemstoneColor, DeferredItem<Item>> itemFactory
    ) {
        return zipMap(
                ids,
                VALUES,
                itemFactory
        );
    }

    public static PastelGemstoneColorCollection<String> prefixWithColor(String baseId) {
        return NAMES.map(name -> name + "_" + baseId);
    }

    public static PastelGemstoneColorCollection<String> prefixWithGemstone(String baseId) {
        return GEMSTONE_NAMES.map(name -> name + "_" + baseId);
    }

    public T topaz() {
        return this.cyan();
    }

    public T amethyst() {
        return this.magenta();
    }

    public T citrine() {
        return this.yellow();
    }

    public T onyx() {
        return this.black();
    }

    public T moonstone() {
        return this.white();
    }
}
