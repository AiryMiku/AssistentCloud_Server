package com.kexie.acloud.util;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * Created by zojian on 2017/6/1.
 */
public class RedisUtil {

    /**
     * 生成消息
     * @param msgType
     * @param id
     * @param info
     * @param recipients
     * @return
     */

    public static Map<String,Object> generateMessage(String msgType, int id, String info, List<User>recipients){
        String type="";

        if(msgType.equals("notice")) type="公告通知";
        else if(msgType.equals("meeting")) type="会议通知";
        else if(msgType.equals("task")) type="任务通知";

        String identifier = UUID.randomUUID().toString();
        HashMap<String,Object> values = new HashMap<String,Object>();

        values.put("id",id);
        values.put("title","你有一条新的"+type);
        values.put("info",info);
        values.put("time",System.currentTimeMillis());
        values.put("identifier",identifier);
        return values;
    }

    /**
     * 发送新通知
     * @param conn redis连接
     * @param msgType 消息类型
     * @param id 消息在MySQL表中的id
     * @param info 消息粗略内容
     * @param recipients
     */
   public static void sendPushMsg(Jedis conn,String msgType, int id, String info,List<User> recipients){

       Set<String>reci = FormatUtil.formatUserId(recipients);
       Map<String,Object> message = generateMessage(msgType, id, info, recipients);
       Transaction transaction = conn.multi();
       for (String user_id:reci) {

           String packed = JSON.toJSONString(message);
           transaction.hset(("msg:"+msgType+":"+user_id),
                   message.get("identifier").toString(),// key
                   packed                               // value
           );
       }
       transaction.exec();
   }

    /**
     * 返回某种类型的未读消息数量
     * @param conn
     * @param userId
     * @param msgType 消息类型
     * @return
     */
   public static Long getMsgCount(Jedis conn, String userId, String msgType){
       String msgKey = "msg:"+msgType+":"+userId;
       return conn.hlen(msgKey);
   }

    /**
     * 获取某个类型的所有未读消息
     * @param conn
     * @param userId
     * @param msgType
     * @return
     */
   public static Set<PushMessage> getMsg(Jedis conn,String userId, String msgType){
       String msgKey = "msg:"+msgType+":"+userId;
       Set<PushMessage> msg = new HashSet<>();
       Map<String, String>result = conn.hgetAll(msgKey);
       for (Map.Entry<String, String> entry:result.entrySet()){
            msg.add(JSON.parseObject(entry.getValue().toString(),PushMessage.class));
       }
       return msg;
   }

    /**
     * 删除已读通知
     * @param conn
     * @param userId
     * @param identifier
     * @param msgType
     */
   public static void deleteMsg(Jedis conn,String userId,String identifier,String msgType){
       if(identifier!=null) {
           String msgKey = "msg:" + msgType + ":" + userId;
           conn.hdel(msgKey, identifier);
       }
   }

    /**
     * 添加离线通知
     * @param conn
     * @param userId
     * @param message
     */
   public static void addOfflineMsg(Jedis conn,String userId,Map<String,Object>message){
       String identifier = message.get("identifier").toString();
       String offlineMsgKey = "offline:"+userId;
       conn.hset(offlineMsgKey,identifier, JSON.toJSONString(message));
   }

   public static boolean hasOfflineMsg(Jedis conn,String userId){
       String offlineMsgKey = "offline:"+userId;
       return conn.hlen(offlineMsgKey)==0?false:true;
   }

    /**
     * 获取所有离线通知
     * @param conn
     * @param userId
     * @return
     */
   public static Set<PushMessage> getAllOfflineMsg(Jedis conn,String userId){
       String offlineMsgKey = "offline:"+userId;
       Set<PushMessage> msg = new HashSet<>();
       Map<String, String>result = conn.hgetAll(offlineMsgKey);
       for (Map.Entry<String, String> entry:result.entrySet()){
           msg.add(JSON.parseObject(entry.getValue().toString(),PushMessage.class));
       }
       conn.del(offlineMsgKey);
       return msg;
   }

    /**
     * 获取公告浏览者列表
     * @param conn
     * @param noticeId
     * @return
     */
   public static Set<String> getNoticeVisitor(Jedis conn, int noticeId) {
       String notice_visitor = "notice:visitor:" + noticeId;
       return new HashSet<>(conn.smembers(notice_visitor));
   }

    /**
     * 从MySQL加载公告浏览者数据到redis
     * @param conn
     * @param users
     */
   public static void loadNoticeVisitorByMySQL(Jedis conn,int noticeId, List<User>users){
       String notice_visitor = "notice:visitor:" + noticeId;
       Set<String> executors = FormatUtil.formatUserId(users);
       for(String user:executors) {
           conn.sadd(notice_visitor, user);
       }
   }
}
