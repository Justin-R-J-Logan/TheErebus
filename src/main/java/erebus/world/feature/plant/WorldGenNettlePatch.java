package erebus.world.feature.plant;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import erebus.ModBlocks;
import erebus.block.BlockSmallPlants;

//@formatter:off
public class WorldGenNettlePatch extends WorldGenerator {
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z){
		float ang,len;
		
		for(int attempt = 0, placed = 0, xx, yy, zz; attempt < 48 && placed < 15; ++attempt){
			ang = (float)(rand.nextDouble()*Math.PI*2D);
			len = rand.nextFloat()*(0.3F+rand.nextFloat()*0.7F)*7F;
			
			xx = (int)(x+0.5F+MathHelper.cos(ang)*len);
			yy = y+rand.nextInt(3)-rand.nextInt(3);
			zz = (int)(z+0.5F+MathHelper.sin(ang)*len);

			if (world.isAirBlock(xx,yy,zz) && world.getBlockId(xx,yy-1,zz) == Block.grass.blockID){
				world.setBlock(xx,yy,zz,ModBlocks.erebusPlantSmall.blockID,rand.nextBoolean() ? BlockSmallPlants.dataNettle : BlockSmallPlants.dataNettleFlowered,2);
				++placed;
			}
		}
		
		return true;
	}
}
//@formatter:on