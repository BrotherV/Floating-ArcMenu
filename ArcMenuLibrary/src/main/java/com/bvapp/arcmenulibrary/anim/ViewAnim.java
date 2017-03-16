package com.bvapp.arcmenulibrary.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;

/**
 * Created by Mohsen on 3/15/2017.
 */

public class ViewAnim {

	public static void shrinkExpandAnimation(final View myView) {
		if (myView != null) {
			final Animation animClickIn = AnimationObject.scaleAnimationRelativeToSelf(1.0f, 0.85f,
					1.0f, 0.85f, 0.5f, 0.5f, 100, false);
			final Animation animClickOut = AnimationObject.scaleAnimationRelativeToSelf(0.85f, 1.0f,
					0.85f, 1.0f, 0.5f, 0.5f, 100, false);

			myView.startAnimation(animClickIn);

			animClickIn.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					myView.startAnimation(animClickOut);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}
			});
		}
	}


	public static void rotateAnimation(final View myView, final boolean dir) {
		if (myView != null) {
			final Animation animRotate = AnimationObject.rotationAnimationRelativeToSelf(dir ? 0 : 45f, dir ? 45f : 0,
					0.5f, 0.5f, 200, true);
			myView.startAnimation(animRotate);
		}
	}


}
