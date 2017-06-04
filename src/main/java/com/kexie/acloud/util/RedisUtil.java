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
     * 发送新通知
     * @param conn
     * @param recipients
     * @param msgType
     * @param msg
     */
   public static void sendMsg(Jedis conn,List<User> recipients, String msgType, String msg){

       Set<String>reci = FormatUtil.formatUserId(recipients);
       String type="";
       if(msgType.equals("notice")) type="公告通知";
       else if(msgType.equals("meeting")) type="会议通知";
       else if(msgType.equals("task")) type="任务通知";
       Transaction transaction = conn.multi();
       for (String user_id:reci) {
           // 获取消息ID
           // long messageId = conn.incr(("ids:"+msgType+":"+user_id));
           String identifier = UUID.randomUUID().toString();
           HashMap<String,Object> values = new HashMap<String,Object>();
           values.put("id",identifier);
           values.put("time",System.currentTimeMillis());
           values.put("title","你有一条新"+type);
           values.put("message",msg);
           String packed = JSON.toJSONString(values);
           transaction.hset(("msg:"+msgType+":"+user_id),identifier,packed);
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
   public static Set<Message> getMsg(Jedis conn,String userId, String msgType){
       String msgKey = "msg:"+msgType+":"+userId;
       Set<Message> msg = new HashSet<>();
       Map<String, String>result = conn.hgetAll(msgKey);
       for (Map.Entry<String, String> entry:result.entrySet()){
            msg.add(JSON.parseObject(entry.getValue().toString(),Message.class));
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
     * 获取公告浏览者列表
     * @param conn
     * @param noticeId
     * @param userId
     * @return
     */
   public static Set<String> getNoticeVisitor(Jedis conn, int noticeId) {
       String notice_visitor = "notice:visitor:" + noticeId;
       return new HashSet<>(conn.smembers(notice_visitor));
   }

    /**
     * 从MySQL加载公告浏览者数据到redis
     * @param conn
     * @param notice_id
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
