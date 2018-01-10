package com.bvapp.arcmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bvapp.arcmenulibrary.widget.FloatingActionButton;

public class ActivityMain extends AppCompatActivity {

	private static final int List_View_Fragment = 0x0F1;
	private static final int Recycler_View_Fragment = 0x0F2;
	private static final int Scroll_View_Fragment = 0x0F3;

	AppCompatActivity currenActivity;

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

		LinearLayout l = (LinearLayout) findViewById(R.id.layMain);
		l.addView(getChildItem());
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
	private FloatingActionButton getChildItem(){
		final FloatingActionButton item = new FloatingActionButton(this);
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

		item.setOnShrinkExpandClickListener(item, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				item.setIconSize(i ? 32 : 16);
				i = !i;
			}
		});
		return item;
	}

}
