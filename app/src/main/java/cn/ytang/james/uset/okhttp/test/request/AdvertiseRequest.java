package cn.ytang.james.uset.okhttp.test.request;

import cn.ytang.james.uset.okhttp.test.model.AdvertiseResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 获取ad数据示例
 *
 * Created by James on 17/12/1.
 */

public interface AdvertiseRequest {

    String PATH = "/advertise/v3/materials";

    String KEY_ALIAS = "aliasName";
    String KEY_CITY_ID = "cityId";

    /**
     *
     * @param aliasName
     * @param cityId
     * @return
     */

    @GET(PATH)
    Call<AdvertiseResponseModel> getAdvertiseModel(
            @Query(KEY_ALIAS) String aliasName,
            @Query(KEY_CITY_ID) String cityId);

}
