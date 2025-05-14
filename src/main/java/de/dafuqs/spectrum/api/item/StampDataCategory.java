package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.*;
import net.minecraft.resources.*;

public class StampDataCategory {

    public static final StampDataCategory UNIQUE = new StampDataCategory(SpectrumCommon.locate("unique"), true);
    private final ResourceLocation id;
    private final boolean unique;

    private StampDataCategory(ResourceLocation id, boolean unique) {
        this.id = id;
        this.unique = unique;
    }

    public static StampDataCategory create(ResourceLocation id) {
        return new StampDataCategory(id, false);
    }

    public ResourceLocation getId() {
        return id;
    }

    public boolean isUnique() {
        return unique;
    }
}
