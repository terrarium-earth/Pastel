package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.blocks.decay.BlackMateriaBlock;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;
import earth.terrarium.pastel.registries.PastelBiomes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelFluidTags;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class MidnightSolutionFluid extends PastelFluid {

    @Override
    public Fluid getSource() {
        return PastelFluids.MIDNIGHT_SOLUTION.get();
    }

    @Override
    public Fluid getFlowing() {
        return PastelFluids.FLOWING_MIDNIGHT_SOLUTION.get();
    }

    @Override
    public Item getBucket() {
        return PastelItems.MIDNIGHT_SOLUTION_BUCKET.get();
    }

    @Override
    public FluidType getFluidType() {
        return PastelFluids.MIDNIGHT_SOLUTION_TYPE.get();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return PastelBlocks.MIDNIGHT_SOLUTION
            .get()
            .defaultBlockState()
            .setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == PastelFluids.MIDNIGHT_SOLUTION.get() || fluid == PastelFluids.FLOWING_MIDNIGHT_SOLUTION.get();
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        BlockPos topPos = pos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.isAir() && !topState.isSolidRender(world, topPos) && random.nextInt(2000) == 0) {
            world
                .playLocalSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    PastelSounds.MIDNIGHT_SOLUTION_AMBIENT,
                    SoundSource.BLOCKS,
                    0.2F + random.nextFloat() * 0.2F,
                    0.9F + random.nextFloat() * 0.15F,
                    false
                );
        }
    }

    @Override
    protected int getSlopeFindDistance(LevelReader worldView) {
        return 5;
    }

    @Override
    public void tick(Level world, BlockPos pos, FluidState state) {
        super.tick(world, pos, state);

        if (state.getOwnHeight() < 1.0) {
            for (
                Direction direction : Direction.values()
            ) {
                if (MidnightSolutionFluidBlock.tryConvertNeighbor(world, pos.relative(direction))) {
                    break;
                }
            }
        }

        boolean converted = BlackMateriaBlock
            .spreadBlackMateria(
                world,
                pos,
                world.random,
                MidnightSolutionFluidBlock.SPREAD_BLOCKSTATE
            );
        if (converted) {
            world.scheduleTick(pos, state.getType(), 400 + world.random.nextInt(800));
        }
    }

    @Override
    public int getTickDelay(LevelReader worldView) {
        return 12;
    }

    @Override
    public ParticleOptions getDripParticle() {
        return PastelParticleTypes.DRIPPING_MIDNIGHT_SOLUTION;
    }

    @Override
    public ParticleOptions getSplashParticle() {
        return PastelParticleTypes.MIDNIGHT_SOLUTION_SPLASH;
    }

    @Override
    public void onEntityCollision(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, level, pos, entity);

        if (!level.isClientSide) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isDeadOrDying() && level.getGameTime() % 20 == 0) {
                    var damageMult = 1F;

                    if (level
                        .getBiome(pos)
                        .is(PastelBiomes.BLACK_LANGAST))
                        damageMult = 9F;

                    if (livingEntity.isEyeInFluid(PastelFluidTags.MIDNIGHT_SOLUTION)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 0));
                        livingEntity.hurt(PastelDamageTypes.midnightSolution(level), 2 * damageMult);
                    } else {
                        livingEntity.hurt(PastelDamageTypes.midnightSolution(level), damageMult);
                    }
                    if (livingEntity.isDeadOrDying()) {
                        livingEntity
                            .spawnAtLocation(
                                PastelItems.MIDNIGHT_CHIP
                                    .get()
                                    .getDefaultInstance()
                            );
                    }
                }
            } else if (entity instanceof ItemEntity itemEntity && !itemEntity.hasPickUpDelay()) {
                if (level.random.nextInt(120) == 0) {
                    disenchantItemAndSpawnXP(level, itemEntity);
                }
            }
        }
    }

    @Override
    public RecipeType<? extends FluidConvertingRecipe> getDippingRecipeType() {
        return PastelRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING;
    }

    private static final int EXPERIENCE_DISENCHANT_RETURN_DIV = 3;

    private static void disenchantItemAndSpawnXP(Level world, ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        // if the item is enchanted: remove enchantments and spawn XP
        // basically disenchanting the item
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
        if (!enchantments.isEmpty()) {
            int randomEnchantmentIndex = world.random.nextInt(enchantments.size());
            Object2IntMap.Entry<Holder<Enchantment>> entry = enchantments
                .entrySet()
                .stream()
                .toList()
                .get(randomEnchantmentIndex);
            Tuple<ItemStack, Integer> result = Ench.removeEnchantments(itemStack, entry.getKey());

            if (result.getB() > 0) {
                spawnXP(
                    world,
                    itemEntity,
                    Ench.getEnchantmentCost(entry.getKey(), entry.getIntValue(), itemStack.getEnchantmentValue())
                );
                itemEntity.setItem(result.getA());
                itemEntity.setDefaultPickUpDelay();
            }
        } else if (itemStack.is(PastelItems.ENCHANTMENT_CANVAS.get()) && itemStack
            .has(
                PastelDataComponentTypes.CANVAS_ENCHANTMENTS
            )) {
                ItemEnchantments canvas = itemStack.get(PastelDataComponentTypes.CANVAS_ENCHANTMENTS);
                Item boundItem = BuiltInRegistries.ITEM.get(itemStack.get(PastelDataComponentTypes.BOUND_ITEM));

                if (!canvas.isEmpty()) {
                    int randomEnchantmentIndex = world.random.nextInt(canvas.size());
                    Object2IntMap.Entry<Holder<Enchantment>> entry = canvas
                        .entrySet()
                        .stream()
                        .toList()
                        .get(randomEnchantmentIndex);

                    var builder = new ItemEnchantments.Mutable(canvas);
                    builder.set(entry.getKey(), 0);

                    spawnXP(world, itemEntity, Ench.getEnchantmentCost(entry.getKey(), entry.getIntValue(), 1));

                    ItemEnchantments targetEnchants = builder.toImmutable();
                    if (targetEnchants.isEmpty()) {
                        itemStack.remove(PastelDataComponentTypes.CANVAS_ENCHANTMENTS);
                        itemStack.remove(PastelDataComponentTypes.BOUND_ITEM);
                    } else {
                        itemStack.set(PastelDataComponentTypes.CANVAS_ENCHANTMENTS, targetEnchants);
                    }
                    itemEntity.setDefaultPickUpDelay();
                }
            }
    }

    private static void spawnXP(Level world, ItemEntity itemEntity, int exp) {
        exp /= EXPERIENCE_DISENCHANT_RETURN_DIV;
        if (exp > 0) {
            ExperienceOrb experienceOrbEntity = new ExperienceOrb(
                world,
                itemEntity.getX(),
                itemEntity.getY(),
                itemEntity.getZ(),
                exp
            );
            world.addFreshEntity(experienceOrbEntity);
        }
        world
            .playSound(
                null,
                itemEntity.blockPosition(),
                SoundEvents.GRINDSTONE_USE,
                SoundSource.NEUTRAL,
                1.0F,
                0.9F + world
                    .getRandom()
                    .nextFloat() * 0.2F
            );
        PlayParticleWithRandomOffsetAndVelocityPayload
            .playParticleWithRandomOffsetAndVelocity(
                (ServerLevel) world,
                itemEntity.position(),
                ColoredSparkleRisingParticleEffect.GRAY,
                10,
                Vec3.ZERO,
                new Vec3(0.2, 0.4, 0.2)
            );
    }

    public static class Flowing extends MidnightSolutionFluid {

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }

    }

    public static class Still extends MidnightSolutionFluid {

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

    }
}
