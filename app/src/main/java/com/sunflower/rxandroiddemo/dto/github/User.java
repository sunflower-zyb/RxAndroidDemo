package com.sunflower.rxandroiddemo.dto.github;

/**
 * Created by Sunflower on 2016/1/26.
 */
public class User {


    /**
     * login : sunflower-zyb
     * id : 8893855
     * avatar_url : https://avatars.githubusercontent.com/u/8893855?v=3
     * gravatar_id :
     * url : https://api.github.com/users/sunflower-zyb
     * html_url : https://github.com/sunflower-zyb
     * followers_url : https://api.github.com/users/sunflower-zyb/followers
     * following_url : https://api.github.com/users/sunflower-zyb/following{/other_user}
     * gists_url : https://api.github.com/users/sunflower-zyb/gists{/gist_id}
     * starred_url : https://api.github.com/users/sunflower-zyb/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/sunflower-zyb/subscriptions
     * organizations_url : https://api.github.com/users/sunflower-zyb/orgs
     * repos_url : https://api.github.com/users/sunflower-zyb/repos
     * events_url : https://api.github.com/users/sunflower-zyb/events{/privacy}
     * received_events_url : https://api.github.com/users/sunflower-zyb/received_events
     * type : User
     * site_admin : false
     * name : null
     * company : null
     * blog : null
     * location : null
     * email : null
     * hireable : null
     * bio : null
     * public_repos : 3
     * public_gists : 0
     * followers : 1
     * following : 10
     * created_at : 2014-09-24T05:16:55Z
     * updated_at : 2016-01-13T08:29:15Z
     */

    private String login;
    private int id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private Object name;
    private Object company;
    private Object blog;
    private Object location;
    private Object email;
    private Object hireable;
    private Object bio;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public Object getName() {
        return name;
    }

    public Object getCompany() {
        return company;
    }

    public Object getBlog() {
        return blog;
    }

    public Object getLocation() {
        return location;
    }

    public Object getEmail() {
        return email;
    }

    public Object getHireable() {
        return hireable;
    }

    public Object getBio() {
        return bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
