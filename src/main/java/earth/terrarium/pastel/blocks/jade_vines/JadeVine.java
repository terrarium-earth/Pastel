package earth.terrarium.pastel.blocks.jade_vines;

import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.effect.ColoredFallingSporeBlossomParticleEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public interface JadeVine {
	
	BooleanProperty DEAD = BooleanProperty.create("dead");
	VoxelShape BULB_SHAPE = Block.box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	VoxelShape TIP_SHAPE = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	static void spawnBloomParticlesClient(Level world, BlockPos blockPos) {
		spawnParticlesClient(world, blockPos, PastelParticleTypes.JADE_VINES_BLOOM);
		
		RandomSource random = world.random;
		double x = blockPos.getX() + 0.2 + (random.nextFloat() * 0.6);
		double y = blockPos.getY() + 0.2 + (random.nextFloat() * 0.6);
		double z = blockPos.getZ() + 0.2 + (random.nextFloat() * 0.6);
		world.addParticle(ColoredFallingSporeBlossomParticleEffect.PINK, x, y, z, 0.0D, 0.0D, 0.0D);
	}
	
	static void spawnParticlesClient(Level world, BlockPos blockPos) {
		spawnParticlesClient(world, blockPos, PastelParticleTypes.JADE_VINES);
	}
	
	private static void spawnParticlesClient(Level world, BlockPos blockPos, ParticleOptions particleType) {
		RandomSource random = world.random;
		double x = blockPos.getX() + 0.2 + (random.nextFloat() * 0.6);
		double y = blockPos.getY() + 0.2 + (random.nextFloat() * 0.6);
		double z = blockPos.getZ() + 0.2 + (random.nextFloat() * 0.6);
		
		double velX = 0.06 - random.nextFloat() * 0.12;
		double velY = 0.06 - random.nextFloat() * 0.12;
		double velZ = 0.06 - random.nextFloat() * 0.12;
		
		world.addParticle(particleType, x, y, z, velX, velY, velZ);
	}
	
	static void spawnParticlesServer(ServerLevel world, BlockPos blockPos, int amount) {
		PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, Vec3.atCenterOf(blockPos), PastelParticleTypes.JADE_VINES, amount, new Vec3(0.6, 0.6, 0.6), new Vec3(0.12, 0.12, 0.12));
	}
	
	static boolean isExposedToSunlight(@NotNull Level world, @NotNull BlockPos blockPos) {
		return world.getBrightness(LightLayer.SKY, blockPos) > 8 && TimeHelper.isBrightSunlight(world);
	}
	
	boolean setToAge(Level world, BlockPos blockPos, int age);
	
}
