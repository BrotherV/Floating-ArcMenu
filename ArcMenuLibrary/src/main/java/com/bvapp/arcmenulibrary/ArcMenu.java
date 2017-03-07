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

package com.bvapp.arcmenulibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 */
public class ArcMenu extends RelativeLayout {

	public static final int     TOP_LEFT	     = 0xF01;
	public static final int     TOP_RIGHT	    = 0xF02;
	public static final int     TOP_MIDDLE	   = 0xF03;
	public static final int     BOTTOM_LEFT	  = 0xF04;
	public static final int     BOTTOM_RIGHT	 = 0xF05;
	public static final int     BOTTOM_MIDDLE	= 0xF06;
	public static final int     RIGHT_MIDDLE	 = 0xF07;
	public static final int     LEFT_MIDDLE	  = 0xF08;
	public static final int     CENTER	       = 0xF09;

	public static final int ANIM_BOTTOM_TO_DOWN = 0xF0A;
	public static final int ANIM_BOTTOM_TO_LEFT= 0xF0B;
	public static final int ANIM_BOTTOM_TO_RIGHT = 0xF0C;
	public static final int ANIM_BOTTOM_TO_UP = 0xF0D;
	public static final int ANIM_MIDDLE_TO_DOWN= 0xF0E;
	public static final int ANIM_MIDDLE_TO_RIGHT= 0xF0F;
	public static final int ANIM_MIDDLE_TO_LEFT= 0xF10;
	public static final int ANIM_MIDDLE_TO_UP= 0xF11;
	public static final int ANIM_TOP_TO_UP = 0xF12;
	public static final int ANIM_TOP_TO_LEFT = 0xF13;
	public static final int ANIM_TOP_TO_RIGHT = 0xF14;
	public static final int ANIM_TOP_TO_DOWN = 0xF15;

	public static final int ANIM_INTERPOLATOR_DECLERATE= 0xF16;
	public static final int ANIM_INTERPOLATOR_ACCELERATE= 0xF17;
	public static final int ANIM_INTERPOLATOR_ACCELERATE_DECLERATE = 0xF18;
	public static final int ANIM_INTERPOLATOR_ANTICIPATE= 0xF19;
	public static final int ANIM_INTERPOLATOR_BOUNCE = 0xF1A;


	private static final int ANIM_DURATION = 300;

	private Context	   mContext;
	private ArcLayout	   mArcLayout;
	private FloatingActionButton fabMenu;
	private int		 mChildSize;
	private int		 mToltalChildCount;
	private int		 mPrimaryChildCount;
	private int		 mMenuSize;
	private int		 mMenuGravity;
	private int		 mShadowBorder;
	private int		 mFromDegree;
	private int		 mToDegree;
	private int		 mShadowElevation;
	private int		 mAnimType;
	private int		 mAnimDurationOut = ANIM_DURATION;
	private int		 mAnimDurationIn = ANIM_DURATION;
	private int		 mScrollThreshold;
	private int		 mMarginTop;
	private int		 mMarginBottom;
	private int		 mMarginRight;
	private int		 mMarginLeft;
	private int mMenuMargin;
	private boolean childAnim;
	private boolean menuAnim;
	private boolean isShadow;
	private boolean isMenuIn = true;
	private boolean isMenuOut;


	private Animation menuClickIn =
			ScaleAndTranslateAnimation.scaleAnimationRelativeToSelf(1.0f,0.8f,1.0f,0.8f,0.5f,0.5f,100,false);
	private Animation menuClickOut =
			ScaleAndTranslateAnimation.scaleAnimationRelativeToSelf(0.8f,1.0f,0.8f,1.0f,0.5f,0.5f,100,false);

	private Animation menuTranslateIn;
	private Animation menuTranslateOut;

	private ArrayList<TextView> mPopupView;
	private ArrayList<String> mPopupValue;
	private int mBackgroundColor = Color.TRANSPARENT;
	private int mPadding;
	private int mTextSize = 0;
	private int mCornerRadius;
	private ColorStateList mTextColor;
	private Typeface mTypeface = Typeface.DEFAULT;
	/**
	 *
	 * @param context
	 */
	public ArcMenu(Context context) {
		super(context);
		init(context);
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public ArcMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		applyAttrs(context, attrs);
	}

	/**
	 *
	 * @param context
	 */
	private void init(Context context) {
		mContext = context;

		mPopupValue = new ArrayList<>();
		mPopupView = new ArrayList<>();

		LayoutInflater li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.arc_menu, this);

		mArcLayout = (ArcLayout) findViewById(R.id.arcmenu_item_layout);

		fabMenu = (FloatingActionButton) findViewById(R.id.fabArcMenu);

		fabMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(menuAnim && mArcLayout.isAnimDone()){
					fabMenu.startAnimation(menuClickIn);
				}
				if(mArcLayout.isAnimDone()){
					mArcLayout.switchState(true);
				}
			}
		});

		menuClickIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				fabMenu.startAnimation(menuClickOut);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		mArcLayout.setOnMenuItemOpenClose(new ArcLayout.OnMenuItemOpenClose() {
			@Override
			public void menuStatus(boolean s) {

			}
		});
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	private void applyAttrs(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray b = getContext().obtainStyledAttributes(attrs,
					R.styleable.ArcMenu);

			int mType = b.getInt(R.styleable.ArcMenu_menuType, FloatingActionButton.TYPE_NORMAL);
			int defaultMenuSize = 0;
			switch (mType){
				case FloatingActionButton.TYPE_LARGE:
					defaultMenuSize = getResources().getDimensionPixelSize(R.dimen.fab_size_large);
					break;
				case FloatingActionButton.TYPE_NORMAL:
					defaultMenuSize = getResources().getDimensionPixelSize(R.dimen.fab_size_normal);
					break;
				case FloatingActionButton.TYPE_MINI:
					defaultMenuSize = getResources().getDimensionPixelSize(R.dimen.fab_size_mini);
					break;
				default:
					defaultMenuSize = getResources().getDimensionPixelSize(R.dimen.fab_size_normal);
			}

			int color = b.getColor(R.styleable.ArcMenu_menuNormalColor, Color.BLUE);
			fabMenu.setColorNormal(color);

			color = b.getColor(R.styleable.ArcMenu_menuPressedColor, color);
			fabMenu.setColorPressed(color);

			color = b.getColor(R.styleable.ArcMenu_menuRippleColor, Color.BLUE);
			fabMenu.setColorRipple(color);

			Drawable drawable = b.getDrawable(R.styleable.ArcMenu_menuImage);
			if (drawable != null) {
				fabMenu.setImageDrawable(drawable);
				//fabMenu.setBackground(drawable);
			}

			isShadow = b.getBoolean(R.styleable.ArcMenu_menuShadowElevation,false);
			mShadowElevation = fabMenu.getEleationSize();
			if (isShadow) {
				//defaultMenuSize += mShadowElevation * 2;
				fabMenu.setShadow(true);
				mMenuMargin = mShadowElevation;
			}else{
				fabMenu.setShadow(false);
				mMenuMargin = (int) dpToPx(8);
			}

			mMenuGravity = b.getInt(R.styleable.ArcMenu_menuGravity,
					CENTER);
			setMenuGravity(mMenuGravity);

			int defaultChildSize = (int) getResources().getDimension(R.dimen.menu_child_size);
			mChildSize = b.getDimensionPixelSize(
					R.styleable.ArcMenu_menuChildSize, defaultChildSize);
			mArcLayout.setChildSize(mChildSize);
			mArcLayout.setTextViewSize(mChildSize * 2, mChildSize/2);

			mMenuSize = defaultMenuSize;
			fabMenu.setFabSize(mType);
			mArcLayout.setMenuSize(mMenuSize);

			mMarginBottom = b.getDimensionPixelSize(R.styleable.ArcMenu_menuMarginBottom, 0);
			mMarginTop = b.getDimensionPixelSize(R.styleable.ArcMenu_menuMarginTop, 0);
			mMarginRight = b.getDimensionPixelSize(R.styleable.ArcMenu_menuMarginRight, 0);
			mMarginLeft = b.getDimensionPixelSize(R.styleable.ArcMenu_menuMarginLeft, 0);
			mArcLayout.setMargin(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
			//mArcLayout.setDefaultShift(mShadowElevation);

			int defaultChildRadius = (int) getResources().getDimension(R.dimen.menu_child_radius);
			int childRadius = b.getInt(R.styleable.ArcMenu_menuChildRadius,
					defaultChildRadius);
			mArcLayout.setMinRadius(childRadius);

			childAnim = b.getBoolean(
					R.styleable.ArcMenu_menuChildAnim, false);
			mArcLayout.setItemRotation(childAnim);

			menuAnim = b.getBoolean(
					R.styleable.ArcMenu_menuClickAnim, false);

			mArcLayout.setDefaultShift((int) dpToPx(10));

			mScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.menu_scroll_threshold);

			b.recycle();
		}
	}

	/**
	 *
	 * @param item
	 * @param listener
	 */
	public void addItem(View item , String tootTip, OnClickListener listener) {
		mPrimaryChildCount++;
		mArcLayout.addView(item);
		mArcLayout.addView(getContentView(tootTip));
		item.setOnClickListener(getItemClickListener(listener));
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	private TextView getContentView(String str) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(mBackgroundColor);
		drawable.setCornerRadius(mCornerRadius);

		TextView textView = new TextView(mContext);
		textView.setText(str);
		textView.setPadding(mPadding, mPadding/2, mPadding, mPadding/2);
		textView.setTypeface(mTypeface);

		if (mTextSize > 0) {
			textView.setTextSize(mTextSize);
		}else{
			textView.setTextSize(10);
		}
		if (mTextColor != null) {
			textView.setTextColor(mTextColor);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			textView.setBackground(drawable);
		} else {
			//noinspection deprecation
			textView.setBackgroundDrawable(drawable);
		}

		LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
		textViewParams.gravity = Gravity.RIGHT;
		textView.setLayoutParams(textViewParams);

		FrameLayout mContentView = new FrameLayout(mContext);
		mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		final int w = TextUtil.getWidth(mContext, str, mTextSize == 0 ? 10:mTextSize,
				TextUtil.getDeviceWidth(),mTypeface,mPadding);
		final int h = TextUtil.getHeight(mContext, str, mTextSize == 0 ? 10:mTextSize,
				TextUtil.getDeviceWidth(),mTypeface,mPadding);

		Log.i("Child measure", "Size Height: " + h + "   Size Width: " + w);
		setArcLayoutTextSize(w, h);
		//mContentView.addView(textView);
		return textView;
	}

	/**
	 *
	 * @param listener
	 * @return
	 */
	private OnClickListener getItemClickListener(final OnClickListener listener) {
		return new OnClickListener() {

			@Override
			public void onClick(final View viewClicked) {
				if (mArcLayout.isExpandDone()) {
					Animation animation = bindItemAnimation(viewClicked, true, 250);
					animation.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {

						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							postDelayed(new Runnable() {

								@Override
								public void run() {
									itemDidDisappear();
								}
							}, 0);
						}
					});

					final int itemCount = mArcLayout.getChildCount();
					for (int i = 0; i < itemCount; i++) {
						View item = mArcLayout.getChildAt(i);
						if (viewClicked != item) {
							bindItemAnimation(item, false, 200);
						}
					}

					mArcLayout.invalidate();
					mArcLayout.setExpandDone(false);
					if (listener != null) {
						listener.onClick(viewClicked);
					}
				}
			}
		};
	}

	/**
	 *
	 * @param child
	 * @param isClicked
	 * @param duration
	 * @return
	 */
	private Animation bindItemAnimation(final View child,
	                                    final boolean isClicked, final long duration) {
		Animation animation = createItemDisapperAnimation(duration, isClicked);
		child.setAnimation(animation);

		return animation;
	}

	/**
	 *
	 */
	private void itemDidDisappear() {
		final int itemCount = mArcLayout.getChildCount();
		for (int i = 0; i < itemCount; i++) {
			View item = mArcLayout.getChildAt(i);
			item.clearAnimation();
		}

		mArcLayout.switchState(false);
	}

	/**
	 *
	 * @param duration
	 * @param isClicked
	 * @return
	 */
	private static Animation createItemDisapperAnimation(final long duration,
	                                                     final boolean isClicked) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(1.0f, isClicked ? 1.4f
				: 0.0f, 1.0f, isClicked ? 1.4f : 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f));
		animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));

		animationSet.setDuration(duration);
		animationSet.setInterpolator(new DecelerateInterpolator());
		animationSet.setFillAfter(true);

		return animationSet;
	}

	/**
	 *
	 * @param gravity
	 */
	public void setMenuGravity(int gravity) {
		mArcLayout.setMenuGravity(gravity);
		switch (gravity) {
			case BOTTOM_LEFT:
				setArcLayoutParam(ANIM_BOTTOM_TO_DOWN,
						Gravity.BOTTOM|Gravity.LEFT, mFromDegree = 265, mToDegree = 365);
				setMenuParam(mMenuMargin, 0 , 0 , mMenuMargin,Gravity.BOTTOM|Gravity.LEFT);
				break;
			case BOTTOM_MIDDLE:
				setArcLayoutParam(ANIM_BOTTOM_TO_DOWN,
						Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, mFromDegree = 175, mToDegree = 365);
				setMenuParam(0 , 0 , 0 ,mMenuMargin,Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
				break;
			case BOTTOM_RIGHT:
				setArcLayoutParam(ANIM_BOTTOM_TO_DOWN,
						Gravity.RIGHT | Gravity.BOTTOM, mFromDegree = 275, mToDegree = 175);
				setMenuParam(0, 0 ,mMenuMargin, mMenuMargin,Gravity.RIGHT | Gravity.BOTTOM);
				break;
			case LEFT_MIDDLE:
				setArcLayoutParam(ANIM_MIDDLE_TO_DOWN,
						Gravity.LEFT | Gravity.CENTER_VERTICAL, mFromDegree = -95, mToDegree = 95);
				setMenuParam(mMenuMargin,0 , 0 , 0, Gravity.LEFT | Gravity.CENTER_VERTICAL);
				break;
			case RIGHT_MIDDLE:
				setArcLayoutParam(ANIM_MIDDLE_TO_DOWN,
						Gravity.RIGHT | Gravity.CENTER_VERTICAL, mFromDegree = 275, mToDegree = 85);
				setMenuParam(0, 0 ,mMenuMargin, 0, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
				break;
			case TOP_LEFT:
				setArcLayoutParam(ANIM_TOP_TO_UP,
						Gravity.LEFT | Gravity.TOP, mFromDegree = -5, mToDegree = 95);
				setMenuParam(mMenuMargin, mMenuMargin, 0 , 0, Gravity.LEFT | Gravity.TOP);
				break;
			case TOP_MIDDLE:
				setArcLayoutParam(ANIM_TOP_TO_UP,
						Gravity.TOP | Gravity.CENTER_HORIZONTAL, mFromDegree = -5, mToDegree = 185);
				setMenuParam(0, mMenuMargin, 0 , 0,Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				break;
			case TOP_RIGHT:
				setArcLayoutParam(ANIM_TOP_TO_UP,
						Gravity.RIGHT | Gravity.TOP, mFromDegree = 85, mToDegree = 185);
				setMenuParam(0,mMenuMargin, mMenuMargin,0,Gravity.RIGHT | Gravity.TOP);
				break;
			case CENTER:
				setArcLayoutParam(ANIM_MIDDLE_TO_DOWN,
						Gravity.CENTER, mFromDegree = 0, mToDegree = 360);
				setMenuParam(0, 0, 0 , 0, Gravity.CENTER);
				break;
			default:
				setArcLayoutParam(ANIM_MIDDLE_TO_DOWN,
						Gravity.CENTER, mFromDegree = 0, mToDegree = 360);
				mArcLayout.setMenuGravity(CENTER);
				setMenuParam(0, 0, 0 , 0, Gravity.CENTER);
				break;
		}
		setDefaultAnim();
	}

	private void setMenuParam(int left, int top, int right, int bottom, int gravity){
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fabMenu
				.getLayoutParams();
		params.setMargins(left, top, right, bottom);
		params.gravity = gravity;
		fabMenu.setLayoutParams(params);
	}

	/**
	 *
	 * @param animType
	 * @param gravity
	 * @param fromDeg
	 * @param toDeg
	 * @return
	 */
	private void setArcLayoutParam(int animType, int gravity, int fromDeg, int toDeg){
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mArcLayout
				.getLayoutParams();
		mAnimType = animType;
		params.gravity = gravity;
		mArcLayout.setLayoutParams(params);
		mArcLayout.setArc(fromDeg, toDeg);
	}

	/**
	 *
	 */
	private void clickMenu(){
		if(mArcLayout.isExpanded()){
			mArcLayout.switchState(!mArcLayout.isExpanded());
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public boolean performClick() {
		clickMenu();
		return false;
	}
	/**
	 *
	 * @param fromDegree
	 * @param toDegree
	 */
	public void setArc(float fromDegree, float toDegree) {
		mArcLayout.setArc(fromDegree,toDegree);
	}

	public void setToolTipTextSize(int size) {
		mTextSize = size;
	}
	public void setToolTipBackColor(int color) {
		mBackgroundColor = color;
	}
	public void setToolTipTextColor(int color) {
		mTextColor = ColorStateList.valueOf(color);
	}
	public void setToolTipPadding(float padding) {
		mPadding = (int) dpToPx(padding);
	}
	public void setToolTipCorner(float corner) {
		mCornerRadius = (int) dpToPx(corner);
	}

	private void setArcLayoutTextSize(int w, int h){
		mArcLayout.setTextSize(w, h);
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
	 */
	private void setDefaultAnim(){
		switch(mAnimType){
			case ANIM_BOTTOM_TO_DOWN:
				menuTranslateOut =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,0.0f,0.2f,
								new DecelerateInterpolator(),mAnimDurationOut,true);
				menuTranslateIn =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,2.0f,0.0f,
								new DecelerateInterpolator(),mAnimDurationIn,true);
				break;
			case ANIM_MIDDLE_TO_DOWN:
				menuTranslateOut =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,0.0f,0.7f,
								new DecelerateInterpolator(),mAnimDurationOut,true);
				menuTranslateIn =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,7.0f,0.0f,
								new DecelerateInterpolator(),mAnimDurationIn,true);
				break;
			case ANIM_TOP_TO_UP:
				menuTranslateOut =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,0.0f,-0.2f,
								new DecelerateInterpolator(),mAnimDurationOut,true);
				menuTranslateIn =
						ScaleAndTranslateAnimation.translateAnimationRelativeToParent(0.0f,0.0f,-0.2f,0.0f,
								new DecelerateInterpolator(),mAnimDurationIn,true);
				break;
		}

	}

	/**
	 *
	 */
	public void menuOut(){
		if(mArcLayout.isExpanded()){
			mArcLayout.switchState(true);
		}
		this.startAnimation(menuTranslateOut);
	}

	/**
	 *
	 */
	public void menuIn(){
		mArcLayout.setExpandMenu(false);
		this.startAnimation(menuTranslateIn);
	}

	/**
	 *
	 * @param durationOut
	 * @param durationIn
	 * @param directionOut
	 * @param directionIn
	 * @param interpolateTypeOut
	 * @param interpolateTypeIn
	 */
	public void setAnim(int durationOut, int durationIn,
	                        int directionOut, int directionIn,
	                        int interpolateTypeOut,int interpolateTypeIn){
		float fromX, toX;
		float fromY, toY;
		Interpolator in;
		Interpolator out;
		mAnimDurationOut = durationOut;
		mAnimDurationIn = durationIn;

		switch (interpolateTypeIn){
			case ANIM_INTERPOLATOR_ACCELERATE:
				in = new AccelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_ACCELERATE_DECLERATE:
				in = new AccelerateDecelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_DECLERATE:
				in = new DecelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_ANTICIPATE:
				in = new AnticipateInterpolator();
				break;
			case ANIM_INTERPOLATOR_BOUNCE:
				in = new BounceInterpolator();
				break;
			default:
				in = new AccelerateInterpolator();
		}

		switch (interpolateTypeOut){
			case ANIM_INTERPOLATOR_ACCELERATE:
				out = new AccelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_ACCELERATE_DECLERATE:
				out = new AccelerateDecelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_DECLERATE:
				out = new DecelerateInterpolator();
				break;
			case ANIM_INTERPOLATOR_ANTICIPATE:
				out = new AnticipateInterpolator();
				break;
			case ANIM_INTERPOLATOR_BOUNCE:
				out = new BounceInterpolator();
				break;
			default:
				out = new AccelerateInterpolator();
		}

		switch(directionOut){
			case ANIM_BOTTOM_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = 0.2f;
				break;
			case ANIM_BOTTOM_TO_RIGHT:
				fromX = 0.0f; toX = 0.2f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_BOTTOM_TO_LEFT:
				fromX = 0.0f; toX = -0.2f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_BOTTOM_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = -1.0f;
				break;
			case ANIM_MIDDLE_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = 0.7f;
				break;
			case ANIM_MIDDLE_TO_RIGHT:
				fromX = 0.0f; toX = 0.7f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_LEFT:
				fromX = 0.0f; toX = -0.7f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = -0.7f;
				break;
			case ANIM_TOP_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = 1.0f;
				break;
			case ANIM_TOP_TO_LEFT:
				fromX = 0.0f; toX = -0.2f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_TOP_TO_RIGHT:
				fromX = 0.0f; toX = 0.2f;
				fromY = 0.0f; toY = 0.2f;
				break;
			case ANIM_TOP_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = -0.2f;
				break;
			default:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.0f; toY = 0.2f;
		}

		menuTranslateOut =
				ScaleAndTranslateAnimation.translateAnimationRelativeToParent(fromX,toX,fromY,toY,
						out,mAnimDurationOut,true);

		switch(directionIn){
			case ANIM_BOTTOM_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.2f; toY = 0.0f;
				break;
			case ANIM_BOTTOM_TO_RIGHT:
				fromX = 0.2f; toX = 0.0f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_BOTTOM_TO_LEFT:
				fromX = -0.2f; toX = 0.0f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_BOTTOM_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = -1.0f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.7f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_RIGHT:
				fromX = 0.7f; toX = 0.0f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_LEFT:
				fromX = -0.7f; toX = 0.0f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_MIDDLE_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = -0.7f; toY = 0.0f;
				break;
			case ANIM_TOP_TO_DOWN:
				fromX = 0.0f; toX = 0.0f;
				fromY = 1.0f; toY = 0.0f;
				break;
			case ANIM_TOP_TO_LEFT:
				fromX = -0.2f; toX = 0.0f;
				fromY = 0.0f; toY = 0.0f;
				break;
			case ANIM_TOP_TO_RIGHT:
				fromX = 0.2f; toX = 0.0f;
				fromY = 0.0f; toY = 0.2f;
				break;
			case ANIM_TOP_TO_UP:
				fromX = 0.0f; toX = 0.0f;
				fromY = -0.2f; toY = 0.0f;
				break;
			default:
				fromX = 0.0f; toX = 0.0f;
				fromY = 0.2f; toY = 0.0f;
		}

		menuTranslateIn =
				ScaleAndTranslateAnimation.translateAnimationRelativeToParent(fromX,toX,fromY,toY,
						in,mAnimDurationIn,true);
	}

	/**
	 *
	 */
	public void hide(){
		if(isMenuIn && !isMenuOut) {
			isMenuOut = true;
			isMenuIn = false;
			menuOut();
		}
	}

	/**
	 *
	 */
	public void show(){
		if(!isMenuIn && isMenuOut) {
			isMenuOut = false;
			isMenuIn = true;
			menuIn();
		}
	}

	/**
	 *
	 * @param listView
	 */
	public void attachToListView(@NonNull AbsListView listView) {
		attachToListView(listView, null, null);
	}

	/**
	 *
	 * @param listView
	 * @param scrollDirectionListener
	 */
	public void attachToListView(@NonNull AbsListView listView,
	                             ScrollDirectionListener scrollDirectionListener) {
		attachToListView(listView, scrollDirectionListener, null);
	}

	/**
	 *
	 * @param recyclerView
	 */
	public void attachToRecyclerView(@NonNull RecyclerView recyclerView) {
		attachToRecyclerView(recyclerView, null, null);
	}

	/**
	 *
	 * @param recyclerView
	 * @param scrollDirectionListener
	 */
	public void attachToRecyclerView(@NonNull RecyclerView recyclerView,
	                                 ScrollDirectionListener scrollDirectionListener) {
		attachToRecyclerView(recyclerView, scrollDirectionListener, null);
	}

	/**
	 *
	 * @param scrollView
	 */
	public void attachToScrollView(@NonNull ObservableScrollView scrollView) {
		attachToScrollView(scrollView, null, null);
	}

	/**
	 *
	 * @param scrollView
	 * @param scrollDirectionListener
	 */
	public void attachToScrollView(@NonNull ObservableScrollView scrollView,
	                               ScrollDirectionListener scrollDirectionListener) {
		attachToScrollView(scrollView, scrollDirectionListener, null);
	}

	/**
	 *
	 * @param listView
	 * @param scrollDirectionListener
	 * @param onScrollListener
	 */
	public void attachToListView(@NonNull AbsListView listView,
	                             ScrollDirectionListener scrollDirectionListener,
	                             AbsListView.OnScrollListener onScrollListener) {
		AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
		scrollDetector.setScrollDirectionListener(scrollDirectionListener);
		scrollDetector.setOnScrollListener(onScrollListener);
		scrollDetector.setListView(listView);
		scrollDetector.setScrollThreshold(mScrollThreshold);
		listView.setOnScrollListener(scrollDetector);
	}

	/**
	 *
	 * @param recyclerView
	 * @param scrollDirectionlistener
	 * @param onScrollListener
	 */
	public void attachToRecyclerView(@NonNull RecyclerView recyclerView,
	                                 ScrollDirectionListener scrollDirectionlistener,
	                                 RecyclerView.OnScrollListener onScrollListener) {
		RecyclerViewScrollDetectorImpl scrollDetector = new RecyclerViewScrollDetectorImpl();
		scrollDetector.setScrollDirectionListener(scrollDirectionlistener);
		scrollDetector.setOnScrollListener(onScrollListener);
		scrollDetector.setScrollThreshold(mScrollThreshold);
		recyclerView.addOnScrollListener(scrollDetector);
	}

	/**
	 *
	 * @param scrollView
	 * @param scrollDirectionListener
	 * @param onScrollChangedListener
	 */
	public void attachToScrollView(@NonNull ObservableScrollView scrollView,
	                               ScrollDirectionListener scrollDirectionListener,
	                               ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
		ScrollViewScrollDetectorImpl scrollDetector = new ScrollViewScrollDetectorImpl();
		scrollDetector.setScrollDirectionListener(scrollDirectionListener);
		scrollDetector.setOnScrollChangedListener(onScrollChangedListener);
		scrollDetector.setScrollThreshold(mScrollThreshold);
		scrollView.setOnScrollChangedListener(scrollDetector);
	}

	/**
	 *
	 */
	private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector {
		private ScrollDirectionListener mScrollDirectionListener;
		private AbsListView.OnScrollListener mOnScrollListener;

		/**
		 *
		 * @param scrollDirectionListener
		 */
		private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
			mScrollDirectionListener = scrollDirectionListener;
		}

		/**
		 *
		 * @param onScrollListener
		 */
		public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
			mOnScrollListener = onScrollListener;
		}

		/**
		 *
		 */
		@Override
		public void onScrollDown() {
			show();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollDown();
			}
		}

		/**
		 *
		 */
		@Override
		public void onScrollUp() {
			hide();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollUp();
			}
		}

		/**
		 *
		 * @param view
		 * @param firstVisibleItem
		 * @param visibleItemCount
		 * @param totalItemCount
		 */
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
		                     int totalItemCount) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}

			super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}

		/**
		 *
		 * @param view
		 * @param scrollState
		 */
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}

			super.onScrollStateChanged(view, scrollState);
		}
	}

	/**
	 *
	 */
	private class RecyclerViewScrollDetectorImpl extends RecyclerViewScrollDetector {
		private ScrollDirectionListener mScrollDirectionListener;
		private RecyclerView.OnScrollListener mOnScrollListener;

		/**
		 *
		 * @param scrollDirectionListener
		 */
		private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
			mScrollDirectionListener = scrollDirectionListener;
		}

		/**
		 *
		 * @param onScrollListener
		 */
		public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
			mOnScrollListener = onScrollListener;
		}

		/**
		 *
		 */
		@Override
		public void onScrollDown() {
			show();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollDown();
			}
		}

		/**
		 *
		 */
		@Override
		public void onScrollUp() {
			hide();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollUp();
			}
		}

		/**
		 *
		 * @param recyclerView
		 * @param dx
		 * @param dy
		 */
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrolled(recyclerView, dx, dy);
			}

			super.onScrolled(recyclerView, dx, dy);
		}

		/**
		 *
		 * @param recyclerView
		 * @param newState
		 */
		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(recyclerView, newState);
			}

			super.onScrollStateChanged(recyclerView, newState);
		}
	}

	/**
	 *
	 */
	private class ScrollViewScrollDetectorImpl extends ScrollViewScrollDetector {
		private ScrollDirectionListener mScrollDirectionListener;

		private ObservableScrollView.OnScrollChangedListener mOnScrollChangedListener;

		/**
		 *
		 * @param scrollDirectionListener
		 */
		private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
			mScrollDirectionListener = scrollDirectionListener;
		}

		/**
		 *
		 * @param onScrollChangedListener
		 */
		public void setOnScrollChangedListener(ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
			mOnScrollChangedListener = onScrollChangedListener;
		}

		/**
		 *
		 */
		@Override
		public void onScrollDown() {
			show();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollDown();
			}
		}

		/**
		 *
		 */
		@Override
		public void onScrollUp() {
			hide();
			if (mScrollDirectionListener != null) {
				mScrollDirectionListener.onScrollUp();
			}
		}

		/**
		 *
		 * @param who
		 * @param l
		 * @param t
		 * @param oldl
		 * @param oldt
		 */
		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
			if (mOnScrollChangedListener != null) {
				mOnScrollChangedListener.onScrollChanged(who, l, t, oldl, oldt);
			}

			super.onScrollChanged(who, l, t, oldl, oldt);
		}
	}

	/**
	 *
	 * @param l
	 */
	public void showTooltip(boolean l){
		mArcLayout.showTooltip(l);
	}
}
