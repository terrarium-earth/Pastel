package earth.terrarium.pastel.compat.modonomicon.unlock_conditions;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.context.BookConditionContext;
import com.klikli_dev.modonomicon.book.conditions.context.BookConditionEntryContext;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconCompat;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class EnchantmentRegisteredCondition extends BookCondition {
    
    protected static final String TOOLTIP = "book.condition.tooltip." + PastelCommon.MOD_ID + ".enchantment_registered";
    
    protected ResourceKey<Enchantment> enchantmentKey;
    
    public EnchantmentRegisteredCondition(Component tooltip, ResourceKey<Enchantment> enchantmentKey) {
        super(tooltip);
        this.enchantmentKey = enchantmentKey;
    }
    
    public static EnchantmentRegisteredCondition fromJson(ResourceLocation conditionParentId, JsonObject json, HolderLookup.Provider provider) {
        ResourceLocation enchantmentID = ResourceLocation.parse(GsonHelper.getAsString(json, "enchantment_id"));
        Component tooltip = tooltipFromJson(json, provider);
        return new EnchantmentRegisteredCondition(tooltip, ResourceKey.create(Registries.ENCHANTMENT, enchantmentID));
    }
    
    public static EnchantmentRegisteredCondition fromNetwork(RegistryFriendlyByteBuf buffer) {
        var tooltip = buffer.readBoolean() ? ComponentSerialization.STREAM_CODEC.decode(buffer) : null;
        var entryId = buffer.readResourceLocation();
        return new EnchantmentRegisteredCondition(tooltip, ResourceKey.create(Registries.ENCHANTMENT, entryId));
    }
    
    @Override
    public ResourceLocation getType() {
        return ModonomiconCompat.ENCHANTMENT_REGISTERED;
    }
    
    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(this.tooltip != null);
        if (this.tooltip != null) {
            ComponentSerialization.STREAM_CODEC.encode(buffer, this.tooltip);
        }
        buffer.writeResourceLocation(this.enchantmentKey.location());
    }
    
    @Override
    public boolean test(BookConditionContext context, Player player) {
		return PastelEnchantmentHelper.getEntry(player.level().registryAccess(), this.enchantmentKey).isPresent();
    }
    
    @Override
    public List<Component> getTooltip(Player player, BookConditionContext context) {
        if (this.tooltip == null && context instanceof BookConditionEntryContext entryContext) {
            this.tooltip = Component.translatable(TOOLTIP, Component.translatable(entryContext.getBook().getEntry(this.enchantmentKey.location()).getName()));
        }
        return super.getTooltip(player, context);
    }
}
