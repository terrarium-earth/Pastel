package earth.terrarium.pastel.api.energy;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.components.InkPoweredComponent;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class InkPoweredStatusEffectInstance {

    public static final Codec<InkPoweredStatusEffectInstance> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    MobEffectInstance.CODEC
                        .fieldOf("effect")
                        .forGetter(c -> c.statusEffectInstance),
                    InkCost.CODEC
                        .fieldOf("ink_cost")
                        .forGetter(c -> c.cost),
                    Codec.INT
                        .optionalFieldOf("custom_color", -1)
                        .forGetter(c -> c.customColor),
                    Codec.BOOL
                        .optionalFieldOf("unidentifiable", false)
                        .forGetter(c -> c.unidentifiable),
                    Codec.BOOL
                        .optionalFieldOf("incurable", false)
                        .forGetter(c -> c.incurable)
                )
                .apply(
                    i,
                    InkPoweredStatusEffectInstance::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, InkPoweredStatusEffectInstance> STREAM_CODEC = StreamCodec
        .composite(
            MobEffectInstance.STREAM_CODEC,
            c -> c.statusEffectInstance,
            InkCost.STREAM_CODEC,
            c -> c.cost,
            ByteBufCodecs.VAR_INT,
            c -> c.customColor,
            ByteBufCodecs.BOOL,
            c -> c.unidentifiable,
            ByteBufCodecs.BOOL,
            c -> c.incurable,
            InkPoweredStatusEffectInstance::new
        );

    public static final String NBT_KEY = "InkPoweredStatusEffects";

    private final MobEffectInstance statusEffectInstance;

    private final InkCost cost;

    private final int customColor; // -1: use effect default

    private final boolean unidentifiable;

    //TODO why can't this use StatusEffectInstance's mixed-in incurable?
    private final boolean incurable;

    public InkPoweredStatusEffectInstance(
        MobEffectInstance statusEffectInstance,
        InkCost cost,
        int customColor,
        boolean unidentifiable,
        boolean incurable
    ) {
        this.statusEffectInstance = statusEffectInstance;
        this.cost = cost;
        this.customColor = customColor;
        this.unidentifiable = unidentifiable;
        this.incurable = incurable;
        if (incurable)
            statusEffectInstance
                .getCures()
                .add(PastelMobEffects.Cures.INCURABLE);
    }

    public MobEffectInstance getStatusEffectInstance() {
        return statusEffectInstance;
    }

    public InkCost getInkCost() {
        return cost;
    }

    public static List<InkPoweredStatusEffectInstance> getEffects(ItemStack stack) {
        return stack
            .getOrDefault(PastelDataComponentTypes.INK_POWERED, InkPoweredComponent.DEFAULT)
            .effects();
    }

    public static void setEffects(ItemStack stack, List<InkPoweredStatusEffectInstance> effects) {
        stack.set(PastelDataComponentTypes.INK_POWERED, new InkPoweredComponent(effects));
    }

    public static void buildTooltip(
        List<Component> tooltip,
        List<InkPoweredStatusEffectInstance> effects,
        MutableComponent attributeModifierText,
        boolean showDuration,
        float tickRate
    ) {
        if (!effects.isEmpty()) {
            List<Tuple<Holder<Attribute>, AttributeModifier>> attributeModifiers = Lists.newArrayList();
            for (
                InkPoweredStatusEffectInstance entry : effects
            ) {
                if (entry.isUnidentifiable()) {
                    tooltip.add(Component.translatable("item.pastel.potion.tooltip.unidentifiable"));
                    continue;
                }

                MobEffectInstance effect = entry.getStatusEffectInstance();
                if (effect == null) { // serialization error or removed effect
                    continue;
                }

                InkCost cost = entry.getInkCost();

                if (effect == null) {
                    tooltip.add(Component.translatable("item.pastel.potion.tooltip.invalid"));
                    continue;
                }
                MutableComponent mutableText = Component.translatable(effect.getDescriptionId());
                if (effect.getAmplifier() > 0) {
                    mutableText = Component
                        .translatable(
                            "potion.withAmplifier",
                            mutableText,
                            Component
                                .translatable(
                                    "potion.potency." + effect.getAmplifier()
                                )
                        );
                }
                if (showDuration && effect.getDuration() > 20) {
                    mutableText = Component
                        .translatable(
                            "potion.withDuration",
                            mutableText,
                            MobEffectUtil.formatDuration(effect, 1.0F, tickRate)
                        );
                }
                mutableText
                    .withStyle(
                        effect
                            .getEffect()
                            .value()
                            .getCategory()
                            .getTooltipFormatting()
                    );
                mutableText
                    .append(
                        Component
                            .translatable(
                                "pastel.tooltip.ink_cost",
                                Support.getShortenedNumberString(cost.cost()),
                                cost
                                    .color()
                                    .getColoredInkName()
                            )
                            .withStyle(ChatFormatting.GRAY)
                    );
                if (entry.isIncurable()) {
                    mutableText.append(Component.translatable("item.pastel.potion.tooltip.incurable"));
                }
                tooltip.add(mutableText);

                effect
                    .getEffect()
                    .value()
                    .createModifiers(
                        effect.getAmplifier(),
                        (attribute, modifier) -> attributeModifiers.add(new Tuple<>(attribute, modifier))
                    );
            }

            if (!attributeModifiers.isEmpty()) {
                tooltip.add(Component.empty());
                tooltip.add(attributeModifierText.withStyle(ChatFormatting.DARK_PURPLE));

                for (
                    var pair : attributeModifiers
                ) {
                    var translatedAttribute = Component
                        .translatable(
                            pair
                                .getA()
                                .value()
                                .getDescriptionId()
                        );
                    var mutableText = pair.getB();

                    double statusEffect = mutableText.amount();
                    double d;
                    if (mutableText.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && mutableText
                        .operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                        d = mutableText.amount();
                    } else {
                        d = mutableText.amount() * 100.0D;
                    }

                    if (statusEffect > 0.0D) {
                        tooltip
                            .add(
                                (Component
                                    .translatable(
                                        "attribute.modifier.plus." + mutableText
                                            .operation()
                                            .id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d),
                                        translatedAttribute
                                    )).withStyle(ChatFormatting.BLUE)
                            );
                    } else if (statusEffect < 0.0D) {
                        d *= -1.0D;
                        tooltip
                            .add(
                                (Component
                                    .translatable(
                                        "attribute.modifier.take." + mutableText
                                            .operation()
                                            .id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d),
                                        translatedAttribute
                                    )).withStyle(ChatFormatting.RED)
                            );
                    }
                }
            }
        }
    }

    public int getColor() {
        if (this.customColor == -1) {
            return statusEffectInstance
                .getEffect()
                .value()
                .getColor();
        }
        return this.customColor;
    }

    public boolean isUnidentifiable() {
        return this.unidentifiable;
    }

    public boolean isIncurable() {
        return this.incurable;
    }
}
