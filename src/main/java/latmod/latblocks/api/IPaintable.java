package latmod.latblocks.api;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public interface IPaintable
{
	boolean isPaintValid(int i, Paint paint);
	boolean setPaint(PaintData data);
}
