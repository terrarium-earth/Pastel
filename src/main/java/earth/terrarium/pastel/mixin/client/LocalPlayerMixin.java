package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import earth.terrarium.pastel.items.trinkets.ConsumptionRingItem;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
    LocalPlayer.class
)
public abstract class LocalPlayerMixin extends AttachmentHolder {

    @WrapOperation(
        method = "hasEnoughFoodToStartSprinting", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I"
        )
    )
    public int sprintWithVampireRing(FoodData instance, Operation<Integer> original) {
        var hasRing = this.getData(ConsumptionRingData.ATTACHMENT);
        return hasRing ? 10 : original.call(instance);
    }

    @WrapOperation(
        method = "aiStep", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z", ordinal = 3
        )
    )
    private boolean doubleJump(LocalPlayer instance, Operation<Boolean> original) {
        boolean actuallyOnGround = original.call(instance);
        return CrystalArmorItem.doubleJump(instance, actuallyOnGround);
    }
}
