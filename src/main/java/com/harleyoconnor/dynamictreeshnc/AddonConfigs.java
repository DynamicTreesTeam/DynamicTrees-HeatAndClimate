package com.harleyoconnor.dynamictreeshnc;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class AddonConfigs {
	
	public static File configDirectory;

	public static float teaSpawnChance;
	public static float wisteriaSpawnChance;
	public static boolean useHeatAndClimateSeasons;
	public static boolean fruityLeaves;
	
	public static void preInit(FMLPreInitializationEvent event) {
		
		configDirectory = event.getModConfigurationDirectory();
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		//World
		teaSpawnChance = config.getFloat("teaSpawnChance", "world", 0.05f, 0f, 1f, "Chance for Tea plants to spawn per chunk in Hill biomes.");
		wisteriaSpawnChance = config.getFloat("wisteriaSpawnChance", "world", 0.2f, 0f, 1f, "Chance for Wisteria plants to spawn per chunk in Hill biomes.");
		useHeatAndClimateSeasons = config.getBoolean("useHeatAndClimateSeasons", "world", true, "Allows Dynamic Trees to use the Heat and Climate seasons. (Overrides Serene Seasons' if installed)");

		//Fruit
		fruityLeaves = config.getBoolean("fruityLeaves", "fruit", true, "Allows leaves to grow fruit on their own, like the non-dynamic versions. (on addition to the Dynamic Trees fruit blocks)");

		config.save();
	}
}
