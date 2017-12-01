package cn.ytang.james.uset.base.mvc;

import android.text.TextUtils;

import java.io.Serializable;

/**
 *
 *
 * Created by James on 17/11/17.
 */

public class BaseHttpModel implements Serializable{

    private int status;
    private String message;
    private String msg;

    public BaseHttpModel() {}

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return !TextUtils.isEmpty(this.message)?this.message:(!TextUtils.isEmpty(this.msg)?this.msg:null);
    }

}
