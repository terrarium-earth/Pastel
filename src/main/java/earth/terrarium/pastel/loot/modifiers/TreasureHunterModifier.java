package earth.terrarium.pastel.loot.modifiers;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.entity.LizardEntity;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class TreasureHunterModifier extends LootModifier {

    private static final Map<Holder<EntityType<?>>, HeadProvider> SPECIAL_CASES = new HashMap();

    public static final MapCodec<TreasureHunterModifier> CODEC = RecordCodecBuilder.mapCodec(i ->
            LootModifier.codecStart(i).and(i.group(
                    TagKey.codec(Registries.ENTITY_TYPE).fieldOf("special")
                            .forGetter(m -> m.special),
                    TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("exempt")
                            .forGetter(m -> m.exempt),
                    Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance),
                    Codec.FLOAT.fieldOf("special_chance").forGetter(m -> m.specialChance)
                    )
            ).apply(i, TreasureHunterModifier::new));

    private final float chance;
    private final float specialChance;
    private final TagKey<EntityType<?>> special;
    private final Optional<TagKey<EntityType<?>>> exempt;

    protected TreasureHunterModifier(LootItemCondition[] conditionsIn, TagKey<EntityType<?>> special, Optional<TagKey<EntityType<?>>> exempt, float chance, float specialChance) {
        super(conditionsIn);
        this.chance = chance;
        this.specialChance = specialChance;
        this.special = special;
        this.exempt = exempt;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> original, LootContext lootContext) {
        var target = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        float scalingChance = chance;

        if (target == null)
            return original;

        if (exempt.map(e -> target.getType().is(e)).orElse(false))
            return original;

        if (target.getType().is(special))
            scalingChance = specialChance;

        var type = lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE);

        if (type == null)
            return original;

        var weapon = type.getWeaponItem();

        if (weapon == null)
            return original;

        var level = Ench.getLevel(lootContext.getLevel().registryAccess(), PastelEnchantments.TREASURE_HUNTER, weapon);
        var head = tryGetHead(target);

        if (!shouldDrop(head, scalingChance, level, lootContext.getRandom()))
            return original;

        assert head.isPresent();
        original.add(new ItemStack(head.get()));
        return original;
    }

    public static Optional<Item> tryGetHead(Entity target) {
        var typeHolder = target.getType().builtInRegistryHolder();

        if (SPECIAL_CASES.containsKey(typeHolder))
            return Optional.of(SPECIAL_CASES.get(typeHolder).get(target).asItem());

        var rawId = typeHolder.key().location().toString();
        var head = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(rawId + "_head"));

        if (head instanceof AbstractSkullBlock)
            return Optional.of(head.asItem());

        head = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(rawId + "_skull"));

        if (head instanceof AbstractSkullBlock)
            return Optional.of(head.asItem());

        return Optional.empty();
    }

    private boolean shouldDrop(Optional<Item> head, float chance, int level, RandomSource random) {
        if (head.isEmpty())
            return false;

        return random.nextFloat() <= chance * level;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    static {
        SPECIAL_CASES.put(EntityType.FOX.builtInRegistryHolder(), (entity) ->
                switch (((Fox) entity).getVariant())
                {
                    case RED -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.FOX);
                    case SNOW -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.FOX_ARCTIC);
                });

        SPECIAL_CASES.put(EntityType.MOOSHROOM.builtInRegistryHolder(), (entity) ->
                switch (((MushroomCow) entity).getVariant())
                {
                    case RED -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.MOOSHROOM_RED);
                    case BROWN -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.MOOSHROOM_BROWN);
                });

        SPECIAL_CASES.put(EntityType.AXOLOTL.builtInRegistryHolder(), (entity) ->
                switch (((Axolotl) entity).getVariant())
                {
                    case LUCY -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.AXOLOTL_LEUCISTIC);
                    case WILD -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.AXOLOTL_WILD);
                    case GOLD -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.AXOLOTL_GOLD);
                    case CYAN -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.AXOLOTL_CYAN);
                    case BLUE -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.AXOLOTL_BLUE);
                });

        SPECIAL_CASES.put(EntityType.PARROT.builtInRegistryHolder(), (entity) ->
                switch (((Parrot) entity).getVariant())
                {
                    case RED_BLUE -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.PARROT_RED);
                    case BLUE -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.PARROT_BLUE);
                    case GREEN -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.PARROT_GREEN);
                    case YELLOW_BLUE -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.PARROT_CYAN);
                    case GRAY -> PastelSkullBlock.MOB_HEADS.get(PastelSkullType.PARROT_GRAY);
                });

        SPECIAL_CASES.put(EntityType.FROG.builtInRegistryHolder(), (entity) -> {
            var variant = ((Frog) entity).getVariant();

            if (variant == FrogVariant.WARM)
                return PastelSkullBlock.MOB_HEADS.get(PastelSkullType.FROG_COLD);
            else if (variant == FrogVariant.COLD)
                return PastelSkullBlock.MOB_HEADS.get(PastelSkullType.FROG_WARM);
            else
                return PastelSkullBlock.MOB_HEADS.get(PastelSkullType.FROG_TEMPERATE);
        });

        SPECIAL_CASES.put(EntityType.SHULKER.builtInRegistryHolder(), entity -> {
            var color = ((Shulker) entity).getVariant();
            var type = PastelSkullType.SHULKER;

            if (color.isEmpty())
                return PastelSkullBlock.MOB_HEADS.get(type);

            type = switch (color.get()) {
                case WHITE -> PastelSkullType.SHULKER_WHITE;
                case ORANGE -> PastelSkullType.SHULKER_ORANGE;
                case MAGENTA -> PastelSkullType.SHULKER_MAGENTA;
                case LIGHT_BLUE -> PastelSkullType.SHULKER_LIGHT_BLUE;
                case YELLOW -> PastelSkullType.SHULKER_YELLOW;
                case LIME -> PastelSkullType.SHULKER_LIME;
                case PINK -> PastelSkullType.SHULKER_PINK;
                case GRAY -> PastelSkullType.SHULKER_GRAY;
                case LIGHT_GRAY -> PastelSkullType.SHULKER_LIGHT_GRAY;
                case CYAN -> PastelSkullType.SHULKER_CYAN;
                case PURPLE -> PastelSkullType.SHULKER_PURPLE;
                case BLUE -> PastelSkullType.SHULKER_BLUE;
                case BROWN -> PastelSkullType.SHULKER_BROWN;
                case GREEN -> PastelSkullType.SHULKER_GREEN;
                case RED -> PastelSkullType.SHULKER_RED;
                case BLACK -> PastelSkullType.SHULKER_BLACK;
            };
            return PastelSkullBlock.MOB_HEADS.get(type);
        });

        SPECIAL_CASES.put(PastelEntityTypes.LIZARD, entity -> {
            var color = ((LizardEntity) entity).getColor();
            var type = PastelSkullType.LIZARD_WHITE;

            type = switch (color.getDyeColor().get()) {
                case WHITE -> PastelSkullType.LIZARD_WHITE;
                case ORANGE -> PastelSkullType.LIZARD_ORANGE;
                case MAGENTA -> PastelSkullType.LIZARD_MAGENTA;
                case LIGHT_BLUE -> PastelSkullType.LIZARD_LIGHT_BLUE;
                case YELLOW -> PastelSkullType.LIZARD_YELLOW;
                case LIME -> PastelSkullType.LIZARD_LIME;
                case PINK -> PastelSkullType.LIZARD_PINK;
                case GRAY -> PastelSkullType.LIZARD_GRAY;
                case LIGHT_GRAY -> PastelSkullType.LIZARD_LIGHT_GRAY;
                case CYAN -> PastelSkullType.LIZARD_CYAN;
                case PURPLE -> PastelSkullType.LIZARD_PURPLE;
                case BLUE -> PastelSkullType.LIZARD_BLUE;
                case BROWN -> PastelSkullType.LIZARD_BROWN;
                case GREEN -> PastelSkullType.LIZARD_GREEN;
                case RED -> PastelSkullType.LIZARD_RED;
                case BLACK -> PastelSkullType.LIZARD_BLACK;
            };
            return PastelSkullBlock.MOB_HEADS.get(type);
        });
    }

    @FunctionalInterface
    private interface HeadProvider {
        Block get(Entity entity);
    }
}
