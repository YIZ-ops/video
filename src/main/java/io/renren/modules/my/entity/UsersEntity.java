package io.renren.modules.my.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@Data
@TableName("users")
public class UsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type = IdType.AUTO)
	private long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 头像，如果没有默认给一张
	 */
	private String faceImage;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 视频推荐强度
	 */
	private Integer ratio;
	/**
	 * 0-爱看实用 1-爱看享乐
	 */
	private Integer kind;

}
