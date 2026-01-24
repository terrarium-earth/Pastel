package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public PastelDamageTypeTagsProvider(
        PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(packOutput, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("neoforge", "no_flinch")))
            .add(PastelDamageTypes.SET_HEALTH)
            .add(PastelDamageTypes.SLEEP)
            .add(PastelDamageTypes.PRIMORDIAL_FIRE);
    }
}
