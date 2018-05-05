package wuxiang.miku.scorpio.paperactivate;

import android.app.Application;

/**
 * Created by Wangtianrui on 2018/5/5.
 */

public class PaperActivate extends Application {
    public static PaperActivate mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static PaperActivate newInstance() {
        return mInstance;
    }
}
