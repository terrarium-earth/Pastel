package earth.terrarium.pastel.mixin.client;

import earth.terrarium.pastel.attachments.data.JumpCooldownAttachment;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixinClient extends LivingEntity {

    protected PlayerMixinClient(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "jumpFromGround", at=@At(value="HEAD"))
    public void setJumpCooldown(CallbackInfo ci){
        this.setData(JumpCooldownAttachment.ATTACHMENT, CrystalArmorItem.JUMP_COOLDOWN);
    }
}
