package io.renren.modules.my.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.modules.my.utils.JsonResult;
import io.renren.modules.my.utils.PagedResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.my.entity.VideosEntity;
import io.renren.modules.my.service.VideosService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import static io.renren.modules.my.utils.FileUtils.uploadFile;


/**
 * 视频信息表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@RestController
@RequestMapping("ware/videos")
public class VideosController {
    @Autowired
    private VideosService videosService;
    private static final String VIDEO_PATH = "D:/Project/AwesomeVideoUpload/video/";
    private static final String COVER_PATH = "D:/Project/AwesomeVideoUpload/cover/";

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = videosService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id) {
        VideosEntity videos = videosService.getById(id);
        return R.ok().put("videos", videos);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody VideosEntity videos) {
        videos.setCreateTime(new Date());
        videosService.save(videos);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody VideosEntity videos) {
        videosService.updateById(videos);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        videosService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 上传视频
     *
     * @param file
     * @return
     */
    @PostMapping("/upload-video")
    public R uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = uploadFile(file, VIDEO_PATH);
            return R.ok().put("finalVideoPath", "video/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 上传封面
     *
     * @param file
     * @return
     */
    @PostMapping("/upload-cover")
    public R uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = uploadFile(file, COVER_PATH);
            return R.ok().put("finalCoverPath", "cover/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 分页和搜索查询视频列表
     *
     * @param currentPage
     * @return
     */
    @PostMapping("/showAll")
    public JsonResult showAll(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 1;
        }
        PagedResult result = videosService.getAllVideos(currentPage);
        return JsonResult.ok(result);
    }

    @PostMapping("/showMyLike")
    public JsonResult showMyLike(Long userId, Integer page) {
        if (StringUtils.isBlank(String.valueOf(userId))) {
            return JsonResult.errorMsg("用户id不能为空");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        // 获取我点赞过的视频列表分页结果
        PagedResult result = videosService.queryMyLikeVideos(userId, page, pageSize);
        return JsonResult.ok(result);
    }

    @PostMapping("/userLike")
    public JsonResult userLike(Long userId, Long videoId) {
        // 给视频点赞
        videosService.userLikeVideo(userId, videoId);
        return JsonResult.ok();
    }

    @PostMapping("/userUnLike")
    public JsonResult userUnLike(Long userId, Long videoId) {
        // 给视频取消点赞
        videosService.userUnlikeVideo(userId, videoId);
        return JsonResult.ok();
    }
}
