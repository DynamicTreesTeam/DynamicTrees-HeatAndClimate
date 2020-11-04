package com.harleyoconnor.dynamictreeshnc.trees;

import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorSeed;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFlareBottom;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFruit;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenMound;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeshnc.AddonConstants;
import com.harleyoconnor.dynamictreeshnc.AddonContent;
import com.harleyoconnor.dynamictreeshnc.genfeatures.FeatureGenFruitLeaves;
import defeatedcrow.hac.main.ClimateMain;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Random;

/**
 * @author Harley O'Connor
 */
public final class TreeWalnut extends TreeFamily {

    public static Block primitiveLeavesBlock = Block.getBlockFromName(ClimateMain.MOD_ID + ":dcs_leaves_walnut");

    public static float fruitingOffset = 0.5f; //summer-autumn

    public static class SpeciesLemon extends Species {

        public SpeciesLemon(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, AddonContent.walnutLeavesProperties[0]);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.3F, 14.0F, this.upProbability, 4, 0.9F);

            // Setup environment factors.
            this.envFactor(BiomeDictionary.Type.HOT, 1.1F);
            this.envFactor(BiomeDictionary.Type.DRY, 1.2F);

            // Setup seed.
            this.generateSeed();

            setFlowerSeasonHold(fruitingOffset - 0.5f, fruitingOffset + 0.5f);

            addDropCreator(new DropCreatorSeed() {
                @Override public List<ItemStack> getHarvestDrop(World world, Species species, BlockPos leafPos, Random random, List<ItemStack> dropList, int soilLife, int fortune) {
                    float rarity = getHarvestRarity();
                    rarity *= (fortune + 1) / 128f; //Extra rare so players are incentivized to get fruits from growing instead of chopping
                    rarity *= Math.min(species.seasonalSeedDropFactor(world, leafPos) + 0.15f, 1.0);
                    if(rarity > random.nextFloat()) dropList.add(getFruit ()); //1 in 128 chance to drop a fruit on destruction..
                    return dropList;
                }

                private ItemStack getFruit (){
                    Item fruit = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ClimateMain.MOD_ID,"dcs_food_crops"));
                    assert fruit != null;
                    return new ItemStack(fruit, 1, 16);
                }

                @Override public List<ItemStack> getLeavesDrop(IBlockAccess access, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, int fortune) {
                    int chance = 40;
                    //Hokey fortune stuff here to match Vanilla logic.
                    if (fortune > 0) {
                        chance -= 2 << fortune;
                        if (chance < 10) chance = 10;
                    }
                    float seasonFactor = 1.0f;
                    if(access instanceof World) {
                        World world = (World) access;
                        if(!world.isRemote) seasonFactor = species.seasonalSeedDropFactor(world, breakPos);
                    }
                    if(random.nextInt((int) (chance / getLeavesRarity())) == 0)
                        if(seasonFactor > random.nextFloat())
                            dropList.add(getFruit());
                    return dropList;
                }
            });

            addGenFeature(new FeatureGenFruitLeaves(6, 18, AddonContent.walnutLeavesProperties, 0.5f));

            AddonContent.fruitWalnut.setSpecies(this);
            addGenFeature(new FeatureGenFruit(AddonContent.fruitWalnut));

            addGenFeature(new FeatureGenFlareBottom());
            addGenFeature(new FeatureGenMound(999));
        }

        @Override
        public boolean isThick() {
            return true;
        }

        @Override
        public int maxBranchRadius() {
            return 16;
        }

        @Override
        public float seasonalFruitProductionFactor(World world, BlockPos pos) {
            float offset = fruitingOffset;
            return SeasonHelper.globalSeasonalFruitProductionFactor(world, pos, offset);
        }

        @Override
        public boolean testFlowerSeasonHold(World world, BlockPos pos, float seasonValue) {
            return SeasonHelper.isSeasonBetween(seasonValue, flowerSeasonHoldMin, flowerSeasonHoldMax);
        }

        // TODO: override isBiomePerfect.
    }

    public TreeWalnut() {
        super(new ResourceLocation(AddonConstants.MOD_ID, "walnut"));

        this.setPrimitiveLog(Blocks.LOG.getStateFromMeta(0));
        for (int i=0;i<4;i++) AddonContent.walnutLeavesProperties[i].setTree(this);

        this.addConnectableVanillaLeaves((state) -> state.getBlock() == primitiveLeavesBlock);
    }

    @Override
    public boolean isThick() {
        return true;
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesLemon(this));
    }

}
