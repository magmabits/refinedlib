package xyz.magmabits.refinedlib;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.magmabits.refinedlib.block.ModBlocks;

public class RefinedLib implements ModInitializer {
	public static final String MOD_ID = "refinedlib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
	}
}