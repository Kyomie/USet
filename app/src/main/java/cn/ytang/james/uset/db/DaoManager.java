package cn.ytang.james.uset.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.ytang.james.uset.db.gen.DaoMaster;
import cn.ytang.james.uset.db.gen.DaoSession;

/**
 * 进行数据库的管理
 * 1、创建数据库
 * 2、创建数据库表
 * 3、对数据库的增删改查
 * 4、对数据路进行升级
 *
 * Created by James on 17/11/20.
 */

public class DaoManager {

    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "uset.db";
    private volatile static DaoManager sDaoManager; //多线程访问
    private static GreenDaoOpenHelper sHelper;
    private static DaoMaster sDaoMaster;
    private static DaoSession sDaoSession;
    private static SQLiteDatabase sDb;

    private Context mContext;

    public static DaoManager getInstance() {
        DaoManager instance = null;
        if(sDaoManager == null) {
            synchronized (DaoManager.class) {
                if (instance == null) {
                    instance = new DaoManager();
                    sDaoManager = instance;
                }
            }
        }
        return sDaoManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
    *
    *   判断数据库是否存在，如果不存在则创建
    *
    *   author James 
    *   email yangnaochun@wanda.cn
    *   create 17/11/20  上午10:41
    */
    public DaoMaster getDaoMaster() {
        if (null == sDaoMaster){
            sHelper =  new GreenDaoOpenHelper(mContext,DB_NAME);
            sDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    public DaoSession getDaoSession(){
        if (null == sDaoSession){
            if (null == sDaoMaster){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     * @param flag
     */
    public void setDebug(boolean flag){
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession(){
        if (null != sDaoSession){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

    public  void  closeHelper(){
        if (sHelper!=null){
            sHelper.close();
            sHelper = null;
        }
    }


}
