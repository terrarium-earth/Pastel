package earth.terrarium.pastel.mixin;

import com.klikli_dev.modonomicon.book.conditions.BookCategoryHasVisibleEntriesCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BookCategoryHasVisibleEntriesCondition.class)
public class FixModonomiconMixin {
	
	// Legend has it that modonomicon is a good mod
	@ModifyArg(method = "fromJson", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"))
	private static Object[] componentfy(Object[] args, @Local(ordinal = 1) ResourceLocation categoryId) {
		return new String[]{categoryId.toLanguageKey()};
	}
}
