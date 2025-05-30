package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.interaction.projectile_behavior.*;
import earth.terrarium.pastel.api.interaction.OmniAcceleratorProjectile;
import earth.terrarium.pastel.api.item.ExperienceStorageItem;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamBlock;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.items.magic_items.CraftingTabletItem;
import earth.terrarium.pastel.items.magic_items.EnchantmentCanvasItem;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.items.magic_items.PipeBombItem;
import earth.terrarium.pastel.items.magic_items.ampoules.GlassAmpouleItem;
import earth.terrarium.pastel.items.tools.OmniAcceleratorItem;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpectrumItemProjectileBehaviors {
	
	public static void register() {
		ItemProjectileBehaviorRegistry.register(DefaultProjectileBehavior.TYPE);
		ItemProjectileBehaviorRegistry.register(FlatDamageProjectileBehavior.TYPE);
		ItemProjectileBehaviorRegistry.register(MusicDiscProjectileBehavior.TYPE);
	}
}