package wuxiang.miku.scorpio.paperactivate;

import android.app.Application;

import com.baidu.ar.bean.DuMixARConfig;
import com.baidu.ar.util.Res;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import wuxiang.miku.scorpio.paperactivate.utils.Statics;

/**
 * Created by Wangtianrui on 2018/5/5.
 */

public class PaperActivate extends Application {
    public static PaperActivate mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化AR
        Res.addResource(this);
        DuMixARConfig.setAppId(Statics.BAIDUARAPPID);
        DuMixARConfig.setAPIKey(Statics.BAIDUARAPIKEY);
        DuMixARConfig.setSecretKey(Statics.BAIDUARSECRETKEY);

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
                Logger.e(error.getCause(), error.getMessage());
            }
        }, getApplicationContext(), "fkEwsa8rf21TEp5hS83kUF6W", "2LYIZGjDwpcepxKw1G6zoCDLN03F3jRB");

        //init bmob
        Bmob.initialize(this, "99683d1dd8abc6c166a93f832b0384b0");
    }

    public static PaperActivate newInstance() {
        return mInstance;
    }
}
