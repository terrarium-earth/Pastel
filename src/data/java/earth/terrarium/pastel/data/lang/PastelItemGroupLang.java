package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelItemGroups;

public class PastelItemGroupLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        for (
            var itemGroup : PastelItemGroups.ITEM_GROUP_IDS
        ) {
            String prettifiedName = PastelLanguageProvider.prettifyRegisteredName(itemGroup.getPath());
            provider
                .add(
                    "itemGroup.pastel." + itemGroup.getPath(),
                    "Pastel " + prettifiedName
                );
        }
    }
}
