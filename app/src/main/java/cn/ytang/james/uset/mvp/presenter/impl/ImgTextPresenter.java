package cn.ytang.james.uset.mvp.presenter.impl;

import android.content.Context;

import cn.ytang.james.uset.mvp.model.IImgTextModel;
import cn.ytang.james.uset.mvp.model.ImgTextModelImpl;
import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;
import cn.ytang.james.uset.mvp.presenter.IMeituPresenter;
import cn.ytang.james.uset.mvp.presenter.callback.OnImgTextListener;
import cn.ytang.james.uset.mvp.ui.view.IMeituMeijuView;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class ImgTextPresenter implements IMeituPresenter, OnImgTextListener {

    private IMeituMeijuView mIMeituMeijuView;
    private IImgTextModel mIImgTextModel;

    public ImgTextPresenter(IMeituMeijuView iMeituMeijuView) {
        this.mIMeituMeijuView = iMeituMeijuView;
        this.mIImgTextModel = new ImgTextModelImpl();
    }

    @Override
    public void loadImgText(Context context, boolean isFirst, String type, String page) {
        mIImgTextModel.loadMeiju(context, isFirst, type, page, this);
    }

    @Override
    public void loadImgText(Context context, boolean isFirst, String page) {
        mIImgTextModel.loadMeiju(context, isFirst, page, this);
    }

    @Override
    public void onSuccess(SentenceListDetail sentenceListDetail) {
        mIMeituMeijuView.onSuccess(sentenceListDetail);
    }

    @Override
    public void onError(Throwable e) {
        mIMeituMeijuView.onError(e);
    }
}
