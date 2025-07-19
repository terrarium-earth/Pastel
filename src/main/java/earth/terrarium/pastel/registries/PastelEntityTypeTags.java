package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class PastelEntityTypeTags {
	
	public static final TagKey<EntityType<?>> POKING_DAMAGE_IMMUNE = getReference("poking_damage_immune");
	public static final TagKey<EntityType<?>> PRIMORDIAL_FIRE_IMMUNE = getReference("primordial_fire_immune");
	public static final TagKey<EntityType<?>> SOULLESS = getReference("soulless");
	public static final TagKey<EntityType<?>> DRACONIC = getReference("draconic");

	public static final TagKey<EntityType<?>> SLEEP_WEAK = getReference("sleep_weak");
	public static final TagKey<EntityType<?>> SLEEP_RESISTANT = getReference("sleep_resistant");
	public static final TagKey<EntityType<?>> SLEEP_IMMUNEISH = getReference("sleep_immuneish");
	public static final TagKey<EntityType<?>> UNDEFLECTABLE = getReference("undeflectable");


	public static final TagKey<EntityType<?>> STAFF_OF_REMEMBRANCE_BLACKLISTED = getReference("staff_of_remembrance_blacklisted");
	public static final TagKey<EntityType<?>> SPAWNER_MANIPULATION_BLACKLISTED = getReference("spawner_manipulation_blacklisted");
	public static final TagKey<EntityType<?>> EVERPROMISE_RIBBON_BLACKLISTED = getReference("everpromise_ribbon_blacklisted");
	
	
	private static TagKey<EntityType<?>> getReference(String id) {
		return TagKey.create(Registries.ENTITY_TYPE, PastelCommon.locate(id));
	}
	
}
