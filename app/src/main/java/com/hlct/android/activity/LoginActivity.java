package com.hlct.android.activity;

import android.content.Intent;
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

import com.hlct.android.R;
import com.hlct.android.bean.BankInfo;
import com.hlct.android.bean.ResultInfo;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.DaoMaster;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.http.APIService;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hlct.android.constant.HttpConstant.BASE_SERVER_URL;
import static com.hlct.android.util.SnackbarUtil.showSnackbar;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // UI references
    private AutoCompleteTextView mLoginName;

    private EditText mPasswordView;

    //账号和密码
    private String LOGINNAME;
    private String PASSWORD;

    //Dao对象的管理者
    private static DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //        setupDatabase();

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
                doLogin();
            }
        });

    }

    private void doLogin() {
        //获取输入的账号密码
        LOGINNAME = mLoginName.getText().toString();
        PASSWORD = mPasswordView.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                // 设置网络请求的Url地址
                .baseUrl(BASE_SERVER_URL)
                // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 创建 网络请求接口 的实例
        APIService apiService = retrofit.create(APIService.class);
        //对发送请求进行封装
        Call<ResultInfo> call = apiService.login(LOGINNAME, PASSWORD);
        //发送网络请求(异步)
        call.enqueue(new Callback<ResultInfo>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                Logger.d(response.body().getMessage());
                Logger.d(response.body().getCode());
                Logger.d(response.body().getText());
                String flag = response.body().getCode();//返回码
                String msg = response.body().getMessage();//提示信息
                //如果登陆成功
                if (flag.equals(ResultInfo.CODE_SUCCESS)) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    showSnackbar(getWindow().getDecorView(), msg);
                } else if (flag.equals(ResultInfo.CODE_ERROR)) {
                    showSnackbar(getWindow().getDecorView(), msg);
                }

            }

            //请求失败时回调
            @Override
            public void onFailure(Call<ResultInfo> call, Throwable t) {
                t.printStackTrace();
                showSnackbar(getWindow().getDecorView(), "访问服务器失败！");
            }
        });
    }

    /**
     * 数据库相关
     */
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

        BankInfo bankInfo = new BankInfo();

        bankInfo.setBankId(1L);
        bankInfo.setBankName("招商银行华侨城支行");
        bankInfo.setBankTaskStatus("未完成");
        bankInfo.setLineId("A线路");
        //存入数据库
        daoSession.insert(bankInfo);
        //查询数据
        QueryBuilder qb = daoSession.queryBuilder(BankInfo.class);
        qb.list();
        Logger.d(qb.list());
        for (int i = 0; i < qb.list().size(); i++) {
            BankInfo bl = (BankInfo) qb.list().get(i);
            Logger.d(bl.getBankName());
        }
        //修改数据
        bankInfo.setBankName("华夏银行华侨城支行");
        daoSession.update(bankInfo);

        //删除数据
        daoSession.delete(BankInfo.class);
    }


    //    private void showSnackbar(View view, String msg) {
    //        Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_LONG)
    //                .setAction("确定", new OnClickListener() {
    //                    @Override
    //                    public void onClick(View view) {
    //
    //                    }
    //                }).show();
    //    }


}

