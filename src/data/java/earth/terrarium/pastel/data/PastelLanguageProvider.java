package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.lang.*;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;

public class PastelLanguageProvider extends LanguageProvider {
    public PastelLanguageProvider(PackOutput output) {
        super(output, PastelCommon.MOD_ID, "en_us");
    }

    public static String prettifyRegisteredName(String registeredName) {
        StringBuilder formattedName = new StringBuilder();
        for (String word : registeredName.split("_")) {
            if (!word.isEmpty()) {
                if(word.length()==1)
                    formattedName.append(word.toUpperCase()).append(" ");
                else
                    formattedName.append(word.substring(0, 1)
                                         .toUpperCase())
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

        this.add("pastel.cloaked.suffix","...?");
    }
}
