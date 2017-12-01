package cn.ytang.james.uset.okhttp;

import cn.ytang.james.uset.base.mvc.BaseHttpModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 *
 * Created by James on 17/12/1.
 */

public abstract class USetCallback<T> implements Callback<T> {

  /**
   * 错误状态－Cookie过期
   */
  private static final int STATUS_COOKIE_EXPIRE = 3001;
  /**
   * 错误状态－登录身份失效
   */
  private static final int STATUS_NOT_LOGIN = 401;

  /**
   * 错误状态－验证身份失败
   */
  private static final int STATUS_VERIFY_K2_ERROR = 4002;

  @Override
  public void onResponse(Call<T> call, Response<T> response) {
    if (response == null) {
      onFailure("response model is null");
      return;
    }
    if (response.body() instanceof BaseHttpModel) {
      BaseHttpModel model = (BaseHttpModel) response.body();
      if (model.getStatus() == STATUS_COOKIE_EXPIRE || model.getStatus() == STATUS_NOT_LOGIN) {
        // todo
        // AccountManager.getInstance().setTokenOver();
        // DialogUtil.showCookieDialog(model.getMessage());
        onFailure(model.getMessage());
        return;
      } else if (model.getStatus() == STATUS_VERIFY_K2_ERROR) {
        // todo
        // skip to default activity
        // MainThreadPostUtils.toast(model.getMessage());
        onFailure(model.getMessage());
        return;
      }

      if (response.body() != null) {
        onResponse(response.body());
      } else {
        onFailure("response body is null");
      }

    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable t) {
    if (t != null) {
      t.printStackTrace();
      onFailure(t.getMessage());
    } else {
      onFailure("network error");
    }
  }

  /**
   *
   * @param t model wouldn't be null
   */
  public abstract void onResponse(T t);

  public abstract void onFailure(String messgae);

}
