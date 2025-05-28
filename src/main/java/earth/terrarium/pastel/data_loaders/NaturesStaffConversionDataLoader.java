package earth.terrarium.pastel.data_loaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.recipe.RecipeUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NaturesStaffConversionDataLoader extends SimpleJsonResourceReloadListener {
	
	public static final String ID = "natures_staff_conversion";
	public static final NaturesStaffConversionDataLoader INSTANCE = new NaturesStaffConversionDataLoader();
	
	public static final HashMap<Block, BlockState> CONVERSIONS = new HashMap<>();
	public static final HashMap<Block, ResourceLocation> UNLOCK_IDENTIFIERS = new HashMap<>();
	
	private NaturesStaffConversionDataLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> prepared, ResourceManager manager, ProfilerFiller profiler) {
		CONVERSIONS.clear();
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			Block input = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(GsonHelper.getAsString(jsonObject, "input_block")));

			BlockState output;
			try {
				output = RecipeUtils.blockStateFromString(jsonObject.get("output_state").getAsString());
			} catch (CommandSyntaxException e) {
				throw new JsonParseException(e);
			}

			if (input != Blocks.AIR && !output.isAir()) {
				CONVERSIONS.put(input, output);
				if (GsonHelper.isStringValue(jsonObject, "unlock_identifier")) {
					UNLOCK_IDENTIFIERS.put(input, ResourceLocation.tryParse(GsonHelper.getAsString(jsonObject, "unlock_identifier")));
				}
			}
		});
	}
	
	public static @Nullable BlockState getConvertedBlockState(Block block) {
		return CONVERSIONS.getOrDefault(block, null);
	}
	
}