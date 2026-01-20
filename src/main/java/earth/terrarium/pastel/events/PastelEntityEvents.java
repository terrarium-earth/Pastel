package earth.terrarium.pastel.events;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.ItemPickupListener;
import earth.terrarium.pastel.api.item.TickingEquipmentItem;
import earth.terrarium.pastel.api.item.UnequipAwareItem;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.events.game.PastelGameEvents;
import earth.terrarium.pastel.helpers.enchantments.DisarmingHelper;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import earth.terrarium.pastel.items.tools.ParryingSwordItem;
import earth.terrarium.pastel.items.trinkets.AshenCircletItem;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.registries.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class PastelEntityEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::entityTick);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::allowDamage);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::equipmentChange);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::entityDeath);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::parryingSwordBlock);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::disarming);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEntityEvents::listenItemPickup);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEntityEvents::listenEntityAdded);
    }

    private static void listenItemPickup(ItemEntityPickupEvent.Pre event) {
        var entity = event.getPlayer();
        var item = event.getItemEntity();
        var original = item.getItem().copy();

        if (item.hasPickUpDelay())
            return;

        var eListener = entity.getCapability(PastelCapabilities.Pickup.ENTITY);
        ItemStack remainder = null;

        if (eListener != null && eListener.accepts(Optional.empty(), item.getItem())) {
            remainder = eListener.receive(Optional.empty(), item.getItem(), Optional.of(entity));
        }

        var inv = entity.getCapability(Capabilities.ItemHandler.ENTITY);
        if (remainder == null && inv != null) {
            remainder = ItemPickupListener.receiveRecursive(inv, 2, 0,
                    item.getItem(), Optional.of(entity));
        }

        if (remainder == null || ItemStack.isSameItemSameComponents(remainder, original))
            return;


        item.setItem(remainder);
        event.setCanPickup(TriState.FALSE);
        entity.take(item, original.getCount() - remainder.getCount());
        entity.onItemPickup(item);
    }

    private static void listenEntityAdded(EntityJoinLevelEvent event) {
        if (event.loadedFromDisk())
            return;

        var entity = event.getEntity();
        entity.gameEvent(PastelGameEvents.ENTITY_SPAWNED);
    }

    private static void disarming(LivingDamageEvent.Post event) {
        var target = event.getEntity();
        var source = event.getSource();
        var entity = source.getEntity();

        if (!(entity instanceof LivingEntity attacker) || event.getNewDamage() <= Mth.EPSILON || source.is(
            DamageTypes.THORNS))
            return;

        var disarming = Ench.getLevel(
            attacker.registryAccess(), PastelEnchantments.DISARMING, attacker.getMainHandItem());
        if (disarming > 0 && target.getRandom()
                                   .nextFloat() < disarming * PastelCommon.CONFIG.DisarmingChancePerLevelMobs)
            DisarmingHelper.disarmEntity(target);
    }


    private static void parryingSwordBlock(LivingShieldBlockEvent event) {
        var damage = event.getDamageContainer()
                          .getOriginalDamage();
        var shielder = event.getEntity();
        var weapon = shielder.getUseItem();

        if (!(weapon.getItem() instanceof ParryingSwordItem parryingSword))
            return;

        if (event.getDamageSource()
                 .is(PastelDamageTypeTags.BYPASSES_PARRYING)) {
            event.setBlocked(false);
            event.setBlockedDamage(0);
            return;
        }
        boolean perfect = false;

        if (!shielder.isBlocking() || !checkShieldFacing(event.getDamageSource(), event.getEntity(), -0.25))
            return; // Parrying swords have a tighter blocking range than shields

        var useTime = shielder.getTicksUsingItem();

        if (shielder instanceof Player player && parryingSword.canBluffParry(weapon, player, useTime)) {
            perfect = parryingSword.canPerfectParry(weapon, player, useTime);
            var misc = MiscPlayerData.get(player);
            misc.setParryTicks(15);

            if (perfect)
                misc.markForPerfectCounter();
        }

        if (parryingSword.canDeflect(event.getDamageSource(), perfect)) {
            var mult = parryingSword.getBlockingMultiplier(event.getDamageSource(), weapon, shielder, useTime);
            if (mult > 0)
                shielder.level()
                        .broadcastEntityEvent(
                            shielder,
                            (byte) 29
                        ); // without this, the shielding sound does not play on non-perfect parries

            event.setBlocked(true);
            event.setBlockedDamage(damage - damage * mult);
        }
    }

    private static void entityTick(EntityTickEvent.Post event) {
        var entity = event.getEntity();

        if (entity instanceof LivingEntity living) {
            if (living.level()
                      .isClientSide())
                return;

            var additions = PastelEffectEvents.QUEUED_ADDITIONS.get(entity.getUUID());

            if (additions != null) {
                additions.forEach(living::addEffect);
                additions.clear();
            }

            for(var slot : List.of(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET)){
                if(entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(slot).getItem() instanceof TickingEquipmentItem item){
                    item.tick(livingEntity,livingEntity.getItemBySlot(slot));
                }
            }

            PrimordialFireData.serverTick(living);
            AzureDikeProvider.getAzureDikeComponent(living)
                             .serverTick(living);
        }
    }

    private static void allowDamage(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        var source = event.getSource();

        // If the player is damaged by lava and wears an ashen circlet:
        // prevent damage and grant fire resistance
        if (source.is(DamageTypes.LAVA)) {
            Optional<ItemStack> ashenCircletStack = PastelTrinketItem.getFirstEquipped(
                entity, PastelItems.ASHEN_CIRCLET.get());
            if (ashenCircletStack.isPresent()) {
                if (AshenCircletItem.getCooldownTicks(ashenCircletStack.get(), entity.level()) == 0) {
                    AshenCircletItem.grantFireResistance(ashenCircletStack.get(), entity);
                    event.setCanceled(true);
                }
            }
        } else if (source.is(DamageTypeTags.IS_FIRE) && PastelTrinketItem.hasEquipped(
            entity, PastelItems.ASHEN_CIRCLET.get())) {
            event.setCanceled(true);
        }

        if(source.is(DamageTypes.FALL) && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof CrystalArmorItem leggings){
            leggings.onFall(entity.getItemBySlot(EquipmentSlot.LEGS),entity,event.getAmount());
            entity.level().playSound(entity, entity.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1f, 1f);
            event.getEntity().playSound(PastelSounds.SHATTER_HEAVY);
            event.setCanceled(true);
        }
    }

    private static void equipmentChange(LivingEquipmentChangeEvent event) {
        var livingEntity = event.getEntity();
        var oldEquipment = event.getFrom();
        var newEquipment = event.getTo();
        var equipmentSlot = event.getSlot();

        PastelCommon.logWarning("test 1");
        if(oldEquipment.getItem() instanceof UnequipAwareItem item){
            PastelCommon.logWarning("test 2");
            item.onUnequip(livingEntity, oldEquipment, equipmentSlot);
        }

        if(equipmentSlot.getType() == EquipmentSlot.Type.HAND && oldEquipment.has(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED)){
            PastelCommon.logWarning("test 3");
            // this needs to be handled specially here since the items won't be UnequipAware
            CrystalArmorItem.removeEmpowered(oldEquipment);
        }

        var oldInexorable = Ench.getLevel(
            livingEntity.level()
                        .registryAccess(), PastelEnchantments.INEXORABLE, oldEquipment
        );
        var newInexorable = Ench.getLevel(
            livingEntity.level()
                        .registryAccess(), PastelEnchantments.INEXORABLE, newEquipment
        );

        var effectType = equipmentSlot == EquipmentSlot.CHEST ? PastelAttributeTags.INEXORABLE_ARMOR_EFFECTIVE
                                                              : PastelAttributeTags.INEXORABLE_HANDHELD_EFFECTIVE;

        //TODO make inexorable use enchantment effects or something
        //TODO also move the enchantment cloaking logic from LivingEntityMixin into here
        if (oldInexorable > 0 && newInexorable <= 0) {
            livingEntity.getActiveEffects()
                        .stream()
                        .filter(instance -> {
                            AtomicBoolean result = new AtomicBoolean(false);
                            instance.getEffect()
                                    .value()
                                    .createModifiers(
                                        instance.getAmplifier(), (attribute, modifier) -> {
                                            if (attribute.is(effectType))
                                                result.set(true);
                                        }
                                    );
                            return result.get();
                        })
                        .forEach(instance -> instance.getEffect()
                                                     .value()
                                                     .onEffectStarted(livingEntity, instance.getAmplifier()));
        }

    }

    private static void entityDeath(LivingDeathEvent event) {
        var killedEntity = event.getEntity();
        var damageSource = event.getSource();

        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        var curios = CuriosApi.getCuriosInventory(killedEntity);

        if (curios.isPresent()) {
            var totem = curios.get()
                              .findFirstCurio(PastelItems.TOTEM_PENDANT.get());
            if (totem.isPresent()) {
                ItemStack totemStack = totem.get()
                                            .stack();

                if (totemStack.getCount() > 0) {
                    // increase stat
                    if (killedEntity instanceof ServerPlayer serverPlayerEntity) {
                        serverPlayerEntity.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                        CriteriaTriggers.USED_TOTEM.trigger(serverPlayerEntity, totemStack);
                    }
                    // consume pendant
                    totemStack.shrink(1);
                    // Heal and add effects
                    killedEntity.setHealth(1.0F);
                    killedEntity.removeAllEffects();
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    killedEntity.level()
                                .broadcastEntityEvent(killedEntity, EntityEvent.TALISMAN_ACTIVATE);
                    event.setCanceled(true);
                    return;
                }
            }
        }

        if (killedEntity instanceof ServerPlayer player) { //At this point it can only be concluded that this bitch
            // dead as hell
            evaluateAndDropPlayerHead(player, damageSource);
        }
    }

    private static void evaluateAndDropPlayerHead(ServerPlayer player, DamageSource source) {
        if (!player.isSpectator()) {
            // TODO: Can we evaluate a PastelLootPoolModifiers.treasureHunter() here instead?
            // code reuse is always nice
            ServerLevel serverWorld = player.serverLevel();

            boolean shouldDropHead = source.is(PastelDamageTypeTags.ALWAYS_DROPS_MOB_HEAD);
            if (!shouldDropHead && source.getEntity() instanceof LivingEntity livingAttacker) {
                int damageSourceTreasureHunt = Ench.getEquipmentLevel(
                    serverWorld.registryAccess(),
                    PastelEnchantments.TREASURE_HUNTER,
                    livingAttacker
                );

                shouldDropHead = damageSourceTreasureHunt > 0 && serverWorld.getRandom()
                                                                            .nextFloat() <
                                                                 0.2 * damageSourceTreasureHunt;
            }

            if (shouldDropHead) {
                ItemStack headItemStack = new ItemStack(Items.PLAYER_HEAD);
                headItemStack.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));

                ItemEntity headEntity = new ItemEntity(
                    serverWorld, player.getX(), player.getY(), player.getZ(), headItemStack);
                serverWorld.addFreshEntity(headEntity);
            }
        }
    }

    private static boolean checkShieldFacing(DamageSource damageSource, LivingEntity entity, double leniency) {
        Vec3 vec32 = damageSource.getSourcePosition();
        if (vec32 != null) {
            Vec3 vec3 = entity.calculateViewVector(0.0F, entity.getYHeadRot());
            Vec3 vec31 = vec32.vectorTo(entity.position());
            vec31 = new Vec3(vec31.x, 0.0, vec31.z).normalize();
            return vec31.dot(vec3) < leniency;
            // This is largely ripped from LivingEntity. Only real change is leniency.
            // Remember: 1 = facing the same direction, -1 = facing opposite directions
        }
        return false;
    }
}
