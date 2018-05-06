package wuxiang.miku.scorpio.paperactivate.modules.home.childpagers.left_ar;

import android.content.Intent;
import android.os.Bundle;

import com.baidu.ocr.ui.camera.CameraView;

import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.base.BaseFragment;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class BARFragment extends BaseFragment {
    private CameraView cameraView;

    public static BARFragment newInstance() {
        return new BARFragment();
    }


    @Override
    public int getLayoutResId() {
        return R.layout.bd_ocr_activity_camera;
    }

    @Override
    public void finishCreateView(Bundle state) {
        cameraView = (CameraView) getActivity().findViewById(com.baidu.ocr.ui.R.id.camera_view);
    }

    @Override
    public void onStart() {
        super.onStart();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraView.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
