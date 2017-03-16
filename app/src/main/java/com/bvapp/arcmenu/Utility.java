package com.bvapp.arcmenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Created by Mohsen on 2/5/2017.
 */

public class Utility {

	public static RoundedBitmapDrawable getRoundedBitmap(Context context, @DrawableRes int id) {
		Resources res = context.getResources();
		Bitmap src = BitmapFactory.decodeResource(res, id);

		int size = Math.max(src.getHeight(), src.getWidth());
		RoundedBitmapDrawable dr =
				RoundedBitmapDrawableFactory.create(res, src);
		dr.setCornerRadius(size/8.0f);
		return dr;
	}
}
