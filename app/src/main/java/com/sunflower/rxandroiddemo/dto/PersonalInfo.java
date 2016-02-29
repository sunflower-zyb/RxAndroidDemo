package com.sunflower.rxandroiddemo.dto;


/**
 * Created by Sunflower on 2015/9/25.
 */
public class PersonalInfo {

    /**
     * ID
     */
    public String id;

    /**
     * 昵称
     */
    public String nickname;

    /**
     * 地址
     */
    public String address;

    /**
     * 头像
     */
    public String avatar;


    /**
     * 身份证号
     */
    public String IDNO;


    /**
     * 生日
     */
    public String birthday;

    /**
     * 预产期
     */
    public String expected;

    /**
     * 身高
     */
    public double stature;

    /**
     * 体重
     */
    public double weight;

    /**
     * 亲情帐号
     */
    public String kinship;

    public int age;

    public long areaId;


    public PersonalInfo(String id, String nickname, String address, String avatar, String IDNO,
                        String birthday, String expected, double stature, double weight, String kinship,
                        int age, long areaId) {
        this.id = id;
        this.nickname = nickname;
        this.address = address;
        this.avatar = avatar;
        this.IDNO = IDNO;
        this.birthday = birthday;
        this.expected = expected;
        this.stature = stature;
        this.weight = weight;
        this.kinship = kinship;
        this.age = age;
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", IDNO='" + IDNO + '\'' +
                ", birthday='" + birthday + '\'' +
                ", expected='" + expected + '\'' +
                ", stature=" + stature +
                ", weight=" + weight +
                ", kinship='" + kinship + '\'' +
                ", age=" + age +
                ", areaId=" + areaId +
                '}';
    }

    public static class Builder {

        /**
         * ID
         */
        private String id;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 地址
         */
        private String address;

        /**
         * 头像
         */
        private String avatar;


        /**
         * 身份证号
         */
        private String IDNO;


        /**
         * 生日
         */
        private String birthday;

        /**
         * 预产期
         */
        private String expected;

        /**
         * 身高
         */
        private double stature;

        /**
         * 体重
         */
        private double weight;

        /**
         * 亲情帐号
         */
        private String kinship;

        private int age;
        public long areaId;

        public Builder(String id) {
            this.id = id;
        }

        public Builder() {
//            this.id = Preference.getInstance().getUserId();
        }


//        public Builder(String id, String nickname, String address, String avatar, String IDNO,
//                       String birthday, String expected, Double stature, Double weight,
//                       String kinship) {
//            this.id = id;
//            this.nickname = nickname;
//            this.address = address;
//            this.avatar = avatar;
//            this.IDNO = IDNO;
//            this.birthday = birthday;
//            this.expected = expected;
//            this.stature = stature;
//            this.weight = weight;
//            this.kinship = kinship;
//        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setIDNO(String IDNO) {
            this.IDNO = IDNO;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setExpected(String expected) {
            this.expected = expected;
            return this;
        }

        public Builder setStature(double stature) {
            this.stature = stature;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setKinship(String kinship) {
            this.kinship = kinship;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public void setAreaId(long areaId) {
            this.areaId = areaId;
        }

        public PersonalInfo build() {
//            id = builder.id;
//            nickname = builder.nickname;
//            address = builder.address;
//            avatar = builder.avatar;
//            birthday = builder.birthday;
//            IDNO = builder.IDNO;
//            expected = builder.expected;
//            stature = builder.stature;
//            weight = builder.weight;
//            kinship = builder.kinship;

            return new PersonalInfo(id, nickname, address, avatar, IDNO, birthday, expected,
                    stature, weight, kinship, age, areaId);
        }

    }
}
