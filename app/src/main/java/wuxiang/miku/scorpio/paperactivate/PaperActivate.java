package wuxiang.miku.scorpio.paperactivate;

import android.app.Application;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
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
        //init baidu ocr
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Logger.e(error.getCause(),error.getMessage());
            }
        }, getApplicationContext(), "fkEwsa8rf21TEp5hS83kUF6W", "2LYIZGjDwpcepxKw1G6zoCDLN03F3jRB");

    }
    public static PaperActivate newInstance() {
        return mInstance;
    }
}
