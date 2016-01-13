package com.sunflower.rxandroiddemo.dto;

/**
 * 帖子
 */
public class ArticleListDTO {

    /**
     * ID
     */
    private long id;

    /**
     * 图片
     */
    private String image;

    /**
     * 标题
     */
    private String title;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 发布时间
     */
    private String createDate;

    /**
     * 评论数
     */
    private long hits;

    private AuthorDTO author;

    /**
     * 是否选中
     * add by sunflower
     */
    private boolean selected;

    public AuthorDTO getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getIntro() {
        return intro;
    }

    public String getCreateDate() {
        return createDate;
    }

    public long getHits() {

        return hits;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
