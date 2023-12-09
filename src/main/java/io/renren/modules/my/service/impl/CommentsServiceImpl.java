package io.renren.modules.my.service.impl;

import io.renren.modules.my.service.CommentsService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.my.dao.CommentsDao;
import io.renren.modules.my.entity.CommentsEntity;


@Service("commentsService")
public class CommentsServiceImpl extends ServiceImpl<CommentsDao, CommentsEntity> implements CommentsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentsEntity> page = this.page(
                new Query<CommentsEntity>().getPage(params),
                new QueryWrapper<CommentsEntity>()
        );

        return new PageUtils(page);
    }

}
