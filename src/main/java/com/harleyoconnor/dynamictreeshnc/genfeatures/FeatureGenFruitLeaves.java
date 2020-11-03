package com.harleyoconnor.dynamictreeshnc.genfeatures;

import com.ferreusveritas.dynamictrees.api.IPostGenFeature;
import com.ferreusveritas.dynamictrees.api.IPostGrowFeature;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Random;

public class FeatureGenFruitLeaves implements IPostGenFeature, IPostGrowFeature {
    public int boxSize, boxHeight;
    public Block leaf;
    public float worldGenProportion;
    protected int fruitingRadius = 8;

    public FeatureGenFruitLeaves(int size, int height, ILeavesProperties[] leafType, float proportionForWorldgen){
        boxSize = size;
        boxHeight = height;
        leaf = leafType[0].getDynamicLeavesState().getBlock();
        worldGenProportion = proportionForWorldgen;
    }

    public FeatureGenFruitLeaves setFruitingRadius(int fruitingRadius) {
        this.fruitingRadius = fruitingRadius;
        return this;
    }

    @Override
    public boolean postGrow(World world, BlockPos rootPos, BlockPos treePos, Species species, int soilLife, boolean natural) {
        if ((TreeHelper.getRadius(world, rootPos.up()) >= fruitingRadius) && natural)
            changeRandLeaf(world, rootPos, 5, species);
        return false;
    }

    @Override
    public boolean postGeneration(World world, BlockPos rootPos, Species species, Biome biome, int radius, List<BlockPos> endPoints, SafeChunkBounds safeBounds, IBlockState initialDirtState) {
        changeLeaves(world, rootPos, worldGenProportion, species);
        return true;
    }

    private void attemptLeafChange(World world, BlockPos pos, boolean worldGen, Species species){
        if (!world.isBlockLoaded(pos)){
            return;
        }
        if (worldGen){
            world.setBlockState(pos, leaf.getDefaultState().withProperty(BlockDynamicLeaves.TREE,  world.rand.nextInt(4)));
        }
        else if (world.getBlockState(pos).getBlock() == leaf){
            int growthStage = world.getBlockState(pos).getValue(BlockDynamicLeaves.TREE);
            switch (growthStage){
                case 0:
                    if (world.rand.nextFloat() <= species.seasonalFruitProductionFactor(world, pos)){
                        world.setBlockState(pos, leaf.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 1));
                    }
                    break;
                case 1:
                    if (world.rand.nextFloat() <= species.seasonalFruitProductionFactor(world, pos)){
                        world.setBlockState(pos, leaf.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 2));
                    }
                    break;
                case 2:
                    if (world.rand.nextFloat() <= species.seasonalFruitProductionFactor(world, pos)){
                        world.setBlockState(pos, leaf.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 3));
                    }
                    break;
                case 3:
                    world.setBlockState(pos, leaf.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
            }
        }

    }

    private void changeRandLeaf(World world, BlockPos rootPos, int attempts, Species species){
        Random rand = new Random();
        do {
            int randX = rootPos.getX() + rand.nextInt((2*boxSize)+1) - boxSize;
            int randZ = rootPos.getZ() + rand.nextInt((2*boxSize)+1) - boxSize;
            int randY = rootPos.getY() + rand.nextInt(boxHeight + 1);
            attemptLeafChange(world, new BlockPos(randX,randY,randZ), false, species);
        } while ((attempts--)>0);
    }

    private void changeLeaves(World world, BlockPos rootPos, float proportion, Species species){
        if (proportion <= 0 || proportion > 1) return;
        Random rand = new Random();
        for(int x=rootPos.getX()-boxSize; x<rootPos.getX()+2*boxSize; x++){
            for(int z=rootPos.getZ()-boxSize; z<rootPos.getZ()+2*boxSize; z++){
                for(int y=rootPos.getY(); y<rootPos.getY()+boxHeight; y++){
                    if (rand.nextFloat()*(1/proportion) <= 1){
                        attemptLeafChange(world, new BlockPos(x,y,z), true, species);
                    }
                }
            }
        }
    }

}
