package cn.edu.sjtu.ist.irp.entity;

/**
 * @author dyanjun
 * @date 2021/12/15 11:16
 */
public enum RescueMemberStatus {
    FREE("FREE"),
    WORKING("WORKING");
    /**
     * 状态
     */
    private final String status;

    /**
     * 构造函数
     *
     * @param status 状态
     */
    RescueMemberStatus(String status) {
        this.status = status;
    }

    /**
     * 获取状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 从字符串表示转为枚举
     *
     * @param text 字符串表示
     */
    public static RescueMemberStatus fromString(String text) {
        for (RescueMemberStatus b : RescueMemberStatus.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
