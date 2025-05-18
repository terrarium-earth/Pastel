package de.dafuqs.spectrum.api.item;

import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface SplitDamageItem {

    DamageComposition getDamageComposition(LivingEntity attacker, LivingEntity target, ItemStack stack, float damage);

    class DamageComposition {

        private final List<Tuple<DamageSource, Float>> damageSourcesWithPercentage = new ArrayList<>();

        public DamageComposition() {

        }

        public void addPlayerOrEntity(LivingEntity entity, float ratio) {
            if (entity instanceof Player player) {
                this.damageSourcesWithPercentage.add(new Tuple<>(player.damageSources().playerAttack(player), ratio));
            } else {
				this.damageSourcesWithPercentage.add(new Tuple<>(entity.damageSources().mobAttack(entity), ratio));
            }
        }
		
		public DamageSource getPlayerOrEntity(LivingEntity entity) {
			if (entity instanceof Player player) {
				return player.damageSources().playerAttack(player);
			} else {
				return entity.damageSources().mobAttack(entity);
			}
		}

        public void add(DamageSource damageSource, float ratio) {
            this.damageSourcesWithPercentage.add(new Tuple<>(damageSource, ratio));
        }

        public List<Tuple<DamageSource, Float>> get() {
            return damageSourcesWithPercentage;
        }

    }


}
