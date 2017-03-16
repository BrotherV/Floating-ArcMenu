package com.bvapp.arcmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;

/**
 * Created by Mohsen on 2/5/2017.
 */

public class ActivityMultipleMenu extends AppCompatActivity {

	private static final int[] ITEM_DRAWABLES = { R.mipmap.facebook,
			R.mipmap.twitter, R.mipmap.flickr, R.mipmap.instagram,
			R.mipmap.skype, R.mipmap.github };

	private static final int[] menuId = {R.id.arcMenu,R.id.arcMenu2,R.id.arcMenu3,R.id.arcMenu4
			,R.id.arcMenu5,R.id.arcMenu6,R.id.arcMenu7,R.id.arcMenu8,R.id.arcMenu9};

	private String[] str = {"Facebook","Twiiter","Flickr","Instagram","Skype","Github"};

	private ArcMenu[] arcMenu = new ArcMenu[menuId.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_menu);


		for(int i=0; i<arcMenu.length; i++){
			arcMenu[i] = (ArcMenu) findViewById(menuId[i]);
			initArcMenu(arcMenu[i], str, ITEM_DRAWABLES, i);
		}
	}

	private void initArcMenu(final ArcMenu menu, final String[] str, int[] itemDrawables, final int menuNum) {
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(itemDrawables[i]);

			if(menuNum == 1){
				menu.setAnim(400,400, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
						ArcMenu.ANIM_INTERPOLATOR_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_BOUNCE);
			}

			if(menuNum == 4){
				menu.setAnim(500,500, ArcMenu.ANIM_MIDDLE_TO_DOWN, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
						ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE, ArcMenu.ANIM_INTERPOLATOR_ANTICIPATE);
			}

			final int position = i;
			menu.addItem(item, str[i], new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(ActivityMultipleMenu.this,  str[position],
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
