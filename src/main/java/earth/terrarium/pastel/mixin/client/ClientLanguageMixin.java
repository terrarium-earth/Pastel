package earth.terrarium.pastel.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(
    ClientLanguage.class
)
public class ClientLanguageMixin {

    @Mutable
    @Shadow
    @Final
    private Map<String, String> storage;

    @Inject(
        method = "Lnet/minecraft/client/resources/language/ClientLanguage;<init>(Ljava/util/Map;ZLjava/util/Map;)V", at = @At(
            "TAIL"
        )
    )
    private void addTranslations(
        Map<String, String> translations,
        boolean rightToLeft,
        Map<String, Component> componentStorage,
        CallbackInfo ci
    ) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) != Calendar.APRIL || calendar.get(Calendar.DAY_OF_MONTH) != 1) return;

        Map<String, String> builder = new HashMap<>(translations);
        builder.put("block.pastel.crystallarieum", getCrystallarieuaeuieueum());
        builder.put("item.pastel.ring_of_pursuit", "Ring of Fursuit");
        builder.put("item.pastel.draconic_twinsword", "Draconic Winblade");
        builder.put("item.pastel.dragon_talon", "Sellsword Winblades");
        builder.put("effect.pastel.fatal_slumber", "Fat Slumber");
        builder.put("item.pastel.oblivion_pickaxe", "Oblivious Pickaxe");
        builder.put("item.pastel.shimmerstone_gem", "Stimmerstone Gem");
        builder.put("block.pastel.shimmerstone_block", "Block of Stimmerstone");
        builder.put("block.pastel.amethyst_glass_arrow", "Chlorophyte Bullet");
        builder.put("pastel.tooltip.crystal_armor_empowered", "+あたし");
        builder.put("item.pastel.celestial_pocketwatch", "Luna Dial");

        builder.put("item.pastel.mermaids_gem", translations.get("item.pastel.storm_stone"));
        builder.put("item.pastel.storm_stone", translations.get("item.pastel.mermaids_gem"));

        this.storage = builder;
    }

    @Unique private static String getCrystallarieuaeuieueum() {
        List<String> possibilities = new ArrayList<>() {
            {
                add("Crystallarieum");
                add("Crystallareium");
                add("Crystallerium");
                add("Crystallarium");
                add("Crystallium");
                add("Crystalleium");
                add("Crystallum");
                add("Crystallarieium");
                add("Christalerium");
            }
        };
        char c = Minecraft
            .getInstance()
            .getUser()
            .getName()
            .toCharArray()[0];
        return possibilities.get((int) c % possibilities.size());
    }

}
