package com.bvapp.arcmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;

public class ActivityTooltip extends AppCompatActivity {

	private static final int[] ITEM_DRAWABLES = { R.mipmap.facebook,
			R.mipmap.twitter, R.mipmap.flickr, R.mipmap.instagram,
			R.mipmap.skype, R.mipmap.github };

	private String[] str = {"Facebook","Twiiter","Flickr","Instagram","Skype","Github"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arcMenuX);
		ArcMenu arcMenuY = (ArcMenu) findViewById(R.id.arcMenuY);

		arcMenu.setToolTipTextSize(14);
		arcMenuY.setToolTipTextSize(14);

		arcMenu.setAnim(500,500,ArcMenu.ANIM_MIDDLE_TO_DOWN,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
				ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE,ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);

		arcMenu.showTooltip(true);
		arcMenuY.showTooltip(true);

		initArcMenu(arcMenu, str, ITEM_DRAWABLES);
		initArcMenu(arcMenuY, str, ITEM_DRAWABLES);
	}

	private void initArcMenu(final ArcMenu menu, final String[] str, int[] itemDrawables) {
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(itemDrawables[i]);

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
