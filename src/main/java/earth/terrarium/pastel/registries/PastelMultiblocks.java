package earth.terrarium.pastel.registries;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PastelMultiblocks {

    public static final Component PEDESTAL_SIMPLE_TEXT = Component.translatable("multiblock.pastel.pedestal_simple");
    public static final ResourceLocation PEDESTAL_SIMPLE = PastelCommon.locate("pedestal_simple");
    public static final Component PEDESTAL_ADVANCED_TEXT = Component.translatable(
        "multiblock.pastel.pedestal_advanced");
    public static final ResourceLocation PEDESTAL_ADVANCED = PastelCommon.locate("pedestal_advanced");
    public static final Component PEDESTAL_COMPLEX_TEXT = Component.translatable("multiblock.pastel.pedestal_complex");
    public static final ResourceLocation PEDESTAL_COMPLEX = PastelCommon.locate("pedestal_complex");
    public static final ResourceLocation PEDESTAL_COMPLEX_WITHOUT_MOONSTONE = PastelCommon.locate(
        "pedestal_complex_without_moonstone");

    public static final Component FUSION_SHRINE_TEXT = Component.translatable("multiblock.pastel.fusion_shrine");
    public static final ResourceLocation FUSION_SHRINE = PastelCommon.locate("fusion_shrine");

    public static final Component ENCHANTER_TEXT = Component.translatable("multiblock.pastel.enchanter");
    public static final ResourceLocation ENCHANTER = PastelCommon.locate("enchanter");

    public static final Component SPIRIT_INSTILLER_TEXT = Component.translatable("multiblock.pastel.spirit_instiller");
    public static final ResourceLocation SPIRIT_INSTILLER = PastelCommon.locate("spirit_instiller");

    public static final Component CINDERHEARTH_TEXT = Component.translatable("multiblock.pastel.cinderhearth");
    public static final ResourceLocation CINDERHEARTH = PastelCommon.locate("cinderhearth");
    public static final ResourceLocation CINDERHEARTH_WITHOUT_LAVA = PastelCommon.locate("cinderhearth_no_lava");

    public static Multiblock get(ResourceLocation id) {
        return ModonomiconAPI.get()
                             .getMultiblock(id);
    }

}
