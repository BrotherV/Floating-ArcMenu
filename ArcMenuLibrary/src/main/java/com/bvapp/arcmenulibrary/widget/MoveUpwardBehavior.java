package com.bvapp.arcmenulibrary.widget;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Mohsen on 1/3/2018.
 */

public class MoveUpwardBehavior extends CoordinatorLayout.Behavior<View> {
	private static final boolean SNACKBAR_BEHAVIOR_ENABLED;

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof Snackbar.SnackbarLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
		child.setTranslationY(translationY);
		return true;
	}

	static {
		SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;
	}
}
