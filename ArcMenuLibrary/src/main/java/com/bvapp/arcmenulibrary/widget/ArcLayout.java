/*
 * Copyright (C) 2015 BrotherV - Modified by Mohsen(BrotherV) on 1/20/2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bvapp.arcmenulibrary.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import com.bvapp.arcmenulibrary.TextStructure;
import com.bvapp.arcmenulibrary.anim.RotateAndTranslateAnimation;

import java.util.ArrayList;


/**
 * A Layout that arranges its children around its center. The arc can be set by
 * calling {@link #setArc(float, float) setArc()}. You can override the method
 * {@link #onMeasure(int, int) onMeasure()}, otherwise it is always
 * WRAP_CONTENT.
 *
 * @author BrotherV
 *
 */
public class ArcLayout extends RelativeLayout {

	public interface  OnMenuItemOpenClose{
		public void menuStatus(boolean s);
	}

	private OnMenuItemOpenClose menuListener;

	public static final int     TOP_LEFT	     = 0xF01;
	public static final int     TOP_RIGHT	    = 0xF02;
	public static final int     TOP_MIDDLE	   = 0xF03;
	public static final int     BOTTOM_LEFT	  = 0xF04;
	public static final int     BOTTOM_RIGHT	 = 0xF05;
	public static final int     BOTTOM_MIDDLE	= 0xF06;
	public static final int     RIGHT_MIDDLE	 = 0xF07;
	public static final int     LEFT_MIDDLE	  = 0xF08;
	public static final int     CENTER	       = 0xF09;

	public static final float DEFAULT_FROM_DEGREES = 270.0f;
	public static final float DEFAULT_TO_DEGREES   = 360.0f;
	public static final int   DEFAULT_HINT_GRAVITY = CENTER;

	private static final int  MIN_RADIUS	   = 100;

	public static final int TOOLTIP_UP= 0xF20;
	public static final int TOOLTIP_DOWN= 0xF21;
	public static final int TOOLTIP_RIGHT= 0xF22;
	public static final int TOOLTIP_LEFT= 0xF23;

	private Context mContext;
	private int	       mLayoutCenterX;
	private int	       mLayoutCenterY;
	private int	       childCount	   = 2;
	private int	       mMenuSize	   = 32;
	private int	       mChildSize	   = 32;
	private int	       mChildPadding	= 5;
	private int	       mLayoutPadding       = 10;
	private int	       mLayoutHeight;
	private int	       mLayoutWidth;
	private int	       mDefaultShift;
	private int	       mDuration = 300;
	private int		 mMarginTop;
	private int		 mMarginBottom;
	private int		 mMarginRight;
	private int		 mMarginLeft;
	private int mViewWidth;
	private int mViewHeight;
	private long	mPreOffset;
	private int	       menuGravity	  = DEFAULT_HINT_GRAVITY;
	private float	     mFromDegrees	 = DEFAULT_FROM_DEGREES;
	private float	     mToDegrees	   = DEFAULT_TO_DEGREES;
	private float	     tDeg	 = DEFAULT_FROM_DEGREES;
	private float	     tPerDeg	   = DEFAULT_TO_DEGREES;

	/* the distance between the layout's center and any child's center */
	private int	       mRadius = 0;
	private int	       mMinRadius = MIN_RADIUS;
	private boolean	   mExpanded	    = false;
	private boolean	   mExpandDone;
	private boolean	   mAnimDone = true;
	private boolean	   mRaiusCtrl;

	/*    */
	private ArrayList<TextStructure> textStructure = new ArrayList<>();
	private boolean	   menuItemRotatationInClosing;
	private boolean	   checkCenterGravity;
	private boolean	   toolTipCtrl;
	private int	   mToolTipSide;
	private int	   mPreChildOffset;

	/**
	 *
	 * @param context
	 */
	public ArcLayout(Context context) {
		super(context);
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public ArcLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/**
	 *
	 * @param arcDegrees
	 * @param childCount
	 * @param childSize
	 * @param childPadding
	 * @param minRadius
	 * @return
	 */
	private static int computeRadius(final float arcDegrees,
	                                 final int childCount, final int childSize, final int childPadding,
	                                 final int minRadius) {
		if (childCount < 2) {
			return minRadius;
		}

		final float perDegrees = arcDegrees / (childCount - 1);
		final float perHalfDegrees = perDegrees / 2;
		final int perSize = childSize + childPadding;

		final int radius = (int) ((perSize / 2) / Math.sin(Math
				.toRadians(perHalfDegrees)));

		return Math.max(radius, minRadius);
	}

	/**
	 *
	 * @param centerX
	 * @param centerY
	 * @param radius
	 * @param degrees
	 * @param size
	 * @return
	 */
	private static Rect computeChildFrame(final int centerX, final int centerY,
	                                      final int radius, final float degrees, final int size) {

		final double childCenterX = centerX + radius
				* Math.cos(Math.toRadians(degrees));
		final double childCenterY = centerY + radius
				* Math.sin(Math.toRadians(degrees));

		return new Rect((int) (childCenterX - size / 2),
				(int) (childCenterY - size / 2),
				(int) (childCenterX + size / 2),
				(int) (childCenterY + size / 2));
	}

	private static Rect computeChildFrame(final int centerX, final int centerY,
	                                      final int radius, final int shiftY, final int shiftX, final float degrees, final int h, int w) {

		final double childCenterX = centerX + (shiftX + radius)
				* Math.cos(Math.toRadians(degrees));
		final double childCenterY = centerY + (shiftY + radius)
				* Math.sin(Math.toRadians(degrees));

		return new Rect((int) (childCenterX - w / 2),
				(int) (childCenterY - h / 2),
				(int) (childCenterX + w / 2),
				(int) (childCenterY + h / 2));
	}


	private static double computeOffsetY(final int offset, final float degrees) {
		return offset
				* Math.sin(Math.toRadians(degrees));
	}

	private static double computeOffsetX(final int offset, final float degrees) {
		return offset
				* Math.cos(Math.toRadians(degrees));
	}

	/**
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int radius = mRadius = computeRadius(
				Math.abs(mToDegrees - mFromDegrees), getChildCount()/2,
				mChildSize, mChildPadding, mMinRadius);

		final int size = radius * 3 + mChildSize + mChildPadding
				+ mLayoutPadding * 2 + mMenuSize + 4 * mDefaultShift + mViewWidth;

		switch (menuGravity) {
			case BOTTOM_LEFT:
				setMeasuredDimension(size / 2, size / 2);
				break;
			case BOTTOM_MIDDLE:
				setMeasuredDimension(size, size / 2);
				break;
			case BOTTOM_RIGHT:
				setMeasuredDimension(size / 2, size / 2);
				break;
			case LEFT_MIDDLE:
				setMeasuredDimension(size / 2, size);
				break;
			case RIGHT_MIDDLE:
				setMeasuredDimension(size / 2, size);
				break;
			case TOP_LEFT:
				setMeasuredDimension(size / 2, size / 2);
				break;
			case TOP_MIDDLE:
				setMeasuredDimension(size, size / 2);
				break;
			case TOP_RIGHT:
				setMeasuredDimension(size / 2, size / 2);
				break;
			case CENTER:
				setMeasuredDimension(size, size);
				break;
			default:
				setMeasuredDimension(size, size);
		}


		final int count = getChildCount();
		TextStructure str = new TextStructure();
		int j = 0;
		for (int i = 0; i < count; i++) {
			if(i % 2 != 0){
				str = textStructure.get(j);
				j++;
			}
			//Log.i("Child measure", "Size Height: " + str.h + "   Size Width: " + str.w);
			getChildAt(i)
					.measure(
							MeasureSpec.makeMeasureSpec((i%2 == 0 )? mChildSize : str.w,
									MeasureSpec.EXACTLY),
							MeasureSpec.makeMeasureSpec((i%2 == 0 )? mChildSize : str.h,
									MeasureSpec.EXACTLY));
		}
	}

	private void getLayoutCenter(){
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;

		mLayoutHeight = getHeight();
		mLayoutWidth = getWidth();
		switch (menuGravity) {
			case BOTTOM_LEFT:
				centerX = (int) (1.5 * mDefaultShift + mMenuSize/2);
				centerY = (int) (getHeight() - 1.5 * mDefaultShift - mMenuSize/2);
				break;
			case BOTTOM_MIDDLE:
				centerX = getWidth() / 2;
				centerY = (int) (getHeight()- 1.5 * mDefaultShift - mMenuSize/2);
				break;
			case BOTTOM_RIGHT:
				centerX = (int) (getWidth()- 1.5 * mDefaultShift - mMenuSize/2);
				centerY = (int) (getHeight()- 1.5 * mDefaultShift - mMenuSize/2);
				break;
			case LEFT_MIDDLE:
				centerX = (int) (1.5 * mDefaultShift + mMenuSize/2);
				centerY = getHeight() / 2;
				break;
			case RIGHT_MIDDLE:
				centerX = (int) (getWidth()- 1.5 * mDefaultShift - mMenuSize/2);
				centerY = getHeight() / 2;
				break;
			case TOP_LEFT:
				centerX = (int) (1.5 * mDefaultShift + mMenuSize/2);
				centerY = (int) (1.5 * mDefaultShift + mMenuSize/2);
				break;
			case TOP_MIDDLE:
				centerX = getWidth()/2;
				centerY = (int) (1.5 * mDefaultShift + mMenuSize/2);
				break;
			case TOP_RIGHT:
				centerX = (int) (getWidth()- 1.5 * mDefaultShift - mMenuSize/2);
				centerY = (int) (1.5 *  mDefaultShift + mMenuSize/2);
				break;
			case CENTER:
				centerX = getWidth() / 2;
				centerY = getHeight() / 2;
				break;
		}

		mLayoutCenterX = centerX;
		mLayoutCenterY = centerY;
	}
	/**
	 *
	 * @param changed
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		getLayoutCenter();
		int centerX = mLayoutCenterX;
		int centerY = mLayoutCenterY;

		final int radius = mExpanded ? mRadius : 0;

		if ((menuGravity == CENTER) && !checkCenterGravity) {
			mToDegrees -= mToDegrees / (getChildCount()/2);
			checkCenterGravity = true;
		}

		childCount = getChildCount()/2;
		final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
		tPerDeg = (Math.abs(mToDegrees - mFromDegrees) -5) / (childCount - 1);
		if(perDegrees < 0){
			tPerDeg *= -1;
		}

		float degrees = mFromDegrees;
		float tDeg = degrees;
		if(mFromDegrees == -5){
			tDeg = -2.5f;
		}else if(mFromDegrees == -95){
			tDeg = -92.5f;
		}else if(mFromDegrees == 85){
			tDeg = 87.5f;
		}else if(mFromDegrees == 275){
			tDeg = 272.5f;
		}else if(mFromDegrees == 175){
			tDeg = 177.5f;
		} else{
			tDeg = 0;
		}
		this.tDeg = tDeg;

		TextStructure tStructure = new TextStructure();
		int j = 0;
		int childAt = 0;
		for (int i = 0; i < childCount; i++) {
			Rect frame = computeChildFrame(centerX, centerY, radius, degrees,
					mChildSize);
			getChildAt(childAt).layout(frame.left, frame.top, frame.right,
					frame.bottom);
			getChildAt(childAt).setVisibility(mExpanded ? VISIBLE :INVISIBLE);
			childAt++;

			if(toolTipCtrl){
				tStructure = textStructure.get(j);
				j++;
				int l1, r1, t1, b1;
				l1 = r1 = t1 = b1 = 0;
				frame = computeChildFrame(centerX, centerY, mExpanded ? mRadius : 0, 0, 0, degrees,
						getChildAt(childAt).getMeasuredHeight(), getChildAt(childAt).getMeasuredWidth());
				if(mToolTipSide == TOOLTIP_UP){
					t1 = getFrameOffsetY(tStructure.h);
				}else if(mToolTipSide == TOOLTIP_DOWN){
					b1 = getFrameOffsetY(tStructure.h);
				}else if(mToolTipSide == TOOLTIP_RIGHT){
					r1 = getFrameOffsetX(tStructure.w);
				}else if(mToolTipSide == TOOLTIP_LEFT){
					l1 = getFrameOffsetX(tStructure.w);
				}else {
					int shiftX = getShift(mFromDegrees, mToDegrees, tDeg, tStructure.w);
					int shiftY = mChildSize;
					frame = computeChildFrame(centerX, centerY, mExpanded ? mRadius : 0, shiftY, shiftX, tDeg,
							getChildAt(childAt).getMeasuredHeight(), getChildAt(childAt).getMeasuredWidth());
				}

				getChildAt(childAt).layout(frame.left - l1 + r1, frame.top - t1 + b1, frame.right - l1 + r1, frame.bottom - t1 + b1);
				getChildAt(childAt).setVisibility(mExpanded ? VISIBLE :INVISIBLE);
			}else{
				getChildAt(childAt).setVisibility(INVISIBLE);
			}

			childAt++;
			degrees += perDegrees;
			tDeg += tPerDeg;
		}
	}

	private int getFrameOffsetX(int w){
		/*
		mPreChildOffset = w;
		for(TextStructure str: textStructure){
			if(str.w > mPreChildOffset){
				mPreChildOffset = str.w;
			}
		}
		return (mPreChildOffset + mChildSize + (int) dpToPx(4))/2;
		*/
		return (w + mChildSize + (int) dpToPx(4))/2  ;
	}

	private int getFrameOffsetY(int h){
		return h > mChildSize ? h : mChildSize;
	}
	/**
	 *
	 * @param fDeg
	 * @param tDeg
	 * @param cDeg
	 * @param w
	 * @return
	 */
	private int getShift(float fDeg, float tDeg, float cDeg, int w){
		int shift = mChildSize;
		if(fDeg == 265 && tDeg == 365){
			if(Math.abs(cDeg - 365) < 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == 175 && tDeg == 365){
			if(Math.abs(cDeg - 365) < 45 || Math.abs(cDeg - 175) < 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == 275 && tDeg == 175){
			if(Math.abs(cDeg - 275) > 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == -95 && tDeg == 95){
			if(Math.abs(cDeg - (-95f)) > 45 || Math.abs(cDeg - 95) > 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == 275 && tDeg == 85){
			if(Math.abs(cDeg - 275) > 45 || Math.abs(cDeg - 85) > 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == -5 && tDeg == 95){
			if(Math.abs(cDeg - 95) > 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == -5 && tDeg == 185){
			if(Math.abs(cDeg) < 45 || Math.abs(cDeg - 185) < 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == 85 && tDeg == 185){
			if(Math.abs(cDeg - 85) > 45){
				if(w > mChildSize){
					shift = w;
				}
			}
		}else if(fDeg == 0 && tDeg == 360){
			if((Math.abs(cDeg - 270) > 45 && cDeg > 135) ||
					Math.abs(cDeg - 360) < 45 || cDeg < 45 ){
				if(w > mChildSize){
					shift = w;
				}
			}
		}
		return shift;
	}
	/**
	 *
	 * @param child
	 * @param index
	 * @param duration
	 */
	private void bindChildAnimation(final View child, final int index, final int order,
	                                final long duration) {
		final boolean expanded = mExpanded;
		getLayoutCenter();
		int centerX = mLayoutCenterX;
		int centerY = mLayoutCenterY;

		final int radius = expanded ? 0 : mRadius;

		final int childCount = getChildCount()/2;
		final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
		Rect frame;
		if(order % 2 != 0){
			frame = computeChildFrame(centerX, centerY, mExpanded ? 0 : mRadius, 0, 0,mFromDegrees
					+ index * perDegrees, child.getMeasuredHeight(), child.getMeasuredWidth());
			int offsetX = getFrameOffsetX(child.getMeasuredWidth());
			int offsetY = getFrameOffsetY(child.getMeasuredHeight());
			if(mToolTipSide == TOOLTIP_UP){
				frame.top -= offsetY;
				frame.bottom -= offsetY;
			}else if(mToolTipSide == TOOLTIP_DOWN){
				frame.top += offsetY;
				frame.bottom += offsetY;
			}else if(mToolTipSide == TOOLTIP_RIGHT){
				frame.left += offsetX;
				frame.right += offsetX;
			}else if(mToolTipSide == TOOLTIP_LEFT){
				frame.left -= offsetX;
				frame.right -= offsetX;
			}else{
				int shift = getShift(mFromDegrees, mToDegrees, tDeg + index * tPerDeg, child.getMeasuredWidth());
				frame = computeChildFrame(centerX, centerY, mExpanded ? 0 : mRadius, mChildSize, shift,tDeg
						+ index * tPerDeg, child.getMeasuredHeight(), child.getMeasuredWidth());
			}
		}else{
			frame = computeChildFrame(centerX, centerY, radius, mFromDegrees
					+ index * perDegrees, mChildSize);
		}

		int toXDelta = frame.left - child.getLeft();
		int toYDelta = frame.top - child.getTop();

		Interpolator interpolator = mExpanded ? new AccelerateInterpolator()
				: new OvershootInterpolator(1.5f);
		final long startOffset = computeStartOffset(childCount, mExpanded,
				index, 0.1f, duration, interpolator);
		if(order % 2 == 0){
			mPreOffset = startOffset;
		}

		Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0,
				toYDelta, mPreOffset, duration, interpolator , order)
				: createExpandAnimation(0, toXDelta, 0, toYDelta, mPreOffset,
				duration, interpolator, order);

		final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				mAnimDone = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isLast) {
					postDelayed(new Runnable() {

						@Override
						public void run() {
							onAllAnimationsEnd();
						}
					}, 0);
				}
			}
		});

		child.setAnimation(animation);
	}


	/**
	 * refers to {@link LayoutAnimationController#getDelayForView(View view)}
	 */
	private long computeStartOffset(final int childCount,
	                                final boolean expanded, final int index, final float delayPercent,
	                                final long duration, Interpolator interpolator) {
		final float delay = delayPercent * duration;
		final long viewDelay = (long) (getTransformedIndex(expanded,
				childCount, index) * delay);
		final float totalDelay = delay * childCount;

		float normalizedDelay = viewDelay / totalDelay;
		normalizedDelay = interpolator.getInterpolation(normalizedDelay);

		return (long) (normalizedDelay * totalDelay);
	}

	/**
	 *
	 * @param expanded
	 * @param count
	 * @param index
	 * @return
	 */
	private int getTransformedIndex(final boolean expanded, final int count,
	                                final int index) {
		if (expanded) {
			return count - 1 - index;
		}

		return index;
	}

	/**
	 *
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param startOffset
	 * @param duration
	 * @param interpolator
	 * @return
	 */
	private Animation createExpandAnimation(float fromXDelta, float toXDelta,
	                                        float fromYDelta, float toYDelta, long startOffset, long duration,
	                                        Interpolator interpolator, int index) {
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.setFillAfter(true);

		Animation translateAnimation = new RotateAndTranslateAnimation(0,
				toXDelta, 0, toYDelta, 0, 720);
		translateAnimation.setStartOffset(startOffset);
		translateAnimation.setDuration(duration);
		translateAnimation.setInterpolator(interpolator);
		translateAnimation.setFillAfter(true);

		animationSet.addAnimation(translateAnimation);

		Animation alphaT = new AlphaAnimation(0f, 1.0f);
		alphaT.setStartOffset(startOffset);
		alphaT.setDuration(duration);
		alphaT.setInterpolator(interpolator);
		alphaT.setFillAfter(true);

		Animation alpha = new AlphaAnimation(0f, 1.0f);
		alpha.setDuration((long) (duration * 0.2));
		alpha.setInterpolator(interpolator);
		alpha.setFillAfter(true);

		if(index % 2 != 0){
			animationSet.addAnimation(alphaT);
		}else {
			animationSet.addAnimation(alpha);
		}
		return animationSet;
	}

	/**
	 *
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param startOffset
	 * @param duration
	 * @param interpolator
	 * @return
	 */
	private Animation createShrinkAnimation(float fromXDelta, float toXDelta,
	                                        float fromYDelta, float toYDelta, long startOffset, long duration,
	                                        Interpolator interpolator, int index) {
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.setFillAfter(true);

		final long preDuration = duration / 2;
		Animation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setStartOffset(startOffset);
		rotateAnimation.setDuration(preDuration);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setFillAfter(true);

		if (menuItemRotatationInClosing) {
			animationSet.addAnimation(rotateAnimation);
		}

		Animation translateAnimation = new RotateAndTranslateAnimation(0,
				toXDelta, 0, toYDelta, 360, 720);
		translateAnimation.setStartOffset(startOffset + preDuration);
		translateAnimation.setDuration(duration - preDuration);
		translateAnimation.setInterpolator(interpolator);
		translateAnimation.setFillAfter(true);

		animationSet.addAnimation(translateAnimation);

		Animation alpha = new AlphaAnimation(1.0f, 0f);
		alpha.setStartOffset(startOffset + preDuration);
		alpha.setDuration(duration - preDuration);
		alpha.setInterpolator(interpolator);
		alpha.setFillAfter(true);

		if(index % 2 != 0){
			animationSet.addAnimation(alpha);
		}
		return animationSet;
	}

	/**
	 *
	 * @param px
	 * @return
	 */
	public static float pxToDp(float px) {
		return px / Resources.getSystem().getDisplayMetrics().density;
	}

	/**
	 *
	 * @param dp
	 * @return
	 */
	public static float dpToPx(float dp) {
		return dp * Resources.getSystem().getDisplayMetrics().density;
	}

	/**
	 *
	 * @return
	 */
	public boolean isExpanded() {
		return mExpanded;
	}

	/**
	 *
	 * @return
	 */
	public boolean isExpandDone() {
		return mExpandDone;
	}

	/**
	 *
	 * @return
	 */
	public boolean isAnimDone() {
		return mAnimDone;
	}

	/**
	 *
	 * @param m
	 */
	public void setExpandDone(boolean m) {
		mExpandDone = m;
	}

	/**
	 *
	 * @param m
	 */
	public void setExpandMenu(boolean m) {
		mExpanded = m;
	}

	/**
	 *
	 * @param m
	 */
	public void setAnimDone(boolean m) {
		mAnimDone = m;
	}

	/**
	 *
	 * @param fromDegrees
	 * @param toDegrees
	 */
	public void setArc(float fromDegrees, float toDegrees) {
		if (mFromDegrees == fromDegrees && mToDegrees == toDegrees) {
			return;
		}

		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;

		requestLayout();
	}

	public void setToolTipSide(int mToolTipSide) {
		this.mToolTipSide = mToolTipSide;
	}

	/**
	 *
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setMargin(int left, int top, int right, int bottom){
		mMarginBottom = bottom;
		mMarginLeft = left;
		mMarginRight = right;
		mMarginTop = top;
		requestLayout();
	}

	/**
	 *
	 * @param r
	 */
	public void setMinRadius(int r) {
		mMinRadius = r;
		requestLayout();
	}

	/**
	 *
	 * @param size
	 */
	public void setChildSize(int size) {
		if (mChildSize == size || size < 0) {
			return;
		}

		mChildSize = size;
		requestLayout();
	}

	/**
	 *
	 * @param size
	 */
	public void setMenuSize(int size) {
		mMenuSize = size;
		requestLayout();
	}

	/**
	 *
	 * @param gravity
	 */
	public void setMenuGravity(int gravity) {
		menuGravity = gravity;
		requestLayout();
	}

	/**
	 *
	 * @param shift
	 */
	public void setDefaultShift(int shift) {
		mDefaultShift = shift;
		requestLayout();
	}

	public void setTextViewSize(int w, int h) {
		mViewHeight = h;
		mViewWidth = w;
		requestLayout();
	}

	public void setDuration(int duration){
		if(duration > 100){
			mDuration = duration;
		}
	}

	/**
	 *
	 * @param rotate
	 */
	public void setItemRotation(boolean rotate) {
		menuItemRotatationInClosing = rotate;
	}

	/**
	 *
	 * @return
	 */
	public int getChildSize() {
		return mChildSize;
	}

	/**
	 *
	 * @return
	 */
	public int getLayoutCenterX() {
		return mLayoutCenterX;
	}

	/**
	 *
	 * @return
	 */
	public int getLayoutCenterY() {
		return mLayoutCenterY;
	}

	/**
	 *
	 * @return
	 */
	public int getRadius() {
		return mRadius;
	}

	public void setRadius(int mRadius) {
		this.mRadius = mRadius;
		mRaiusCtrl = true;
		requestLayout();
	}

	/**
	 *
	 * @param l
	 */
	public void showTooltip(boolean l){
		toolTipCtrl = l;
	}

	/**
	 * switch between expansion and shrinkage
	 *
	 * @param showAnimation
	 */
	public void switchState(final boolean showAnimation) {
		if (showAnimation) {
			final int childCount = getChildCount();
			int j = 0;
			for (int i = 0; i < childCount; i++) {
				if(i % 2 == 0 && i != 0){
					j++;
				}
				if(i % 2 != 0 && !toolTipCtrl){
					continue;
				}
				bindChildAnimation(getChildAt(i), j, i, mDuration);
			}
		}

		mExpanded = !mExpanded;
		if (!showAnimation) {
			requestLayout();
		}
		invalidate();
	}

	/**
	 *
	 */
	private void onAllAnimationsEnd() {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			getChildAt(i).clearAnimation();
		}
		mAnimDone = true;
		mExpandDone = mExpanded;

		if(menuListener != null){
			menuListener.menuStatus(mExpanded);
		}
		requestLayout();
	}

	public void setTextSize(int w, int h){
		TextStructure str = new TextStructure();
		str.h = h;
		str.w = w;
		textStructure.add(str);
	}

	public void setOnMenuItemOpenClose(OnMenuItemOpenClose l){
		menuListener = l;
	}
}
