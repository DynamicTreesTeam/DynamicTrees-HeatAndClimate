package com.harleyoconnor.dynamictreeshnc.worldgen;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.harleyoconnor.dynamictreeshnc.AddonConfigs;
import defeatedcrow.hac.food.FoodInit;
import defeatedcrow.hac.main.config.ModuleConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator {

    //Tea and Wisteria need to be re-generated since they were generated with the rest of the trees.
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int X = chunkX << 4;
        int Z = chunkZ << 4;
        int posX = X + world.rand.nextInt(16);
        int posZ = Z + world.rand.nextInt(16);
        BlockPos pos = new BlockPos(posX, world.getHeight(posX, posZ), posZ).down();

        Biome biome = world.getBiomeForCoordsBody(pos);

        if (world.rand.nextFloat() <= AddonConfigs.teaSpawnChance &&
                (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.HILLS)) &&
                DirtHelper.isSoilAcceptable(world.getBlockState(pos).getBlock(), DirtHelper.getSoilFlags(DirtHelper.DIRTLIKE))) {

            world.setBlockState(pos.up(), FoodInit.leavesTea.getDefaultState());
        }

        if (world.rand.nextFloat() <= AddonConfigs.wisteriaSpawnChance &&
                (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.WET)) &&
                DirtHelper.isSoilAcceptable(world.getBlockState(pos).getBlock(), DirtHelper.getSoilFlags(DirtHelper.DIRTLIKE))) {

            world.setBlockState(pos.up(), FoodInit.cropWisteria.getDefaultState());
        }
    }
}
