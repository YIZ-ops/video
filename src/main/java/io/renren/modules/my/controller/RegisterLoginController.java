package io.renren.modules.my.controller;

import io.renren.modules.my.entity.UsersEntity;
import io.renren.modules.my.service.UsersService;
import io.renren.modules.my.utils.JsonResult;
import io.renren.modules.my.utils.MD5Utils;
import io.renren.modules.my.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 注册登陆控制器
 */
@RestController
@RequestMapping("ware")
public class RegisterLoginController extends BaseController {

    @Autowired
    private UsersService userService;

    @PostMapping("/register")
    public JsonResult register(@RequestBody UsersEntity user) throws Exception {
        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }
        // 2.判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(user.getUsername());
        // 3.保存用户，注册信息
        if (!isExist) {
            user.setUsername(user.getUsername());
            user.setNickname(user.getUsername());
            // 密码进行MD5加密
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setRatio(5);
            user.setKind(3);
            // 保存用户到数据库
            userService.save(user);
        } else {
            return JsonResult.errorMsg("用户名已存在，请更换一个再尝试");
        }
        // 清空密码
        user.setPassword("");
        // 设置用户Token到Redis中
        UserVo userVo = setUserRedisSessionToken(user);
        // 返回Vo对象
        return JsonResult.ok(userVo);
    }

    @PostMapping("/login")
    public JsonResult login(@RequestBody UsersEntity user) throws Exception {
        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }
        // 2.判断用户名和密码是否存在（密码进行MD5加密）
        UsersEntity resultUser = userService.queryUserForLogin(user.getUsername(),
                MD5Utils.getMD5Str(user.getPassword()));
        // 3.登陆失败
        if (resultUser == null) {
            return JsonResult.errorMsg("用户名或密码错误");
        }
        // 清空密码，返回用户信息
        resultUser.setPassword("");
        // 设置用户Token到Redis中
        UserVo userVo = setUserRedisSessionToken(resultUser);
        // 返回用户视图对象
        return JsonResult.ok(userVo);
    }

    @PostMapping("/logout")
    public JsonResult logout(String userId) {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return JsonResult.ok();
    }

    /**
     * 设置用户Token到Redis中
     *
     * @param user 用户
     * @return 用户视图对象
     */
    private UserVo setUserRedisSessionToken(UsersEntity user) {
        // 生成Token
        String uniqueToken = UUID.randomUUID().toString();
        // 将用户信息存入Redis（有效期30分钟）
        redis.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 * 30);
        // 用户视图对象
        UserVo userVo = new UserVo();
        // 将用户中的属性拷贝到Vo对象
        BeanUtils.copyProperties(user, userVo);
        // 设置Token到Vo对象
        userVo.setUserToken(uniqueToken);
        return userVo;
    }

}
