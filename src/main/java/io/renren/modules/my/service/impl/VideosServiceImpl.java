package io.renren.modules.my.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.my.entity.UsersEntity;
import io.renren.modules.my.entity.UsersLikeVideosEntity;
import io.renren.modules.my.service.UsersLikeVideosService;
import io.renren.modules.my.service.UsersService;
import io.renren.modules.my.utils.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.my.dao.VideosDao;
import io.renren.modules.my.entity.VideosEntity;
import io.renren.modules.my.service.VideosService;


@Service("videosService")
public class VideosServiceImpl extends ServiceImpl<VideosDao, VideosEntity> implements VideosService {

    @Autowired
    UsersLikeVideosService usersLikeVideosService;
    @Autowired
    UsersService usersService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VideosEntity> page = this.page(
                new Query<VideosEntity>().getPage(params),
                new QueryWrapper<VideosEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PagedResult getVideos(Integer currentPage, Integer loginUserId) {
        // 构造分页对象
        Page<VideosEntity> page = new Page<>(currentPage, 5);
        IPage<VideosEntity> videosPage;

        if (loginUserId != null) {
            UsersEntity byId = usersService.getById(loginUserId);
            long ratio = byId.getRatio();
            long kind = byId.getKind();

            if (ratio == 3) {
                // 查询所有视频
                videosPage = this.page(page, new QueryWrapper<>());
            } else {
                // 查询推荐视频列表，包括类型为 kind 的视频，以及其他类型的视频
                List<VideosEntity> recommendedVideos = this.list(new QueryWrapper<VideosEntity>()
                        .eq("kind", kind)
                        .last("limit " + ratio));

                recommendedVideos.addAll(this.list(new QueryWrapper<VideosEntity>()
                        .ne("kind", kind)
                        .last("limit " + (10 - ratio))));

                videosPage = new Page<>();
                videosPage.setRecords(recommendedVideos);
                videosPage.setTotal(recommendedVideos.size());
                videosPage.setSize(5);
                videosPage.setCurrent(currentPage);
            }
        } else {
            // 查询所有视频
            videosPage = this.page(page, new QueryWrapper<>());
        }

        // 自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(currentPage); // 设置当前页数
        pagedResult.setTotal((int) videosPage.getPages()); // 设置总页数
        pagedResult.setRows(videosPage.getRecords()); // 设置每行显示的内容
        pagedResult.setRecords(videosPage.getTotal()); // 设置总记录数
        return pagedResult;
    }

    @Override
    public PagedResult queryMyLikeVideos(Long userId, Integer currentPage, int pageSize) {
        // 查询用户喜欢的视频id列表
        List<UsersLikeVideosEntity> userLikeVideos = usersLikeVideosService.list(new QueryWrapper<UsersLikeVideosEntity>().eq("user_id", userId));
        List<Long> videoIdList = userLikeVideos.stream().map(UsersLikeVideosEntity::getVideoId).collect(Collectors.toList());
        // 如果用户没有喜欢的视频，直接返回空结果
        if (videoIdList.isEmpty()) {
            return new PagedResult();
        }
        // 构造分页对象
        Page<VideosEntity> page = new Page<>(currentPage, pageSize);
        // 根据视频id列表查询视频
        IPage<VideosEntity> videosPage = this.page(page, new QueryWrapper<VideosEntity>().in("id", videoIdList));
        // 自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(currentPage); // 设置当前页数
        pagedResult.setTotal((int) videosPage.getPages()); // 设置总页数
        pagedResult.setRows(videosPage.getRecords()); // 设置每行显示的内容
        pagedResult.setRecords(videosPage.getTotal()); // 设置总记录数
        return pagedResult;
    }

    @Override
    public void userLikeVideo(Long userId, Long videoId) {
        // 在users_like_videos表中插入一条记录
        UsersLikeVideosEntity entity = new UsersLikeVideosEntity();
        entity.setVideoId(videoId);
        entity.setUserId(userId);
        usersLikeVideosService.save(entity);
        // 更新videos表中对应视频的like_count字段加1
        VideosEntity video = this.getById(videoId);
        if (video != null) {
            video.setLikeCounts(video.getLikeCounts() + 1);
            this.updateById(video);
        }
    }

    @Override
    public void userUnlikeVideo(Long userId, Long videoId) {
        // 在users_like_videos表中插入一条记录
        usersLikeVideosService.remove(new QueryWrapper<UsersLikeVideosEntity>()
                .eq("video_id", videoId)
                .eq("user_id", userId));
        // 更新videos表中对应视频的like_count字段-1
        VideosEntity video = this.getById(videoId);
        if (video != null) {
            video.setLikeCounts(video.getLikeCounts() - 1);
            this.updateById(video);
        }
    }

}
