package cn.ytang.james.uset.okhttp.test.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.uset.base.mvc.BaseModel;

/**
 * ${desc}
 * <p>
 * Created by James on 17/12/1.
 */

public class AdvertiseDetailModel implements BaseModel, Serializable {

  private long resourceId;
  private String aliasName;
  private int number;
  private List<AdvertisePlanModel> plans;

  public AdvertiseDetailModel() {}

  public List<AdvertisePlanModel> getPlans() {
    if (this.plans == null) {
      return null;
    } else {
      ArrayList list = new ArrayList();

      for (int i = 0; i < this.plans.size(); ++i) {
        AdvertisePlanModel plan = (AdvertisePlanModel) this.plans.get(i);
        if (!TextUtils.isEmpty(plan.getUrlContent())) {
          list.add(plan);
        }
      }

      return list;
    }
  }

}
