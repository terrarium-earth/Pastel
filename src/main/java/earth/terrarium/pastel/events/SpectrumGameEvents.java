package earth.terrarium.pastel.events;

import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumGameEvents {

	private static final DeferredRegister<GameEvent> REGISTER = DeferredRegister.create(Registries.GAME_EVENT, SpectrumCommon.MOD_ID);

	public static Holder<GameEvent> ENTITY_SPAWNED;
	public static Holder<GameEvent> BLOCK_CHANGED;

	public static Holder<GameEvent> HUMMINGSTONE_HUMMING;
	public static Holder<GameEvent> HUMMINGSTONE_HYMN;

	public static Holder<GameEvent> WIRELESS_REDSTONE_SIGNAL;

	public static void register(IEventBus bus) {
		ENTITY_SPAWNED = register("entity_spawned", 16);
		BLOCK_CHANGED = register("block_changed", 16);
		
		HUMMINGSTONE_HUMMING = register("hummingstone_humming", 16);
		HUMMINGSTONE_HYMN = register("hummingstone_hymn", 16);
		
		WIRELESS_REDSTONE_SIGNAL = register("wireless_redstone_signal", 16);
		REGISTER.register(bus);

	}
	
	private static Holder<GameEvent> register(String id, int range) {
		return REGISTER.register(id, () ->new GameEvent(range));
	}
	
}