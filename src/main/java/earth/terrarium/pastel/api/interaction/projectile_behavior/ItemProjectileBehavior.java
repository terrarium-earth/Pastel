package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.*;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Todo: Convert to Capability
public interface ItemProjectileBehavior {
	Map<ResourceLocation, ItemProjectileBehavior> CUSTOM_BEHAVIORS = new HashMap<>();
	Codec<ItemProjectileBehavior> CODEC = ResourceLocation.CODEC.xmap(CUSTOM_BEHAVIORS::get, behavior -> {
		for (Map.Entry<ResourceLocation, ItemProjectileBehavior> entry : CUSTOM_BEHAVIORS.entrySet()) {
			if (entry.getValue() == behavior) { return entry.getKey(); }
		}
		return SpectrumCommon.ofSpectrumDefaulted("default");
	});
	
	static ItemProjectileBehavior get(ItemStack stack) {
		ItemProjectileBehavior behavior = stack.getItemHolder().getData(PastelDataMaps.PROJECTILE_BEHAVIOR);
		return behavior == null ? DefaultProjectileBehavior.INSTANCE : behavior;
	}
	
	/**
	 * Invoked when the projectile hits an entity.
	 *
	 * @param projectile The ItemProjectile
	 * @param stack      The stack contained in the ItemProjectile. Quick access to projectile.getStack()
	 * @param owner      The owner of the projectile
	 * @param hitResult  The EntityHitResult. Contains the entity hit and position
	 * @return The stack that should be dropped. If the stack has a count > 0, it automatically gets dropped at the position of the impact. If the item should get consumed, decrement the stack from the parameters and return it here
	 */
	ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, EntityHitResult hitResult);
	
	/**
	 * Invoked when the projectile hits a block
	 *
	 * @param projectile The ItemProjectile
	 * @param stack      The stack contained in the ItemProjectile. Quick access to projectile.getStack()
	 * @param owner      The owner of the projectile
	 * @param hitResult  The EntityHitResult. Contains the entity hit and position
	 * @return The stack that should be dropped. If the stack has a count > 0, it automatically gets dropped at the position of the impact. If the item should get consumed, decrement the stack from the parameters and return it here
	 */
	ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, @Nullable Entity owner, BlockHitResult hitResult);
	
	/**
	 * Projectile behavior type used for serialization to and from json
	 *
	 * @return The type of the projectile behavior, used for serialization and deserialization
	 */
	ProjectileBehaviorType<?> type();
}
