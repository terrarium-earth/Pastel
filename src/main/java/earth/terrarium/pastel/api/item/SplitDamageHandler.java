package earth.terrarium.pastel.api.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface SplitDamageHandler {

    DamageComposition getDamageComposition(LivingEntity attacker, LivingEntity target, ItemStack stack, float damage);

    class DamageComposition {
        private final List<Partition> partitions = new ArrayList<>();

        public DamageComposition() {
        }

        public void addPlayerOrEntity(LivingEntity entity, float damage) {
            if (entity instanceof Player player) {
                this.partitions.add(new Partition(
                    player.damageSources()
                          .playerAttack(player), damage
                ));
            } else {
                this.partitions.add(new Partition(
                    entity.damageSources()
                          .mobAttack(entity), damage
                ));
            }
        }

        public DamageSource getPlayerOrEntity(LivingEntity entity) {
            if (entity instanceof Player player) {
                return player.damageSources()
                             .playerAttack(player);
            } else {
                return entity.damageSources()
                             .mobAttack(entity);
            }
        }

        public void add(DamageSource damageSource, float damage) {
            this.partitions.add(new Partition(damageSource, damage));
        }

        public List<Partition> get() {
            return partitions;
        }

    }

    record Partition(DamageSource source, float damage) {
    }
}
