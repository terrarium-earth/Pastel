package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

@SuppressWarnings("unused")
public class PastelJukeboxSongs {

    public static final ResourceKey<JukeboxSong> CREDITS = of("credits");
    public static final ResourceKey<JukeboxSong> DISCOVERY = of("discovery");
    public static final ResourceKey<JukeboxSong> DIVINITY = of("divinity");

    private static ResourceKey<JukeboxSong> of(String id) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, PastelCommon.locate(id));
    }

}
