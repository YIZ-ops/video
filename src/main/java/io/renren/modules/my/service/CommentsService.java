package io.renren.modules.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.my.entity.CommentsEntity;

import java.util.Map;

/**
 * 课程评论表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
public interface CommentsService extends IService<CommentsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

