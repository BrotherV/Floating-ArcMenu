package com.bvapp.arcmenulibrary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mohsen on 3/6/2017.
 */

public class Util {

	public static int getHeight(Context context, CharSequence text, int textSize,
	                            int deviceWidth, Typeface typeface, int padding) {
		int[] measure = getMeasured(context, text, textSize, deviceWidth, typeface, padding);
		return measure[1];
	}

	public static int getWidth(Context context, CharSequence text, int textSize,
	                            int deviceWidth, Typeface typeface, int padding) {
		int[] measure = getMeasured(context, text, textSize, deviceWidth, typeface, padding);
		return measure[0];
	}

	private static int[] getMeasured(Context context, CharSequence text, int textSize,
	                                 int deviceWidth, Typeface typeface, int padding){
		int[] measure = new int[2];
		TextView textView = new TextView(context);
		textView.setPadding(padding,0,padding,padding);
		textView.setTypeface(typeface);
		textView.setText(text, TextView.BufferType.SPANNABLE);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
		int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		textView.measure(widthMeasureSpec, heightMeasureSpec);
		measure[0] = textView.getMeasuredWidth();
		measure[1] = textView.getMeasuredHeight();
		return measure;
	}

	public static int getDeviceWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}

	public static int getDeviceHeight() {
		return Resources.getSystem().getDisplayMetrics().heightPixels;
	}

	public static int spToPx(float sp) {
		return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
	}

	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static int dpToSp(int dp) {
		return (int) ((float) dpToPx(dp) / (float) spToPx(dp));
	}
}
