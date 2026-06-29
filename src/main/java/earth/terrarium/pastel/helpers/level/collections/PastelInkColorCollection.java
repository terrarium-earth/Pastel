package earth.terrarium.pastel.helpers.level.collections;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public record PastelInkColorCollection<T>(
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
) implements App<PastelInkColorCollection.Mu, T> {
    public static final class Mu implements K1 {
    }

    public static final PastelInkColorCollection<InkColor> VALUES = new PastelInkColorCollection<>(
        InkColors.CYAN,
        InkColors.LIGHT_BLUE,
        InkColors.BLUE,
        InkColors.PURPLE,
        InkColors.MAGENTA,
        InkColors.PINK,
        InkColors.RED,
        InkColors.ORANGE,
        InkColors.YELLOW,
        InkColors.LIME,
        InkColors.GREEN,
        InkColors.BROWN,
        InkColors.BLACK,
        InkColors.GRAY,
        InkColors.LIGHT_GRAY,
        InkColors.WHITE
    );

    public static final PastelInkColorCollection<String> NAMES = VALUES
        .map(it -> it.getDyeColor().get().getSerializedName());

    public static final PastelInkColorCollection<Item> DYE_ITEMS = new PastelInkColorCollection<>(
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

    public static final PastelInkColorCollection<DyeColor> DYE_COLORS =
        // should be safe because all builtins map to a dye
        VALUES.map(it -> it.getDyeColor().orElseThrow());

    public static <T> PastelInkColorCollection<T> unbox(final App<Mu, T> box) {
        return (PastelInkColorCollection<T>) box;
    }

    public T pick(InkColor color) {
        if (color == InkColors.CYAN) {
            return cyan;
        } else if (color == InkColors.LIGHT_BLUE) {
            return lightBlue;
        } else if (color == InkColors.BLUE) {
            return blue;
        } else if (color == InkColors.PURPLE) {
            return purple;
        } else if (color == InkColors.MAGENTA) {
            return magenta;
        } else if (color == InkColors.PINK) {
            return pink;
        } else if (color == InkColors.RED) {
            return red;
        } else if (color == InkColors.ORANGE) {
            return orange;
        } else if (color == InkColors.YELLOW) {
            return yellow;
        } else if (color == InkColors.LIME) {
            return lime;
        } else if (color == InkColors.GREEN) {
            return green;
        } else if (color == InkColors.BROWN) {
            return brown;
        } else if (color == InkColors.BLACK) {
            return black;
        } else if (color == InkColors.GRAY) {
            return gray;
        } else if (color == InkColors.LIGHT_GRAY) {
            return lightGray;
        } else if (color == InkColors.WHITE) {
            return white;
        }
        throw new IllegalArgumentException("Pastel Ink collections only support built-in colors");
    }

    public void forEach(Consumer<T> action) {
        action.accept(this.cyan());
        action.accept(this.lightBlue());
        action.accept(this.blue());
        action.accept(this.purple());
        action.accept(this.magenta());
        action.accept(this.pink());
        action.accept(this.red());
        action.accept(this.orange());
        action.accept(this.yellow());
        action.accept(this.lime());
        action.accept(this.green());
        action.accept(this.brown());
        action.accept(this.black());
        action.accept(this.gray());
        action.accept(this.lightGray());
        action.accept(this.white());
    }

    public <U> PastelInkColorCollection<U> map(Function<? super T, ? extends U> mapper) {
        return new PastelInkColorCollection<>(
            mapper.apply(this.cyan()),
            mapper.apply(this.lightBlue()),
            mapper.apply(this.blue()),
            mapper.apply(this.purple()),
            mapper.apply(this.magenta()),
            mapper.apply(this.pink()),
            mapper.apply(this.red()),
            mapper.apply(this.orange()),
            mapper.apply(this.yellow()),
            mapper.apply(this.lime()),
            mapper.apply(this.green()),
            mapper.apply(this.brown()),
            mapper.apply(this.black()),
            mapper.apply(this.gray()),
            mapper.apply(this.lightGray()),
            mapper.apply(this.white())
        );
    }

    public static <T, U, R> PastelInkColorCollection<R> zipMap(
        PastelInkColorCollection<T> first,
        PastelInkColorCollection<U> second,
        BiFunction<T, U, R> operation
    ) {
        return new PastelInkColorCollection<>(
            operation.apply(first.cyan(), second.cyan()),
            operation.apply(first.lightBlue(), second.lightBlue()),
            operation.apply(first.blue(), second.blue()),
            operation.apply(first.purple(), second.purple()),
            operation.apply(first.magenta(), second.magenta()),
            operation.apply(first.pink(), second.pink()),
            operation.apply(first.red(), second.red()),
            operation.apply(first.orange(), second.orange()),
            operation.apply(first.yellow(), second.yellow()),
            operation.apply(first.lime(), second.lime()),
            operation.apply(first.green(), second.green()),
            operation.apply(first.brown(), second.brown()),
            operation.apply(first.black(), second.black()),
            operation.apply(first.gray(), second.gray()),
            operation.apply(first.lightGray(), second.lightGray()),
            operation.apply(first.white(), second.white())
        );
    }

    public static <T, U> void zipApply(
        PastelInkColorCollection<T> first,
        PastelInkColorCollection<U> second,
        BiConsumer<T, U> operation
    ) {
        operation.accept(first.cyan(), second.cyan());
        operation.accept(first.lightBlue(), second.lightBlue());
        operation.accept(first.blue(), second.blue());
        operation.accept(first.purple(), second.purple());
        operation.accept(first.magenta(), second.magenta());
        operation.accept(first.pink(), second.pink());
        operation.accept(first.red(), second.red());
        operation.accept(first.orange(), second.orange());
        operation.accept(first.yellow(), second.yellow());
        operation.accept(first.lime(), second.lime());
        operation.accept(first.green(), second.green());
        operation.accept(first.brown(), second.brown());
        operation.accept(first.black(), second.black());
        operation.accept(first.gray(), second.gray());
        operation.accept(first.lightGray(), second.lightGray());
        operation.accept(first.white(), second.white());
    }

    public static PastelInkColorCollection<String> prefixWithColor(String baseId) {
        return NAMES.map(color -> color + "_" + baseId);
    }

    public static <B extends Block, Id> PastelInkColorCollection<DeferredBlock<Block>> registerBlocks(
        PastelInkColorCollection<Id> ids,
        GroupedCollection.BlockFactory<InkColor, Id> register,
        BiFunction<InkColor, BlockBehaviour.Properties, B> colorBlockFactory,
        Function<InkColor, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return zipMap(
            VALUES,
            ids,
            (key, id) -> register
                .apply(key, id, p -> colorBlockFactory.apply(key, p), () -> propertiesSupplier.apply(key))
        );
    }

    public static <Id> PastelInkColorCollection<DeferredItem<Item>> registerItems(
        PastelInkColorCollection<Id> ids,
        BiFunction<Id, InkColor, DeferredItem<Item>> itemFactory
    ) {
        return zipMap(
            ids,
            VALUES,
            itemFactory
        );
    }

    // This may seem insane but its actually quite reasonable
    public enum Instance implements Applicative<Mu, Instance.Mu> {
        INSTANCE;

        @Override
        public <A> App<PastelInkColorCollection.Mu, A> point(A a) {
            return new PastelInkColorCollection<>(
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a,
                a
            );
        }

        @Override
        public <A, R> Function<App<PastelInkColorCollection.Mu, A>, App<PastelInkColorCollection.Mu, R>> lift1(
            App<PastelInkColorCollection.Mu, Function<A, R>> function
        ) {
            return in -> {
                var unboxed = unbox(in);
                return zipMap(unbox(function), unboxed, Function::apply);
            };
        }

        public static final class Mu implements Applicative.Mu {
        }

        @Override
        public <T, R> App<PastelInkColorCollection.Mu, R> map(
            final Function<? super T, ? extends R> func,
            final App<PastelInkColorCollection.Mu, T> ts
        ) {
            return unbox(ts).map(func);
        }
    }

}
