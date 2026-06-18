package earth.terrarium.pastel.registries;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.color.ItemColors;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.fluid.DragonrotFluid;
import earth.terrarium.pastel.blocks.fluid.HumusFluid;
import earth.terrarium.pastel.blocks.fluid.LiquidCrystalFluid;
import earth.terrarium.pastel.blocks.fluid.MidnightSolutionFluid;
import earth.terrarium.pastel.blocks.fluid.PastelFluid;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.render.FluidRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class PastelFluids {

    private static final DeferredRegister<Fluid> FLUID_REGISTER = DeferredRegister
        .create(
            Registries.FLUID,
            PastelCommon.MOD_ID
        );

    public static final DeferredRegister<FluidType> TYPE_REGISTER = DeferredRegister
        .create(
            NeoForgeRegistries.FLUID_TYPES,
            PastelCommon.MOD_ID
        );

    // LIQUID CRYSTAL
    public static final DeferredHolder<FluidType, FluidType> LIQUID_CRYSTAL_TYPE = TYPE_REGISTER
        .register(
            "liquid_crystal",
            () -> new FluidType(FluidType.Properties.create())
        );

    public static final DeferredHolder<Fluid, PastelFluid> LIQUID_CRYSTAL = FLUID_REGISTER
        .register(
            "liquid_crystal",
            LiquidCrystalFluid.Still::new
        );

    public static final DeferredHolder<Fluid, PastelFluid> FLOWING_LIQUID_CRYSTAL = FLUID_REGISTER
        .register(
            "flowing_liquid_crystal",
            LiquidCrystalFluid.Flowing::new
        );

    public static final int LIQUID_CRYSTAL_TINT = 0xFFcbbbcb;

    public static final Vector3f LIQUID_CRYSTAL_COLOR_VEC = ColorHelper.colorIntToVec(LIQUID_CRYSTAL_TINT);

    public static final float LIQUID_CRYSTAL_OVERLAY_ALPHA = 0.6F;

    // HUMUS
    public static final DeferredHolder<FluidType, FluidType> HUMUS_TYPE = TYPE_REGISTER
        .register(
            "humus",
            () -> new FluidType(FluidType.Properties.create())
        );

    public static final DeferredHolder<Fluid, PastelFluid> HUMUS = FLUID_REGISTER
        .register(
            "humus",
            HumusFluid.StillHumus::new
        );

    public static final DeferredHolder<Fluid, PastelFluid> FLOWING_HUMUS = FLUID_REGISTER
        .register(
            "flowing_humus",
            HumusFluid.FlowingHumus::new
        );

    public static final int HUMUS_TINT = 0xFF4e2e0a;

    public static final Vector3f HUMUS_COLOR_VEC = ColorHelper.colorIntToVec(HUMUS_TINT);

    public static final float HUMUS_OVERLAY_ALPHA = 0.995F;

    // MIDNIGHT SOLUTION
    public static final DeferredHolder<FluidType, FluidType> MIDNIGHT_SOLUTION_TYPE = TYPE_REGISTER
        .register(
            "midnight_solution",
            () -> new FluidType(FluidType.Properties.create())
        );

    public static final DeferredHolder<Fluid, PastelFluid> MIDNIGHT_SOLUTION = FLUID_REGISTER
        .register(
            "midnight_solution",
            MidnightSolutionFluid.Still::new
        );

    public static final DeferredHolder<Fluid, PastelFluid> FLOWING_MIDNIGHT_SOLUTION = FLUID_REGISTER
        .register(
            "flowing_midnight_solution",
            MidnightSolutionFluid.Flowing::new
        );

    public static final int MIDNIGHT_SOLUTION_TINT = 0xFF11183b;

    public static final Vector3f MIDNIGHT_SOLUTION_COLOR_VEC = ColorHelper.colorIntToVec(MIDNIGHT_SOLUTION_TINT);

    public static final float MIDNIGHT_SOLUTION_OVERLAY_ALPHA = 0.995F;

    // DRAGONROT
    public static final DeferredHolder<FluidType, FluidType> DRAGONROT_TYPE = TYPE_REGISTER
        .register(
            "dragonrot",
            () -> new FluidType(FluidType.Properties.create())
        );

    public static final DeferredHolder<Fluid, PastelFluid> DRAGONROT = FLUID_REGISTER
        .register(
            "dragonrot",
            DragonrotFluid.Still::new
        );

    public static final DeferredHolder<Fluid, PastelFluid> FLOWING_DRAGONROT = FLUID_REGISTER
        .register(
            "flowing_dragonrot",
            DragonrotFluid.Flowing::new
        );

    public static final int DRAGONROT_TINT = 0xFFe3772f;

    public static final Vector3f DRAGONROT_COLOR_VEC = ColorHelper.colorIntToVec(DRAGONROT_TINT);

    public static final float DRAGONROT_OVERLAY_ALPHA = 0.98F;

    public static void register(IEventBus bus) {
        // TODO: unfuck
        //registerFluid("liquid_crystal", LIQUID_CRYSTAL.get(), FLOWING_LIQUID_CRYSTAL.get(), InkColors.LIGHT_GRAY);
        //registerFluid("humus", HUMUS, FLOWING_HUMUS, InkColors.BROWN);
        //registerFluid("midnight_solution", MIDNIGHT_SOLUTION, FLOWING_MIDNIGHT_SOLUTION, InkColors.GRAY);
        //registerFluid("dragonrot", DRAGONROT, FLOWING_DRAGONROT, InkColors.GRAY);

        FLUID_REGISTER.register(bus);
        TYPE_REGISTER.register(bus);
    }

    private static void registerFluid(String name, Fluid stillFluid, Fluid flowingFluid, InkColor color) {
        FLUID_REGISTER.register(name, () -> stillFluid);
        FLUID_REGISTER.register("flowing_" + name, () -> flowingFluid);
        ItemColors.FLUID_COLORS.registerColorMapping(stillFluid, color);
        ItemColors.FLUID_COLORS.registerColorMapping(flowingFluid, color);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        var fluids = List
            .of(
                LIQUID_CRYSTAL.get(),
                FLOWING_LIQUID_CRYSTAL.get(),
                HUMUS.get(),
                FLOWING_HUMUS.get(),
                MIDNIGHT_SOLUTION.get(),
                FLOWING_MIDNIGHT_SOLUTION.get(),
                DRAGONROT.get(),
                FLOWING_DRAGONROT.get()
            );

        for (
            var fluid : fluids
        ) {
            ItemBlockRenderTypes.setRenderLayer(fluid, RenderType.translucent());
        }
    }

    public static void registerClient(RegisterClientExtensionsEvent event) {
        setupFluidRendering(
            event,
            LIQUID_CRYSTAL_TYPE.get(),
            "liquid_crystal",
            LIQUID_CRYSTAL_TINT,
            LIQUID_CRYSTAL_OVERLAY_ALPHA
        );
        setupFluidRendering(event, HUMUS_TYPE.get(), "humus", HUMUS_TINT, HUMUS_OVERLAY_ALPHA);
        setupFluidRendering(
            event,
            MIDNIGHT_SOLUTION_TYPE.get(),
            "midnight_solution",
            MIDNIGHT_SOLUTION_TINT,
            MIDNIGHT_SOLUTION_OVERLAY_ALPHA
        );
        setupFluidRendering(event, DRAGONROT_TYPE.get(), "dragonrot", DRAGONROT_TINT, DRAGONROT_OVERLAY_ALPHA);
    }

    @OnlyIn(
        Dist.CLIENT
    )
    private static void setupFluidRendering(
        RegisterClientExtensionsEvent event,
        final FluidType fluidType,
        final String name,
        int tint,
        float overlayAlpha
    ) {
        var overlay = PastelCommon.locate("textures/misc/%s_overlay.png".formatted(name));
        var still = PastelCommon.locate("block/%s_still".formatted(name));
        var flowing = PastelCommon.locate("block/%s_flow".formatted(name));

        event
            .registerFluidType(
                new IClientFluidTypeExtensions() {
                    @Override
                    public @NotNull ResourceLocation getStillTexture() {
                        return still;
                    }

                    @Override
                    public @NotNull ResourceLocation getFlowingTexture() {
                        return flowing;
                    }

                    @Override
                    public void renderOverlay(@NotNull Minecraft mc, @NotNull PoseStack stack) {
                        FluidRendering.renderFluidOverlay(mc, stack, overlay, overlayAlpha);
                    }

                    @Override
                    public int getTintColor() {
                        return tint;
                    }
                },
                fluidType
            );
    }

}
