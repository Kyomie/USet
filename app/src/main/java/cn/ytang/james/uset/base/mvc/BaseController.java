package cn.ytang.james.uset.base.mvc;

/**
 *
 *
 * Created by James on 17/11/17.
 */

public abstract class BaseController <V extends BaseView, T extends BaseModel>{

    public BaseController() {}

    public void preBind(V view){}

    public void unBind(){}

    public abstract void bind(V v, T t);

}
