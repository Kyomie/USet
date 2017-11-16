package cn.ytang.james.uset.mvp.presenter;

import android.content.Context;

/**
 * 美图美句
 * 
 * Created by James on 17/11/16.
 */

public interface IMeituPresenter {

    void loadImgText(Context context, boolean isFirst, String type, String page);

    void loadImgText(Context context, boolean isFirst, String page);

}
