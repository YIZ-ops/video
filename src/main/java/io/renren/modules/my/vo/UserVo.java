package io.renren.modules.my.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private Long id;

    /*** 用户认证凭据 ***/
    private String userToken;

    /*** 用户名 ***/
    private String username;

    /*** 密码 ***/
    @JsonIgnore
    private String password;

    /*** 我的头像，如果没有默认给一张 ***/
    private String faceImage;

    /*** 昵称 ***/
    private String nickname;

    private Integer ratio;
    private Integer kind;
}
