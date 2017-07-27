package com.hlct.android.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlct.android.DataCache.DataCache;
import com.hlct.android.R;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.PlanBean;
import com.hlct.android.bean.PropertyPlan;
import com.hlct.android.bean.User;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.util.ActivityUtils;
import com.hlct.android.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.hlct.android.constant.DatabaseConstant.DATACHCHE_FILE_RESULT;
import static com.hlct.android.constant.DatabaseConstant.FILE_PATH;
import static com.hlct.android.constant.DatabaseConstant.setupDatabase;

public class WelcomeActivity extends AppCompatActivity {
    //等待的Dialog
    private MaterialDialog materialDialog = null;
    //等待的ProgressBar
    private ProgressBar progressBar = null;
    //时间戳
    private String date = FileUtils.getDate();
    //Dao对象的管理者
    private static DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        setContentView(R.layout.activity_welcome);
        //设为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取progressBar
        progressBar = (ProgressBar) findViewById(R.id.pb_welcome);
        //初始化数据库
        daoSession = setupDatabase(this, daoSession);
        //解析文件并存入数据库
        mAsyncTask mAsyncTask = new mAsyncTask();
        mAsyncTask.execute();
    }

    private class mAsyncTask extends AsyncTask<String, Integer, String> {

        /**
         * 执行后台任务前的UI操作
         */
        @Override
        protected void onPreExecute() {

        }

        /**
         * 后台任务
         *
         * @param strings
         * @return string
         */
        @Override
        protected String doInBackground(String... strings) {
            String str;
            String details;
            String plans;
            Gson gson = new Gson();
            PropertyPlan p = new PropertyPlan();
            boolean flag = false;
            try {
                //解析文件
                str = FileUtils.readFile(FILE_PATH + date + "WDRW.txt");
                p = gson.fromJson(str, PropertyPlan.class);
                details = FileUtils.readFile(FILE_PATH+"detail.txt");
                plans = FileUtils.readFile(FILE_PATH + "plan.txt");
                ArrayList<Detail> detailArrayList = gson.fromJson(details, new TypeToken<ArrayList<Detail>>() {}.getType());
                ArrayList<PlanBean> planBeanArrayList =
                        gson.fromJson(plans,new TypeToken<ArrayList<PlanBean>>(){}.getType());
                //清空当前数据库
                daoSession.deleteAll(User.class);
                daoSession.deleteAll(Detail.class);

                //存入数据库
                Log.e("开始时间---->", new Date().toString());
                daoSession.getPlanBeanDao().insertOrReplaceInTx(planBeanArrayList);
                daoSession.getUserDao().insertOrReplaceInTx(p.getUser());
                daoSession.getDetailDao().insertOrReplaceInTx(detailArrayList);
                /*for (int i = 0; i < p.getUser().size(); i++) {
                    daoSession.insert(p.getUser().get(i));
                }
                for (int i = 0; i < detailArrayList.size(); i++) {
                    daoSession.insert(detailArrayList.get(i));
                }*/
                Log.e("结束时间---->", new Date().toString());
                flag = true;
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
            } catch (NullPointerException e) {
                flag = false;
                e.printStackTrace();
            }

            //将解析结果放入缓存
            DataCache.saveParseResult(getApplicationContext(), DATACHCHE_FILE_RESULT, flag);

            return null;
        }

        /**
         * 后台任务完成后
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
        super.onDestroy();
    }
}
