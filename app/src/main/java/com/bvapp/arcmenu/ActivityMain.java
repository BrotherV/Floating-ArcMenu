package com.bvapp.arcmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bvapp.arcmenulibrary.widget.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ActivityMain extends AppCompatActivity {

	private static final int List_View_Fragment = 0x0F1;
	private static final int Recycler_View_Fragment = 0x0F2;
	private static final int Scroll_View_Fragment = 0x0F3;

	AppCompatActivity currenActivity;

	private CoordinatorLayout coordinatorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		currenActivity = this;

		Button btnMultipleMenu = (Button) findViewById(R.id.btnMultipleMenu);
		Button btnMenuWithTooltip = (Button) findViewById(R.id.btnMenuWithTooltip);
		Button btnListView = (Button) findViewById(R.id.btnListView);
		Button btnRecycleView = (Button) findViewById(R.id.btnRecycleView);
		Button btnScrollView = (Button) findViewById(R.id.btnScrollView);

		View.OnClickListener click = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (view.getId()){
					case R.id.btnMultipleMenu:
						openActivity(ActivityMultipleMenu.class, null, 0);
						break;
					case R.id.btnMenuWithTooltip:
						openActivity(ActivityTooltip.class, null, 0);
						break;
					case R.id.btnListView:
						openActivity(ActivityFragment.class, "FRAGMENT_TYPE", List_View_Fragment);
						break;
					case R.id.btnRecycleView:
						openActivity(ActivityFragment.class, "FRAGMENT_TYPE", Recycler_View_Fragment);
						break;
					case R.id.btnScrollView:
						openActivity(ActivityFragment.class, "FRAGMENT_TYPE", Scroll_View_Fragment);
						break;
				}
			}
		};

		btnMultipleMenu.setOnClickListener(click);
		btnMenuWithTooltip.setOnClickListener(click);
		btnListView.setOnClickListener(click);
		btnRecycleView.setOnClickListener(click);
		btnScrollView.setOnClickListener(click);

		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layMain);
		setChildItem();
	}

	private void openActivity(Class<?> cls, String key, int value){
		if(cls != null){
			Intent intent = new Intent(currenActivity, cls);
			if(key != null){
				intent.putExtra(key, value);
			}
			currenActivity.startActivity(intent);
		}
	}

	boolean i;
	private void setChildItem(){
		final FloatingActionButton item = (FloatingActionButton) findViewById(R.id.fab);
		item.setSize(FloatingActionButton.SIZE_NORMAL);
		item.setIcon(R.mipmap.facebook_w);
		item.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		item.setIconSize(16);
		/*
		item.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
					case MotionEvent.ACTION_DOWN:
						item.setSize(FloatingActionButton.SIZE_MINI);
						//item.setIconSize(32);
						break;
					case MotionEvent.ACTION_UP:
						item.setSize(FloatingActionButton.SIZE_NORMAL);
						//item.setIconSize(16);
						break;
				}
				return false;
			}
		});
		*/

		item.setOnShrinkExpandClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				item.setIconSize(i ? 32 : 16);
				i = !i;
				Snackbar snackbar = Snackbar
						.make(coordinatorLayout, "Hello this is FAB", Snackbar.LENGTH_LONG);
				snackbar.show();
			}
		});
	}

}
