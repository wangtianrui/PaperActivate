package wuxiang.miku.scorpio.paperactivate.modules.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.adapter.HomePagerAdapter;
import wuxiang.miku.scorpio.paperactivate.base.BaseFragment;
import wuxiang.miku.scorpio.paperactivate.modules.MainActivity;
import wuxiang.miku.scorpio.paperactivate.utils.ToastUtil;
import wuxiang.miku.scorpio.paperactivate.widget.CircleImageView;
import wuxiang.miku.scorpio.paperactivate.widget.NoScrollViewPager;

/**
 * Created by Wangtianrui on 2018/5/1.
 */

public class HomePageFragmet extends BaseFragment {
    @BindView(R.id.toolbar_user_avatar)
    CircleImageView toolbarUserAvatar;
    @BindView(R.id.navigation_layout)
    LinearLayout navigationLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    Unbinder unbinder;
    @BindView(R.id.navigation_user_id_textview)
    TextView navigationUserIdTextview;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        initToolbar();
        initSearchView();
        initViewPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.navigation_layout)
    public void onViewClicked() {
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).toggleDrawer();
        }
    }

    /**
     * maker
     *
     * @return
     */
    public static HomePageFragmet newInstance() {
        return new HomePageFragmet();
    }

    /**
     * 初始化Menu
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        //设置searchview
        MenuItem item = menu.findItem(R.id.id_action_search);
        searchView.setMenuItem(item);

    }

    public boolean isOpenSearchView() {
        return searchView.isSearchOpen();
    }

    public void closeSearchView() {
        searchView.closeSearch();
    }


    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar.setTitle("");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        initUserInformation();
    }

    /**
     * 初始化searchview
     */
    private void initSearchView() {
        searchView.setHint("搜索");
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtil.showShort(getSupportActivity(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * 初始化pager
     */
    private void initViewPager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(), getApplicationContext());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(mHomeAdapter);
        slidingTabs.setViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    /**
     * 初始化用户信息(navigation里的)
     */
    private void initUserInformation() {
        toolbarUserAvatar.setImageResource(R.drawable.user_avatar_test);
        navigationUserIdTextview.setText("ScorpioMiku");
    }
}
