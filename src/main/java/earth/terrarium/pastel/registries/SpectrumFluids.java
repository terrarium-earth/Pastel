package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.color.ItemColors;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.fluid.DragonrotFluid;
import earth.terrarium.pastel.blocks.fluid.GooFluid;
import earth.terrarium.pastel.blocks.fluid.LiquidCrystalFluid;
import earth.terrarium.pastel.blocks.fluid.MidnightSolutionFluid;
import earth.terrarium.pastel.blocks.fluid.SpectrumFluid;
import earth.terrarium.pastel.helpers.SpectrumColorHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.*;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.*;
import org.joml.Vector3f;

import java.util.List;


public class SpectrumFluids {

	private static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(Registries.FLUID, SpectrumCommon.MOD_ID);

	// LIQUID CRYSTAL
	public static final DeferredHolder<Fluid, SpectrumFluid> LIQUID_CRYSTAL = REGISTER.register("liquid_crystal", LiquidCrystalFluid.Still::new);
	public static final DeferredHolder<Fluid, SpectrumFluid> FLOWING_LIQUID_CRYSTAL= REGISTER.register("flowing_liquid_crystal", LiquidCrystalFluid.Flowing::new);
	public static final int LIQUID_CRYSTAL_TINT = 0xcbbbcb;
	public static final Vector3f LIQUID_CRYSTAL_COLOR_VEC = SpectrumColorHelper.colorIntToVec(LIQUID_CRYSTAL_TINT);
	public static final ResourceLocation LIQUID_CRYSTAL_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/liquid_crystal_overlay.png");
	public static final float LIQUID_CRYSTAL_OVERLAY_ALPHA = 0.6F;
	
	// GOO
	public static final DeferredHolder<Fluid, SpectrumFluid> GOO = REGISTER.register("goo", GooFluid.StillGoo::new);
	public static final DeferredHolder<Fluid, SpectrumFluid> FLOWING_GOO = REGISTER.register("flowing_goo", GooFluid.FlowingGoo::new);
	public static final int GOO_TINT = 0x4e2e0a;
	public static final Vector3f GOO_COLOR_VEC = SpectrumColorHelper.colorIntToVec(GOO_TINT);
	public static final ResourceLocation GOO_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/goo_overlay.png");
	public static final float GOO_OVERLAY_ALPHA = 0.995F;
	
	// MIDNIGHT SOLUTION
	public static final DeferredHolder<Fluid, SpectrumFluid> MIDNIGHT_SOLUTION = REGISTER.register("midnight_solution", MidnightSolutionFluid.Still::new);
	public static final DeferredHolder<Fluid, SpectrumFluid> FLOWING_MIDNIGHT_SOLUTION = REGISTER.register("flowing_midnight_solution", MidnightSolutionFluid.Flowing::new);
	public static final int MIDNIGHT_SOLUTION_TINT = 0x11183b;
	public static final Vector3f MIDNIGHT_SOLUTION_COLOR_VEC = SpectrumColorHelper.colorIntToVec(MIDNIGHT_SOLUTION_TINT);
	public static final ResourceLocation MIDNIGHT_SOLUTION_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/midnight_solution_overlay.png");
	public static final float MIDNIGHT_SOLUTION_OVERLAY_ALPHA = 0.995F;
	
	// DRAGONROT
	public static final DeferredHolder<Fluid, SpectrumFluid> DRAGONROT = REGISTER.register("dragonrot", DragonrotFluid.Still::new);
	public static final DeferredHolder<Fluid, SpectrumFluid> FLOWING_DRAGONROT = REGISTER.register("flowing_dragonrot", DragonrotFluid.Flowing::new);
	public static final int DRAGONROT_TINT = 0xe3772f;
	public static final Vector3f DRAGONROT_COLOR_VEC = SpectrumColorHelper.colorIntToVec(DRAGONROT_TINT);
	public static final ResourceLocation DRAGONROT_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/dragonrot_overlay.png");
	public static final float DRAGONROT_OVERLAY_ALPHA = 0.98F;
	
	public static void register(IEventBus bus) {
		// TODO: unfuck
		//registerFluid("liquid_crystal", LIQUID_CRYSTAL.get(), FLOWING_LIQUID_CRYSTAL.get(), InkColors.LIGHT_GRAY);
		//registerFluid("goo", GOO, FLOWING_GOO, InkColors.BROWN);
		//registerFluid("midnight_solution", MIDNIGHT_SOLUTION, FLOWING_MIDNIGHT_SOLUTION, InkColors.GRAY);
		//registerFluid("dragonrot", DRAGONROT, FLOWING_DRAGONROT, InkColors.GRAY);

		REGISTER.register(bus);
	}
	
	private static void registerFluid(String name, Fluid stillFluid, Fluid flowingFluid, InkColor color) {
		REGISTER.register(name, () -> stillFluid);
		REGISTER.register("flowing_" + name, () -> flowingFluid);
		ItemColors.FLUID_COLORS.registerColorMapping(stillFluid, color);
		ItemColors.FLUID_COLORS.registerColorMapping(flowingFluid, color);
	}

	public static void clientSetup(FMLClientSetupEvent event) {
		var fluids = List.of(
			LIQUID_CRYSTAL.get(), FLOWING_LIQUID_CRYSTAL.get(),
			GOO.get(), FLOWING_GOO.get(),
			MIDNIGHT_SOLUTION.get(), FLOWING_MIDNIGHT_SOLUTION.get(),
			DRAGONROT.get(), FLOWING_DRAGONROT.get()
		);

		for (var fluid : fluids) {
			ItemBlockRenderTypes.setRenderLayer(fluid, RenderType.translucent());
		}
	}

	public static void registerClient(RegisterClientExtensionsEvent event) {
		setupFluidRendering(event, LIQUID_CRYSTAL.get().getFluidType(), "liquid_crystal", LIQUID_CRYSTAL_TINT);
		setupFluidRendering(event, GOO.get().getFluidType(), "goo", GOO_TINT);
		setupFluidRendering(event, MIDNIGHT_SOLUTION.get().getFluidType(), "midnight_solution", MIDNIGHT_SOLUTION_TINT);
		setupFluidRendering(event, DRAGONROT.get().getFluidType(), "dragonrot", DRAGONROT_TINT);
	}

	@OnlyIn(Dist.CLIENT)
	private static void setupFluidRendering(RegisterClientExtensionsEvent event, final FluidType fluidType, final String name, int tint) {
		event.registerFluidType(new IClientFluidTypeExtensions() {
			@Override
			public ResourceLocation getStillTexture() {
				return SpectrumCommon.locate("block/" + name + "_still");
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return SpectrumCommon.locate("block/" + name + "_flow");
			}

			@Override
			public int getTintColor() {
				return tint;
			}
		}, fluidType);
	}
	
}
