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

    public String login;
    public int id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public Object name;
    public Object company;
    public Object blog;
    public String location;
    public Object email;
    public Object hireable;
    public Object bio;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public String created_at;
    public String updated_at;

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

    public String getLocation() {
        return String.valueOf(location);
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


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", gravatar_id='" + gravatar_id + '\'' +
                ", url='" + url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", followers_url='" + followers_url + '\'' +
                ", following_url='" + following_url + '\'' +
                ", gists_url='" + gists_url + '\'' +
                ", starred_url='" + starred_url + '\'' +
                ", subscriptions_url='" + subscriptions_url + '\'' +
                ", organizations_url='" + organizations_url + '\'' +
                ", repos_url='" + repos_url + '\'' +
                ", events_url='" + events_url + '\'' +
                ", received_events_url='" + received_events_url + '\'' +
                ", type='" + type + '\'' +
                ", site_admin=" + site_admin +
                ", name=" + name +
                ", company=" + company +
                ", blog=" + blog +
                ", location=" + location +
                ", email=" + email +
                ", hireable=" + hireable +
                ", bio=" + bio +
                ", public_repos=" + public_repos +
                ", public_gists=" + public_gists +
                ", followers=" + followers +
                ", following=" + following +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
