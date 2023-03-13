package org.example.web.dto;

import javax.validation.constraints.NotBlank;

public class RegexToRemove {

    @NotBlank
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
