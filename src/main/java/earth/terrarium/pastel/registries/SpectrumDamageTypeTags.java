package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class SpectrumDamageTypeTags {
	
	public static final TagKey<DamageType> DROPS_LOOT_LIKE_PLAYERS = of("drops_loot_like_players");
	public static final TagKey<DamageType> USES_SET_HEALTH = of("uses_set_health");
	public static final TagKey<DamageType> BYPASSES_DIKE = of("bypasses_dike");
	public static final TagKey<DamageType> BYPASSES_PARRYING = of("bypasses_dike");
	public static final TagKey<DamageType> INCREASED_ARMOR_DAMAGE = of("increased_armor_damage");
	public static final TagKey<DamageType> DOES_NOT_DAMAGE_ARMOR = of("does_not_damage_armor");
	public static final TagKey<DamageType> CALCULATES_DAMAGE_BASED_ON_TOUGHNESS = of("calculates_damage_based_on_toughness");
	public static final TagKey<DamageType> PARTLY_IGNORES_PROTECTION = of("partly_ignores_protection");
	public static final TagKey<DamageType> ALWAYS_DROPS_MOB_HEAD = of("always_drops_mob_head");
	
	private static TagKey<DamageType> of(String id) {
		return TagKey.create(Registries.DAMAGE_TYPE, SpectrumCommon.locate(id));
	}
}
