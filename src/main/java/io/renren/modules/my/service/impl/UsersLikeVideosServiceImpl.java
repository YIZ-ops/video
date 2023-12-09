package io.renren.modules.my.service.impl;

import io.renren.modules.my.service.UsersLikeVideosService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.my.dao.UsersLikeVideosDao;
import io.renren.modules.my.entity.UsersLikeVideosEntity;


@Service("usersLikeVideosService")
public class UsersLikeVideosServiceImpl extends ServiceImpl<UsersLikeVideosDao, UsersLikeVideosEntity> implements UsersLikeVideosService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersLikeVideosEntity> page = this.page(
                new Query<UsersLikeVideosEntity>().getPage(params),
                new QueryWrapper<UsersLikeVideosEntity>()
        );

        return new PageUtils(page);
    }

}
