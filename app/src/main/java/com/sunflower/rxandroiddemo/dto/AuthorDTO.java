package com.sunflower.rxandroiddemo.dto;

/**
 * DTO - 作者
 *
 * @author Mounate Yan。
 * @version 1.0
 */
public class AuthorDTO {

    /**
     * 类型
     */
    public enum AuthorType {
        /**
         * 孕妇
         */
        GRAVIDA(0),
        /**
         * 医生
         */
        DOCTOR(1),

        /**
         * 系统管理员
         */
        SYSTEM(2);


        private int key;

        private AuthorType(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public static AuthorType valueOf(int ordinal) {
            if (ordinal < 0 || ordinal >= values().length) {
                throw new IndexOutOfBoundsException("Invalid ordinal");
            }
            return values()[ordinal];
        }
    }

    /**
     * ID
     */
    private long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 类型
     */
    private AuthorType type;

    public Long getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return String.valueOf(nickname);
    }

    public AuthorType getType() {
        return type;
    }
}
