package io.renren.modules.my.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.my.entity.CommentsEntity;
import io.renren.modules.my.service.CommentsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 课程评论表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@RestController
@RequestMapping("ware/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:comments:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:comments:info")
    public R info(@PathVariable("id") String id){
		CommentsEntity comments = commentsService.getById(id);

        return R.ok().put("comments", comments);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:comments:save")
    public R save(@RequestBody CommentsEntity comments){
		commentsService.save(comments);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:comments:update")
    public R update(@RequestBody CommentsEntity comments){
		commentsService.updateById(comments);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleNamez}:comments:delete")
    public R delete(@RequestBody String[] ids){
		commentsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
