package com.sunflower.rxandroiddemo.dto;

/**
 * Created by Sunflower on 2015/11/26.
 */
public class RemindDTO implements Comparable<RemindDTO> {
    /**
     * ID
     */
    private long id;

    /**
     * 时间
     */
    private String remindTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private RemindStatus status;

    /**
     * 类型
     */
    private RemindType type;

    /**
     * 进入医院流程提醒详情页面时，需要该字段
     */
    private long templateId;

    public long getId() {
        return id;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public String getContent() {
        return content;
    }

    public RemindStatus getStatus() {
        return status;
    }

    public RemindType getType() {
        return type;
    }

    public long getTemplateId() {
        return templateId;
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(RemindDTO another) {
        return remindTime.compareTo(another.remindTime);
    }


    /**
     * 提醒状态
     */
    public enum RemindStatus {
        CREATE {
            public String label() {
                return "新建";
            }
        },
        DONE {
            public String label() {
                return "过时";
            }
        };

        public String label() {
            return this.label();
        }
    }

    /**
     * 提醒类型
     */
    public enum RemindType {
        SYSTEM {
            public String label() {
                return "医院流程";
            }
        },
        USERDEFINED {
            public String label() {
                return "自定义";
            }
        };

        public String label() {
            return this.label();
        }
    }


    @Override
    public String toString() {
        return "RemindDTO{" +
                "id=" + id +
                ", remindTime='" + remindTime + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", templateId=" + templateId +
                '}';
    }
}
