package cmy.android_jk_new;

import cmy.android_jk_new.bean.ResultInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static cmy.android_jk_new.constant.HttpConstant.URL_PDA_LOGIN;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/21
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :
 */

public interface APIService {
    /**
     * 获取登陆信息的返回
     *
     * @return ResultInfo
     */
    @FormUrlEncoded
    @POST(URL_PDA_LOGIN)
    Call<ResultInfo> login(@Field("login_name") String login_name,
                           @Field("login_password") String login_password);
}
