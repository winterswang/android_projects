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
        //����slding menu�ļ�������ģʽ
        //TOUCHMODE_FULLSCREEN ȫ��ģʽ����contentҳ���У����������Դ�sliding menu
        //TOUCHMODE_MARGIN ��Եģʽ����contentҳ���У�������slding ,����Ҫ����Ļ��Ե�����ſ��Դ�slding menu
        //TOUCHMODE_NONE ��Ȼ�ǲ���ͨ�����ƴ���
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        //ʹ�����Ϸ�icon�ɵ㣬������onOptionsItemSelected����ſ��Լ�����R.id.home
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

