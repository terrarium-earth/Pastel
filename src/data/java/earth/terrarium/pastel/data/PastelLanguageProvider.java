package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.lang.PastelAdvancementLang;
import earth.terrarium.pastel.data.lang.PastelAttributeLang;
import earth.terrarium.pastel.data.lang.PastelBiomeLang;
import earth.terrarium.pastel.data.lang.PastelBlockLang;
import earth.terrarium.pastel.data.lang.PastelCommandLang;
import earth.terrarium.pastel.data.lang.PastelContainerLang;
import earth.terrarium.pastel.data.lang.PastelCuriosLang;
import earth.terrarium.pastel.data.lang.PastelDeathMessageLang;
import earth.terrarium.pastel.data.lang.PastelDimensionLang;
import earth.terrarium.pastel.data.lang.PastelEffectLang;
import earth.terrarium.pastel.data.lang.PastelEnchantmentLang;
import earth.terrarium.pastel.data.lang.PastelEntityLang;
import earth.terrarium.pastel.data.lang.PastelFluidTypeLang;
import earth.terrarium.pastel.data.lang.PastelGuiLang;
import earth.terrarium.pastel.data.lang.PastelGuidebookLang;
import earth.terrarium.pastel.data.lang.PastelInkLang;
import earth.terrarium.pastel.data.lang.PastelItemGroupLang;
import earth.terrarium.pastel.data.lang.PastelItemLang;
import earth.terrarium.pastel.data.lang.PastelJukeboxSongLang;
import earth.terrarium.pastel.data.lang.PastelMultiblockLang;
import earth.terrarium.pastel.data.lang.PastelOptionLang;
import earth.terrarium.pastel.data.lang.PastelREIReagentLang;
import earth.terrarium.pastel.data.lang.PastelRecipeGroupLang;
import earth.terrarium.pastel.data.lang.PastelRecipeLang;
import earth.terrarium.pastel.data.lang.PastelSignTextLang;
import earth.terrarium.pastel.data.lang.PastelSpecialLang;
import earth.terrarium.pastel.data.lang.PastelStructureLang;
import earth.terrarium.pastel.data.lang.PastelSubtitleLang;
import earth.terrarium.pastel.data.lang.PastelTagLang;
import earth.terrarium.pastel.data.lang.PastelToastLang;
import earth.terrarium.pastel.data.lang.PastelTodoUnusedLang;
import earth.terrarium.pastel.data.lang.PastelTooltipAndLoreLang;
import earth.terrarium.pastel.data.lang.PastelVanillaChangeLang;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class PastelLanguageProvider extends LanguageProvider {
    public PastelLanguageProvider(PackOutput output) {
        super(output, PastelCommon.MOD_ID, "en_us");
    }

    public static String prettifyRegisteredName(String registeredName) {
        StringBuilder formattedName = new StringBuilder();
        for (
            String word : registeredName.split("_")
        ) {
            if (!word.isEmpty()) {
                if (word.length() == 1)
                    formattedName.append(word.toUpperCase()).append(" ");
                else
                    formattedName
                        .append(
                            word
                                .substring(0, 1)
                                .toUpperCase()
                        )
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return formattedName.toString().strip();
    }

    @Override
    protected void addTranslations() {
        PastelAdvancementLang.addTranslations(this);
        PastelAttributeLang.addTranslations(this);
        PastelBiomeLang.addTranslations(this);
        PastelBlockLang.addTranslations(this);
        PastelCommandLang.addTranslations(this);
        PastelContainerLang.addTranslations(this);
        PastelCuriosLang.addTranslations(this);
        PastelDeathMessageLang.addTranslations(this);
        PastelDimensionLang.addTranslations(this);
        PastelEffectLang.addTranslations(this);
        PastelEnchantmentLang.addTranslations(this);
        PastelEntityLang.addTranslations(this);
        PastelFluidTypeLang.addTranslations(this);
        PastelGuiLang.addTranslations(this);
        PastelGuidebookLang.addTranslations(this);
        PastelInkLang.addTranslations(this);
        PastelItemGroupLang.addTranslations(this);
        PastelItemLang.addTranslations(this);
        PastelJukeboxSongLang.addTranslations(this);
        PastelMultiblockLang.addTranslations(this);
        PastelOptionLang.addTranslations(this);
        PastelRecipeGroupLang.addTranslations(this);
        PastelRecipeLang.addTranslations(this);
        PastelREIReagentLang.addTranslations(this);
        PastelSignTextLang.addTranslations(this);
        PastelSpecialLang.addTranslations(this);
        PastelStructureLang.addTranslations(this);
        PastelSubtitleLang.addTranslations(this);
        PastelTagLang.addTranslations(this);
        PastelToastLang.addTranslations(this);
        PastelTooltipAndLoreLang.addTranslations(this);
        PastelVanillaChangeLang.addTranslations(this);

        PastelTodoUnusedLang.addTranslations(this);

        this.add("pastel.cloaked.suffix", "...?");
    }
}
