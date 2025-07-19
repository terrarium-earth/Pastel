package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(Cat.class)
public abstract class CatEntityMixin extends TamableAnimal {

    protected CatEntityMixin(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "mobInteract")
    private void feedKitten(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (this.level()
                .isClientSide()) return;
        if (!this.hasCustomName()) return;

        assert this.getCustomName() != null;
        String customName = this.getCustomName()
                                .getString()
                                .toUpperCase(Locale.ROOT);

        boolean howMany = customName.equals("AAA") || customName.equals("AAA ❣");
        if (player instanceof ServerPlayer serverPlayerEntity) {
            if (item.equals(PastelItems.STRATINE_GEM.get()) && this.hasEffect(MobEffects.LEVITATION) && howMany) {
                Support.grantAdvancementCriterion(
                    serverPlayerEntity, ResourceLocation.fromNamespaceAndPath("pastel", "midgame/become_enlightened"),
                    "confirmed"
                );
                this.removeEffect(MobEffects.LEVITATION);
                this.addEffect(new MobEffectInstance(
                    MobEffects.SLOW_FALLING,
                    600,
                    1
                ));
            }
        }
    }
}
