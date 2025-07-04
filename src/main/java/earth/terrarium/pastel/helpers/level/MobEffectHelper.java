package earth.terrarium.pastel.helpers.level;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public class MobEffectHelper {
	
	public enum RenderType {
		GUI_LARGE,
		GUI_SMALL,
		HUD_DEFAULT,
		HUD_AMBIENT
	}
	
	public record StatusEffectBackground(ResourceLocation guiLarge, ResourceLocation guiSmall, ResourceLocation hudDefault, ResourceLocation hudAmbient) {
		
		public StatusEffectBackground(String name) {
			this(PastelCommon.locate("container/inventory/" + name + "_effect_background_gui_large"),
					PastelCommon.locate("container/inventory/" + name + "_effect_background_gui_small"),
					PastelCommon.locate("hud/" + name + "_effect_background_hud_default"),
					PastelCommon.locate("hud/" + name + "_effect_background_hud_ambient"));
		}
		
		public ResourceLocation get(RenderType type) {
			return switch (type) {
				case GUI_LARGE -> guiLarge;
				case GUI_SMALL -> guiSmall;
				case HUD_DEFAULT -> hudDefault;
				case HUD_AMBIENT -> hudAmbient;
			};
		}
	}
	
	private static final StatusEffectBackground DIVINITY = new StatusEffectBackground("divinity");
	private static final StatusEffectBackground INCURABLE = new StatusEffectBackground("incurable");
	private static final StatusEffectBackground NIGHT_ALCHEMY = new StatusEffectBackground("night_alchemy");
	
	public static ResourceLocation getTextureLocation(ResourceLocation original, MobEffectInstance effect, RenderType renderType) {
		var type = effect.getEffect();
		
		if (type == PastelMobEffects.DIVINITY)
			return DIVINITY.get(renderType);
		
		if (resistsRemoval(effect)) {
			return INCURABLE.get(renderType);
		}
		
		if (type.is(PastelMobEffectTags.NIGHT_ALCHEMY))
			return NIGHT_ALCHEMY.get(renderType);
		
		return original;
	}

	public static boolean resistsRemoval(MobEffectInstance instance) {
		var type = instance.getEffect();

		if (type.is(PastelMobEffectTags.CANNOT_BE_INCURABLE))
			return false; // We are merciful
		
		return instance.getCures().contains(PastelMobEffects.Cures.INCURABLE);
	}

	/**
	 * @return Whether immunity should be removed outright
	 */
	public static boolean drainImmunity(MobEffectInstance immunity, float multiplier) {
		var cost = Math.round(600 * multiplier);
		if (immunity.getDuration() <= cost) {
			return true;
		}

		((MobEffectInstanceInjector) immunity).setDuration(Math.max(5, immunity.getDuration() - cost));
		return false;
	}

	/**
	 * @param ignoreIncurable Whether to attempt removing incurable effects
	 */
	public static void actionImmunity(LivingEntity entity, boolean ignoreIncurable) {
		var removals = new ArrayList<Holder<MobEffect>>();
		var immunity = entity.getEffect(PastelMobEffects.IMMUNITY);

		assert immunity != null;
		for (MobEffectInstance proposal : entity.getActiveEffects()) {
			if (proposal.getEffect().is(PastelMobEffectTags.BYPASSES_IMMUNITY))
				continue;

			if (ignoreIncurable && resistsRemoval(proposal))
				continue;

			removals.add(proposal.getEffect());
		}

		removals.forEach(entity::removeEffect);
	}
}
