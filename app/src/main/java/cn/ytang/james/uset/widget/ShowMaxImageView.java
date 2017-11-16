package cn.ytang.james.uset.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import cn.ytang.james.uset.conf.GlobalConfig;
import cn.ytang.james.uset.utils.ScreenUtil;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class ShowMaxImageView extends ImageView {

    private float mHeight = 0;

    public ShowMaxImageView(Context context) {
        super(context);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowMaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {

        if (bm != null) {
            getHeight(bm);
        }

        super.setImageBitmap(bm);
        requestLayout();
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {

        if (drawable != null) {
            getHeight(drawableToBitamp(drawable));
        }

        super.setImageDrawable(drawable);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        if (mHeight != 0) {

            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

            int resultHeight = (int) Math.max(mHeight, sizeHeight);

            if (resultHeight >= ScreenUtil.getScreenHeight(getContext())) {
                resultHeight = ScreenUtil.getScreenHeight(getContext()) / 3;
            }

            setMeasuredDimension(sizeWidth, resultHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    private void getHeight(Bitmap bm) {

        float bitmapWidth = bm.getWidth();
        float bitmapHeight = bm.getHeight();

        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scaleWidth = getWidth() / bitmapWidth;
            if (scaleWidth != 0) {
                mHeight = bitmapHeight * scaleWidth;
            }
        }
    }


    private Bitmap drawableToBitamp(Drawable drawable) {

        if (drawable != null) {
            if (drawable instanceof GlideBitmapDrawable) {
                GlideBitmapDrawable bd = (GlideBitmapDrawable) drawable;
                return bd.getBitmap();

            } else if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                return bd.getBitmap();

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
