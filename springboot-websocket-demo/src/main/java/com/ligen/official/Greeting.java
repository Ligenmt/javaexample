package com.ligen.official;

/**
 * Created by ligen on 2018/5/12.
 */
public class Greeting {

    private String content;

    private String userId;

    public Greeting() {
    }

    public Greeting(String content, String userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
