package org.example.web.dto;

public class RegexToRemove {

    @RegexValidationAnnotation
    private String queryRegex;

    public RegexToRemove() {
    }

    public RegexToRemove(String queryRegex) {
        this.queryRegex = queryRegex;
    }

    public String getQueryRegex() {
        return queryRegex;
    }

    public void setQueryRegex(String queryRegex) {
        this.queryRegex = queryRegex;
    }
}
