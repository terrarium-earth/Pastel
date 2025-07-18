package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PastelBlockSetTypes {

    public static final BlockSetType POLISHED_BASALT = new BlockSetType(
        PastelCommon.MOD_ID + ":polished_basalt",
        true,
        true,
        false,
        BlockSetType.PressurePlateSensitivity.MOBS,
        SoundType.BASALT,
        SoundEvents.IRON_DOOR_CLOSE,
        SoundEvents.IRON_DOOR_OPEN,
        SoundEvents.IRON_TRAPDOOR_CLOSE,
        SoundEvents.IRON_TRAPDOOR_OPEN,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
        SoundEvents.STONE_BUTTON_CLICK_OFF,
        SoundEvents.STONE_BUTTON_CLICK_ON
    );

    public static final BlockSetType POLISHED_CALCITE = new BlockSetType(
        PastelCommon.MOD_ID + ":polished_calcite",
        true,
        true,
        false,
        BlockSetType.PressurePlateSensitivity.MOBS,
        SoundType.CALCITE,
        SoundEvents.IRON_DOOR_CLOSE,
        SoundEvents.IRON_DOOR_OPEN,
        SoundEvents.IRON_TRAPDOOR_CLOSE,
        SoundEvents.IRON_TRAPDOOR_OPEN,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
        SoundEvents.STONE_BUTTON_CLICK_OFF,
        SoundEvents.STONE_BUTTON_CLICK_ON
    );

    public static final BlockSetType POLISHED_BLACKSLAG = new BlockSetType(
        PastelCommon.MOD_ID + ":polished_blackslag",
        true,
        true,
        false,
        BlockSetType.PressurePlateSensitivity.MOBS,
        SoundType.DEEPSLATE,
        SoundEvents.IRON_DOOR_CLOSE,
        SoundEvents.IRON_DOOR_OPEN,
        SoundEvents.IRON_TRAPDOOR_CLOSE,
        SoundEvents.IRON_TRAPDOOR_OPEN,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
        SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
        SoundEvents.STONE_BUTTON_CLICK_OFF,
        SoundEvents.STONE_BUTTON_CLICK_ON
    );

    // TODO - Custom wood sounds? Maybe?
    public static final BlockSetType NOXWOOD = new BlockSetType("noxwood");

    public static final BlockSetType COLORED_WOOD = new BlockSetType("colored_wood");
}
