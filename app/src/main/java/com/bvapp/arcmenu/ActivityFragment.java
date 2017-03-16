package com.bvapp.arcmenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.bvapp.arcmenulibrary.widget.ObservableScrollView;

/**
 * Created by Mohsen on 2/5/2017.
 */

public class ActivityFragment extends AppCompatActivity {

	private static final int List_View_Fragment = 0x0F1;
	private static final int Recycler_View_Fragment = 0x0F2;
	private static final int Scroll_View_Fragment = 0x0F3;
	public static Activity _actiity;
	Fragment fragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		_actiity = this;
		Bundle bundle = getIntent().getExtras();
		if(bundle != null && bundle.containsKey("FRAGMENT_TYPE")){
			switch (bundle.getInt("FRAGMENT_TYPE")){
				case List_View_Fragment:
					addFragment(new ListViewFragment());
					break;
				case Recycler_View_Fragment:
					addFragment(new RecyclerViewFragment());
					break;
				case Scroll_View_Fragment:
					addFragment(new ScrollViewFragment());
					break;
			}
		}
	}

	private void addFragment(Fragment fragment){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.content, fragment);
		ft.commit();
	}

	public static class ListViewFragment extends Fragment {

		boolean scroolDown = true;
		boolean scroolUp;
		@SuppressLint("InflateParams")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_listview, container, false);

			ListView list = (ListView) root.findViewById(android.R.id.list);
			ListViewAdapter listAdapter = new ListViewAdapter(getActivity(),
					getResources().getStringArray(R.array.countries));
			list.setAdapter(listAdapter);

			ArcMenu menu = (ArcMenu) root.findViewById(R.id.arcMenu);
			menu.attachToListView(list);

			final int itemCount = MenuItem.ITEM_DRAWABLES.length;
			for (int i = 0; i < itemCount; i++) {
				ImageView item = new ImageView(getContext());
				item.setImageResource(MenuItem.ITEM_DRAWABLES[i]);

				final int position = i;
				menu.addItem(item, MenuItem.STR[i], new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(_actiity, MenuItem.STR[position],
								Toast.LENGTH_SHORT).show();
					}
				});
			}
			return root;
		}


	}

	public static class RecyclerViewFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

			RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
			recyclerView.setHasFixedSize(true);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
			recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
			recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

			RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
					.getStringArray(R.array.countries));
			recyclerView.setAdapter(adapter);

			ArcMenu menu = (ArcMenu) root.findViewById(R.id.arcMenu);
			menu.attachToRecyclerView(recyclerView);
			menu.showTooltip(true);
			menu.setToolTipBackColor(Color.WHITE);
			menu.setToolTipCorner(6f);
			menu.setToolTipPadding(4f);
			menu.setToolTipTextSize(14);
			menu.setToolTipTextColor(Color.BLUE);
			menu.setAnim(300,300, ArcMenu.ANIM_MIDDLE_TO_RIGHT, ArcMenu.ANIM_MIDDLE_TO_RIGHT,
					ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE, ArcMenu.ANIM_INTERPOLATOR_ACCELERATE_DECLERATE);

			final int itemCount = MenuItem.ITEM_DRAWABLES.length;
			for (int i = 0; i < itemCount; i++) {
				ImageView item = new ImageView(getContext());
				item.setImageResource(MenuItem.ITEM_DRAWABLES[i]);

				final int position = i;
				menu.addItem(item, MenuItem.STR[i], new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(_actiity, MenuItem.STR[position],
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			return root;
		}
	}

	public static class ScrollViewFragment extends Fragment {

		boolean scroolDown = true;
		boolean scroolUp;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_scrollview, container, false);
			ObservableScrollView scrollView = (ObservableScrollView) root.findViewById(R.id.scroll_view);
			ArcMenu menu = (ArcMenu) root.findViewById(R.id.arcMenu);
			menu.attachToScrollView(scrollView);
			menu.showTooltip(true);
			menu.setToolTipBackColor(Color.BLUE);
			menu.setToolTipCorner(6f);
			menu.setToolTipPadding(4f);
			menu.setToolTipTextSize(14);
			menu.setToolTipTextColor(Color.WHITE);

			final int itemCount = MenuItem.ITEM_DRAWABLES.length;
			for (int i = 0; i < itemCount; i++) {
				ImageView item = new ImageView(getContext());
				item.setImageResource(MenuItem.ITEM_DRAWABLES[i]);

				final int position = i;
				menu.addItem(item, MenuItem.STR[i], new View.OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});
			}

			return root;
		}
	}
}
