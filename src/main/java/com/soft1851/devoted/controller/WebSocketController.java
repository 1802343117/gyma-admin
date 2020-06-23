package com.soft1851.devoted.controller;

import com.alibaba.fastjson.JSONObject;
import com.soft1851.devoted.domain.entity.TMessages;
import com.soft1851.devoted.domain.entity.TUser;
import com.soft1851.devoted.util.DateUtil;
import com.soft1851.devoted.util.MapperUtil;
import com.soft1851.devoted.util.UserUtil;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author 12559
 */
@ServerEndpoint("/websocket")
@Component
public class WebSocketController {

    private TUser tUser;

    private List<TUser> adminList;

    /**
     * 连接成功时被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("连接成功");
        System.out.println("请求参数" + "\t" + session.getQueryString());
        // 获取前端传入的用户ID
        Integer userId = Integer.parseInt(session.getQueryString());
        // 判断参数是否为空
        if ( userId != null ) {
            // 根据ID获取用户信息
            tUser = MapperUtil.selectById(userId);

            // 创建通信地址
            UserUtil userUtil = UserUtil.getUserUtils();
            userUtil.addUser(tUser.getUserPath(), session);

            // 修改账号状态
            tUser.setStatus(true);
            MapperUtil.updateUserPath(tUser);
        } else {
            System.out.println("连接失败");
        }

    }

    /**
     * 接收到客户端发送的数据时被调用
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("我接收到的信息：" + message);
        // 将聊天消息保存
//        ChatUtil chatUtil = ChatUtil.getChatUtils();
//        chatUtil.addMessages(message);

        // 将消息 message 转成 jsonObject 对象
        JSONObject jsonObject = JSONObject.parseObject(message);

        // 通过 jsonObject 对象获取 发送者的ID
        String mFromUserId = jsonObject.getString("mFromUserid");
        // 通过 jsonObject 对象获取 接收者的ID、
        Object mToUserId = jsonObject.getString("mToUserid");
        // 通过 jsonObject 对象获取 消息内容
        String mContent = jsonObject.getString("mContent");
        // 通过 jsonObject 对象获取 接收地址
        String userPath = jsonObject.getString("userPath");

        TMessages tMessages = new TMessages();
        LocalDateTime localDateTime = LocalDateTime.now();
        // 时间格式化
        DateUtil dateUtil = new DateUtil();

        // 判断消息是否有接收对象
        if ( mToUserId == null ) {
            // 客服机器人 “ 小 T ”
            try {
                // 获取在线客服的相关消息
                adminList = MapperUtil.selectRsu();
                System.out.println("在线客服有：" + adminList);
                if ( adminList.size() != 0 ) {
                    // 生成随机数，通过随机数作为索引来分配服务员任务
                    Random random = new Random(1);
                    System.out.println(adminList.size());
                    int index = random.nextInt(adminList.size());

                    tUser = adminList.get(index);
                    // 客服收信地址
                    String adminPath = tUser.getUserPath();

                    tMessages.setDeleteFlag(true);
                    tMessages.setGmtCreate(localDateTime);
                    tMessages.setGmtModified(localDateTime);
                    tMessages.setMContent("你好！客服0573为您服务");
                    tMessages.setMFromUserid(Integer.toString(tUser.getUserId()));
                    tMessages.setUserPath(adminPath);
                    tMessages.setMTime(dateUtil.getDate(localDateTime));
                    UserUtil userUtil = UserUtil.getUserUtils();
                    tMessages.setMToUserid(userUtil.getKey(session));
                    // 将对象转成JSON字符串
                    String json = JSONObject.toJSONString(tMessages);
                    System.out.println(json);
                    // 将消息推送给用户
                    UserUtil userUtils = UserUtil.getUserUtils();
                    userUtils.sending(json);
                } else {

                    tMessages.setDeleteFlag(true);
                    tMessages.setGmtCreate(localDateTime);
                    tMessages.setGmtModified(localDateTime);
                    tMessages.setMContent("我是机器人小T,很高兴为您服务");
                    tMessages.setMFromUserid("小T");
                    tMessages.setUserPath(null);
                    tMessages.setMTime(dateUtil.getDate(localDateTime));
                    tMessages.setMToUserid(userPath);
                    // 将对象转成JSON字符串
                    String json = JSONObject.toJSONString(tMessages);
                    System.out.println(json);
                    // 将消息推送给用户
                    UserUtil userUtils = UserUtil.getUserUtils();
                    userUtils.sending(json);

//                    tMessages.setMContent("小T非常抱歉, 暂时没有服务人员在线");
//                    String json1 = JSONObject.toJSONString(tMessages);
//                    userUtils.sending(json1);
//
//                    tMessages.setMContent("如果你有什么事情可以说给小T听, 小T会是您最好的听众");
//                    String json2 = JSONObject.toJSONString(tMessages);
//                    userUtils.sending(json2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            UserUtil userUtil = UserUtil.getUserUtils();
            // 判断接收人是否在线
            if ( userUtil.getValue(jsonObject.getString("mToUserid")) == null ) {

                tMessages.setDeleteFlag(true);
                tMessages.setGmtCreate(localDateTime);
                tMessages.setGmtModified(localDateTime);
                tMessages.setMContent("非常抱歉，对方已经下线了，您发的消息小T会为您转达");
                tMessages.setMFromUserid("小T");
                tMessages.setUserPath(jsonObject.getString("mToUserid"));
                tMessages.setMTime(dateUtil.getDate(localDateTime));
                tMessages.setMToUserid(userPath);
                // 将对象转成JSON字符串
                String json = JSONObject.toJSONString(tMessages);
                System.out.println(json);
                UserUtil userUtils = UserUtil.getUserUtils();
                userUtils.sending(json);
            } else {
                System.out.println("推送消息");
                System.out.println("推送内容：" + mContent);

                tMessages.setDeleteFlag(true);
                tMessages.setGmtCreate(localDateTime);
                tMessages.setGmtModified(localDateTime);
                tMessages.setMContent(mContent);
                tMessages.setMFromUserid(mFromUserId);
                tMessages.setUserPath(userPath);
                tMessages.setMTime(dateUtil.getDate(localDateTime));
                tMessages.setMToUserid(jsonObject.getString("mToUserid"));
                // 将对象转成JSON字符串
                String json = JSONObject.toJSONString(tMessages);
                System.out.println(json);
                UserUtil userUtils = UserUtil.getUserUtils();
                userUtils.sending(json);
            }

        }

    }

    /**
     * 连接关闭时被调用
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("我关闭了通信连接");
        UserUtil userUtils = UserUtil.getUserUtils();

        // 用户退出时，将用户数据库中的联系人地址修改为： null（无通讯地址）,用户状态为：false(未接入)
        tUser.setUserPath(userUtils.getKey(session));
        tUser.setStatus(false);
        System.out.println(tUser);
        MapperUtil.updateStatus(tUser);

        userUtils.delete(session);
    }
}
