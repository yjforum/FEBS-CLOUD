package cc.michael.febs.auth;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: michael
 * @create: 2020/12/1 10:56
 */
public class Encry {

    /**
     * 订单号
     */
    String orderNumber;

    /**
     *
     * @param prefix 订单号前缀
     * @return
     */
    public String encry(String prefix){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String date=simpleDateFormat.format(new Date());
        SnowflakeIdUtils idWorker = new SnowflakeIdUtils(3, 1);
        String encrt=String.valueOf(idWorker.nextId()).substring(String.valueOf(idWorker.nextId()).length()-4,String.valueOf(idWorker.nextId()).length());
        orderNumber=prefix+date+encrt;
        return orderNumber;
    }

}
