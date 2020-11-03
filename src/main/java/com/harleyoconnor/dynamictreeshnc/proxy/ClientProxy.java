package com.harleyoconnor.dynamictreeshnc.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.harleyoconnor.dynamictreeshnc.AddonConstants;
import com.harleyoconnor.dynamictreeshnc.AddonContent;
import com.harleyoconnor.dynamictreeshnc.models.ModelLoaderBlockPalmFronds;
import net.minecraft.block.Block;
import net.minecraftforge.client.model.ModelLoaderRegistry;

/**
 * @author Harley O'Connor
 */
public final class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        ModelLoaderRegistry.registerLoader(new ModelLoaderBlockPalmFronds());
    }

    @Override
    public void init() {
        super.init();

        this.registerColorHandlers(); // Register colour handlers for leaves.
    }

    private void registerColorHandlers() {
        BlockDynamicLeaves[] leavesList = {AddonContent.lemonLeaves, AddonContent.morusLeaves, AddonContent.oliveLeaves, AddonContent.walnutLeaves, AddonContent.dateFrondLeaves};
        for (BlockDynamicLeaves leaves: leavesList) {
            ModelHelper.regColorHandler(leaves, (state, worldIn, pos, tintIndex) -> {
                //boolean inWorld = worldIn != null && pos != null;
                Block block = state.getBlock();
                if (TreeHelper.isLeaves(block)) {
                    return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);
                }
                return 0x00FF00FF; //Magenta
            });
        }
    }

}
