package com.harleyoconnor.dynamictreeshnc.proxy;

import com.ferreusveritas.dynamictrees.seasons.SeasonGrowthCalculatorActive;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.seasons.SeasonManager;
import com.harleyoconnor.dynamictreeshnc.seasons.SeasonProviderHeatAndClimate;
import defeatedcrow.hac.main.config.ModuleConfig;
import net.minecraft.util.Tuple;

public class CommonProxy {

    public void preInit() {
        if (ModuleConfig.crop){
            SeasonHelper.setSeasonManager(new SeasonManager( w -> new Tuple<>(new SeasonProviderHeatAndClimate(), new SeasonGrowthCalculatorActive())));
        }
    }

    public void init() {
    }

    public void postInit() {
    }

}
