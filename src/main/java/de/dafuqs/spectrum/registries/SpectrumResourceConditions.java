package de.dafuqs.spectrum.registries;

import java.util.*;

import static de.dafuqs.spectrum.SpectrumCommon.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.compat.*;
import net.fabricmc.fabric.api.resource.conditions.v1.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.*;
import org.jetbrains.annotations.*;

public class SpectrumResourceConditions {
	
	public static void register() {
		ResourceConditions.register(EnchantmentsExistResourceCondition.TYPE);
		ResourceConditions.register(IntegrationPackActiveResourceCondition.TYPE);
	}
	
	public record EnchantmentsExistResourceCondition(List<RegistryKey<Enchantment>> enchantments) implements ResourceCondition {
		
		public static MapCodec<EnchantmentsExistResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
				RegistryKey.createCodec(RegistryKeys.ENCHANTMENT).listOf().fieldOf("values").forGetter(EnchantmentsExistResourceCondition::enchantments)
		).apply(instance, EnchantmentsExistResourceCondition::new));
		public static ResourceConditionType<EnchantmentsExistResourceCondition> TYPE = ResourceConditionType.create(locate("enchantments_exist"), CODEC);
		
		@Override
		public ResourceConditionType<?> getType() {
			return TYPE;
		}
		
		@Override
		public boolean test(@Nullable RegistryWrapper.WrapperLookup wrapperLookup) {
			if (wrapperLookup == null || wrapperLookup.getOptionalWrapper(RegistryKeys.ENCHANTMENT).isEmpty())
				return false;
			RegistryWrapper.Impl<Enchantment> impl = wrapperLookup.getOptionalWrapper(RegistryKeys.ENCHANTMENT).get();
			return enchantments.stream().allMatch(key -> impl.getOptional(key).isPresent());
		}
	}
	
	public record IntegrationPackActiveResourceCondition(String integrationPack) implements ResourceCondition {
		
		public static MapCodec<IntegrationPackActiveResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
				Codec.STRING.fieldOf("integration_pack").forGetter(IntegrationPackActiveResourceCondition::integrationPack)
		).apply(instance, IntegrationPackActiveResourceCondition::new));
		public static ResourceConditionType<IntegrationPackActiveResourceCondition> TYPE = ResourceConditionType.create(locate("integration_pack_active"), CODEC);
		
		@Override
		public ResourceConditionType<?> getType() {
			return TYPE;
		}
		
		@Override
		public boolean test(@Nullable RegistryWrapper.WrapperLookup wrapperLookup) {
			return SpectrumIntegrationPacks.isIntegrationPackActive(integrationPack);
		}
	}
	
}
