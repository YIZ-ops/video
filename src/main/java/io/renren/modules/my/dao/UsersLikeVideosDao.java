package io.renren.modules.my.dao;

import io.renren.modules.my.entity.UsersLikeVideosEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户喜欢的/赞过的视频
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@Mapper
public interface UsersLikeVideosDao extends BaseMapper<UsersLikeVideosEntity> {

}
