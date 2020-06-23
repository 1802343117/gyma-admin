package com.soft1851.devoted.util;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 12559
 * 使用单例模型管理通信用户
 */
public class UserUtil {

    // 静态的 UserUtils 表明该对象只有一个
    private static UserUtil userUtils;

    // 用户通信容器
    private Map<String ,Session> map;

    /**
     * 仅允许创建一次该类对象
     * @return
     */
    public static synchronized UserUtil getUserUtils() {
        if ( userUtils == null ) {
            userUtils = new  UserUtil();
        }
        return userUtils;
    }

    /**
     * 无参构造器，提供参加该类对象
     * 在创建对象的同时 创建存储空间
     */
    private UserUtil() {
        this.map = new HashMap<>();
    }

    /**
     * 添加通信用户
     * @param session
     */
    public synchronized void addUser(String userPath ,Session session) {
        this.map.put(userPath, session);
        System.out.println(this.map.toString());
    }

    /**
     * 指定聊天对象进行点对点消息推送
     * @param message
     */
    public synchronized void sending(String message) {
        // 获取接收方的ID
        JSONObject jsonObject = JSONObject.parseObject(message);
        String i = jsonObject.getString("mToUserid");
        System.out.println(i);
//        String mess = jsonObject.getString("mContent");
        try {
            this.map.get(i).getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户退出后删除容器中的用户信息
     * @param session
     */
    public synchronized void delete(Session session) {
        String key = this.getKey(session);
        this.map.remove(key ,session);

        System.out.println(this.map.toString());
    }

    /**
     * 查看通信用户容器中的通信地址
     * @param session
     * @return String
     * 返回 null 说明没有该用户的通信地址
     */
    public synchronized String getKey(Session session) {
        // 变量查看 List 集合中 Session 对应的索引
        for (String key : this.map.keySet()) {
            if ( this.map.get(key).equals(session) ) {
                return key;
            }
        }
        return null;
    }

    /**
     * 获取通信地址
     * @param key
     * @return
     */
    public synchronized Session getValue(String key) {
        return this.map.get(key);
    }
}
