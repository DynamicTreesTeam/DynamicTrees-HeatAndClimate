package com.harleyoconnor.dynamictreeshnc;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockFruit;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeshnc.blocks.BlockDynamicLeavesFruit;
import com.harleyoconnor.dynamictreeshnc.blocks.BlockDynamicLeavesPalm;
import com.harleyoconnor.dynamictreeshnc.blocks.BlockFruitPalm;
import com.harleyoconnor.dynamictreeshnc.trees.*;
import defeatedcrow.hac.main.ClimateMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Harley O'Connor
 */
@Mod.EventBusSubscriber(modid = AddonConstants.MOD_ID)
public final class AddonContent {

    public static BlockFruit fruitLemon, fruitOlive, fruitMulberry, fruitWalnut, fruitDate;

    public static BlockDynamicLeavesFruit lemonLeaves, oliveLeaves, morusLeaves, walnutLeaves;
    public static BlockDynamicLeavesPalm dateFrondLeaves;

    public static ILeavesProperties[] lemonLeavesProperties = new ILeavesProperties[4], oliveLeavesProperties = new ILeavesProperties[4],
            morusLeavesProperties = new ILeavesProperties[4], walnutLeavesProperties = new ILeavesProperties[4];
    public static ILeavesProperties dateLeavesProperties;

    public static ArrayList<TreeFamily> trees = new ArrayList<TreeFamily>();

    @SubscribeEvent
    public static void registerDataBasePopulators(final WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent event) {
        // event.register(new BiomeDataBasePopulator());
    }

    private static AxisAlignedBB createBox (float radius, float height, float stemLength, float fraction){
        float topHeight = fraction - stemLength;
        float bottomHeight = topHeight - height;
        return new AxisAlignedBB(
                ((fraction/2) - radius)/fraction, topHeight/fraction, ((fraction/2) - radius)/fraction,
                ((fraction/2) + radius)/fraction, bottomHeight/fraction, ((fraction/2) + radius)/fraction);
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        lemonLeaves = new BlockDynamicLeavesFruit("leaves_lemon", BlockDynamicLeavesFruit.fruitType.LEMON);
        registry.register(lemonLeaves);
        oliveLeaves = new BlockDynamicLeavesFruit("leaves_olive", BlockDynamicLeavesFruit.fruitType.OLIVE);
        registry.register(oliveLeaves);
        morusLeaves = new BlockDynamicLeavesFruit("leaves_morus", BlockDynamicLeavesFruit.fruitType.MORUS);
        registry.register(morusLeaves);
        walnutLeaves = new BlockDynamicLeavesFruit("leaves_walnut", BlockDynamicLeavesFruit.fruitType.WALNUT);
        registry.register(walnutLeaves);
        dateFrondLeaves = new BlockDynamicLeavesPalm("leaves_date");
        registry.register(dateFrondLeaves);

        Item cropItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ClimateMain.MOD_ID,"dcs_food_crops"));
        assert cropItem != null;

        fruitLemon = new BlockFruit("fruitlemon"){
            @Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer()
            {
                return BlockRenderLayer.CUTOUT_MIPPED;
            }
            protected final AxisAlignedBB[] FRUIT_AABB = new AxisAlignedBB[] {
                    createBox(1,1,0, 16),
                    createBox(1,2,0, 16),
                    createBox(2f,5,0, 20),
                    createBox(2f,5,1.25f, 20)
            };
            @Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
                return FRUIT_AABB[state.getValue(AGE)];
            }
        }.setDroppedItem(new ItemStack(cropItem, 1, 6));
        registry.register(fruitLemon);
        fruitOlive = new BlockFruit("fruitolive"){
            @Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer()
            {
                return BlockRenderLayer.CUTOUT_MIPPED;
            }
            protected final AxisAlignedBB[] FRUIT_AABB = new AxisAlignedBB[] {
                    createBox(1,1,0, 16),
                    createBox(2,3,0, 20),
                    createBox(3.3f,4,1, 20),
                    createBox(3.3f,4,2, 20)
            };
            @Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
                return FRUIT_AABB[state.getValue(AGE)];
            }
        }.setDroppedItem(new ItemStack(cropItem, 1, 7));
        registry.register(fruitOlive);
        fruitMulberry = new BlockFruit("fruitmulberry"){
            @Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer()
            {
                return BlockRenderLayer.CUTOUT_MIPPED;
            }
            protected final AxisAlignedBB[] FRUIT_AABB = new AxisAlignedBB[] {
                    createBox(1,1,0, 16),
                    createBox(2,3,0, 20),
                    createBox(3.3f,4,1, 20),
                    createBox(3.3f,4,2, 20)
            };
            @Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
                return FRUIT_AABB[state.getValue(AGE)];
            }
        }.setDroppedItem(new ItemStack(cropItem, 1, 11));
        registry.register(fruitMulberry);
        fruitWalnut = new BlockFruit("fruitwalnut"){
            @Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT_MIPPED; }
        }.setDroppedItem(new ItemStack(cropItem, 1, 16));
        registry.register(fruitWalnut);
        fruitDate = new BlockFruitPalm("fruitdate"){
            @Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT_MIPPED; }
        }.setDroppedItem(new ItemStack(cropItem, 1, 17));
        registry.register(fruitDate);

        for (int i=0;i<4;i++){
            lemonLeavesProperties[i] = setUpLeaves(TreeLemon.primitiveLeavesBlock, "deciduous");
            lemonLeavesProperties[i].setDynamicLeavesState(lemonLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, i));
            lemonLeaves.setProperties(i, lemonLeavesProperties[i]);

            oliveLeavesProperties[i] = setUpLeaves(TreeOlive.primitiveLeavesBlock, "deciduous");
            oliveLeavesProperties[i].setDynamicLeavesState(oliveLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, i));
            oliveLeaves.setProperties(i, oliveLeavesProperties[i]);

            morusLeavesProperties[i] = setUpLeaves(TreeMorus.primitiveLeavesBlock, "deciduous");
            morusLeavesProperties[i].setDynamicLeavesState(morusLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, i));
            morusLeaves.setProperties(i, morusLeavesProperties[i]);

            walnutLeavesProperties[i] = setUpLeaves(TreeWalnut.primitiveLeavesBlock, "deciduous");
            walnutLeavesProperties[i].setDynamicLeavesState(walnutLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, i));
            walnutLeaves.setProperties(i, walnutLeavesProperties[i]);
        }
        dateLeavesProperties = new LeavesProperties(TreeDate.leavesBlock.getDefaultState(), TreeRegistry.findCellKit("palm") ) {
            @Override public boolean appearanceChangesWithHydro() { return true; }
        };
        dateLeavesProperties.setDynamicLeavesState(dateFrondLeaves.getDefaultState().withProperty(BlockDynamicLeaves.TREE, 0));
        dateFrondLeaves.setProperties(0, dateLeavesProperties);

        Collections.addAll(trees, new TreeLemon(), new TreeOlive(), new TreeMorus(), new TreeWalnut(), new TreeDate());

        trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
        ArrayList<Block> treeBlocks = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
        treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(AddonConstants.MOD_ID).values());
        registry.registerAll(treeBlocks.toArray(new Block[treeBlocks.size()]));
    }

    public static ILeavesProperties setUpLeaves (Block primitiveLeavesBlock, String cellKit){
        return new LeavesProperties(
                primitiveLeavesBlock.getDefaultState(),
                TreeRegistry.findCellKit(cellKit)) {
            @Override public ItemStack getPrimitiveLeavesItemStack() { return new ItemStack(primitiveLeavesBlock); }
            @Override public int getLightRequirement() {
                return 1;
            }
        };
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        ArrayList<Item> treeItems = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableItems(treeItems));
        registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        Item fruit = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ClimateMain.MOD_ID,"dcs_food_crops"));
        Block sapling = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ClimateMain.MOD_ID,"dcs_crop_sapling"));
        Block sapling2 = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ClimateMain.MOD_ID,"dcs_crop_sapling2"));
        assert fruit != null; assert sapling != null; assert sapling2 != null;

        setUpSeedRecipes("lemon", new ItemStack(sapling, 1, 0), new ItemStack(fruit, 1, 6), false);
        setUpSeedRecipes("olive", new ItemStack(sapling, 1, 1), new ItemStack(fruit, 1, 7), false);
        setUpSeedRecipes("morus", new ItemStack(sapling, 1, 3), new ItemStack(fruit, 1, 11), false);
        setUpSeedRecipes("walnut", new ItemStack(sapling2, 1, 0), new ItemStack(fruit, 1, 16), true);
        setUpSeedRecipes("date", new ItemStack(sapling2, 1, 1), new ItemStack(fruit, 1, 17), false);

    }

    public static void setUpSeedRecipes (String name, ItemStack treeSapling, ItemStack treeFruit, boolean needsBonemeal){
        Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(AddonConstants.MOD_ID, name));
        ItemStack treeSeed = treeSpecies.getSeedStack(1);
        ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotion.DendroPotionType.TRANSFORM.getIndex()), treeSpecies.getFamily());
        BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotion.DendroPotionType.TRANSFORM.getIndex()), treeSeed, treeTransformationPotion);
        if (treeFruit == null){
            ModRecipes.createDirtBucketExchangeRecipes(treeSapling, treeSeed, true);
        } else {
            ModRecipes.createDirtBucketExchangeRecipesWithFruit(treeSapling, treeSeed, treeFruit, true, needsBonemeal);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (TreeFamily tree : trees) {
            ModelHelper.regModel(tree.getDynamicBranch());
            ModelHelper.regModel(tree.getCommonSpecies().getSeed());
            ModelHelper.regModel(tree);
        }
        LeavesPaging.getLeavesMapForModId(AddonConstants.MOD_ID).forEach((key, leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));

        ModelLoader.setCustomStateMapper(lemonLeaves, new StateMap.Builder().ignore(BlockDynamicLeaves.HYDRO).ignore(BlockDynamicLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(oliveLeaves, new StateMap.Builder().ignore(BlockDynamicLeaves.HYDRO).ignore(BlockDynamicLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(morusLeaves, new StateMap.Builder().ignore(BlockDynamicLeaves.HYDRO).ignore(BlockDynamicLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(walnutLeaves, new StateMap.Builder().ignore(BlockDynamicLeaves.HYDRO).ignore(BlockDynamicLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(dateFrondLeaves, new StateMap.Builder().ignore(BlockDynamicLeaves.TREE).build());
    }

}
