package org.linker.foundation.utils;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.linker.foundation.JavaBean.User;
import org.linker.foundation.JavaBean.WechatUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author RWM
 * @date 2018/8/25
 */
public class BeanUtilsTest {

    @Test
    public void castTo() {
        User user = new User(UUID.randomUUID().toString(), "NguyenVm", 23, DateUtils.now());
        System.out.println(user);
        WechatUser wechatUser = BeanUtils.castTo(user, WechatUser.class);
        System.out.println(wechatUser);
        wechatUser.createTime = user.birthday;
        wechatUser.updateTime = DateUtils.now();
        System.out.println(wechatUser);

        List<User> users = Lists.newArrayList(user);
        System.out.println(users);
        List<WechatUser> wechatUsers = BeanUtils.castTo(users, WechatUser.class);
        System.out.println(wechatUsers);
    }

    @Test
    public void beanToMap() {
        User user = new User(UUID.randomUUID().toString(), "NguyenVm", 23, DateUtils.now());
        Map<String, Object> map = BeanUtils.bean2Map(user);
        System.out.println(map);
    }

    @Test
    public void mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "c2b180e8-3299-4bcc-ba16-bbbdbf1b59c6");
        map.put("name", "NguyenVm");
        map.put("age", 23);
        map.put("birthday", DateUtils.now());
        User user = BeanUtils.map2Bean(map, User.class);
        System.out.println(user);
    }

    @Test
    public void combine() {
        User model = new User("modelId", "modelName", 24, null);
        WechatUser request = new WechatUser("requestId", "requestName", 23, null, null);
        model = (User) BeanUtils.combineModel(model, request);
        System.out.println(model);
    }
}
