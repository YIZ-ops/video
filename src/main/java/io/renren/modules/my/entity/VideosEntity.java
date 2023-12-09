package io.renren.modules.my.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 视频信息表
 *
 * @author firenay
 * @email 1046762075@qq.com
 * @date 2023-12-08 20:53:20
 */
@Data
@TableName("videos")
public class VideosEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private long id;
    /**
     * 视频描述
     */
    private String videoDesc;
    /**
     * 视频存放的路径
     */
    private String videoPath;
    /**
     * 视频封面图
     */
    private String coverPath;
    /**
     * 喜欢/赞美的数量
     */
    private Long likeCounts;
    /**
     * 视频状态：
     * 1、发布成功
     * 2、禁止播放，管理员操作
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 0-实用 1-享乐
     */
    private Integer kind;

}
