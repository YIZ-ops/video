package io.renren.modules.my.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.my.utils.MD5Utils;
import io.renren.modules.my.vo.UserVo;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.my.entity.UsersEntity;
import io.renren.modules.my.service.UsersService;
import org.springframework.web.multipart.MultipartFile;
import io.renren.modules.my.utils.JsonResult;

/**
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@RestController
@RequestMapping("ware/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    private static final String FACE_PATH = "D:/Project/AwesomeVideoUpload/face/";

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:users:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = usersService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:users:info")
    public R info(@PathVariable("id") String id) {
        UsersEntity users = usersService.getById(id);
        return R.ok().put("users", users);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:users:save")
    public R save(@RequestBody UsersEntity users) throws Exception {
        users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
        usersService.save(users);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:users:update")
    public R update(@RequestBody UsersEntity users) {
        usersService.updateById(users);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:users:delete")
    public R delete(@RequestBody String[] ids) {
        usersService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @PostMapping("/upload-face")
    public R uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String finalCoverPath = FACE_PATH + fileName;
            File dest = new File(finalCoverPath);
            file.transferTo(dest);
            return R.ok().put("finalFacePath", "face/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    @PostMapping(value = "/changeFace", headers = "content-type=multipart/form-data")
    public JsonResult uploadFace(Long userId, MultipartFile file) {
        if (StringUtils.isBlank(String.valueOf(userId))) {
            return JsonResult.errorMsg("用户id不能为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            String finalCoverPath = FACE_PATH + fileName;
            File dest = new File(finalCoverPath);
            file.transferTo(dest);
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setId(userId);
            usersEntity.setFaceImage("face/" + fileName);
            usersService.updateById(usersEntity);
            return JsonResult.ok("face/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.errorMsg("上传文件失败！");
        }
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "/query")
    public JsonResult query(String userId) {
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }
        // 查询用户信息
        UsersEntity userInfo = usersService.getById(userId);
        UserVo userVo = new UserVo();
        // 将用户信息复制到Vo对象
        BeanUtils.copyProperties(userInfo, userVo);
        return JsonResult.ok(userVo);
    }

    @PostMapping(value = "/queryIsLike")
    public JsonResult queryIsLike(Long loginUserId, Long videoId) {
        // 查询当前登陆者和视频的点赞关系
        boolean userLikeVideo = usersService.isUserLikeVideo(loginUserId, videoId);
        return JsonResult.ok(userLikeVideo);
    }

}
