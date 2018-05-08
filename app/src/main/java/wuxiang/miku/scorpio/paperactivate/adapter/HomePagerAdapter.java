package wuxiang.miku.scorpio.paperactivate.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.modules.home.childpagers.center.CenterFragment;
import wuxiang.miku.scorpio.paperactivate.modules.home.childpagers.left_ar.BARFragment;
import wuxiang.miku.scorpio.paperactivate.modules.home.childpagers.right_ocr.OcrFragment;

/**
 * Created by Wangtianrui on 2018/5/1.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = BARFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = CenterFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = OcrFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
