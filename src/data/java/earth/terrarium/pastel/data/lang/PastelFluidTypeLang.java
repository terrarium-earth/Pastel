package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelFluids;

public class PastelFluidTypeLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        for (var fluidType : PastelFluids.TYPE_REGISTER.getEntries()) {
            var registeredName = fluidType.getRegisteredName()
                                          .substring(7);
            provider.add(
                "fluid_type.pastel." + registeredName,
                PastelLanguageProvider.prettifyRegisteredName(registeredName)
            );
        }
    }
}
