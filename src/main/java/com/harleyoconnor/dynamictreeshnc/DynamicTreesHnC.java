package com.harleyoconnor.dynamictreeshnc;

import com.harleyoconnor.dynamictreeshnc.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Main mod class for Dynamic Trees for Heat and Climate
 *
 * @author Harley O'Connor
 */
@Mod(modid = AddonConstants.MOD_ID, name = AddonConstants.MOD_NAME, version = AddonConstants.VERSION, dependencies = AddonConstants.DEPENDENCIES)
public final class DynamicTreesHnC {

    @SidedProxy(clientSide = "com.harleyoconnor.dynamictreeshnc.proxy.ClientProxy", serverSide = "com.harleyoconnor.dynamictreeshnc.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
