package cn.ytang.james.uset.db;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import cn.ytang.james.uset.db.gen.DaoMaster;
import cn.ytang.james.uset.db.gen.UserDao;

/**
 *
 *
 * Created by James on 17/11/20.
 */

public class GreenDaoOpenHelper extends DaoMaster.OpenHelper{

    public GreenDaoOpenHelper(Context context, String dbName){
        super(context,dbName,null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
            Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.getInstance().migrate(db, UserDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
            //MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

}
