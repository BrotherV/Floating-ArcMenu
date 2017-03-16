package com.bvapp.floatingarcmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;

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

}
