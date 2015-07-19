package latmod.latblocks.client;
import latmod.ftbu.core.LatCoreMC;
import latmod.ftbu.core.client.ClientConfig;
import latmod.ftbu.core.util.MathHelperLM;
import latmod.latblocks.*;
import latmod.latblocks.client.render.tile.*;
import latmod.latblocks.client.render.world.*;
import latmod.latblocks.tile.*;
import latmod.latblocks.tile.tank.TileTankBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
	private static final ClientConfig clientConfig = new ClientConfig("latblocks");
	public static final ClientConfig.Property rotateBlocks = new ClientConfig.Property("rotate_blocks", false);
	public static final ClientConfig.Property renderHighlights = new ClientConfig.Property("render_highlights", true);
	public static final ClientConfig.Property addAllGlowiumBlocks = new ClientConfig.Property("all_glowium_blocks", true);
	
	public void preInit()
	{
		LatCoreMC.BusType.FORGE.register(LatBlockClientEventHandler.instance);
		
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
		clientConfig.add(addAllGlowiumBlocks);
		ClientConfig.Registry.add(clientConfig);
		
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
		
		for(int i = 0; i < c * 3; i++)
		{
			double mx = MathHelperLM.sinFromDeg(i * 360D / (double)c + tick) * mxz;
			double mz = MathHelperLM.cosFromDeg(i * 360D / (double)c + tick) * mxz;
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBlockDustFX(t.getWorldObj(), x, y + MathHelperLM.rand.nextFloat() * 0.3D, z, mx, my, mz, block, 0));
		}
	}
	
	public static void rotateBlocks()
	{ if(rotateBlocks.getB()) GL11.glRotated(Minecraft.getSystemTime() * 0.053D, 0D, 1D, 0D); }
}