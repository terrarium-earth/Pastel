package earth.terrarium.pastel.blocks.conditional;

import com.cmdpro.databank.hidden.types.BlockHiddenType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.mixin.accessors.ExperienceDroppingBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Map;

public class CloakedOreBlock extends DropExperienceBlock {

    public static final MapCodec<CloakedOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                                                                              IntProvider.codec(0, 10)
                                                                                                                         .fieldOf("experience")
                                                                                                                         .forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getXpRange()),
                                                                                                              propertiesCodec()
                                                                                                          )
                                                                                                          .apply(
                                                                                                              instance,
                                                                                                              CloakedOreBlock::new
                                                                                                          ));

    protected static boolean dropXP;

    public CloakedOreBlock(IntProvider experienceDropped, Properties settings) {
        super(experienceDropped, settings);
    }

    @Override
    public MapCodec<? extends CloakedOreBlock> codec() {
        return CODEC;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        // workaround: since onStacksDropped() has no way of checking if it was
        // triggered by a player we have to cache that information here
        Player lootPlayerEntity = null;
        if (builder.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player) {
            lootPlayerEntity = player;
        }
        dropXP = lootPlayerEntity != null && BlockHiddenType.isVisible(state, lootPlayerEntity);

        return super.getDrops(state, builder);
    }

    @Override
    public void spawnAfterBreak(
        BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, stack, dropExperience && dropXP);
    }

}
