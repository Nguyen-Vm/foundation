package org.linker.foundation.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.linker.foundation.JavaBean.User;

import java.io.IOException;
import java.util.List;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class JsonUtilsTest {

    User user = new User("1", "NguyenVm", 23, DateUtils.now());

    @Test
    public void toJSONString() {
        System.out.println(JsonUtils.toJSONString(user));

        PropertyFilter propertyFilter = (object, name, value) -> "id".equals(name) && value.equals("1");
        System.out.println(JsonUtils.toJSONString(user, propertyFilter));

        NameFilter nameFilter = (object, name, value) -> "new_" + name;
        System.out.println(JsonUtils.toJSONString(user, nameFilter));

        ValueFilter valueFilter = (object, name, value) -> {
            if (value instanceof String) {
                return "new_" + value;
            }
            return value;
        };
        System.out.println(JsonUtils.toJSONString(user, valueFilter));

        BeforeFilter beforeFilter = new BeforeFilter() {
            @Override
            public void writeBefore(Object object) {
                System.out.println("before");
                User user = (User) object;
                user.id = "new_" + user.id;
            }
        };
        System.out.println(JsonUtils.toJSONString(user, beforeFilter));
        System.out.println(user);

        AfterFilter afterFilter = new AfterFilter() {
            @Override
            public void writeAfter(Object object) {
                System.out.println("after");
                User user = (User) object;
                user.name = "modify_" + user.name;
            }
        };
        System.out.println(JsonUtils.toJSONString(user, afterFilter));
        System.out.println(user);
    }

    @Test
    public void jsonBytes() {
        byte[] bytes = JsonUtils.toJSONBytes(user);
        User byteUser = JsonUtils.parseObject(bytes, User.class);
        Assert.assertEquals(user.id, byteUser.id);
    }

    @Test
    public void toJson() {
        JSONObject jsonObject = JsonUtils.toJson(user);
        System.out.println(jsonObject);
        JSONObject jsonObject1 = JsonUtils.parseObject(jsonObject.toString());
        System.out.println(jsonObject1);
    }

    @Test
    public void parseObject() {
        String userStr = "{\"age\":23,\"birthday\":\"2018-08-27 13:23:26\",\"id\":\"1\",\"name\":\"NguyenVm\"}";
        User user = JsonUtils.parseObject(userStr, User.class);
        System.out.println(user);
    }

    @Test
    public void parseArray() {
        List<User> userList = Lists.newArrayList(user);
        System.out.println(JsonUtils.toJSONString(userList));
        List<User> users = JsonUtils.parseArray("[{\"age\":23,\"birthday\":\"2018-08-27 13:23:26\",\"id\":\"1\",\"name\":\"NguyenVm\"}]", User.class);
        System.out.println(JsonUtils.toJSONString(users));
    }

    @Test
    public void getResourceJSONString() throws IOException {
        String resourceJSONString = JsonUtils.getResourceJSONString("utils.json");
        System.out.println(resourceJSONString);
    }
}
