package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class ColoredSaplingBlock extends SaplingBlock implements RevelationAware, ColoredTree {

	/*public static final MapCodec<ColoredSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredSaplingBlock::getColor)
	).apply(instance, ColoredSaplingBlock::new));*/

    private static final Map<InkColor, ColoredSaplingBlock> SAPLINGS = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public ColoredSaplingBlock(Properties settings, InkColor color, TreeGrower saplingGenerator) {
        super(saplingGenerator, settings);
        this.color = color;
        SAPLINGS.put(color, this);
        RevelationAware.register(this);
    }

    @Override
    public MapCodec<? extends ColoredSaplingBlock> codec() {
        throw new NotImplementedException();
    }

    @Override
    public ResourceLocation getCloakAdvancementIdentifier() {
        return ColoredTree.getTreeCloakAdvancementIdentifier(ColoredTree.TreePart.SAPLING, this.color);
    }

    @Override
    public Map<BlockState, BlockState> getBlockStateCloaks() {
        // Colored Logs => Oak logs
        Map<BlockState, BlockState> map = new Hashtable<>();
        for (int stage = 0; stage < 2; stage++) {
            map.put(
                this.defaultBlockState()
                    .setValue(SaplingBlock.STAGE, stage), Blocks.OAK_SAPLING.defaultBlockState()
                                                                            .setValue(SaplingBlock.STAGE, stage)
            );
        }
        return map;
    }

    @Override
    public Tuple<Item, Item> getItemCloak() {
        return new Tuple<>(this.asItem(), Blocks.OAK_SAPLING.asItem());
    }

    @Override
    public InkColor getColor() {
        return this.color;
    }

    public static ColoredSaplingBlock byColor(InkColor color) {
        return SAPLINGS.get(color);
    }

    public static Collection<ColoredSaplingBlock> all() {
        return SAPLINGS.values();
    }

}
