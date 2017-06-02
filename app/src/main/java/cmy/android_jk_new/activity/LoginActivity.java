package cmy.android_jk_new.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.ButterKnife;
import cmy.android_jk_new.R;
import cmy.android_jk_new.constant.HttpConstant;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references
    private AutoCompleteTextView mLoginName;

    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkGo.init(getApplication());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        // Set up the login form.
        mLoginName = (AutoCompleteTextView) findViewById(R.id.loginName_tv_login);

        mPasswordView = (EditText) findViewById(R.id.password_et_login);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.login_bt_login);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    OkGo.post(HttpConstant.URL_PDA_LOGIN)
                            .params("login_name", "12")
                            .params("login_password", "123456")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.i("test", "" + s);
                                    Log.i("test", "" + call);
                                    Log.i("test", "" + response);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    Log.i("test", "" + e);
                                    Log.i("test", "" + call);
                                    Log.i("test", "" + response);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

