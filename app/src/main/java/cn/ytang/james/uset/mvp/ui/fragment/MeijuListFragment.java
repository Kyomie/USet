package cn.ytang.james.uset.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.recyclerviewlib.flexibledivider.HorizontalDividerItemDecoration;
import cn.ytang.james.recyclerviewlib.recyclerview.EndlessRecyclerOnScrollListener;
import cn.ytang.james.recyclerviewlib.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import cn.ytang.james.recyclerviewlib.recyclerview.RecyclerViewLoadingFooter;
import cn.ytang.james.recyclerviewlib.recyclerview.RecyclerViewStateUtils;
import cn.ytang.james.uset.R;
import cn.ytang.james.uset.base.BaseFragment;
import cn.ytang.james.uset.mvp.model.bean.SentenceImageText;
import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;
import cn.ytang.james.uset.mvp.presenter.impl.ImgTextPresenter;
import cn.ytang.james.uset.mvp.ui.adapter.MeituwenAdapter;
import cn.ytang.james.uset.mvp.ui.view.IMeituMeijuView;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class MeijuListFragment extends BaseFragment implements IMeituMeijuView {

    private static final String TAG = MeijuListFragment.class.getSimpleName();

    private static final String ARG_TYPE = "type";

    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImgTextPresenter mImgTextPresenter;
    private HeaderAndFooterRecyclerViewAdapter mAdapter;

    private String mType;
    private List<SentenceImageText> mDatas;
    private String page;
    private String totalpage;
    private boolean mHasMore = true;
    private boolean isRefresh = true;

    public static MeijuListFragment newInstance(String type) {
        MeijuListFragment fragment = new MeijuListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,">>>onCreate");
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_meiju_list, container, false);
            initviews(mView);
            mImgTextPresenter = new ImgTextPresenter(this);
            queryMeijus();
        }
        return mView;
    }

    private void initviews(View v) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.layoutSwipeRefresh);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.listJuzi);

        mDatas = new ArrayList<>();
        MeituwenAdapter adapter = new MeituwenAdapter(getActivity(), mDatas, onClickListener);
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .colorResId(R.color.divider_color)
                        .size(4)
                        .build());

        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh_color));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = null;
            isRefresh = true;

            queryMeijus();
        }
    };


    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            RecyclerViewLoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);

            if (state == RecyclerViewLoadingFooter.State.Loading) {
                return;
            }

            if (mHasMore) {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mHasMore, RecyclerViewLoadingFooter.State.Loading, null);
                queryMeijus();

            } else {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mHasMore, RecyclerViewLoadingFooter.State.TheEnd, null);
            }
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mHasMore, RecyclerViewLoadingFooter.State.Loading, null);

            queryMeijus();
        }
    };

    private void queryMeijus() {
        if (TextUtils.isEmpty(mType)) {
            mImgTextPresenter.loadImgText(getActivity(), isRefresh, page);
        } else {
            mImgTextPresenter.loadImgText(getActivity(), isRefresh, mType, page);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mView) {
//            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }

    @Override
    public void onSuccess(SentenceListDetail sentenceListDetail) {
        List<SentenceImageText> sentenceImageTexts = sentenceListDetail.mImageTexts;

        if (page == null) {
            page = "1";
        } else {
            int i_page = Integer.parseInt(page);
            i_page = i_page + 1;
            page = "" + i_page;
        }

        if (isRefresh) {

            mDatas.clear();

            isRefresh = false;

            totalpage = sentenceListDetail.page;
        }

        if (page.equals(totalpage)) {
            mHasMore = false;
        }

        Log.i(TAG,"mHasMore : " + mHasMore + "  currentpage : " + page + "  totalpage : " + totalpage);

        if (sentenceImageTexts != null) {
            mDatas.addAll(sentenceImageTexts);
            mAdapter.notifyDataSetChanged();
        }

        mSwipeRefreshLayout.setRefreshing(false);

        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, RecyclerViewLoadingFooter.State.Normal);
    }

    @Override
    public void onError(Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, mHasMore, RecyclerViewLoadingFooter.State.NetWorkError, mFooterClick);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int pos = (int) view.getTag();
            SentenceImageText sentenceImageText = mDatas.get(pos);
            Log.i(TAG,"onItemClick position: " + pos);
        }
    };
}
