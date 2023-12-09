package io.renren.modules.my.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.my.entity.CommentsEntity;
import io.renren.modules.my.entity.VideosEntity;
import io.renren.modules.my.utils.PagedResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 视频信息表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
public interface VideosService extends IService<VideosEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询视频列表
     *
     * @param currentPage  当前页数
     * @return 封装分页后的数据格式
     */
    PagedResult getAllVideos(Integer currentPage);

    PagedResult queryMyLikeVideos(Long userId, Integer page, int pageSize);

    void userLikeVideo(Long userId, Long videoId);

    void userUnlikeVideo(Long userId, Long videoId);
}

