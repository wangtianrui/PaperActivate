package wuxiang.miku.scorpio.paperactivate.modules.login_register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuxiang.miku.scorpio.paperactivate.R;

public class QQLoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btQQLogin ;
    Button btQQLogout ;
    ImageView userHead ;
    TextView userName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);
//        ButterKnife.bind(this);
        btQQLogin = findViewById( R.id.button_qq_login );
        btQQLogout = findViewById( R.id.button_qq_logout );
        userHead = findViewById( R.id.img_qq_user_head);
        userName = findViewById( R.id.text_qq_user_name);
        btQQLogout.setOnClickListener(this);
        btQQLogin.setOnClickListener(this);
    }

    public void initView(){
        
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ){
            case R.id.button_qq_login:
                Toast.makeText( QQLoginActivity.this , "click login" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_qq_logout:
                Toast.makeText( QQLoginActivity.this , "click logout" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
