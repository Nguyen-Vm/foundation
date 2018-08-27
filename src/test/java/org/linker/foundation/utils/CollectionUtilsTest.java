package org.linker.foundation.utils;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.linker.foundation.JavaBean.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class CollectionUtilsTest {

    List<String> list = Lists.newArrayList("1", "2", "3", "4", "5");
    List<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);
    String[] array = new String[]{"1", "2", "3", "4", "5"};

    User user1 = new User("1", "yhj", 22, DateUtils.now());
    User user2 = new User("2", "rwm", 23, DateUtils.now());
    User user3 = new User("3", "jack", 23, DateUtils.now());
    List<User> users = Lists.newArrayList(user1, user2, user3);

    @Test
    public void isNullOrEmpty() {
        List<String> list = Lists.newArrayList();
        Assert.assertTrue(CollectionUtils.isNullOrEmpty(list));
        list.add("rwm");
        Assert.assertTrue(!CollectionUtils.isNullOrEmpty(list));

        String[] array1 = new String[]{};
        Assert.assertTrue(CollectionUtils.isNullOrEmpty(array1));
        String[] array2 = new String[1];
        Assert.assertTrue(!CollectionUtils.isNullOrEmpty(array2));
        String[] array3 = new String[]{"rwm"};
        Assert.assertTrue(!CollectionUtils.isNullOrEmpty(array3));

        Map<String, Object> map = Maps.newHashMap();
        Assert.assertTrue(CollectionUtils.isNullOrEmpty(map));
        map.put("name", "rwm");
        Assert.assertTrue(!CollectionUtils.isNullOrEmpty(map));
    }

    @Test
    public void headEndContains() {
        Assert.assertEquals("1", CollectionUtils.head(list));
        Assert.assertEquals("5", CollectionUtils.end(list));
        Assert.assertTrue(CollectionUtils.contains(array, "1"));
    }

    @Test
    public void map() {
        System.out.println(CollectionUtils.map(list, e -> e + "map"));
    }

    @Test
    public void groupBy() {
        ListMultimap<Integer, User> ageMap = CollectionUtils.groupBy(users, e -> e.age);
        System.out.println(ageMap);
        ListMultimap<Boolean, Integer> booleanMap = CollectionUtils.groupBy(integers, e -> e >= 3);
        System.out.println(booleanMap);
    }

    @Test
    public void ofMap() {
        LinkedHashMap<String, User> map = CollectionUtils.ofMap(users, e -> e.id);
        System.out.println(map);
    }

}
