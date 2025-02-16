package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.injectors.*;
import net.minecraft.item.map.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;
import java.util.function.*;

@Mixin(MapDecoration.class)
public class MapDecorationMixin implements MapDecorationInjector {
	
	@Unique
	private byte scale = 0;
	
	@WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/codec/PacketCodec;tuple(Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lnet/minecraft/network/codec/PacketCodec;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function5;)Lnet/minecraft/network/codec/PacketCodec;"))
	private static PacketCodec<RegistryByteBuf, MapDecoration> wrapCodec(
			PacketCodec<? super RegistryByteBuf, RegistryEntry<MapDecorationType>> codec1, Function<MapDecoration, RegistryEntry<MapDecorationType>> from1,
			PacketCodec<? super RegistryByteBuf, Byte> codec2, Function<MapDecoration, Byte> from2,
			PacketCodec<? super RegistryByteBuf, Byte> codec3, Function<MapDecoration, Byte> from3,
			PacketCodec<? super RegistryByteBuf, Byte> codec4, Function<MapDecoration, Byte> from4,
			PacketCodec<? super RegistryByteBuf, Optional<Text>> codec5, Function<MapDecoration, Optional<Text>> from5,
			Function5<RegistryEntry<MapDecorationType>, Byte, Byte, Byte, Optional<Text>, MapDecoration> _to,
			Operation<PacketCodec<RegistryByteBuf, MapDecoration>> original
	) {
		PacketCodec<RegistryByteBuf, MapDecoration> codec = original.call(codec1, from1, codec2, from2, codec3, from3, codec4, from4, codec5, from5, _to);
		return new PacketCodec<>() {
			@Override
			public void encode(RegistryByteBuf buf, MapDecoration value) {
				codec.encode(buf, value);
				buf.writeByte(value.spectrum$getScale());
			}
			
			@Override
			public MapDecoration decode(RegistryByteBuf buf) {
				MapDecoration decoration = codec.decode(buf);
				decoration.spectrum$setScale(buf.readByte());
				return decoration;
			}
		};
	}
	
	@Override
	public void spectrum$setScale(byte scale) {
		this.scale = scale;
	}
	
	@Override
	public byte spectrum$getScale() {
		return this.scale;
	}
	
}
