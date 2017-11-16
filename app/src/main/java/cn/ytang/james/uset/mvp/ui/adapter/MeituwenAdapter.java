package cn.ytang.james.uset.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.ytang.james.uset.R;
import cn.ytang.james.uset.mvp.model.bean.SentenceImageText;
import cn.ytang.james.uset.utils.StringUtil;
import cn.ytang.james.uset.widget.ShowMaxImageView;

/**
 * 图文
 *
 * Created by James on 17/11/16.
 */

public class MeituwenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mInflater;

    private Context mContext;

    private List<SentenceImageText> mDatas;

    private View.OnClickListener onItemClick;

    public MeituwenAdapter(Context context, List<SentenceImageText> mDatas, View.OnClickListener onItemClick) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.onItemClick = onItemClick;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_scene_imgtext, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        SentenceImageText sentenceImageText = mDatas.get(position);

        if (sentenceImageText != null) {
            Glide.with(mContext)
                    .load(sentenceImageText.getPic())
                    .asBitmap()
                    .placeholder(R.drawable.load_default_img)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imgView);

            if (StringUtil.isEmpty(sentenceImageText.getDesc())) {
                viewHolder.textDesc.setVisibility(View.GONE);
            } else {
                viewHolder.textDesc.setVisibility(View.VISIBLE);
                viewHolder.textDesc.setText(sentenceImageText.getDesc());
            }

            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(onItemClick);
        } else {
            Glide.clear(viewHolder.imgView);
            // remove the placeholder (optional); read comments below
            viewHolder.imgView.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ShowMaxImageView imgView;
        public TextView textDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            imgView = (ShowMaxImageView) itemView.findViewById(R.id.imgView);
            textDesc = (TextView) itemView.findViewById(R.id.textDesc);
        }
    }

}
