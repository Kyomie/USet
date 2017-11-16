package cn.ytang.james.uset.mvp.ui.view;

import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public interface IMeituMeijuView {

    void onSuccess(SentenceListDetail sentenceListDetail);

    void onError(Throwable e);

}
