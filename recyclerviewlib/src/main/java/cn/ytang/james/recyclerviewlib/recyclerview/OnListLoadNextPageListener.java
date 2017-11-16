package cn.ytang.james.recyclerviewlib.recyclerview;

import android.view.View;

/**
 * RecyclerView/ListView/GridView 滑动加载下一页时的回调接口
 * 
 * Created by James on 17/11/9.
 */

public interface OnListLoadNextPageListener {

  /**
   * 开始加载下一页
   *
   * @param view 当前RecyclerView/ListView/GridView
   */
  public void onLoadNextPage(View view);

}
