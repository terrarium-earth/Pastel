package de.dafuqs.revelationary.api.advancements;

import de.dafuqs.revelationary.*;
import de.dafuqs.revelationary.advancement_criteria.AdvancementCountCriterion;
import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import de.dafuqs.revelationary.advancement_criteria.HadRevelationCriterion;
import net.minecraft.advancements.*;
import net.minecraft.core.registries.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class AdvancementCriteria {
	/**
	 * Triggered every time a player gets a new advancement
	 */
	public static final AdvancementGottenCriterion ADVANCEMENT_GOTTEN = new AdvancementGottenCriterion();
	/**
	 * Triggered every time a player gets a new advancement
	 * matches multiple advancements with optional count parameter
	 */
	public static final AdvancementCountCriterion ADVANCEMENT_COUNT = new AdvancementCountCriterion();

	public static void register(IEventBus bus) {
		var registry = DeferredRegister.create(Registries.TRIGGER_TYPE, Revelationary.MOD_ID);

		registry.register("advancement_count", () -> ADVANCEMENT_COUNT);
		registry.register("advancement_gotten", () -> ADVANCEMENT_GOTTEN);

		registry.register(bus);
	}
}