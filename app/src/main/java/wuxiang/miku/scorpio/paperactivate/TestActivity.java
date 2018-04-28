package wuxiang.miku.scorpio.paperactivate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_button)
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*
        Logger test
         */
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("hello");

        /*
        butterknifetest
         */
        ButterKnife.bind(this);
    }

    /*
      butterknifetest
     */
    @OnClick(R.id.test_button)
    public void onViewClicked() {
        Logger.d("点击了");
        Toast.makeText(TestActivity.this, "我是用butterKnife绑定的", Toast.LENGTH_SHORT).show();
    }
}
