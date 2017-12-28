package com.x9710.common.redis;

import com.x9710.common.redis.domain.GeoCoordinate;
import com.x9710.common.redis.domain.Postion;

import java.util.List;

public interface LBSService {

    /**
     * 存储一个位置
     *
     * @param postion 增加的位置对象
     * @throws Exception
     */
    boolean addPostion(Postion postion);

    /**
     * 查询以指定的坐标为中心，指定的距离为半径的范围类的所有位置点
     *
     * @param center   中心点位置
     * @param distinct 最远距离，单位米
     * @param asc      是否倒序排序
     * @return 有效的位置
     */
    List<Postion> radious(String type, GeoCoordinate center, Long distinct, Boolean asc);
}
