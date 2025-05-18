package de.dafuqs.spectrum.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static de.dafuqs.spectrum.SpectrumCommon.locate;

public class SpectrumBlockSetTypes {
    
    public static final BlockSetType POLISHED_BASALT = new BlockSetTypeBuilder()
            .soundGroup(SoundType.BASALT)
            .doorCloseSound(SoundEvents.IRON_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.IRON_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.IRON_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.IRON_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF)
            .pressurePlateClickOnSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.MOBS)
            .buttonClickOffSound(SoundEvents.STONE_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.STONE_BUTTON_CLICK_ON)
            .buttonActivatedByArrows(false)
            .build(locate("polished_basalt"));

    public static final BlockSetType POLISHED_CALCITE = new BlockSetTypeBuilder()
            .soundGroup(SoundType.CALCITE)
            .doorCloseSound(SoundEvents.IRON_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.IRON_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.IRON_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.IRON_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF)
            .pressurePlateClickOnSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.MOBS)
            .buttonClickOffSound(SoundEvents.STONE_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.STONE_BUTTON_CLICK_ON)
            .buttonActivatedByArrows(false)
            .build(locate("polished_calcite"));

    public static final BlockSetType POLISHED_BLACKSLAG = new BlockSetTypeBuilder()
            .soundGroup(SoundType.DEEPSLATE)
            .doorCloseSound(SoundEvents.IRON_DOOR_CLOSE)
            .doorOpenSound(SoundEvents.IRON_DOOR_OPEN)
            .trapdoorCloseSound(SoundEvents.IRON_TRAPDOOR_CLOSE)
            .trapdoorOpenSound(SoundEvents.IRON_TRAPDOOR_OPEN)
            .pressurePlateClickOffSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF)
            .pressurePlateClickOnSound(SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON)
            .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.MOBS)
            .buttonClickOffSound(SoundEvents.STONE_BUTTON_CLICK_OFF)
            .buttonClickOnSound(SoundEvents.STONE_BUTTON_CLICK_ON)
            .buttonActivatedByArrows(false)
            .build(locate("polished_blackslag"));

    // TODO - Custom wood sounds? Maybe?
    public static final BlockSetType NOXWOOD = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(locate("noxwood"));
    
    public static final BlockSetType COLORED_WOOD = BlockSetTypeBuilder.copyOf(BlockSetType.ACACIA).register(locate("colored_wood"));
}
