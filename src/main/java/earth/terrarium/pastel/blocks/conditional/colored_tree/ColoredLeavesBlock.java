package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class ColoredLeavesBlock extends LeavesBlock implements ColoredTree {

    public static final MapCodec<ColoredLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                                                                                 propertiesCodec(),
                                                                                                                 InkColor.CODEC.fieldOf("color")
                                                                                                                               .forGetter(ColoredLeavesBlock::getColor)
                                                                                                             )
                                                                                                             .apply(
                                                                                                                 instance,
                                                                                                                 ColoredLeavesBlock::new
                                                                                                             ));

    private static final Map<InkColor, ColoredLeavesBlock> LEAVES = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public ColoredLeavesBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        LEAVES.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredLeavesBlock> codec() {
        return CODEC;
    }

    @Override
    public InkColor getColor() {
        return this.color;
    }

    public static ColoredLeavesBlock byColor(InkColor color) {
        return LEAVES.get(color);
    }

    public static Collection<ColoredLeavesBlock> all() {
        return LEAVES.values();
    }

}
