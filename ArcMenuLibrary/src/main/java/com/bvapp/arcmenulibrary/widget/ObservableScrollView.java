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

package com.bvapp.arcmenulibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class ObservableScrollView extends ScrollView {

	/**
	 *
	 */
	public interface OnScrollChangedListener {
		void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
	}

	/**
	 *
	 */
	private OnScrollChangedListener mOnScrollChangedListener;

	public ObservableScrollView(Context context) {
		super(context);
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 *
	 * @param l
	 * @param t
	 * @param oldl
	 * @param oldt
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}

	/**
	 *
	 * @param listener
	 */
	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		mOnScrollChangedListener = listener;
	}
}