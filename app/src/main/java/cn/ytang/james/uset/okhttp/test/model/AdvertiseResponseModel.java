package cn.ytang.james.uset.okhttp.test.model;

import java.io.Serializable;
import java.util.List;

import cn.ytang.james.uset.base.mvc.BaseHttpModel;
import cn.ytang.james.uset.base.mvc.BaseModel;

/**
 *
 *
 * Created by James on 17/12/1.
 */

public class AdvertiseResponseModel extends BaseHttpModel implements BaseModel, Serializable {

  private AdvertiseResultModel data;

  public AdvertiseResponseModel() {}

  public AdvertiseResultModel getData() {
    return this.data;
  }

  public List<AdvertisePlanModel> getPlans() {
    return this.data == null ? null : this.data.getPlans();
  }

}
