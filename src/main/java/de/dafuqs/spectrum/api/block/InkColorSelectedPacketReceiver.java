package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.api.energy.color.*;
import net.minecraft.block.entity.*;
import net.minecraft.registry.entry.*;

import java.util.*;

public interface InkColorSelectedPacketReceiver {
	
	void onInkColorSelectedPacket(Optional<RegistryEntry<InkColor>> inkColor);
	
	BlockEntity getBlockEntity();
	
}
