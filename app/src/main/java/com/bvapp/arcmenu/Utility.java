package com.bvapp.arcmenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

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
