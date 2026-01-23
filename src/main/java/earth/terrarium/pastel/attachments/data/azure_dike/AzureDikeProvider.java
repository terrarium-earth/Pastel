package earth.terrarium.pastel.attachments.data.azure_dike;

import earth.terrarium.pastel.attachments.data.AttachmentUtil;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class AzureDikeProvider {

    /**
     * Uses as much Azure Dike as possible to protect the Provider from incoming damage
     *
     * @param provider       The Component Provider
     * @param incomingDamage The incoming damage
     * @return All damage that could not be protected from
     */
    public static float absorbDamage(LivingEntity provider, float incomingDamage) {
        return absorbDamage(provider, incomingDamage, incomingDamage, false);
    }

    public static float absorbDamage(LivingEntity provider, float rawDamage, float incomingDamage, boolean effective) {
        var azureDike = provider.getData(AzureDikeData.ATTACHMENT);
        var passedDamage = azureDike.absorbDamage(incomingDamage, effective);
        // dike didn't do anything, so undo the armor calculation so it doesn't apply twice
        if(passedDamage == incomingDamage) passedDamage = rawDamage;
        if (incomingDamage - passedDamage > 0.0001F) {
            AttachmentUtil.syncToTracking(
                new AzureDikeData.Payload(provider.getId(), azureDike), provider.level(), provider.blockPosition());
            if (provider instanceof ServerPlayer player)
                PastelCriteria.AZURE_DIKE_CHARGE.trigger(
                    player, azureDike.getCurrentProtection(), azureDike.getTicksPerPointOfRecharge(),
                    -(incomingDamage - passedDamage)
                );
        }

        if (PastelTrinketItem.hasEquipped(provider, PastelItems.AZURESQUE_DIKE_CORE.get()))
            return passedDamage * 2;

        return passedDamage;
    }

    public static float getAzureDikeCharges(LivingEntity provider) {
        return provider.getData(AzureDikeData.ATTACHMENT)
                       .getCurrentProtection();
    }

    public static float getMaxAzureDikeCharges(LivingEntity provider) {
        return provider.getData(AzureDikeData.ATTACHMENT)
                       .getMaxProtection();
    }

    public static AzureDikeData getAzureDikeComponent(LivingEntity provider) {
        return provider.getData(AzureDikeData.ATTACHMENT);
    }

}
