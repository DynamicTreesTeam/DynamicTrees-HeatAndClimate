package com.harleyoconnor.dynamictreeshnc.genfeatures;

import com.ferreusveritas.dynamictrees.api.IPostGenFeature;
import com.ferreusveritas.dynamictrees.api.IPostGrowFeature;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockFruit;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.harleyoconnor.dynamictreeshnc.blocks.BlockFruitPalm;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class FeatureGenFruitPalm implements IPostGenFeature, IPostGrowFeature {

    BlockFruit fruitPod;
    int frondHeight;
    protected int fruitingRadius = 8;

    public FeatureGenFruitPalm(BlockFruit fruitPod, int frondHeight){
        this.fruitPod = fruitPod;
        this.frondHeight = frondHeight;
    }

    public FeatureGenFruitPalm(BlockFruit fruitPod){
        this(fruitPod, 20);
    }

    public FeatureGenFruitPalm setFruitingRadius(int fruitingRadius) {
        this.fruitingRadius = fruitingRadius;
        return this;
    }

    @Override
    public boolean postGrow(World world, BlockPos rootPos, BlockPos treePos, Species species, int soilLife, boolean natural) {
        if((TreeHelper.getRadius(world, rootPos.up()) >= fruitingRadius) && natural && world.rand.nextInt() % 16 == 0) {
            if(species.seasonalFruitProductionFactor(world, rootPos) > world.rand.nextFloat()) {
                addFruit(world, rootPos, getLeavesHeight(rootPos, world), false, null);
            }
        }
        return false;
    }

    private BlockPos getLeavesHeight (BlockPos rootPos, World world){
        for (int y= 1; y < frondHeight; y++){
            BlockPos testPos = rootPos.up(y);
            if ((world.getBlockState(testPos).getBlock() instanceof BlockLeaves)){
                return testPos;
            }
        }
        return rootPos;
    }

    @Override
    public boolean postGeneration(World world, BlockPos rootPos, Species species, Biome biome, int radius, List<BlockPos> endPoints, SafeChunkBounds safeBounds, IBlockState initialDirtState) {
        boolean placed = false;
        int qty = 8;
        qty *= species.seasonalFruitProductionFactor(world, rootPos);
        for (int i=0;i<qty;i++){
            if(world.rand.nextInt(6) == 0) {
                BlockPos h = getLeavesHeight(rootPos, world);
                addFruit(world, rootPos, h,true, safeBounds);
                placed = true;
            }
        }
        return placed;
    }

    private void addFruit(World world, BlockPos rootPos, BlockPos leavesPos, boolean worldGen, SafeChunkBounds safeBounds) {
        if (rootPos.getY() == leavesPos.getY()){
            return;
        }
        EnumFacing placeDir = EnumFacing.HORIZONTALS[world.rand.nextInt(4)];
        leavesPos = leavesPos.down(); //we move the pos down so the fruit can stick to the trunk
        if ((safeBounds == null || safeBounds.inBounds(leavesPos.offset(placeDir), true)) && world.isAirBlock(leavesPos.offset(placeDir))){
            world.setBlockState(leavesPos.offset(placeDir), fruitPod.getDefaultState().withProperty(BlockFruitPalm.FACING, placeDir.getOpposite()).withProperty(BlockFruitPalm.AGE, worldGen?(1+world.rand.nextInt(3)):0));
        }

    }

}