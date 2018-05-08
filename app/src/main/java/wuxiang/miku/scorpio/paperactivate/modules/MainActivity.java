package wuxiang.miku.scorpio.paperactivate.modules;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.base.BaseActivity;
import wuxiang.miku.scorpio.paperactivate.modules.home.HomePageFragmet;


import wuxiang.miku.scorpio.paperactivate.modules.login_register.LoginActivity;
import wuxiang.miku.scorpio.paperactivate.utils.ToastUtil;
import wuxiang.miku.scorpio.paperactivate.widget.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private Fragment[] fragments;
    private HomePageFragmet homePageFragmet;
    private long exitTime; //计算连点时间的间隔

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initViews(Bundle savedInstanceState) {
        initFragment();
        initNavigationView();
    }

    @Override
    public void initToolBar() {

    }

    /**
     * navigation点击事件处理
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_note:
                Intent intent = new Intent(this, NoteBookActivity.class);
                startActivity(intent);
                break;
            case R.id.item_exit:
                //删除本地登录缓存
                BmobUser.logOut(this);
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                this.finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 左侧NavigationView初始化
     */
    private void initNavigationView() {
        String username = BmobUser.getCurrentUser(MainActivity.this).getUsername();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        //设置头像
//        mSwitchMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchNightMode();
//            }
//        });
        mUserAvatarView.setImageResource(R.drawable.user_avatar_test);
        mUserName.setText(username);

    }


    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 初始化Fragment,所有fragment在这里加载哦~
     */
    private void initFragment() {
        homePageFragmet = HomePageFragmet.newInstance();

        fragments = new Fragment[]{
                homePageFragmet
        };
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, homePageFragmet)
                .show(homePageFragmet).commit();
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                if (homePageFragmet != null) {
                    if (homePageFragmet.isOpenSearchView()) {
                        homePageFragmet.closeSearchView();
                    } else {
                        exitApp();
                    }
                } else {
                    exitApp();
                }
            }
        }
        return true;
    }


    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort(getApplicationContext(), "再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    /**
     * 日夜间模式切换
     */
//    private void switchNightMode() {
//        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
//        if (isNight) {
//            // 日间模式
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
//        } else {
//            // 夜间模式
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
//        }
//        recreate();
//    }


}
