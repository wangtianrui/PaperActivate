package wuxiang.miku.scorpio.paperactivate.modules.login_register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import wuxiang.miku.scorpio.paperactivate.R;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/05/08
 *     desc   : 修改密码
 *     version: 1.0
 * </pre>
 */

public class ForgetPasswdActivity extends AppCompatActivity {

    @BindView(R.id.modify_username)
    EditText modifyUsernameEdt;
    @BindView(R.id.modify_old_password)
    EditText modifyOldPasswdEdt;
    @BindView(R.id.modify_new_password)
    EditText modifyNewPasswdEdt;
    @BindView(R.id.modify_go)
    Button modifyGoBtn;
    @BindView(R.id.modify_fab)
    FloatingActionButton modifyFab;
    @BindView(R.id.modify_cv_add)
    CardView cvAdd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasswd);
        ButterKnife.bind(this);
        modifyGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框内容
                String username = modifyUsernameEdt.getText().toString().trim();
                String oldpasswd = modifyOldPasswdEdt.getText().toString().trim();
                String newpasswd = modifyNewPasswdEdt.getText().toString().trim();
                //判空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(oldpasswd)
                        & !TextUtils.isEmpty(newpasswd)) {
                    //修改密码
                    BmobUser.updateCurrentUserPassword(ForgetPasswdActivity.this, oldpasswd,
                            newpasswd, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(ForgetPasswdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(ForgetPasswdActivity.this, "密码修改失败: " + s, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ForgetPasswdActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        modifyFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), modifyFab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                modifyFab.setImageResource(R.drawable.plus);
                ForgetPasswdActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
}
