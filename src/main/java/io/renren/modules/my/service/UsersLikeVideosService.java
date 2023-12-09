package io.renren.modules.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.my.entity.UsersLikeVideosEntity;

import java.util.Map;

/**
 * 用户喜欢的/赞过的视频
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
public interface UsersLikeVideosService extends IService<UsersLikeVideosEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

