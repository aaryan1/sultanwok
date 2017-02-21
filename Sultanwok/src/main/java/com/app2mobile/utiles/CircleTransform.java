package com.app2mobile.utiles;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {
	private static CircleTransform instance = null;

	public static CircleTransform getInstance() {
		if (instance == null) {
			instance = new CircleTransform();
		}
		return instance;
	}

	@Override
	public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());

		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;

		Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
		if (squaredBitmap != source) {
			source.recycle();
		}

		Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		BitmapShader shader = new BitmapShader(squaredBitmap,
				BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
		paint.setShader(shader);
		paint.setAntiAlias(true);

		int borderwidth = 8;
		float r = size / 2f;
		canvas.drawCircle(r, r, r, paint);
		// border code
		paint.setShader(null);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xffffffff);
		paint.setStrokeWidth(borderwidth);
		canvas.drawCircle(r, r, r - borderwidth / 2, paint);
		// --------------------------------------

		squaredBitmap.recycle();
		return bitmap;
	}

	@Override
	public String key() {
		return "circle";
	}
}
