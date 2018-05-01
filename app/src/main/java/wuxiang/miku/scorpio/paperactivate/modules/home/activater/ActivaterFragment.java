package wuxiang.miku.scorpio.paperactivate.modules.home.activater;

import android.os.Bundle;

import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.base.BaseFragment;
import wuxiang.miku.scorpio.paperactivate.utils.ToastUtil;

/**
 * Created by Wangtianrui on 2018/5/1.
 */

public class ActivaterFragment extends BaseFragment {
    public static ActivaterFragment newInstance() {
        return new ActivaterFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_activater;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    private void initView() {
        ToastUtil.showShort(getApplicationContext(),"activater fragment initview");
    }
}
