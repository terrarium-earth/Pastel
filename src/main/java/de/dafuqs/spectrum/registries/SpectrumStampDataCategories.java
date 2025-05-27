package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.StampDataCategory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

@SuppressWarnings("unused")
public class SpectrumStampDataCategories {

    private static final DeferredRegister<StampDataCategory> REGISTER = DeferredRegister.create(SpectrumRegistryKeys.STAMP_DATA_CATEGORY, SpectrumCommon.MOD_ID);

    public static StampDataCategory UNIQUE = StampDataCategory.UNIQUE;
    public static StampDataCategory PASTEL = register("pastel");

    public static void register(IEventBus bus) {
        REGISTER.register(StampDataCategory.UNIQUE.getId().getPath(), () -> StampDataCategory.UNIQUE);
        REGISTER.register(bus);
    }

    private static StampDataCategory register(String name) {
        var cat = StampDataCategory.create(SpectrumCommon.locate(name));
        REGISTER.register(name, () -> cat);
        return cat;
    }
}
