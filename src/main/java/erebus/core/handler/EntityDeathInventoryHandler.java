package erebus.core.handler;

import java.util.Calendar;
import java.util.List;

import erebus.ModBlocks;
import erebus.blocks.BlockBones;
import erebus.core.capabilities.player.IPlayerDeathLocationCapability;
import erebus.core.capabilities.player.PlayerDeathLocationCapability;
import erebus.core.helper.Utils;
import erebus.tileentity.TileEntityBones;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDeathInventoryHandler {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerDrops(PlayerDropsEvent event) {

		final List<EntityItem> drops = event.getDrops();
		if (drops.isEmpty()) return;

		World world = event.getEntityLiving().world;
		if (world.isRemote)
			return;

		if (event.getEntityLiving() instanceof EntityPlayer && !world.getGameRules().getBoolean("keepInventory")) {
			final EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			IPlayerDeathLocationCapability cap = player.getCapability(PlayerDeathLocationCapability.CAPABILITY_PLAYER_DEATH_LOCATION, null);
			BlockPos deathPos=player.getPosition();
			//Prevent void issue (Y<0)
			BlockPos posBones=new BlockPos(deathPos.getX(),Math.max(2,deathPos.getY()+1),deathPos.getZ());
			EnumFacing playerFacing = player.getHorizontalFacing();
			for (EnumFacing offset : EnumFacing.HORIZONTALS) {
					BlockPos offeredPos=posBones.offset(offset);
					if (world.getBlockState(offeredPos).getMaterial().isReplaceable()) {
						posBones = offeredPos;
						break;
					}
			}
			world.setBlockState(posBones, ModBlocks.BLOCK_OF_BONES.getDefaultState().withProperty(BlockBones.FACING, playerFacing), 3);
			cap.setGraveDimension(player.world.provider.getDimension());
			cap.setGraveDimensionName(player.world.provider.getDimensionType().getName());
			cap.setGraveLocationX(posBones.getX());
			cap.setGraveLocationZ(posBones.getZ());
			cap.setDeathTime(getDeathTimeNow());

			TileEntityBones tile = Utils.getTileEntity(world, new BlockPos(posBones), TileEntityBones.class);
			if (tile != null) {
				int index = 0;
				for (int i = 0; i < drops.size(); i++) {
					if (index >= 86 || index >= drops.size())
						break;
					EntityItem entityitem = drops.get(index++);
					if (entityitem != null) {
						ItemStack stack = entityitem.getItem();
						if (stack != null) {
							tile.setInventorySlotContents(i, stack.copy());
							entityitem.setDead();
						}
					}
				}
				tile.setOwner("R.I.P. " + player.getCommandSenderEntity().getName());
				event.setCanceled(true);
			}
		}
	}
	
	public String getDeathTimeNow() {
		String[] MONTH = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		String month = MONTH[Calendar.getInstance().get(Calendar.MONTH)];
		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
		String total = (hour.length() == 1 ? '0' + hour : hour) + ":" + (minute.length() == 1 ? '0' + minute : minute) + ":" + (second.length() == 1 ? '0' + second : second) + " " + (day.length() == 1 ? '0' + day : day) + "/" + month + "/" + year;
		return total;
	}
}