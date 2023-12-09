package io.renren.modules.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.my.entity.UsersEntity;

import java.util.Map;

/**
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户
     */
    UsersEntity queryUserForLogin(String username, String password);

    boolean queryUsernameIsExist(String username);

    boolean isUserLikeVideo(Long loginUserId, Long videoId);
}

