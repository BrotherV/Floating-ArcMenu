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

import android.support.v7.widget.RecyclerView;

abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
	private int mScrollThreshold;

	abstract void onScrollUp();

	abstract void onScrollDown();

	/**
	 *
	 * @param recyclerView
	 * @param dx
	 * @param dy
	 */
	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
		if (isSignificantDelta) {
			if (dy > 0) {
				onScrollUp();
			} else {
				onScrollDown();
			}
		}
	}

	/**
	 *
	 * @param scrollThreshold
	 */
	public void setScrollThreshold(int scrollThreshold) {
		mScrollThreshold = scrollThreshold;
	}
}
