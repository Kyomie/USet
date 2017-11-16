package cn.ytang.james.uset.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public interface USetService {

    @GET
    Call<ResponseBody> loadMeiJuRequest(@Url String url);

    // 手写美句子
    @GET("{type}")
    Call<ResponseBody> loadMeijuRequest(@Path("type") String type, @Query("page") String page);

}
