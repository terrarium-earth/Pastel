package earth.terrarium.pastel.api.color;

import earth.terrarium.pastel.api.collection.GroupedCollection;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.io.function.IOQuadFunction;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InkColorCollection<T> extends GroupedCollection<InkColor, T> {

    public static final InkColorCollection<InkColor> BUILTIN_COLORS =
            new InkColorCollection<>(InkColors.BUILTIN_COLORS.stream().collect(Collectors.toMap(Function.identity(), Function.identity())));

    // TODO: better way to get ink color id (rewrite ink color register to be deferred holders?)
    public static final InkColorCollection<String> BUILTIN_NAMES =
            new InkColorCollection<>(InkColors.BUILTIN_COLORS.stream().map(color -> Pair.of(color, color.getDyeColor().get().getSerializedName())).collect(Collectors.toMap(Pair::getLeft, Pair::getRight)));

    public static final InkColorCollection<Item> BUILTIN_DYES =
            InkColorCollection.createBuiltin(
                   Items.CYAN_DYE,
                    Items.LIGHT_BLUE_DYE,
                    Items.BLUE_DYE,
                    Items.PURPLE_DYE,
                    Items.MAGENTA_DYE,
                    Items.PINK_DYE,
                    Items.RED_DYE,
                    Items.ORANGE_DYE,
                    Items.YELLOW_DYE,
                    Items.LIME_DYE,
                    Items.GREEN_DYE,
                    Items.BROWN_DYE,
                    Items.BLACK_DYE,
                    Items.GRAY_DYE,
                    Items.LIGHT_GRAY_DYE,
                    Items.WHITE_DYE
            );

    public InkColorCollection(Map<InkColor, T> values) {
        super(values);
    }

    public static <T> InkColorCollection<T> createBuiltin(
            T cyan,
            T lightBlue,
            T blue,
            T purple,
            T magenta,
            T pink,
            T red,
            T orange,
            T yellow,
            T lime,
            T green,
            T brown,
            T black,
            T gray,
            T lightGray,
            T white
    ) {
        return new InkColorCollection<>(
                Map.ofEntries(
                    Map.entry(InkColors.CYAN, cyan),
                    Map.entry(InkColors.LIGHT_BLUE, lightBlue),
                    Map.entry(InkColors.BLUE, blue),
                    Map.entry(InkColors.PURPLE, purple),
                    Map.entry(InkColors.MAGENTA, magenta),
                    Map.entry(InkColors.PINK, pink),
                    Map.entry(InkColors.RED, red),
                    Map.entry(InkColors.ORANGE, orange),
                    Map.entry(InkColors.YELLOW, yellow),
                    Map.entry(InkColors.LIME, lime),
                    Map.entry(InkColors.GREEN, green),
                    Map.entry(InkColors.BROWN, brown),
                    Map.entry(InkColors.BLACK, black),
                    Map.entry(InkColors.GRAY, gray),
                    Map.entry(InkColors.LIGHT_GRAY, lightGray),
                    Map.entry(InkColors.WHITE, white)
                )
        );
    }

    public <U> InkColorCollection<U> map(Function<T, U> mapper) {
        var newMap = GroupedCollection.map(this.values, mapper);

        return new InkColorCollection<>(newMap);
    }


    public static <B extends Block, Id> InkColorCollection<DeferredBlock<Block>> registerBlocks(
            InkColorCollection<Id> ids,
            GroupedCollection.QuadFunction<InkColor, Id, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties, DeferredBlock<Block>> register,
            BiFunction<InkColor, BlockBehaviour.Properties, B> colorBlockFactory,
            Function<InkColor, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return new InkColorCollection<>(GroupedCollection.registerBlocks(BUILTIN_COLORS.values, ids.values, register, colorBlockFactory, propertiesSupplier));
    }

    public static <Id> InkColorCollection<DeferredItem<Item>> registerBlockItems(
            InkColorCollection<Id> ids,
            InkColorCollection<DeferredBlock<Block>> blocks,
            TriFunction<Id, DeferredBlock<Block>, InkColor, DeferredItem<Item>> itemFactory) {
        return new InkColorCollection<>(GroupedCollection.registerBlockItems(BUILTIN_COLORS.values, ids.values, blocks.values, itemFactory));
    }

    public static <Id> InkColorCollection<DeferredItem<Item>> registerItems(InkColorCollection<Id> ids, BiFunction<Id, InkColor, DeferredItem<Item>> itemFactory) {
        return new InkColorCollection<>(GroupedCollection.registerItems(BUILTIN_COLORS.values, ids.values, itemFactory));
    }

    public static InkColorCollection<String> prefixWithColor(String baseId) {
        return BUILTIN_NAMES.map(color -> color + "_" + baseId);
    }

    public static <T, U, R> InkColorCollection<R> zipMap(InkColorCollection<T> first, InkColorCollection<U> second, BiFunction<T, U, R> operation) {
        var newMap = GroupedCollection.zipMap(first.values, second.values, operation);

        return new InkColorCollection<>(newMap);
    }
}
