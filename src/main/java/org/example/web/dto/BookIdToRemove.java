package org.example.web.dto;


public class BookIdToRemove {

    private String id;

    public BookIdToRemove(String id) {
        this.id = id;
    }

    public BookIdToRemove() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
