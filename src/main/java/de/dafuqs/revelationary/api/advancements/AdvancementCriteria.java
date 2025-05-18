package de.dafuqs.revelationary.api.advancements;

import de.dafuqs.revelationary.advancement_criteria.AdvancementCountCriterion;
import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import de.dafuqs.revelationary.advancement_criteria.HadRevelationCriterion;
import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementCriteria {
	/**
	 * Triggered every time a player gets a new advancement
	 */
	public static AdvancementGottenCriterion ADVANCEMENT_GOTTEN;
	/**
	 * Triggered every time a player gets a new advancement
	 * matches multiple advancements with optional count parameter
	 */
	public static AdvancementCountCriterion ADVANCEMENT_COUNT;
	/**
	 * Triggers every time a new block is revealed
	 */
	public static HadRevelationCriterion HAD_REVELATION;
	
	public static void register() {
		ADVANCEMENT_COUNT = CriteriaTriggers.register("revelationary:advancement_count", new AdvancementCountCriterion());
		HAD_REVELATION = CriteriaTriggers.register("revelationary:had_revelation", new HadRevelationCriterion());
		ADVANCEMENT_GOTTEN = CriteriaTriggers.register("revelationary:advancement_gotten", new AdvancementGottenCriterion());
	}
}