package wuxiang.miku.scorpio.paperactivate.modules.login_register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import wuxiang.miku.scorpio.paperactivate.R;

public class RegisterActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private CardView cvAdd;
    private EditText usernameEdt;
    private EditText passwordEdt;
    private EditText passwordAgainEdt;
    private EditText emailEdt;
    private Button goBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ShowEnterAnimation();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
        //注册点击事件
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先获取到各个edittext的内容
                String username = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();
                String passwordAgain = passwordAgainEdt.getText().toString().trim();
                String email = emailEdt.getText().toString().trim();
                //判空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(passwordAgain) & !TextUtils.isEmpty(email)) {
                    //判断两次输入的密码是否一致
                    if (password.equals(passwordAgain)) {
                        //注册
                        BmobUser user = new BmobUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.signUp(RegisterActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(RegisterActivity.this, "该用户名或邮箱已被使用", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                        passwordEdt.setText("");
                        passwordAgainEdt.setText("");
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        usernameEdt = findViewById(R.id.et_username);
        passwordEdt = findViewById(R.id.et_password);
        passwordAgainEdt = findViewById(R.id.et_repeatpassword);
        emailEdt = findViewById(R.id.et_email);
        goBtn = findViewById(R.id.btn_go);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


}
