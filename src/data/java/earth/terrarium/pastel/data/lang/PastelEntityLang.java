package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.entity.PastelEntityTypes;

public class PastelEntityLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        for (var entity : PastelEntityTypes.REGISTER.getEntries()) {
            if (entity.getRegisteredName().endsWith("lizard")) {
                provider.addEntityType(entity, "Lurking Lizard"); // rude
                continue;
            }
            String prettifiedName = PastelLanguageProvider.prettifyRegisteredName(entity.getRegisteredName().substring(7));
            provider.addEntityType(entity, prettifiedName);
        }
    }
}
