package com.cos.blog.dto.request;

public class ResponseLogin {
    private String token;
    private String message;

    public ResponseLogin(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getter와 Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
