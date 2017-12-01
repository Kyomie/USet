package cn.ytang.james.uset.okhttp.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.ytang.james.uset.base.mvc.BaseModel;

/**
 * ${desc}
 * <p>
 * Created by James on 17/12/1.
 */

public class AdvertiseResultModel implements BaseModel, Serializable {

  private long cityId;
  private long plazaId;
  private Map<Integer, AdvertiseDetailModel> datas;

  public AdvertiseResultModel() {}

  public List<AdvertisePlanModel> getPlans() {
    if (this.datas == null) {
      return null;
    } else {
      ArrayList list = new ArrayList();
      Iterator var2 = this.datas.entrySet().iterator();

      while (var2.hasNext()) {
        Map.Entry entry = (Map.Entry) var2.next();
        list.addAll(((AdvertiseDetailModel) entry.getValue()).getPlans());
      }

      return list;
    }
  }

}
