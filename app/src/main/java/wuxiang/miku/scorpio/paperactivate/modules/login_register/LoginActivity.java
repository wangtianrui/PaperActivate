package wuxiang.miku.scorpio.paperactivate.modules.login_register;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.modules.MainActivity;
import wuxiang.miku.scorpio.paperactivate.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.other_account_login_textview)
    TextView otherAccountLoginTextview;
    @BindView(R.id.qq_login_button)
    ImageView qqLoginButton;
    @BindView(R.id.weixin_login_button)
    ImageView weixinLoginButton;
    @BindView(R.id.other_login_context)
    LinearLayout otherLoginContext;
    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);
                /**
                 * 嘻嘻嘻
                 */

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i2, oc2.toBundle());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    /**
     * 登录界面“使用其他社交帐号登录”按钮相关事件处理
     *
     * @param view
     */
    @OnClick({R.id.other_account_login_textview, R.id.qq_login_button, R.id.weixin_login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.other_account_login_textview:
                otherAccountLoginTextview.setVisibility(View.INVISIBLE);
                otherLoginContext.setVisibility(View.VISIBLE);
                break;
            case R.id.qq_login_button:
                ToastUtil.showShort(LoginActivity.this,"QQ登录");
                break;
            case R.id.weixin_login_button:
                ToastUtil.showShort(LoginActivity.this,"微信登录");
                break;
        }
    }
}
