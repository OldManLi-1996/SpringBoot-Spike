package com.springboot.spike.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * User: 最帅气的老李头
 * Date: 2022/4/19
 * Time: 23:04
 * Description: 商品公共类，公共方法提取
 */
public class GoodsToolUtil {

    /**
     * 生成商品账单编号信息（当前时间+uid+随机的三位随机数）
     * @param userId
     * @return
     */
    public static String getOrderIdByTime(int userId) {
        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = sdf.format(new Date()) + userId;

        //随机数
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);       //10以内的随机数
        }

        return result;
    }

    /**
     * 生成商品账单编号信息（用UUID生成十六位数唯一订单号）（优先考虑这种方法）
     * @param machineId 最大支持1-9个集群机器部署
     * @return
     */
    public static String getOrderIdByUUId(int machineId) {

        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {     //有可能是负数的情况
            hashCodeV = -hashCodeV;
        }

        //0 代表前面补充0
        //4 代表长度为4
        //d 代表参数为正数型

        return machineId + String.format("%015d", hashCodeV);
    }

}
