package cmy.android_jk_new.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import butterknife.ButterKnife;
import cmy.android_jk_new.R;
import cmy.android_jk_new.bean.LoginUser;
import cmy.android_jk_new.constant.DatabaseConstant;
import cmy.android_jk_new.constant.HttpConstant;
import cmy.android_jk_new.greendao.DaoMaster;
import cmy.android_jk_new.greendao.DaoSession;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // UI references
    private AutoCompleteTextView mLoginName;

    private EditText mPasswordView;

    //Dao对象的管理者
    private static DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkGo.init(getApplication());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupDatabase();

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
                                    Logger.i(s);
                                    Logger.i("" + call);
                                    Logger.i("" + response);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    Logger.i("" + e);
                                    Logger.i("" + call);
                                    Logger.i("" + response);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, DatabaseConstant.DATABASE_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster dm = new DaoMaster(db);
        //获取Dao对象的管理者
        daoSession = dm.newSession();

        LoginUser loginuser = new LoginUser();
        loginuser.setLoginName("QWE");
        loginuser.setPassWord("123");
        loginuser.setUserFinger("====");
        loginuser.setUserType("0");
        daoSession.insert(loginuser);

        QueryBuilder qb = daoSession.queryBuilder(LoginUser.class);
        qb.list();

        Logger.d(qb.list());

        for (int i = 0; i < qb.list().size(); i++) {
            LoginUser lu = (LoginUser) qb.list().get(i);
            Logger.d(lu.getLoginName());
        }

    }

}

