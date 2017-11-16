package cn.ytang.james.uset.mvp.presenter.callback;

import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public interface OnImgTextListener {

    void onSuccess(SentenceListDetail sentenceListDetail);

    void onError(Throwable e);

}
