package cn.ytang.james.uset.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import java.io.InputStream;

import cn.ytang.james.uset.http.Api;
import cn.ytang.james.uset.http.ServiceFactory;
import cn.ytang.james.uset.http.USetService;
import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;
import cn.ytang.james.uset.mvp.presenter.callback.OnImgTextListener;
import cn.ytang.james.uset.utils.DocParseUtil;
import cn.ytang.james.uset.utils.StringUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class ImgTextModelImpl implements IImgTextModel {

    private Context mContext;
    private USetService mService;
    private OnImgTextListener mListener;

    @Override
    public void loadMeiju(Context context, boolean isFirst, String type, String page, OnImgTextListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mService = ServiceFactory.getInstance().createService(USetService.class, Api.BASE_URL_MEITUMEIJU);
        loadMeiju(isFirst, type, page);
    }

    @Override
    public void loadMeiju(Context context, boolean isFirst, String page, OnImgTextListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mService = ServiceFactory.getInstance().createService(USetService.class, Api.BASE_URL_MEITUMEIJU);
        loadMeiju(isFirst, null, page);
    }

    private void loadMeiju(final boolean isFirst, String type, String page) {
        Call<ResponseBody> call = null;
        if (TextUtils.isEmpty(type)) {
            String url = Api.BASE_URL_MEITUMEIJU;
            if (!TextUtils.isEmpty(page)) {
                url = url + "?page=" + page;
            }
            call = mService.loadMeiJuRequest(url);

        } else {
            call = mService.loadMeijuRequest(type, page);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    InputStream inputStream = response.body().byteStream();
                    String result = StringUtil.inToString(inputStream);
                    SentenceListDetail sentenceListDetail = DocParseUtil.parseMeiju(isFirst, result);
                    mListener.onSuccess(sentenceListDetail);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mListener.onError(t);
            }
        });
    }

}
