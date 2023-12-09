package io.renren.modules.my.enums;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description 视频状态枚举
 */
public enum VideoStatusEnum {
    SUCCESS(1), // 发布成功
    FORBID(2); // 禁止播放，观里操作

    public final int value;

    VideoStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
