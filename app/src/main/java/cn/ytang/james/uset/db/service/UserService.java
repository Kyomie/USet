package cn.ytang.james.uset.db.service;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.uset.db.BaseDaoService;
import cn.ytang.james.uset.db.entity.User;
import cn.ytang.james.uset.db.gen.UserDao;

/**
 * user表的一些数据库操作
 *
 * Created by James on 17/11/20.
 */

public class UserService extends BaseDaoService<User>{

    public UserService(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     * @param id
     * @return
     */
    private User loadById(long id){

        return mDaoSession.getUserDao().load(id);
    }

    /**
     * 获取某个对象的主键ID
     * @param user
     * @return
     */
    private long getID(User user){

        return mDaoSession.getUserDao().getKey(user);
    }

    /**
     * 通过名字获取User对象
     * @return
     */
    public List<User> getUserByName(String key){
        QueryBuilder queryBuilder =  mDaoSession.getUserDao().queryBuilder();
        queryBuilder.where(UserDao.Properties.Name.eq(key));
        int size = queryBuilder.list().size();
        if (size > 0){
            return queryBuilder.list();
        }else{
            return null;
        }
    }

    /**
     * 通过名字获取User对象ids
     * @return
     */
    private List<Long> getIdByName(String key){
        List<User> users = getUserByName(key);
        List<Long> ids = new ArrayList<Long>();
        int size = users.size();
        if (size > 0){
            for (int i = 0;i < size;i++){
                ids.add(users.get(i).getId());
            }
            return ids;
        }else{
            return null;
        }
    }

    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     * @param id
     */
    private void deleteById(long id){

        mDaoSession.getUserDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     * @param ids
     */
    private void deleteByIds(List<Long> ids){

        mDaoSession.getUserDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在此添加一些User特有的数据库操作语句
     * ************************************/

}
