package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleItem;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarDispenserBehavior;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.items.tools.PrimordialLighterItem;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

public class PastelDispenserBehaviors {

    public static void register() {
        DispenserBlock.registerBehavior(
            PastelBlocks.BOTTOMLESS_BUNDLE.get(),
            new BottomlessBundleItem.BottomlessBundlePlacementDispenserBehavior()
        );
        DispenserBlock.registerBehavior(PastelItems.BEDROCK_SHEARS.get(), new ShearsDispenseItemBehavior());

        // Shooting Stars
        DispenserBlock.registerBehavior(PastelBlocks.COLORFUL_SHOOTING_STAR.get(), new ShootingStarDispenserBehavior());
        DispenserBlock.registerBehavior(PastelBlocks.FIERY_SHOOTING_STAR.get(), new ShootingStarDispenserBehavior());
        DispenserBlock.registerBehavior(PastelBlocks.GEMSTONE_SHOOTING_STAR.get(), new ShootingStarDispenserBehavior());
        DispenserBlock.registerBehavior(
            PastelBlocks.GLISTERING_SHOOTING_STAR.get(), new ShootingStarDispenserBehavior());
        DispenserBlock.registerBehavior(PastelBlocks.PRISTINE_SHOOTING_STAR.get(), new ShootingStarDispenserBehavior());

        // Fluid Buckets
        DispenseItemBehavior fluidBucketBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);
        DispenserBlock.registerBehavior(PastelItems.HUMUS_BUCKET.get(), fluidBucketBehavior);
        DispenserBlock.registerBehavior(PastelItems.LIQUID_CRYSTAL_BUCKET.get(), fluidBucketBehavior);
        DispenserBlock.registerBehavior(PastelItems.MIDNIGHT_SOLUTION_BUCKET.get(), fluidBucketBehavior);
        DispenserBlock.registerBehavior(PastelItems.DRAGONROT_BUCKET.get(), fluidBucketBehavior);

        // Arrows
        for (GlassArrowVariant variant : PastelRegistries.GLASS_ARROW_VARIANT) {
            DispenserBlock.registerProjectileBehavior(variant.getArrow());
        }

        // Spawn Eggs
        DispenseItemBehavior spawnEggBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.SHEEP_SPAWN_EGG);
        DispenserBlock.registerBehavior(PastelItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(PastelItems.KINDLING_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(PastelItems.LIZARD_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(PastelItems.PRESERVATION_TURRET_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(PastelItems.ERASER_SPAWN_EGG.get(), spawnEggBehavior);

        // Equipping Mob Heads
        DispenseItemBehavior armorEquipBehavior = DispenserBlock.DISPENSER_REGISTRY.get(Items.PLAYER_HEAD);
        for (var skullBlock : PastelBlocks.MOB_HEADS.values()) {
            DispenserBlock.registerBehavior(skullBlock, armorEquipBehavior);
        }

        // Decay
        DispenseItemBehavior blockPlacementDispenserBehavior = new ShulkerBoxDispenseBehavior();

        DispenserBlock.registerBehavior(PastelItems.BOTTLE_OF_FADING.get(), blockPlacementDispenserBehavior);
        DispenserBlock.registerBehavior(PastelItems.BOTTLE_OF_FAILING.get(), blockPlacementDispenserBehavior);
        DispenserBlock.registerBehavior(PastelItems.BOTTLE_OF_RUIN.get(), blockPlacementDispenserBehavior);
        DispenserBlock.registerBehavior(PastelItems.BOTTLE_OF_FORFEITURE.get(), blockPlacementDispenserBehavior);
        DispenserBlock.registerBehavior(PastelItems.BOTTLE_OF_DECAY_AWAY.get(), blockPlacementDispenserBehavior);

        DispenserBlock.registerBehavior(PastelItems.PRIMORDIAL_LIGHTER.get(), PrimordialLighterItem.DISPENSER_BEHAVIOR);

    }

}
