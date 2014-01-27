package erebus.client.render.tileentity;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import erebus.client.model.block.ModelAltarRepair;
import erebus.tileentity.TileEntityErebusAltar;
import erebus.tileentity.TileEntityErebusAltarRepair;

@SideOnly(Side.CLIENT)
public class TileEntityErebusAltarRepairRenderer extends TileEntityErebusAltarRenderer {
	private static final ResourceLocation[] tex = new ResourceLocation[] { new ResourceLocation("erebus:textures/special/tiles/RepairAltar1.png"), new ResourceLocation("erebus:textures/special/tiles/RepairAltar2.png"), new ResourceLocation("erebus:textures/special/tiles/RepairAltar3.png"), new ResourceLocation("erebus:textures/special/tiles/RepairAltar4.png"), new ResourceLocation("erebus:textures/special/tiles/RepairAltar5.png") };

	private final ModelAltarRepair model = new ModelAltarRepair();

	@Override
	protected void renderModel(TileEntityErebusAltar altar) {
		model.render((TileEntityErebusAltarRepair) altar);
	}

	@Override
	protected ResourceLocation getAltarTexture(TileEntityErebusAltar altar) {
		TileEntityErebusAltarRepair tile = (TileEntityErebusAltarRepair) altar;

		if (tile.animationTicks <= 5)
			return tex[0];
		else if (tile.animationTicks > 5 && tile.animationTicks <= 10)
			return tex[1];
		else if (tile.animationTicks > 10 && tile.animationTicks <= 15)
			return tex[2];
		else if (tile.animationTicks > 15 && tile.animationTicks <= 20)
			return tex[3];
		else if (tile.animationTicks > 20 && tile.animationTicks <= 25)
			return tex[4];
		else
			return null;
	}
}