package com.harleyoconnor.dynamictreeshnc.blocks;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockFruit;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFruitPalm extends BlockFruit {

    public BlockFruitPalm (String name){
        super(name);
    }

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    protected final float[] DATE_OFFSET = {6 /16f, 6 /16f, 6 /16f, 6 /16f};
    public static final AxisAlignedBB[] DATE = new AxisAlignedBB[] {
            createBox(1,1,1, 16),
            createBox(4,8,7, 16),
            createBox(5,10,6, 16),
            createBox(5,10,6, 16)
    };
    private static AxisAlignedBB createBox (float radius, float height, float stemLength, float fraction){
        float topHeight = fraction - stemLength;
        float bottomHeight = topHeight - height;
        return new AxisAlignedBB(
                ((fraction/2) - radius)/fraction, topHeight/fraction, ((fraction/2) - radius)/fraction,
                ((fraction/2) + radius)/fraction, bottomHeight/fraction, ((fraction/2) + radius)/fraction);
    }
    protected AxisAlignedBB offsetBoundingBox (AxisAlignedBB box, EnumFacing dir, float offset){
        return box.offset(dir.getFrontOffsetX() * offset, dir.getFrontOffsetY() * offset, dir.getFrontOffsetZ() * offset);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(AGE, (meta & 15) >> 2);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack toDrop = this.getFruitDrop();
        toDrop.setCount(1 + ((World)world).rand.nextInt(4));
        drops.add(toDrop);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        EnumFacing dir = state.getValue(FACING);
        IBlockState offsetState = world.getBlockState(pos.offset(dir));
        if (offsetState.getBlock() instanceof BlockBranch){
            return TreeHelper.getRadius(world, pos.offset(dir)) <= 3;
        }
        return false;

    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();
        i = i | state.getValue(AGE) << 2;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, FACING);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        EnumFacing dir = state.getValue(FACING);
        return offsetBoundingBox(DATE[state.getValue(AGE)], dir, DATE_OFFSET[state.getValue(AGE)]);
    }
}
