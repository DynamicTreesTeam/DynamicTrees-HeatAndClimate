package com.harleyoconnor.dynamictreeshnc.proxy;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.seasons.SeasonGrowthCalculatorActive;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.seasons.SeasonManager;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeshnc.AddonConfigs;
import com.harleyoconnor.dynamictreeshnc.AddonConstants;
import com.harleyoconnor.dynamictreeshnc.AddonContent;
import com.harleyoconnor.dynamictreeshnc.seasons.SeasonProviderHeatAndClimate;
import com.harleyoconnor.dynamictreeshnc.worldgen.WorldGen;
import defeatedcrow.hac.main.ClimateMain;
import defeatedcrow.hac.main.config.ModuleConfig;
import defeatedcrow.hac.main.config.WorldGenConfig;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        if (ModuleConfig.crop && AddonConfigs.useHeatAndClimateSeasons){
            SeasonHelper.setSeasonManager(new SeasonManager( w -> new Tuple<>(new SeasonProviderHeatAndClimate(), new SeasonGrowthCalculatorActive())));
        }
        if (ModConfigs.worldGen) {
            GameRegistry.registerWorldGenerator(new WorldGen(), 19);
            WorldGenConfig.saplingGen = 0;
        }
    }

    public void init() {
        final Block saplingBlock = Block.getBlockFromName(ClimateMain.MOD_ID + ":dcs_crop_sapling");
        final Block secondSaplingBlock = Block.getBlockFromName(ClimateMain.MOD_ID + ":dcs_crop_sapling2");

        registerSaplingReplacement(AddonConstants.LEMON_TREE, saplingBlock, 0);
        registerSaplingReplacement(AddonConstants.OLIVE_TREE, saplingBlock, 1);
        registerSaplingReplacement(AddonConstants.MORUS_TREE, saplingBlock, 3);
        registerSaplingReplacement(AddonConstants.WALNUT_TREE, secondSaplingBlock, 0);
        registerSaplingReplacement(AddonConstants.DATE_TREE, secondSaplingBlock, 1);
    }

    @SuppressWarnings("deprecation")
    private static void registerSaplingReplacement (final String speciesName, final Block saplingBlock, final int saplingMeta) {
        TreeRegistry.registerSaplingReplacer(saplingBlock.getStateFromMeta(saplingMeta), getSpecies(speciesName));
    }

    private static Species getSpecies (final String name) {
        return TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, name));
    }

    public void postInit() {
    }

}
