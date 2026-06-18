package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.PlacedItemBlockEntity;
import earth.terrarium.pastel.blocks.TeaTableBlockEntity;
import earth.terrarium.pastel.blocks.TeaTableBlockEntityRenderer;
import earth.terrarium.pastel.blocks.amphora.AmphoraBlockEntity;
import earth.terrarium.pastel.blocks.block_flooder.BlockFlooderBlockEntity;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlockEntity;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.PastelChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlockEntity;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlockEntity;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlockEntityRenderer;
import earth.terrarium.pastel.blocks.decoration.ProjectorBlockEntity;
import earth.terrarium.pastel.blocks.decoration.ProjectorBlockEntityRenderer;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntityRenderer;
import earth.terrarium.pastel.blocks.ender.EnderDropperBlockEntity;
import earth.terrarium.pastel.blocks.ender.EnderHopperBlockEntity;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlockEntity;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlockEntity;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntity;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntityRenderer;
import earth.terrarium.pastel.blocks.geology.SnowgraveBlockEntity;
import earth.terrarium.pastel.blocks.geology.SnowgraveBlockEntityRenderer;
import earth.terrarium.pastel.blocks.imbrifer.HummingstoneBlockEntity;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntity;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntityRenderer;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlockEntity;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlockEntityRenderer;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineRootsBlockEntity;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineRootsBlockEntityRenderer;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelWallSkullBlock;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntityRenderer;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntityRenderer;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlockEntity;
import earth.terrarium.pastel.blocks.present.PresentBlockEntity;
import earth.terrarium.pastel.blocks.redstone.BlockBreakerBlockEntity;
import earth.terrarium.pastel.blocks.redstone.BlockPlacerBlockEntity;
import earth.terrarium.pastel.blocks.redstone.PlayerDetectorBlockEntity;
import earth.terrarium.pastel.blocks.redstone.RedstoneCalculatorBlockEntity;
import earth.terrarium.pastel.blocks.redstone.RedstoneTransceiverBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.DeepLightBlockEntity;
import earth.terrarium.pastel.blocks.structure.DeepLightBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PlayerTrackerBlockEntity;
import earth.terrarium.pastel.blocks.structure.PlayerTrackingBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PreservationBlockDetectorBlockEntity;
import earth.terrarium.pastel.blocks.structure.PreservationControllerBlockEntity;
import earth.terrarium.pastel.blocks.structure.PreservationControllerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PreservationRoundelBlockEntity;
import earth.terrarium.pastel.blocks.structure.TreasureChestBlockEntity;
import earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlockEntity;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockBlockEntityRenderer;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PastelBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister
        .create(
            Registries.BLOCK_ENTITY_TYPE,
            PastelCommon.MOD_ID
        );

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<FusionShrineBlockEntity>> FUSION_SHRINE;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnchanterBlockEntity>> ENCHANTER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemBowlBlockEntity>> ITEM_BOWL;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemRoundelBlockEntity>> ITEM_ROUNDEL;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderDropperBlockEntity>> ENDER_DROPPER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderHopperBlockEntity>> ENDER_HOPPER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ParticleSpawnerBlockEntity>> PARTICLE_SPAWNER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<UpgradeBlockEntity>> UPGRADE_BLOCK;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BottomlessBundleBlockEntity>> BOTTOMLESS_BUNDLE;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PotionWorkshopBlockEntity>> POTION_WORKSHOP;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystallarieumBlockEntity>> CRYSTALLARIEUM;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CinderhearthBlockEntity>> CINDERHEARTH;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalApothecaryBlockEntity>> CRYSTAL_APOTHECARY;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ColorPickerBlockEntity>> COLOR_PICKER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CompactingChestBlockEntity>> COMPACTING_CHEST;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<FabricationChestBlockEntity>> FABRICATION_CHEST;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HeartboundChestBlockEntity>> HEARTBOUND_CHEST;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlackHoleChestBlockEntity>> BLACK_HOLE_CHEST;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TreasureChestBlockEntity>> PRESERVATION_CHEST;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<AmphoraBlockEntity>> AMPHORA;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ProjectorBlockEntity>> PROJECTOR;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerDetectorBlockEntity>> PLAYER_DETECTOR;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<RedstoneCalculatorBlockEntity>> REDSTONE_CALCULATOR;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<RedstoneTransceiverBlockEntity>> REDSTONE_TRANSCEIVER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockPlacerBlockEntity>> BLOCK_PLACER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockBreakerBlockEntity>> BLOCK_BREAKER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockFlooderBlockEntity>> BLOCK_FLOODER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritInstillerBlockEntity>> SPIRIT_INSTILLER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<MemoryBlockEntity>> MEMORY;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<JadeVineRootsBlockEntity>> JADE_VINE_ROOTS;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PresentBlockEntity>> PRESENT;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TitrationBarrelBlockEntity>> TITRATION_BARREL;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PastelNodeBlockEntity>> PASTEL_NODE;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HummingstoneBlockEntity>> HUMMINGSTONE;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlacedItemBlockEntity>> PLACED_ITEM;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeaTableBlockEntity>> TEA_TABLE;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationControllerBlockEntity>> PRESERVATION_CONTROLLER;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationRoundelBlockEntity>> PRESERVATION_ROUNDEL;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationBlockDetectorBlockEntity>> PRESERVATION_BLOCK_DETECTOR;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<DeepLightBlockEntity>> DEEP_LIGHT;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerTrackerBlockEntity>> PLAYER_TRACKING;

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<SnowgraveBlockEntity>> SNOWGRAVE;

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
        String id,
        BlockEntityType.BlockEntitySupplier<T> factory,
        Supplier<? extends Block>... blocks
    ) {
        return register(
            id,
            factory,
            () -> Arrays
                .asList(blocks)
                .stream()
                .map(Supplier::get)
                .toList()
        );
    }

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
        String id,
        BlockEntityType.BlockEntitySupplier<T> factory,
        Supplier<List<? extends Block>> blocks
    ) {
        return REGISTER
            .register(
                id,
                () -> BlockEntityType.Builder
                    .of(
                        factory,
                        blocks
                            .get()
                            .toArray(new Block[0])
                    )
                    .build(null)
            );
    }

    public static void register(IEventBus bus) {
        PEDESTAL = register(
            "pedestal_block_entity",
            PedestalBlockEntity::new,
            PastelBlocks.PEDESTAL_BASIC_AMETHYST,
            PastelBlocks.PEDESTAL_BASIC_TOPAZ,
            PastelBlocks.PEDESTAL_BASIC_CITRINE,
            PastelBlocks.PEDESTAL_ALL_BASIC,
            PastelBlocks.PEDESTAL_ONYX,
            PastelBlocks.PEDESTAL_MOONSTONE
        );
        FUSION_SHRINE = register(
            "fusion_shrine_block_entity",
            FusionShrineBlockEntity::new,
            PastelBlocks.FUSION_SHRINE_BASALT,
            PastelBlocks.FUSION_SHRINE_CALCITE
        );
        ENCHANTER = register("enchanter_block_entity", EnchanterBlockEntity::new, PastelBlocks.ENCHANTER);
        ITEM_BOWL = register(
            "item_bowl_block_entity",
            ItemBowlBlockEntity::new,
            PastelBlocks.ITEM_BOWL_BASALT,
            PastelBlocks.ITEM_BOWL_CALCITE
        );
        ITEM_ROUNDEL = register("item_roundel", ItemRoundelBlockEntity::new, PastelBlocks.ITEM_ROUNDEL);
        ENDER_DROPPER = register("ender_dropper", EnderDropperBlockEntity::new, PastelBlocks.ENDER_DROPPER);
        ENDER_HOPPER = register("ender_hopper", EnderHopperBlockEntity::new, PastelBlocks.ENDER_HOPPER);
        PARTICLE_SPAWNER = register(
            "particle_spawner",
            ParticleSpawnerBlockEntity::new,
            PastelBlocks.PARTICLE_SPAWNER,
            PastelBlocks.CREATIVE_PARTICLE_SPAWNER
        );
        COMPACTING_CHEST = register("compacting_chest", CompactingChestBlockEntity::new, PastelBlocks.COMPACTING_CHEST);
        FABRICATION_CHEST = register(
            "fabrication_chest",
            FabricationChestBlockEntity::new,
            PastelBlocks.FABRICATION_CHEST
        );
        HEARTBOUND_CHEST = register("heartbound_chest", HeartboundChestBlockEntity::new, PastelBlocks.HEARTBOUND_CHEST);
        BLACK_HOLE_CHEST = register("black_hole_chest", BlackHoleChestBlockEntity::new, PastelBlocks.BLACK_HOLE_CHEST);
        PRESERVATION_CHEST = register(
            "preservation_chest",
            TreasureChestBlockEntity::new,
            PastelBlocks.PRESERVATION_CHEST
        );
        AMPHORA = register(
            "amphora",
            AmphoraBlockEntity::new,
            PastelBlocks.CHESTNUT_NOXWOOD_AMPHORA,
            PastelBlocks.EBONY_NOXWOOD_AMPHORA,
            PastelBlocks.SLATE_NOXWOOD_AMPHORA,
            PastelBlocks.IVORY_NOXWOOD_AMPHORA,
            PastelBlocks.WEEPING_GALA_AMPHORA
        );
        PROJECTOR = register("projector", ProjectorBlockEntity::new, PastelBlocks.PYRITE_PROJECTOR);
        PLAYER_DETECTOR = register("player_detector", PlayerDetectorBlockEntity::new, PastelBlocks.PLAYER_DETECTOR);
        REDSTONE_CALCULATOR = register(
            "redstone_calculator",
            RedstoneCalculatorBlockEntity::new,
            PastelBlocks.REDSTONE_CALCULATOR
        );
        REDSTONE_TRANSCEIVER = register(
            "redstone_transceiver",
            RedstoneTransceiverBlockEntity::new,
            PastelBlocks.REDSTONE_TRANSCEIVER
        );
        BLOCK_PLACER = register("block_placer", BlockPlacerBlockEntity::new, PastelBlocks.BLOCK_PLACER);
        BLOCK_BREAKER = register("block_breaker", BlockBreakerBlockEntity::new, PastelBlocks.BLOCK_BREAKER);
        BLOCK_FLOODER = register("block_flooder", BlockFlooderBlockEntity::new, PastelBlocks.BLOCK_FLOODER);
        BOTTOMLESS_BUNDLE = register(
            "bottomless_bundle",
            BottomlessBundleBlockEntity::new,
            PastelBlocks.BOTTOMLESS_BUNDLE
        );
        POTION_WORKSHOP = register("potion_workshop", PotionWorkshopBlockEntity::new, PastelBlocks.POTION_WORKSHOP);
        SPIRIT_INSTILLER = register("spirit_instiller", SpiritInstillerBlockEntity::new, PastelBlocks.SPIRIT_INSTILLER);
        MEMORY = register("memory", MemoryBlockEntity::new, PastelBlocks.MEMORY);
        JADE_VINE_ROOTS = register("jade_vine_roots", JadeVineRootsBlockEntity::new, PastelBlocks.JADE_VINE_ROOTS);
        CRYSTALLARIEUM = register("crystallarieum", CrystallarieumBlockEntity::new, PastelBlocks.CRYSTALLARIEUM);
        CRYSTAL_APOTHECARY = register(
            "crystal_apothecary",
            CrystalApothecaryBlockEntity::new,
            PastelBlocks.CRYSTAL_APOTHECARY
        );
        COLOR_PICKER = register("color_picker", ColorPickerBlockEntity::new, PastelBlocks.COLOR_PICKER);
        CINDERHEARTH = register("cinderhearth", CinderhearthBlockEntity::new, PastelBlocks.CINDERHEARTH);
        PRESENT = register("present", PresentBlockEntity::new, PastelBlocks.PRESENT);
        TITRATION_BARREL = register("titration_barrel", TitrationBarrelBlockEntity::new, PastelBlocks.TITRATION_BARREL);
        TEA_TABLE = register("tea_table", TeaTableBlockEntity::new, PastelBlocks.TEA_TABLE);
        PASTEL_NODE = register(
            "pastel_node",
            PastelNodeBlockEntity::new,
            PastelBlocks.CONNECTION_NODE,
            PastelBlocks.PROVIDER_NODE,
            PastelBlocks.STORAGE_NODE,
            PastelBlocks.SENDER_NODE,
            PastelBlocks.GATHER_NODE,
            PastelBlocks.BUFFER_NODE
        );
        HUMMINGSTONE = register("hummingstone", HummingstoneBlockEntity::new, PastelBlocks.HUMMINGSTONE);
        PLACED_ITEM = register(
            "placed_item",
            PlacedItemBlockEntity::new,
            PastelBlocks.INCANDESCENT_AMALGAM,
            PastelBlocks.COLORFUL_SHOOTING_STAR,
            PastelBlocks.FIERY_SHOOTING_STAR,
            PastelBlocks.GEMSTONE_SHOOTING_STAR,
            PastelBlocks.GLISTERING_SHOOTING_STAR,
            PastelBlocks.PRISTINE_SHOOTING_STAR
        );
        PRESERVATION_CONTROLLER = register(
            "preservation_controller",
            PreservationControllerBlockEntity::new,
            PastelBlocks.PRESERVATION_CONTROLLER
        );
        PRESERVATION_ROUNDEL = register(
            "preservation_roundel",
            PreservationRoundelBlockEntity::new,
            PastelBlocks.PRESERVATION_ROUNDEL
        );
        PRESERVATION_BLOCK_DETECTOR = register(
            "preservation_block_detector",
            PreservationBlockDetectorBlockEntity::new,
            PastelBlocks.PRESERVATION_BLOCK_DETECTOR
        );
        DEEP_LIGHT = register(
            "deep_light",
            DeepLightBlockEntity::new,
            PastelBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE
        );
        PLAYER_TRACKING = register(
            "player_tracking",
            PlayerTrackerBlockEntity::new,
            PastelBlocks.MANXI,
            PastelBlocks.ENLIGHTENMENT_ITEM_BOWL
        );

        SNOWGRAVE = register("snowgrave", SnowgraveBlockEntity::new, PastelBlocks.SNOWGRAVE);

        // All the upgrades
        List<Supplier<? extends Block>> upgradeBlocksList = List
            .of(
                PastelBlocks.UPGRADE_SPEED,
                PastelBlocks.UPGRADE_SPEED2,
                PastelBlocks.UPGRADE_SPEED3,
                PastelBlocks.UPGRADE_EFFICIENCY,
                PastelBlocks.UPGRADE_EFFICIENCY2,
                PastelBlocks.UPGRADE_YIELD,
                PastelBlocks.UPGRADE_YIELD2,
                PastelBlocks.UPGRADE_EXPERIENCE,
                PastelBlocks.UPGRADE_EXPERIENCE2
            );
        UPGRADE_BLOCK = register("upgrade_block", UpgradeBlockEntity::new, upgradeBlocksList.toArray(new Supplier[0]));

        REGISTER.register(bus);
    }

    public static void registerAdditionalTypes(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.BARREL, PastelBlocks.WEEPING_GALA_BARREL.get());

        List<Block> skullBlocksList = new ArrayList<>(
            PastelSkullBlock
                .getMobHeads()
                .size() + PastelWallSkullBlock
                    .getMobWallHeads()
                    .size()
        );
        for (
            var head : PastelSkullBlock.getMobHeads()
        ) {
            skullBlocksList.add(head.get());
        }
        skullBlocksList.addAll(PastelWallSkullBlock.getMobWallHeads());

        event.modify(BlockEntityType.SKULL, skullBlocksList.toArray(new Block[0]));
    }

    public static void registerClient(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(PastelBlockEntities.PEDESTAL.get(), PedestalBlockEntityRenderer::new);
        BlockEntityRenderers
            .register(
                PastelBlockEntities.BOTTOMLESS_BUNDLE.get(),
                BottomlessBundleBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.HEARTBOUND_CHEST.get(),
                HeartboundChestBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.COMPACTING_CHEST.get(),
                CompactingChestBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.FABRICATION_CHEST.get(),
                FabricationChestBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.PRESERVATION_CHEST.get(),
                PastelChestBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.BLACK_HOLE_CHEST.get(),
                BlackHoleChestBlockEntityRenderer::new
            );
        BlockEntityRenderers.register(PastelBlockEntities.UPGRADE_BLOCK.get(), UpgradeBlockBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.FUSION_SHRINE.get(), FusionShrineBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.ENCHANTER.get(), EnchanterBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.ITEM_BOWL.get(), ItemBowlBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.ITEM_ROUNDEL.get(), ItemRoundelBlockEntityRenderer::new);
        BlockEntityRenderers
            .register(
                PastelBlockEntities.PRESERVATION_ROUNDEL.get(),
                ItemRoundelBlockEntityRenderer::new
            );
        BlockEntityRenderers
            .register(
                PastelBlockEntities.SPIRIT_INSTILLER.get(),
                SpiritInstillerBlockEntityRenderer::new
            );
        BlockEntityRenderers.register(PastelBlockEntities.JADE_VINE_ROOTS.get(), JadeVineRootsBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.CRYSTALLARIEUM.get(), CrystallarieumBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.COLOR_PICKER.get(), ColorPickerBlockEntityRenderer::new);
        BlockEntityRenderers
            .register(
                PastelBlockEntities.PRESERVATION_CONTROLLER.get(),
                PreservationControllerBlockEntityRenderer::new
            );
        BlockEntityRenderers.register(PastelBlockEntities.PROJECTOR.get(), ProjectorBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.DEEP_LIGHT.get(), DeepLightBlockEntityRenderer::new);
        BlockEntityRenderers
            .register(
                PastelBlockEntities.PLAYER_TRACKING.get(),
                PlayerTrackingBlockEntityRenderer::new
            );
        BlockEntityRenderers.register(PastelBlockEntities.SNOWGRAVE.get(), SnowgraveBlockEntityRenderer::new);

        BlockEntityRenderers.register(PastelBlockEntities.PASTEL_NODE.get(), PastelNodeBlockEntityRenderer::new);
        BlockEntityRenderers.register(PastelBlockEntities.TEA_TABLE.get(), TeaTableBlockEntityRenderer::new);
    }

}
