package eu.inmite.android.gridwichterle.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import eu.inmite.android.gridwichterle.core.Config;
import eu.inmite.android.gridwichterle.core.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal Matl (michal.matl@inmite.eu)
 * Date: 7/21/13
 * Time: 4:32 PM
 */
public class DrawView extends View {

	private final Paint paint = new Paint();
	private final int height;
	private final int width;
	private final int mSquare;
	private float[] points;

	public DrawView(Context context, int height, int width) {
		super(context);

		this.height = height;
		this.width = width;

		Config config = (Config) getContext().getApplicationContext().getSystemService(Config.class.getName());
		paint.setColor(config.getColor());
		mSquare = Utils.getPxFromDpi(getContext(), config.getGridSideSize());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		List<Float> gridPoints = new LinkedList<Float>();

		int countHorizontalLines = height / mSquare;
		int countVerticalLines = width / mSquare;

		//prepare horizontal lines
		float gap = mSquare;
		for (int i = 0; i <= countHorizontalLines; i++) {
			gridPoints.add(0f);
			gridPoints.add(gap);
			gridPoints.add((float) width);
			gridPoints.add(gap);

			gap = gap + mSquare;

		}

		//prepare vertical lines
		gap = mSquare;
		for (int i = 0; i <= countVerticalLines; i++) {
			gridPoints.add(gap);
			gridPoints.add(0f);
			gridPoints.add(gap);
			gridPoints.add((float) height);

			gap = gap + mSquare;
		}

		points = new float[gridPoints.size()];
		for (int i = 0; i < gridPoints.size(); i++) {
			points[i] = gridPoints.get(i);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//draw whole grid
		canvas.drawLines(points, paint);
	}
}
