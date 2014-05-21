package erebus.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUmberstone extends Block {

	public static final String[] iconPaths = new String[] { "umberstone", "cobbleUmber", "cobbleUmberMossy", "cobbleUmberWebbed", "umberstoneBricks", "smoothUmbertile", "smoothUmbertiles" };
	@SideOnly(Side.CLIENT)
	public static IIcon[] icons;

	public BlockUmberstone() {
		super(Material.rock);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[iconPaths.length];

		int i = 0;
		for (String path : iconPaths)
			icons[i++] = reg.registerIcon("erebus:" + path);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < 0 || meta >= icons.length)
			return null;
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < icons.length; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	public int damageDropped(int meta) {
		return meta == 0 ? 1 : meta;
	}
}