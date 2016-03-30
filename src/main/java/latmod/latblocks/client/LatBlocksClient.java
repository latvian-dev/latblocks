package latmod.latblocks.client;

import cpw.mods.fml.relauncher.*;
import ftb.lib.EventBusHelper;
import ftb.lib.api.client.*;
import ftb.lib.api.config.*;
import ftb.lib.api.gui.LMGuiHandlerRegistry;
import ftb.lib.api.tile.TileLM;
import latmod.latblocks.*;
import latmod.latblocks.api.Paint;
import latmod.latblocks.client.render.tile.*;
import latmod.latblocks.client.render.world.*;
import latmod.latblocks.net.MessageOpenDefPaintGui;
import latmod.latblocks.tile.*;
import latmod.latblocks.tile.tank.TileTankBase;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
	private static final ConfigGroup clientConfig = new ConfigGroup("latblocks");
	public static final ConfigEntryBool rotateBlocks = new ConfigEntryBool("rotate_blocks", false);
	public static final ConfigEntryBool renderHighlights = new ConfigEntryBool("render_highlights", true);
	public static final ConfigEntryBool blocksGlow = new ConfigEntryBool("blocks_glow", true);
	public static final ConfigEntryBool fluidsFlowing = new ConfigEntryBool("fluids_flowing", false);
	public static final ConfigEntryCustom defaultPaint = new ConfigEntryCustom("def_paint")
	{
		public void onClicked()
		{ new MessageOpenDefPaintGui().sendToServer(); }
	};
	
	public void preInit()
	{
		EventBusHelper.register(LatBlockClientEventHandler.instance);
		
		// TESR //
		FTBLibClient.addTileRenderer(TileQChest.class, RenderQChest.instance);
		FTBLibClient.addTileRenderer(TileTankBase.class, RenderTankTile.instance);
		
		// ISBRH //
		RenderFountain.instance.register();
		RenderPaintable.instance.register();
		RenderTank.instance.register();
		RenderGlowiumBlocks.instance.register();
		RenderPSlope.instance.register();
		RenderQCable.instance.register();
		
		// IIR //
		FTBLibClient.addItemRenderer(LatBlocksItems.b_qchest, RenderQChest.instance);
		FTBLibClient.addItemRenderer(LatBlocksItems.b_tank, RenderTank.instance);
		FTBLibClient.addItemRenderer(LatBlocksItems.b_tank_water, RenderTank.instance);
		FTBLibClient.addItemRenderer(LatBlocksItems.b_tank_void, RenderTank.instance);
	}
	
	public void postInit()
	{
		ClientConfigRegistry.add(clientConfig.addAll(LatBlocksClient.class, null, false));
		LMGuiHandlerRegistry.add(LatBlocksGuiHandler.instance);
	}
	
	public void spawnFountainParticle(TileFountain t)
	{
		double x = t.xCoord + 0.5D;
		double y = t.yCoord + 0.7D;
		double z = t.zCoord + 0.5D;
		
		if(RenderManager.instance.livingPlayer == null || RenderManager.instance.livingPlayer.getDistanceSq(x, y, z) > 64 * 64)
			return;
		
		double mv = MathHelperLM.sin(t.tick * 0.1D);
		
		double mxz = MathHelperLM.map(mv, -1D, 1D, 0.15D, 0.1D);
		double my = MathHelperLM.map(mv, -1D, 1D, 0.4D, 0.5D);
		
		int c = 8;
		double tick = t.tick * 3D;
		
		Block block = t.tank.getFluid().getBlock();
		
		final int brightness = t.getWorldObj().getLightBrightnessForSkyBlocks(t.xCoord, t.yCoord + 1, t.zCoord, Math.max(t.tank.getFluid().getLuminosity(), block.getLightValue()));
		
		for(int i = 0; i < c * 3; i++)
		{
			double mx = MathHelperLM.sinFromDeg(i * 360D / (double) c + tick) * mxz;
			double mz = MathHelperLM.cosFromDeg(i * 360D / (double) c + tick) * mxz;
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBlockDustFX(t.getWorldObj(), x, y + MathHelperLM.rand.nextFloat() * 0.3D, z, mx, my, mz, block, 0)
			{
				public int getBrightnessForRender(float f)
				{ return brightness; }
			});
		}
	}
	
	public void setDefPaint(TileLM t, EntityPlayer ep, Paint[] paint)
	{
		super.setDefPaint(t, ep, paint);
	}
	
	public static void rotateBlocks()
	{ if(rotateBlocks.get()) GlStateManager.rotate((float) (Minecraft.getSystemTime() * 0.053D), 0F, 1F, 0F); }
}