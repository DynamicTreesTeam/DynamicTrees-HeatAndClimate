package com.harleyoconnor.dynamictreeshnc.proxy;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.seasons.SeasonGrowthCalculatorActive;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.seasons.SeasonManager;
import com.harleyoconnor.dynamictreeshnc.AddonConfigs;
import com.harleyoconnor.dynamictreeshnc.seasons.SeasonProviderHeatAndClimate;
import com.harleyoconnor.dynamictreeshnc.worldgen.WorldGen;
import defeatedcrow.hac.main.config.ModuleConfig;
import defeatedcrow.hac.main.config.WorldGenConfig;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        AddonConfigs.preInit(event);
        if (ModuleConfig.crop && AddonConfigs.useHeatAndClimateSeasons){
            SeasonHelper.setSeasonManager(new SeasonManager( w -> new Tuple<>(new SeasonProviderHeatAndClimate(), new SeasonGrowthCalculatorActive())));
        }
        if (ModConfigs.worldGen) {
            GameRegistry.registerWorldGenerator(new WorldGen(), 19);
            WorldGenConfig.saplingGen = 0;
        }
    }

    public void init() {
    }

    public void postInit() {
    }

}
