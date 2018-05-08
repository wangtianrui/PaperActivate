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
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import wuxiang.miku.scorpio.paperactivate.R;
import wuxiang.miku.scorpio.paperactivate.modules.MainActivity;
import wuxiang.miku.scorpio.paperactivate.utils.ToastUtil;

/**
 * 用户登录
 * 实现了用户缓存登陆功能
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.other_account_login_textview)
    TextView otherAccountLoginTextview;
    @BindView(R.id.qq_login_button)
    ImageView qqLoginButton;
    @BindView(R.id.weixin_login_button)
    ImageView weixinLoginButton;
    @BindView(R.id.other_login_context)
    LinearLayout otherLoginContext;
    @BindView(R.id.login_wait_Pb)
    ProgressBar loginWaitPb;
    @BindView(R.id.forget_passwd_tv)
    TextView forgetPasswdTv;
    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //判断是否已经登录过，如果已经登录过，就跳过登陆直接进入
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        if (bmobUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
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
                //获取输入框的值
                final String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                //判空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)) {
                    loginWaitPb.setVisibility(View.VISIBLE);
                    //登陆
                    BmobUser user = new BmobUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            loginWaitPb.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Explode explode = new Explode();
                            explode.setDuration(500);
                            getWindow().setExitTransition(explode);
                            getWindow().setEnterTransition(explode);
                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                            Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i2, oc2.toBundle());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            loginWaitPb.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "登录失败: " + s.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
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

        //忘记密码修改
        forgetPasswdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswdActivity.class);
                startActivity(intent);
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
                ToastUtil.showShort(LoginActivity.this, "QQ登录");
                break;
            case R.id.weixin_login_button:
                ToastUtil.showShort(LoginActivity.this, "微信登录");
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
