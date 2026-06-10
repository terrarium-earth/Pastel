package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.registries.PastelEntityTypeTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.Nullable;

public class FoxoNineTailsItem extends WhipItem implements SlotBackgroundEffect {
    public FoxoNineTailsItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        // more fervor per fervor!
        super(tier, attackDamage, attackSpeed, 1.5f, 64, 2, 5, 0.5f, properties);
    }

    // debuff set: we get a little wacky here. 5-16 gives slow 1, 16-24 gives slow 1, weak 1, and lethal poison,
    // 24-32 gives slow 2, stiffness, weak 1, lethal poison; 32-64 gives slow 3, stiffness, weak 1, lethal poison, and
    // lightweight 4 (makes you hover in place) except to flying enemies, to whom it gives gravity instead, effectively
    // paralyzing most enemies. notably, the higher levels of fervor are mostly useful for extending the duration +
    // buffing allies/self. also, duration scales half as hard with fervor since we get 2 on hit instead of 1
    @Override
    protected void applyDebuffs(LivingEntity attacker, LivingEntity target, int fervorExpended) {
        if(fervorExpended < 16){
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 10, 0), attacker);
        } else if (fervorExpended < 24){
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.DEADLY_POISON, fervorExpended * 10, 0), attacker);
        } else if(fervorExpended < 32){
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 10, 1), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.STIFFNESS, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.DEADLY_POISON, fervorExpended * 10, 0), attacker);
        } else {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 10, 2), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.STIFFNESS, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.DEADLY_POISON, fervorExpended * 10, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.HOVERING, fervorExpended * 10, 10), attacker);
        }
    }

    @Override
    public void healOrBuff(Player user, LivingEntity target, int fervor) {
        user.level()
            .playSound(null, target.blockPosition(), PastelSounds.GLASS_SHIMMER, SoundSource.PLAYERS, 1.0F, 1.0F);
        int effect_divisor = user.is(target) ? 2 : 1; // half as effective when using it on yourself
        if(fervor == this.maxFervor){ // max fervor gets you a buff cocktail!
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600 / effect_divisor, 1), user);
            target.addEffect(new MobEffectInstance(PastelMobEffects.SWIFTNESS, 600 / effect_divisor, 1), user);
            target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600 / effect_divisor, 0), user); // strength is pretty strong even at low potency
        }
        // otherwise, you just get a heal and some speed if you're not already speedmaxxing
        if(!target.hasEffect(MobEffects.MOVEMENT_SPEED))
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Mth.floor(
            (float) 200 / effect_divisor * ((float) fervor / maxFervor))));
        target.heal((float) fervor / effect_divisor * 3.2f); // works out to 5 hearts to yourself, or 10 hearts to a friend, when spending max fervor
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return SlotEffect.BORDER_FADE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return InkColors.PINK_COLOR;
    }
}
