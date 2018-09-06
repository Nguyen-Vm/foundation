package org.linker.foundation.JavaBean;

import java.util.Date;

/**
 * @author RWM
 * @date 2018/8/25
 */
public class WechatUser {

    public String id;

    public String name;

    public Integer age;

    public Date createTime;

    public Date updateTime;

    public WechatUser(String id, String name, Integer age, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "WechatUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
