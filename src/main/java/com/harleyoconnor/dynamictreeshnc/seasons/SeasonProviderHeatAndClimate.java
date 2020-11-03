package com.harleyoconnor.dynamictreeshnc.seasons;

import com.ferreusveritas.dynamictrees.seasons.ISeasonProvider;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import defeatedcrow.hac.config.CoreConfigDC;
import defeatedcrow.hac.core.util.DCTimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SeasonProviderHeatAndClimate implements ISeasonProvider {

    private float seasonValue = 1f;

    @Override
    public Float getSeasonValue(World world) {
            return seasonValue;
    }

    @Override
    public void updateTick(World world, long worldTicks) {
        int d = DCTimeHelper.getDay(world);
        if (!CoreConfigDC.enableRealTime){
            double d1 = d * (365D / CoreConfigDC.yearLength);
            d = MathHelper.floor(d1);
            d = d % 365;
        }
        float val = (d / 365f) + (CoreConfigDC.overYear.id * 0.25f);
        seasonValue = ((val * 4.0f) + 0.25f) % 4;
    }

    @Override
    public boolean shouldSnowMelt(World world, BlockPos blockPos) {
        return SeasonHelper.isSeasonBetween(seasonValue, SeasonHelper.SPRING, SeasonHelper.WINTER) && world.getBiome(blockPos).getTemperature(blockPos) >= 0.15f;
    }
}
