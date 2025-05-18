package de.dafuqs.spectrum.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record InertiaComponent(Block lastMined, long count) {
	
	public static final InertiaComponent DEFAULT = new InertiaComponent(Blocks.AIR, 0);
	
	public static final Codec<InertiaComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("last_mined").forGetter(InertiaComponent::lastMined),
			Codec.LONG.fieldOf("count").forGetter(InertiaComponent::count)
	).apply(i, InertiaComponent::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, InertiaComponent> PACKET_CODEC = StreamCodec.composite(
			ByteBufCodecs.registry(Registries.BLOCK),
			InertiaComponent::lastMined,
			ByteBufCodecs.VAR_LONG,
			InertiaComponent::count,
			InertiaComponent::new
	);
	
}
