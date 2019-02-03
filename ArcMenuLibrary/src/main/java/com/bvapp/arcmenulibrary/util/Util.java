package com.bvapp.arcmenulibrary.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.bvapp.arcmenulibrary.R;

import androidx.core.content.res.ResourcesCompat;

/**
 * Created by Mohsen on 3/6/2017.
 */

public class Util {

	private static TypedValue value;
	public static final long FRAME_DURATION = 1000 / 60;

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

	public static void setBackground(View v, Drawable drawable){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			v.setBackground(drawable);
		else
			v.setBackgroundDrawable(drawable);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorPrimary(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorPrimary, defaultValue);

		return getColor(context, R.attr.colorPrimary, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorPrimaryDark(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorPrimaryDark, defaultValue);

		return getColor(context, R.attr.colorPrimaryDark, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorAccent(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorAccent, defaultValue);

		return getColor(context, R.attr.colorAccent, defaultValue);
	}

	public static int getType(TypedArray array, int index){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return array.getType(index);
		else{
			TypedValue value = array.peekValue(index);
			return value == null ? TypedValue.TYPE_NULL : value.type;
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlNormal(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlNormal, defaultValue);

		return getColor(context, R.attr.colorControlNormal, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlActivated(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlActivated, defaultValue);

		return getColor(context, R.attr.colorControlActivated, defaultValue);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static int colorControlHighlight(Context context, int defaultValue){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			return getColor(context, android.R.attr.colorControlHighlight, defaultValue);

		return getColor(context, R.attr.colorControlHighlight, defaultValue);
	}

	private static int getColor(Context context, int id, int defaultValue){
		if(value == null)
			value = new TypedValue();

		try{
			Resources.Theme theme = context.getTheme();
			if(theme != null && theme.resolveAttribute(id, value, true)){
				if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT)
					return value.data;
				else if (value.type == TypedValue.TYPE_STRING)
					return ResourcesCompat.getColor(context.getResources(), value.resourceId, null);//return context.getResources().getColor(value.resourceId);
			}
		}
		catch(Exception ex){}

		return defaultValue;
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


	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	public static Animation scaleAnimationRelativeToSelf(float xStart,
	                                                     float xEnd, float yStart, float yEnd, float pivotX, float pivotY,
	                                                     int durationMillis, boolean fill) {
		Animation anim = new ScaleAnimation(xStart, xEnd, // Start and end
				// values for
				// the X axis scaling
				yStart, yEnd, // Start and end values for the Y axis scaling
				Animation.RELATIVE_TO_SELF, pivotX, // Pivot point of X scaling
				Animation.RELATIVE_TO_SELF, pivotY); // Pivot point of Y scaling
		anim.setDuration(durationMillis);
		anim.setFillAfter(fill); // Needed to keep the result of the animation
		return anim;
	}

	public static void shrinkExpandAnimation(final View myView, final View.OnClickListener listener) {
		if(myView != null){
			final Animation animClickIn = scaleAnimationRelativeToSelf(1.0f, 0.85f,
					1.0f, 0.85f, 0.5f, 0.5f, 80,false);
			final Animation animClickOut = scaleAnimationRelativeToSelf(0.85f, 1.0f,
					0.85f, 1.0f, 0.5f, 0.5f, 80,false);

			myView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					myView.startAnimation(animClickIn);
				}
			});

			animClickIn.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}

				@Override
				public void onAnimationEnd(Animation animation) {
					myView.startAnimation(animClickOut);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {}
			});

			animClickOut.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}

				@Override
				public void onAnimationEnd(Animation animation) {
					if (listener != null){
						listener.onClick(myView);
					}
				}

				@Override
				public void onAnimationRepeat(Animation animation) {}
			});
		}
	}
}
