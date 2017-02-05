/*
 * Copyright (C) 2017 - Mohsen(BrotherV).
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

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class ScaleAndTranslateAnimation {

    /*
     * 
     */
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

    /*
     * 
     */
    public static Animation scaleAnimation(float xStart, float xEnd,
	    float yStart, float yEnd, int pivotXType, int pivotYType,
	    float pivotX, float pivotY, int durationMillis, boolean fill) {
		Animation anim = new ScaleAnimation(xStart, xEnd, // Start and end
			// values for
			// the X axis scaling
			yStart, yEnd, // Start and end values for the Y axis scaling
			pivotXType, pivotX, // Pivot point of X scaling
			pivotYType, pivotY); // Pivot point of Y scaling
		anim.setDuration(durationMillis);
		anim.setFillAfter(fill); // Needed to keep the result of the animation
		return anim;
    }

    /*
     * 
     */
    public static Animation translateAnimationRelativeToParent(float fromXDelta,
	    float toXDelta, float fromYDelta, float toYDelta,
	    Interpolator interpolator, int durationMillis, boolean fill) {
		Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,fromXDelta,
				Animation.RELATIVE_TO_PARENT,toXDelta,
				Animation.RELATIVE_TO_PARENT,fromYDelta,
				Animation.RELATIVE_TO_PARENT, toYDelta);
		// Start and end
		// values for
		// Start and end values for the Y axis scaling
		anim.setInterpolator(interpolator);
		anim.setDuration(durationMillis);
		anim.setFillAfter(fill); // Needed to keep the result of the animation
		return anim;
    }

	/*
  *
  */
	public static Animation translateAnimation(int relativeType,float fromXDelta,
	                                                           float toXDelta, float fromYDelta, float toYDelta,
	                                                           Interpolator interpolator, int durationMillis , boolean fill) {
		Animation anim = new TranslateAnimation(relativeType,fromXDelta,
				relativeType,toXDelta,
				relativeType,fromYDelta,
				relativeType, toYDelta);
		// Start and end
		// values for
		// Start and end values for the Y axis scaling
		anim.setInterpolator(interpolator);
		anim.setDuration(durationMillis);
		anim.setFillAfter(fill); // Needed to keep the result of the animation
		return anim;
	}

}
