package cn.ytang.james.uset.mvp.model;

import android.content.Context;

import cn.ytang.james.uset.mvp.presenter.callback.OnImgTextListener;

/**
 * 原创句子
 *
 * Created by James on 17/11/16.
 */

public interface IImgTextModel {

    void loadMeiju(Context context, boolean isFirst, String type, String page, OnImgTextListener listener);

    void loadMeiju(Context context, boolean isFirst, String page, OnImgTextListener listener);

}
