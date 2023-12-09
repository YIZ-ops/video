package io.renren.modules.my.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程评论表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@Data
@TableName("comments")
public class CommentsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String fatherCommentId;
	/**
	 *
	 */
	private String toUserId;
	/**
	 * 视频id
	 */
	private String videoId;
	/**
	 * 留言者，评论的用户id
	 */
	private String fromUserId;
	/**
	 * 评论内容
	 */
	private String comment;
	/**
	 *
	 */
	private Date createTime;

}
