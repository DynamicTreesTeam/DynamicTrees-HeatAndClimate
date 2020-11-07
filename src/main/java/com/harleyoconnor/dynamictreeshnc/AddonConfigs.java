package com.harleyoconnor.dynamictreeshnc;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Config(modid = AddonConstants.MOD_ID)
public class AddonConfigs {

	@Config.Comment("Chance for Tea plants to spawn per chunk in Hill biomes.")
	@Config.Name("Tea Spawn Chance")
	@Config.RangeDouble(min = 0.0, max = 1.0)
	public static double teaSpawnChance = 0.05;

	@Config.Comment("Chance for Wisteria plants to spawn per chunk in Hill biomes.")
	@Config.Name("Wisteria Spawn Chance")
	@Config.RangeDouble(min = 0.0, max = 1.0)
	public static double wisteriaSpawnChance = 0.2;

	@Config.Comment("Allows Dynamic Trees to use the Heat and Climate seasons (overrides Serene Seasons' if installed).")
	@Config.Name("Use Heat and Climate Seasons")
	@Config.RequiresMcRestart()
	public static boolean useHeatAndClimateSeasons = true;

	@Config.Comment("Allows leaves to grow fruit on their own, like the non-dynamic versions (in addition to the Dynamic Trees fruit blocks).")
	@Config.Name("Fruity Leaves")
	@Config.RequiresMcRestart()
	public static boolean fruityLeaves = true;

}
