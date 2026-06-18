package earth.terrarium.pastel.items.magic_items;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelStructureTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StructureCompassItem extends CompassItem {

    protected final TagKey<Structure> locatedStructures;

    public StructureCompassItem(Properties settings, TagKey<Structure> locatedStructures) {
        super(settings);
        this.locatedStructures = locatedStructures;
    }

    @Override
    public void inventoryTick(
        @NotNull ItemStack stack,
        @NotNull Level world,
        Entity entity,
        int slot,
        boolean selected
    ) {
        if (!world.isClientSide && world.getGameTime() % 200 == 0) {
            locateStructure(stack, world, entity);
        }
    }

    protected void locateStructure(@NotNull ItemStack stack, @NotNull Level world, Entity entity) {
        Pair<BlockPos, Holder<Structure>> foundStructure = locateStructure((ServerLevel) world, entity.blockPosition());
        if (foundStructure != null) {
            saveStructurePos(stack, world.dimension(), foundStructure.getFirst());
        } else {
            removeStructurePos(stack);
        }
    }

    public @Nullable Pair<BlockPos, Holder<Structure>> locateStructure(
        @NotNull ServerLevel world,
        @NotNull BlockPos pos
    ) {
        Optional<HolderSet.Named<Structure>> registryEntryList = PastelStructureTags
            .entriesOf(
                world,
                locatedStructures
            );
        return registryEntryList
            .map(
                registryEntries -> world
                    .getChunkSource()
                    .getGenerator()
                    .findNearestMapStructure(world, registryEntries, pos, 100, false)
            )
            .orElse(null);
    }

    public static @Nullable GlobalPos getStructurePos(ItemStack stack) {
        return stack.getOrDefault(PastelDataComponentTypes.TARGETED_STRUCTURE, null);
    }

    protected void saveStructurePos(ItemStack stack, @NotNull ResourceKey<Level> worldKey, @NotNull BlockPos pos) {
        stack.set(PastelDataComponentTypes.TARGETED_STRUCTURE, new GlobalPos(worldKey, pos));
    }

    protected void removeStructurePos(@NotNull ItemStack stack) {
        stack.remove(PastelDataComponentTypes.TARGETED_STRUCTURE);
    }

}
