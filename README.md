# Floating-ArcMenu
**A prety menu for all application**

Android floating arc menu which reacts on scrolling events. Becomes visible when an attached target is scrolled up and invisible when scrolled down.

![Demo](art/multiple menu.gif)
![Demo](art/tooltip.gif)
![Demo](art/listview.gif)
![Demo](art/recycler.gif)
![Demo](art/scroll.gif)

**1)** Add the ``com.bvapp.arcmenulibrary.ArcMenu`` to your layout XML file. You can put the menu in everywhere you want.
The width and height of the floating arc menu are hardcoded to **64dp** for the large, **56dp** for the normal and **40dp**
for the mini menu button.

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:arc="http://schemas.android.com/apk/res-auto"
             android:layout_width="match_parent"
             android:layout_height="match_parent">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:cacheColorHint="@android:color/transparent"
        android:scrollbars="vertical"/>

    <com.bvapp.arcmenulibrary.ArcMenu
        android:id="@+id/arcMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        arc:menuType="normal"
        arc:menuChildSize="48dp"
        arc:menuGravity="Right_Middle"
        arc:menuClickAnim="true"
        arc:menuChildAnim="false"
        arc:menuShadowElevation="true"
        arc:menuNormalColor="@color/colorAccent"
        arc:menuImage="@mipmap/tools"
        android:layout_gravity="center_horizontal|right"
        android:layout_marginRight="6dp"
        android:layout_marginBottom="6dp"
        >
    </com.bvapp.arcmenulibrary.ArcMenu>
</FrameLayout>
```
**2)** Add java code in activity or fragment
``` java
private static final int[] ITEM_DRAWABLES = { R.mipmap.facebook,
			R.mipmap.twitter, R.mipmap.flickr, R.mipmap.instagram,
			R.mipmap.skype, R.mipmap.github };

private static final String[] STR = {"Facebook","Twiiter","Flickr","Instagram","Skype","Github"};

//
//

RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
RecyclerViewAdapter adapter = new RecyclerViewAdapter(new MyMisic());
recyclerView.setAdapter(adapter);

ArcMenu menu = (ArcMenu) findViewById(R.id.arcMenu);
menu.attachToRecyclerView(recyclerView);
menu.showTooltip(true);
menu.setToolTipBackColor(Color.WHITE);
menu.setToolTipCorner(6f);
menu.setToolTipPadding(2f);
menu.setToolTipTextColor(Color.BLUE);
menu.setAnim(300,300,ArcMenu.ANIM_MIDDLE_TO_RIGHT,ArcMenu.ANIM_MIDDLE_TO_RIGHT,
      ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE,ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

final int itemCount = ITEM_DRAWABLES.length;
for (int i = 0; i < itemCount; i++) {
	ImageView item = new ImageView(this);
	item.setImageResource(MenuItem.ITEM_DRAWABLES[i]);
 	final int position = i;
	menu.addItem(item, STR[i], new View.OnClickListener() {
			@Override
			public void onClick(View v) {
        //You can access child click in here
			}
	});
}
```

# Credits
I used [ArcMenu by Capricorn](https://github.com/daCapricorn/ArcMenu) and [FloatingActionButton](https://github.com/makovkastar/FloatingActionButton) 
libraries as a base for development.

# License
```
Copyright 2017 BrotherV

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



