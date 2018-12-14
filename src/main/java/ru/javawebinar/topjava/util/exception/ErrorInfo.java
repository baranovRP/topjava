package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private String url;
    private ErrorType type;
    private String detail;

    public ErrorInfo() {
    }

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public ErrorInfo setUrl(final String url) {
        this.url = url;
        return this;
    }

    public ErrorType getType() {
        return type;
    }

    public ErrorInfo setType(final ErrorType type) {
        this.type = type;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public ErrorInfo setDetail(final String detail) {
        this.detail = detail;
        return this;
    }
}