package com.bvapp.arcmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.FloatingActionButton;

public class ActivityTooltip extends AppCompatActivity {

	private static final int[] ITEM_DRAWABLES = { R.mipmap.facebook_w, R.mipmap.flickr_w, R.mipmap.instagram_w,
			R.mipmap.github_w };

	private String[] str = {"Facebook","Flickr","Instagram","Github"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arcMenuX);
		arcMenu.setToolTipTextSize(14);
		arcMenu.setMinRadius(104);
		arcMenu.setArc(175,255);
		arcMenu.setToolTipSide(ArcMenu.TOOLTIP_LEFT);
		arcMenu.setToolTipTextColor(Color.WHITE);
		arcMenu.setToolTipBackColor(Color.parseColor("#88000000"));
		arcMenu.setToolTipCorner(2);
		arcMenu.setToolTipPadding(8);
		arcMenu.setColorNormal(getResources().getColor(R.color.colorPrimary));
		arcMenu.showTooltip(true);
		arcMenu.setDuration(ArcMenu.ArcMenuDuration.LENGTH_LONG);
		arcMenu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
				ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);

		ArcMenu arcMenuY = (ArcMenu) findViewById(R.id.arcMenuY);
		arcMenuY.setToolTipTextSize(14);
		arcMenuY.setToolTipSide(ArcMenu.TOOLTIP_RIGHT);
		arcMenuY.setToolTipTextColor(Color.WHITE);
		arcMenuY.setToolTipBackColor(Color.parseColor("#aa3f51b5"));
		arcMenuY.setToolTipCorner(2);
		arcMenuY.setToolTipPadding(8);
		arcMenuY.setColorNormal(getResources().getColor(R.color.colorPrimary));
		arcMenuY.setDuration(600);
		arcMenuY.setIcon(R.mipmap.facebook_w, R.mipmap.github_w);
		arcMenuY.showTooltip(true);

		initArcMenu(arcMenu, str, ITEM_DRAWABLES, ITEM_DRAWABLES.length - 1);
		initArcMenu(arcMenuY, str, ITEM_DRAWABLES, ITEM_DRAWABLES.length);
	}

	private void initArcMenu(final ArcMenu menu, final String[] str, int[] itemDrawables, int count) {
		for (int i = 0; i < count; i++) {
			FloatingActionButton item = new FloatingActionButton(this);
			item.setSize(FloatingActionButton.SIZE_MINI);
			item.setIcon(itemDrawables[i]);
			item.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
			menu.setChildSize(item.getIntrinsicHeight());

			final int position = i;
			menu.addItem(item, str[i], new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(ActivityTooltip.this, str[position],
							Toast.LENGTH_SHORT).show();
					if(position == 1){
						menu.menuOut();
					}
				}
			});
		}
	}
}
