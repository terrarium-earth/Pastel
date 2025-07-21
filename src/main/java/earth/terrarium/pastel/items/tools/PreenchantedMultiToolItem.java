package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.registries.PastelBannerPatterns;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;
import java.util.Map;

public class PreenchantedMultiToolItem extends MultiToolItem implements Preenchanted, LoomPatternProvider {

    public PreenchantedMultiToolItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
        return Map.of(Enchantments.EFFICIENCY, 1);
    }

    @Override
    public ResourceKey<BannerPattern> getPattern() {
        return PastelBannerPatterns.MULTITOOL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        addBannerPatternProviderTooltip(tooltip);
    }

}
