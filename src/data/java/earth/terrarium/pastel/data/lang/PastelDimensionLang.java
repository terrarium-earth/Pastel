package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelLevels;

public class PastelDimensionLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.addDimension(PastelLevels.DIMENSION_KEY, "Imbrifer");
    }
}
