package earth.terrarium.pastel.blocks.item_bowl;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.color.ColorRegistry;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.InWorldInteractionBlockEntity;
import earth.terrarium.pastel.capabilities.SidedCapabilityProvider;
import earth.terrarium.pastel.events.game.ExactPositionSource;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.s2c_payloads.ColorTransmissionPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredTransmission;
import earth.terrarium.pastel.particle.effect.ColoredTransmissionParticleEffect;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ItemBowlBlockEntity extends InWorldInteractionBlockEntity implements SidedCapabilityProvider {

    protected static final int INVENTORY_SIZE = 1;

    public ItemBowlBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.ITEM_BOWL.get(), pos, state, INVENTORY_SIZE);
        inventory.addListener(i -> setChanged());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        this.unpackLootTable(null);
        return super.getUpdateTag(registryLookup);
    }

    @SuppressWarnings(
        "unused"
    )
    public static void clientTick(
        @NotNull Level world,
        BlockPos blockPos,
        BlockState blockState,
        ItemBowlBlockEntity itemBowlBlockEntity
    ) {
        ItemStack storedStack = itemBowlBlockEntity.getItem(0);
        if (!storedStack.isEmpty()) {
            Optional<InkColor> optionalItemColor = ColorRegistry.ITEM_COLORS.getMapping(storedStack.getItem());
            if (optionalItemColor.isPresent()) {
                int particleCount = Support
                    .chanceRound(
                        Math.max(0.1, (float) storedStack.getCount() / (storedStack.getMaxStackSize() * 2)),
                        world.random
                    );
                spawnRisingParticles(world, blockPos, storedStack, particleCount);
            }
        }
    }

    public static void spawnRisingParticles(Level world, BlockPos blockPos, ItemStack itemStack, int amount) {
        if (amount > 0) {
            Optional<InkColor> optionalItemColor = ColorRegistry.ITEM_COLORS.getMapping(itemStack.getItem());
            if (optionalItemColor.isPresent()) {
                ParticleOptions particleEffect = ColoredSparkleRisingParticleEffect
                    .of(
                        optionalItemColor
                            .get()
                            .getColorInt()
                    );

                for (
                    int i = 0;
                    i < amount;
                    i++
                ) {
                    float randomX = 0.1F + world.random.nextFloat() * 0.8F;
                    float randomZ = 0.1F + world.random.nextFloat() * 0.8F;
                    world
                        .addParticle(
                            particleEffect,
                            blockPos.getX() + randomX,
                            blockPos.getY() + 0.75,
                            blockPos.getZ() + randomZ,
                            0.0D,
                            0.05D,
                            0.0D
                        );
                }
            }
        }
    }

    public int decrementBowlStack(Vec3 orbTargetPos, int amount, boolean doEffects) {
        ItemStack storedStack = this.getItem(0);
        if (storedStack.isEmpty() || level == null) {
            return 0;
        }

        int decrementAmount = Math.min(amount, storedStack.getCount());
        ItemStack remainder = storedStack.getCraftingRemainingItem();
        if (!remainder.isEmpty()) {
            if (storedStack.getCount() == 1) {
                setItem(0, remainder);
            } else {
                getItem(0).shrink(decrementAmount);
                remainder.setCount(decrementAmount);

                ItemEntity itemEntity = new ItemEntity(
                    level,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 1,
                    worldPosition.getZ() + 0.5,
                    remainder
                );
                itemEntity.push(0, 0.1, 0);
                level.addFreshEntity(itemEntity);
            }
        } else {
            getItem(0).shrink(decrementAmount);
        }

        if (decrementAmount > 0) {
            if (doEffects) {
                spawnOrbParticles(orbTargetPos);
            }
            updateInClientWorld();
            setChanged();
        }

        return decrementAmount;
    }

    public void spawnOrbParticles(Vec3 orbTargetPos) {
        ItemStack storedStack = this.getItem(0);
        if (!storedStack.isEmpty() && level != null) {
            InkColor itemColor = ColorRegistry.ITEM_COLORS.getMapping(storedStack.getItem(), InkColors.PURPLE);
            ParticleOptions sparkleRisingParticleEffect = ColoredSparkleRisingParticleEffect
                .of(
                    itemColor.getColorInt()
                );

            if (this.getLevel() instanceof ServerLevel serverWorld) {
                PlayParticleWithRandomOffsetAndVelocityPayload
                    .playParticleWithRandomOffsetAndVelocity(
                        (ServerLevel) level,
                        new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5),
                        sparkleRisingParticleEffect,
                        50,
                        new Vec3(0.4, 0.2, 0.4),
                        new Vec3(0.06, 0.16, 0.06)
                    );

                ColorTransmissionPayload
                    .playColorTransmissionParticle(
                        serverWorld,
                        new ColoredTransmission(
                            new Vec3(
                                this.worldPosition.getX() + 0.5D,
                                this.worldPosition.getY() + 1.0D,
                                this.worldPosition.getZ() + 0.5D
                            ),
                            new ExactPositionSource(orbTargetPos),
                            20,
                            itemColor.getColorInt()
                        )
                    );
            } else if (this.getLevel() instanceof ClientLevel clientWorld) {
                for (
                    int i = 0;
                    i < 50;
                    i++
                ) {
                    float randomOffsetX = worldPosition.getX() + 0.3F + level.random.nextFloat() * 0.6F;
                    float randomOffsetY = worldPosition.getY() + 0.3F + level.random.nextFloat() * 0.6F;
                    float randomOffsetZ = worldPosition.getZ() + 0.3F + level.random.nextFloat() * 0.6F;
                    float randomVelocityX = 0.03F - level.random.nextFloat() * 0.06F;
                    float randomVelocityY = level.random.nextFloat() * 0.16F;
                    float randomVelocityZ = 0.03F - level.random.nextFloat() * 0.06F;

                    clientWorld
                        .addParticle(
                            sparkleRisingParticleEffect,
                            randomOffsetX,
                            randomOffsetY,
                            randomOffsetZ,
                            randomVelocityX,
                            randomVelocityY,
                            randomVelocityZ
                        );
                }

                ParticleOptions sphereParticleEffect = new ColoredTransmissionParticleEffect(
                    new ExactPositionSource(orbTargetPos),
                    20,
                    itemColor.getColorInt()
                );
                clientWorld
                    .addParticle(
                        sphereParticleEffect,
                        this.worldPosition.getX() + 0.5D,
                        this.worldPosition.getY() + 1.0D,
                        this.worldPosition.getZ() + 0.5D,
                        (orbTargetPos.x() - this.worldPosition.getX()) * 0.045,
                        0,
                        (orbTargetPos.z() - this.worldPosition.getZ()) * 0.045
                    );
            }

            level
                .playSound(
                    null,
                    this.worldPosition,
                    PastelSounds.CRAFTING_DING,
                    SoundSource.BLOCKS,
                    PastelCommon.CONFIG.BlockSoundVolume,
                    0.7F + level.random.nextFloat() * 0.6F
                );
        }
    }

    @Override
    public IItemHandler exposeItemHandlers(Direction dir) {
        return inventory;
    }
}
