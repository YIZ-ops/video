package io.renren.modules.my.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.my.entity.UsersLikeVideosEntity;
import io.renren.modules.my.service.UsersLikeVideosService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户喜欢的/赞过的视频
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@RestController
@RequestMapping("ware/userslikevideos")
public class UsersLikeVideosController {
    @Autowired
    private UsersLikeVideosService usersLikeVideosService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:userslikevideos:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usersLikeVideosService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:userslikevideos:info")
    public R info(@PathVariable("id") String id){
		UsersLikeVideosEntity usersLikeVideos = usersLikeVideosService.getById(id);

        return R.ok().put("usersLikeVideos", usersLikeVideos);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:userslikevideos:save")
    public R save(@RequestBody UsersLikeVideosEntity usersLikeVideos){
		usersLikeVideosService.save(usersLikeVideos);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:userslikevideos:update")
    public R update(@RequestBody UsersLikeVideosEntity usersLikeVideos){
		usersLikeVideosService.updateById(usersLikeVideos);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:userslikevideos:delete")
    public R delete(@RequestBody String[] ids){
		usersLikeVideosService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
