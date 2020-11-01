package com.harleyoconnor.dynamictreeshnc.trees;

import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeshnc.AddonContent;
import defeatedcrow.hac.main.ClimateMain;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

/**
 * @author Harley O'Connor
 */
public final class TreeLemon extends TreeFamily {

    public static Block primitiveLeavesBlock = Block.getBlockFromName(ClimateMain.MOD_ID + ":dcs_leaves_lemon");

    public static class SpeciesLemon extends Species {

        public SpeciesLemon(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, AddonContent.lemonLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.3F, 12.0F, this.upProbability, this.lowestBranchHeight, 0.9F);

            // Setup environment factors.
            this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
            this.envFactor(BiomeDictionary.Type.DRY, 1.2F);

            // Setup seed.
            this.generateSeed();
            this.setupStandardSeedDropping();
        }

        // TODO: override isBiomePerfect.
    }

    public TreeLemon() {
        super(new ResourceLocation("dynamictreeshnc", "lemon"));

        this.setPrimitiveLog(Blocks.LOG.getDefaultState(), new ItemStack(Blocks.LOG, 1, 0));
        AddonContent.lemonLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves((state) -> state.getBlock() == primitiveLeavesBlock);
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesLemon(this));
    }

}
