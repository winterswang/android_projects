package com.example.slidingmenutest;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.slidingmenutest.ContentFragment;
import com.example.slidingmenutest.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SlidingMenu Demo");
        setContentView(R.layout.frame_content);
        
     // set the Behind View
        setBehindContentView(R.layout.frame_menu);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new ContentFragment("Welcome"),"Welcome");
        fragmentTransaction.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidth(50);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(60);
        sm.setFadeDegree(0.35f);
        //设置slding menu的几种手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding menu
        //TOUCHMODE_NONE 自然是不能通过手势打开啦
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

