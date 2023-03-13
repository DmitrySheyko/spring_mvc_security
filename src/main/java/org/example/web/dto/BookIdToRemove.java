package org.example.web.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BookIdToRemove {

    @Positive
    @NotNull
    private Integer id;

    public BookIdToRemove(Integer id) {
        this.id = id;
    }

    public BookIdToRemove() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
