package latmod.latblocks.client;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import ftb.lib.EventBusHelper;
import latmod.ftbu.api.client.*;
import latmod.ftbu.api.paint.Paint;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.*;
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
	private static final ClientConfig clientConfig = new ClientConfig("latblocks");
	public static final ClientConfigProperty rotateBlocks = new ClientConfigProperty("rotate_blocks", false);
	public static final ClientConfigProperty renderHighlights = new ClientConfigProperty("render_highlights", true);
	public static final ClientConfigProperty blocksGlow = new ClientConfigProperty("blocks_glow", true);
	public static final ClientConfigProperty fluidsFlowing = new ClientConfigProperty("fluids_flowing", false);
	
	public static final ClientConfigProperty defaultPaint = new ClientConfigProperty("def_paint", 0, "edit")
	{
		public void onClicked()
		{ new MessageOpenDefPaintGui().sendToServer(); }
	};
	
	public void preInit()
	{
		EventBusHelper.register(LatBlockClientEventHandler.instance);
		
		// TESR //
		RenderQChest.instance.register(TileQChest.class);
		RenderTankTile.instance.register(TileTankBase.class);
		
		// ISBRH //
		RenderFountain.instance.register();
		RenderPaintable.instance.register();
		RenderTank.instance.register();
		RenderGlowiumBlocks.instance.register();
		RenderPSlope.instance.register();
		RenderQCable.instance.register();
		
		// IIR //
		RenderQChest.instance.registerItemRenderer(LatBlocksItems.b_qchest);
		RenderTank.instance.registerItemRenderer(LatBlocksItems.b_tank);
		RenderTank.instance.registerItemRenderer(LatBlocksItems.b_tank_water);
		RenderTank.instance.registerItemRenderer(LatBlocksItems.b_tank_void);
	}
	
	public void postInit()
	{
		clientConfig.add(rotateBlocks);
		clientConfig.add(renderHighlights);
		clientConfig.add(blocksGlow);
		clientConfig.add(fluidsFlowing);
		clientConfig.add(defaultPaint);
		ClientConfigRegistry.add(clientConfig);
		
		LatBlocksGuiHandler.instance.registerClient();
	}
	
	public void spawnFountainParticle(TileFountain t)
	{
		double x = t.xCoord + 0.5D;
		double y = t.yCoord + 0.7D;
		double z = t.zCoord + 0.5D;
		
		if(RenderManager.instance.livingPlayer == null || RenderManager.instance.livingPlayer.getDistanceSq(x, y, z) > 64 * 64) return;
		
		double mv = MathHelperLM.sin(t.tick * 0.1D);
		
		double mxz = MathHelperLM.map(mv, -1D, 1D, 0.15D, 0.1D);
		double my = MathHelperLM.map(mv, -1D, 1D, 0.4D, 0.5D);
		
		int c = 8;
		double tick = t.tick * 3D;
		
		Block block = t.tank.getFluid().getBlock();
		
		final int brightness = t.getWorldObj().getLightBrightnessForSkyBlocks(t.xCoord, t.yCoord + 1, t.zCoord, Math.max(t.tank.getFluid().getLuminosity(), block.getLightValue()));
		
		for(int i = 0; i < c * 3; i++)
		{
			double mx = MathHelperLM.sinFromDeg(i * 360D / (double)c + tick) * mxz;
			double mz = MathHelperLM.cosFromDeg(i * 360D / (double)c + tick) * mxz;
			
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
	{ if(rotateBlocks.getB()) GL11.glRotated(Minecraft.getSystemTime() * 0.053D, 0D, 1D, 0D); }
}