package com.hlct.android.activity;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.hlct.android.DataCache.DataCache;
import com.hlct.android.R;
import com.hlct.android.bean.ResultInfo;
import com.hlct.android.bean.User;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.UserDao;
import com.hlct.android.http.APIService;
import com.hlct.android.util.ActivityUtils;
import com.hlct.android.util.IntenetUtils;
import com.hlct.android.util.SecurityUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hlct.android.DataCache.DataCache.getParseResult;
import static com.hlct.android.constant.DatabaseConstant.DATACHCHE_FILE_RESULT;
import static com.hlct.android.constant.DatabaseConstant.DATACHCHE_USER;
import static com.hlct.android.constant.DatabaseConstant.setupDatabase;
import static com.hlct.android.constant.HttpConstant.BASE_SERVER_URL;
import static com.hlct.android.constant.HttpConstant.flag;
import static com.hlct.android.util.IntenetUtils.NETWORN_2G;
import static com.hlct.android.util.IntenetUtils.NETWORN_NONE;
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

    //等待的Dialog
    private MaterialDialog materialDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityUtils.getInstance().addActivity(this);
        //初始化数据库
        daoSession = setupDatabase(this, daoSession);
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
                if (flag.equals("net")) {
                    doNetLogin();
                } else if (flag.equals("file")) {
                    doFileLogin();
                }
            }
        });
    }

    /**
     * 通过查询数据库进行登陆
     */
    public void doFileLogin() {
        mAsyncTask mAsyncTask = new mAsyncTask();
        mAsyncTask.execute();
    }

    /**
     * 通过网络进行操作
     */
    private void doNetLogin() {
        materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                .title("正在登陆")
                .content("请稍后")
                .progress(true, 0)
                .cancelable(false)
                .show();
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
                materialDialog.dismiss();
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
                materialDialog.dismiss();
                t.printStackTrace();
                switch (IntenetUtils.getNetworkState(getApplicationContext())) {
                    case NETWORN_NONE:
                        showSnackbar(getWindow().getDecorView(), "请打开网络数据连接");
                        break;
                    case NETWORN_2G:
                        showSnackbar(getWindow().getDecorView(), "当前网络连接质量不佳");
                        break;
                    default:
                        showSnackbar(getWindow().getDecorView(), "无法连接服务器");
                }
            }
        });

    }


    private class mAsyncTask extends AsyncTask<String, Integer, String> {

        /**
         * 执行后台任务前的UI操作
         */
        @Override
        protected void onPreExecute() {
            //获取输入的账号密码
            LOGINNAME = mLoginName.getText().toString();
            //加密
            PASSWORD = SecurityUtils.getMD5(mPasswordView.getText().toString());
            //弹出提示
            materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                    .title("正在登陆")
                    .content("请稍后")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            super.onPreExecute();
        }

        /**
         * 后台任务
         *
         * @param strings
         * @return string
         */
        @Override
        protected String doInBackground(String... strings) {
            boolean flag = getParseResult(getApplicationContext(), DATACHCHE_FILE_RESULT);
            String s = "";
            if (flag == true) {//如果数据解析成功
                //查询数据
                User user = daoSession.queryBuilder(User.class)
                        .where(UserDao.Properties.LOGIN_NAME.eq(LOGINNAME))
                        .unique();
                try {
                    if (user.getPASSWORD().equals(PASSWORD)) {
                        DataCache.saveUser(getApplicationContext(), DATACHCHE_USER, user);
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        s = "账号密码有误";
                    }
                } catch (Exception e) {
                    s = "账号密码有误";
                }

            } else if (flag == false) {//如果失败
                s = "文件解析失败";
            }
            return s;
        }

        /**
         * 后台任务完成后
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            materialDialog.dismiss();
            showSnackbar(getWindow().getDecorView(), s);
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onDestroy() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ActivityUtils.getInstance().destory();
        }
        return super.onKeyDown(keyCode, event);
    }
}

