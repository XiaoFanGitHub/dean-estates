package com.zooms.dean.auth.service;

import com.zooms.dean.auth.domain.UserDealer;

import java.util.List;

/**
 * @author nondo
 */
public interface UserDealerService {

    /**
     * 保存用户经销商对象
     *
     * @param userId     用户编号
     * @param dealerId   经销商编号
     * @param dealerName 经销商
     */
    void saveUserDealer(Long userId, Long dealerId, String dealerName);

    /**
     * 根据用户编号和权限编号查询经销商对象
     *
     * @param userId   用户编号
     * @param dealerId 经销商编号
     * @return 用户经销商对象
     */
    UserDealer findByUserIdAndDealerId(Long userId, Long dealerId);

    /**
     * 根据用户编号查询用户对应的经销商信息
     *
     * @param userId 用户编号
     * @return 用户权限集合
     */
    List<UserDealer> findByUserId(Long userId);

    /**
     * 根据用户编号和经销商编号删除对应关系
     *
     * @param userId   用户编号
     * @param dealerId 经销商编号
     */
    void deleteByUserIdAndDealerId(Long userId, Long dealerId);

    /**
     * 删除用户经销商关联
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);


}
