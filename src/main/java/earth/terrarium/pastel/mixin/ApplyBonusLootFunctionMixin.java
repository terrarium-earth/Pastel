package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(
    ApplyBonusCount.class
)
public abstract class ApplyBonusLootFunctionMixin {

    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @Shadow
    @Final
    private ApplyBonusCount.Formula formula;

    @ModifyVariable(
        method = "run", at = @At(
            "STORE"
        ), ordinal = 1
    )
    public int rerollBonusLoot(int oldValue, ItemStack stack, LootContext context) {
        // if the player has the ANOTHER_DRAW effect the bonus loot of
        // this function gets rerolled potency+1 times and the best one taken
        ItemStack itemStack = context.getParamOrNull(LootContextParams.TOOL);
        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (itemStack != null && entity instanceof LivingEntity livingEntity) {
            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(this.enchantment, itemStack);
            if (enchantmentLevel > 0) {
                MobEffectInstance effect = livingEntity.getEffect(PastelMobEffects.ANOTHER_ROLL);
                if (effect != null) {
                    int rollCount = effect.getAmplifier() + 1;
                    int highestRoll = oldValue;
                    for (
                        int i = 0;
                        i < rollCount;
                        i++
                    ) {
                        int thisRoll = this.formula
                            .calculateNewCount(
                                context.getRandom(),
                                stack.getCount(),
                                enchantmentLevel
                            );
                        highestRoll = Math.max(highestRoll, thisRoll);
                    }
                    return highestRoll;
                }
            }
        }
        return oldValue;
    }

}
