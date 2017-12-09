package com.x9710.common.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化工具类
 *
 * @author 杨高超
 * @since 2017-10-09
 */
public class SerializeUtil {

    /**
     * 将一个对象序列化为二进制数组
     *
     * @param object 要序列化的对象，该必须实现java.io.Serializable接口
     * @return 被序列化后的二进制数组
     */
    public static byte[] serialize(Object object) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个二进制数组反序列化为一个对象。程序不检查反序列化过程中的对象类型。
     *
     * @param bytes 要反序列化的二进制数
     * @return 反序列化后的对象
     */
    public static Object unserialize(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
