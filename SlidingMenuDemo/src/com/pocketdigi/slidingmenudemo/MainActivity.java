package com.pocketdigi.slidingmenudemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pocketdigi.fragment.FragmentA;
import com.pocketdigi.fragment.FragmentB;
import com.pocketdigi.fragment.MenuFragment;


@EActivity(R.layout.layout_content)
public class MainActivity extends FragmentActivity {
	Fragment fragmentA, fragmentB, menuFragment;
	SlidingMenu menu;
	

	@AfterViews
	public void afterViews() {
		
		menuFragment = new MenuFragment();
		menu = new SlidingMenu(this);
		
		menu.setShadowWidth(50);
		menu.setBehindOffset(60);
		menu.setFadeDegree(0.35f);		
		
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setShadowWidthRes(R.dimen.shadow_width);

		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.layout_menu);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_menu, menuFragment).commit();
		
		showFragmentA();
	}

	/**
	 * 
	 */
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}
	
	public void showFragmentA()
	{
		if(fragmentA==null)
		{
			fragmentA = new FragmentA();
		}
		if(!fragmentA.isVisible())
		{
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.layout_content, fragmentA).commit();
		}
		menu.showContent(true);
	}
	
	public void showFragmentB()
	{
		if(fragmentB==null)
		{
			fragmentB = new FragmentB();
		}
		if(!fragmentB.isVisible())
		{
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.layout_content, fragmentB).commit();
		}
		menu.showContent(true);
	}


}
