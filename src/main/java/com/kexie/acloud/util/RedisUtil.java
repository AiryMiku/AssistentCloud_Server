package com.kexie.acloud.util;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by zojian on 2017/6/1.
 */
public class RedisUtil {

    /**
     * 生成消息
     * @param id
     * @param info
     * @param recipients
     * @return
     */

    public static Map<String,Object> generateMessage(int id,String publisher,String logo, String title, String info, List<User>recipients){

        String identifier = UUID.randomUUID().toString();
        HashMap<String,Object> values = new HashMap<String,Object>();

        values.put("id",id);
        values.put("publisher",publisher);
        values.put("logo",logo);
        values.put("title",title);
        values.put("info",info);
        values.put("time",System.currentTimeMillis());
        values.put("identifier",identifier);
        return values;
    }

    public static Map<String,Object> generateMessage(String id,String publisher,String logo, String title, String info, List<User>recipients){

        String identifier = UUID.randomUUID().toString();
        HashMap<String,Object> values = new HashMap<String,Object>();

        values.put("id",id);
        values.put("publisher",publisher);
        values.put("logo",logo);
        values.put("title",title);
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
   public static void sendPushMsg(Jedis conn,String msgType, int id, String publisher, String logo, String title, String info,List<User> recipients){

       Set<String>reci = FormatUtil.formatUserId(recipients);
       Map<String,Object> message = generateMessage(id, publisher, logo, title, info, recipients);
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

    public static void sendPushMsg(Jedis conn,String msgType, String id,String publisher, String logo, String title, String info,List<User> recipients){

        Set<String>reci = FormatUtil.formatUserId(recipients);
        Map<String,Object> message = generateMessage(id, publisher, logo, title, info, recipients);
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

    /**
     * 更新日榜
     * @param conn
     * @param societyId
     * @param userId
     * @param score
     */
   public static void updateScoreboard(Jedis conn,int societyId, String userId, int score){
       String scoreboardkey="scoreboard:"+societyId+":"+DateUtil.formatCurrentDate();
       conn.zincrby(scoreboardkey,score,userId);
   }

    /**
     * 获取本周排行榜
     * @param conn
     * @param societyId
     * @return
     */
   public static Set<Tuple> getWeekScoreboard(Jedis conn, int societyId){
        List<String> dates = DateUtil.getDateOfThisWeek(Calendar.getInstance());
        List<String> scoreBoardKey = new ArrayList<>();
        String lastWeekScoreboardKey = "scoreboard:"+societyId+":lastweek";

        for (String date:dates){
            scoreBoardKey.add("scoreboard:"+societyId+":"+date);
        }
        conn.zunionstore(lastWeekScoreboardKey, scoreBoardKey.toArray(new String[scoreBoardKey.size()]));
        if(conn.ttl(lastWeekScoreboardKey)==-1){
            // 设置过期时间 7天
            conn.expire(lastWeekScoreboardKey,604800);
        }
        return conn.zrevrangeWithScores(lastWeekScoreboardKey,0,9);

   }

    /**
     * 获取本月排行榜
     * @param conn
     * @param societyId
     * @return
     */
   public static Set<Tuple> getMonthScoreboard(Jedis conn, int societyId){
       List<String> dates = DateUtil.getDateOfThisMonth(Calendar.getInstance());
       List<String> scoreBoardKey = new ArrayList<>();
       String lastMonthScoreboardKey = "scoreboard:"+societyId+":lastmonth";

       for (String date:dates){
           scoreBoardKey.add("scoreboard:"+societyId+":"+date);
       }
       conn.zunionstore(lastMonthScoreboardKey, scoreBoardKey.toArray(new String[scoreBoardKey.size()]));
       if(conn.ttl(lastMonthScoreboardKey)==-1){
           // 设置过期时间 31天
           conn.expire(lastMonthScoreboardKey,2678400);
       }

       return conn.zrevrangeWithScores(lastMonthScoreboardKey,0,9);
   }

    /**
     * 更新用户最近一次登录时间
     * @param conn
     * @param userId
     * @param currentDate
     */
   public static void updateLoginDate(Jedis conn, String userId, String currentDate){
       String loginDateKey = "logindate";
       conn.hset(loginDateKey,userId,currentDate);
   }

    /**
     * 获取用户上次登录的时间
     * @param conn
     * @param userId
     * @return
     */
   public static String getLastLoginDate(Jedis conn, String userId){
       String loginDateKey = "logindate";
       return conn.hget(loginDateKey,userId);
   }
}
