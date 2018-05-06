package wuxiang.miku.scorpio.paperactivate;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by Wangtianrui on 2018/5/5.
 */

public class PaperActivate extends Application {
    public static PaperActivate mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //init Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

    }
    public static PaperActivate newInstance() {
        return mInstance;
    }
}
