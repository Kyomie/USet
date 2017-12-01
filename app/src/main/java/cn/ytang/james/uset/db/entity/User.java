package cn.ytang.james.uset.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 *
 * Created by James on 17/11/17.
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Property(nameInDb = "name")
    private String name;

    @NotNull
    @Property(nameInDb = "pwd")
    private String password;

    @NotNull
    @Property(nameInDb="loginTimes")
    private int loginTimes;

    @Transient
    private String nickName;

    @Property(nameInDb = "desc")
    private String desc;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLoginTimes() {
        return this.loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Generated(hash = 1971261019)
    public User(Long id, @NotNull String name, @NotNull String password,
            int loginTimes, String desc) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.loginTimes = loginTimes;
        this.desc = desc;
    }

    @Generated(hash = 586692638)
    public User() {
    }

}
