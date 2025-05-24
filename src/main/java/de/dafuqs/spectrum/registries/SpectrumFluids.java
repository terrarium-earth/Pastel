package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.color.ItemColors;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.fluid.DragonrotFluid;
import de.dafuqs.spectrum.blocks.fluid.GooFluid;
import de.dafuqs.spectrum.blocks.fluid.LiquidCrystalFluid;
import de.dafuqs.spectrum.blocks.fluid.MidnightSolutionFluid;
import de.dafuqs.spectrum.blocks.fluid.SpectrumFluid;
import de.dafuqs.spectrum.helpers.SpectrumColorHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector3f;

import java.util.List;


public class SpectrumFluids {

	// LIQUID CRYSTAL
	public static final SpectrumFluid LIQUID_CRYSTAL = new LiquidCrystalFluid.Still();
	public static final SpectrumFluid FLOWING_LIQUID_CRYSTAL = new LiquidCrystalFluid.Flowing();
	public static final int LIQUID_CRYSTAL_TINT = 0xcbbbcb;
	public static final Vector3f LIQUID_CRYSTAL_COLOR_VEC = SpectrumColorHelper.colorIntToVec(LIQUID_CRYSTAL_TINT);
	public static final ResourceLocation LIQUID_CRYSTAL_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/liquid_crystal_overlay.png");
	public static final float LIQUID_CRYSTAL_OVERLAY_ALPHA = 0.6F;
	
	// GOO
	public static final SpectrumFluid GOO = new GooFluid.StillGoo();
	public static final SpectrumFluid FLOWING_GOO = new GooFluid.FlowingGoo();
	public static final int GOO_TINT = 0x4e2e0a;
	public static final Vector3f GOO_COLOR_VEC = SpectrumColorHelper.colorIntToVec(GOO_TINT);
	public static final ResourceLocation GOO_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/goo_overlay.png");
	public static final float GOO_OVERLAY_ALPHA = 0.995F;
	
	// MIDNIGHT SOLUTION
	public static final SpectrumFluid MIDNIGHT_SOLUTION = new MidnightSolutionFluid.Still();
	public static final SpectrumFluid FLOWING_MIDNIGHT_SOLUTION = new MidnightSolutionFluid.Flowing();
	public static final int MIDNIGHT_SOLUTION_TINT = 0x11183b;
	public static final Vector3f MIDNIGHT_SOLUTION_COLOR_VEC = SpectrumColorHelper.colorIntToVec(MIDNIGHT_SOLUTION_TINT);
	public static final ResourceLocation MIDNIGHT_SOLUTION_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/midnight_solution_overlay.png");
	public static final float MIDNIGHT_SOLUTION_OVERLAY_ALPHA = 0.995F;
	
	// DRAGONROT
	public static final SpectrumFluid DRAGONROT = new DragonrotFluid.Still();
	public static final SpectrumFluid FLOWING_DRAGONROT = new DragonrotFluid.Flowing();
	public static final int DRAGONROT_TINT = 0xe3772f;
	public static final Vector3f DRAGONROT_COLOR_VEC = SpectrumColorHelper.colorIntToVec(DRAGONROT_TINT);
	public static final ResourceLocation DRAGONROT_OVERLAY_TEXTURE = SpectrumCommon.locate("textures/misc/dragonrot_overlay.png");
	public static final float DRAGONROT_OVERLAY_ALPHA = 0.98F;
	
	public static void register() {
		registerFluid("liquid_crystal", LIQUID_CRYSTAL, FLOWING_LIQUID_CRYSTAL, InkColors.LIGHT_GRAY);
		registerFluid("goo", GOO, FLOWING_GOO, InkColors.BROWN);
		registerFluid("midnight_solution", MIDNIGHT_SOLUTION, FLOWING_MIDNIGHT_SOLUTION, InkColors.GRAY);
		registerFluid("dragonrot", DRAGONROT, FLOWING_DRAGONROT, InkColors.GRAY);
	}
	
	private static void registerFluid(String name, Fluid stillFluid, Fluid flowingFluid, InkColor color) {
		Registry.register(BuiltInRegistries.FLUID, SpectrumCommon.locate(name), stillFluid);
		Registry.register(BuiltInRegistries.FLUID, SpectrumCommon.locate("flowing_" + name), flowingFluid);
		ItemColors.FLUID_COLORS.registerColorMapping(stillFluid, color);
		ItemColors.FLUID_COLORS.registerColorMapping(flowingFluid, color);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void clientSetup(FMLClientSetupEvent event) {
		var fluids = List.of(
			LIQUID_CRYSTAL, FLOWING_LIQUID_CRYSTAL,
			GOO, FLOWING_GOO,
			MIDNIGHT_SOLUTION, FLOWING_MIDNIGHT_SOLUTION,
			DRAGONROT, FLOWING_DRAGONROT
		);

		for (var fluid : fluids) {
			ItemBlockRenderTypes.setRenderLayer(fluid, RenderType.translucent());
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerClient(RegisterClientExtensionsEvent event) {
		setupFluidRendering(event, LIQUID_CRYSTAL.getFluidType(), "liquid_crystal", LIQUID_CRYSTAL_TINT);
		setupFluidRendering(event, GOO.getFluidType(), "goo", GOO_TINT);
		setupFluidRendering(event, MIDNIGHT_SOLUTION.getFluidType(), "midnight_solution", MIDNIGHT_SOLUTION_TINT);
		setupFluidRendering(event, DRAGONROT.getFluidType(), "dragonrot", DRAGONROT_TINT);
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
