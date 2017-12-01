package cn.ytang.james.uset.okhttp.test;

import android.util.Log;

import cn.ytang.james.uset.okhttp.ApiCreator;
import cn.ytang.james.uset.okhttp.USetCallback;
import cn.ytang.james.uset.okhttp.test.model.AdvertiseResponseModel;
import cn.ytang.james.uset.okhttp.test.request.AdvertiseRequest;
import retrofit2.Call;

/**
 * api接口测试
 *
 * Created by James on 17/12/1.
 */

public class ApiTest {

  private void getAdData() {
    AdvertiseRequest request =
        ApiCreator.createApiWithSopHost(AdvertiseRequest.class, "http://api.sit.ffan.com");
    Call<AdvertiseResponseModel> call =
        request.getAdvertiseModel("FA60A96BFF93327A5C3A1D703D2C656D", "310100");
    call.enqueue(new USetCallback<AdvertiseResponseModel>() {
      @Override
      public void onResponse(AdvertiseResponseModel model) {
        if (model != null && model.getData() != null) {
          Log.i("uset", "model != null");
        }
      }

      @Override
      public void onFailure(String message) {
        Log.e("uset", "msg = " + message);
      }
    });
  }

}
