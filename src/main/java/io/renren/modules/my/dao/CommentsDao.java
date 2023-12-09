package io.renren.modules.my.dao;

import io.renren.modules.my.entity.CommentsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程评论表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@Mapper
public interface CommentsDao extends BaseMapper<CommentsEntity> {

}
