package cn.ytang.james.uset.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 *
 * Created by James on 17/11/17.
 */

public class ViewUtil {

    public static View newInstance(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, (ViewGroup) null);
    }

    public static View newInstance(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent);
    }

}
