package io.renren.modules.my.service.impl;

import io.renren.modules.my.entity.UsersLikeVideosEntity;
import io.renren.modules.my.service.UsersLikeVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.my.dao.UsersDao;
import io.renren.modules.my.entity.UsersEntity;
import io.renren.modules.my.service.UsersService;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    UsersLikeVideosService usersLikeVideosService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(
                new Query<UsersEntity>().getPage(params),
                new QueryWrapper<UsersEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public UsersEntity queryUserForLogin(String username, String password) {
        QueryWrapper<UsersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        return this.getOne(wrapper);
    }

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @Override
    public boolean queryUsernameIsExist(String username) {
        QueryWrapper<UsersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UsersEntity user = this.getOne(wrapper);
        return user != null;
    }

    @Override
    public boolean isUserLikeVideo(Long loginUserId, Long videoId) {
        UsersLikeVideosEntity entity = usersLikeVideosService.getOne(new QueryWrapper<UsersLikeVideosEntity>()
                .eq("video_id", videoId)
                .eq("user_id", loginUserId));
        return entity != null;
    }

}
