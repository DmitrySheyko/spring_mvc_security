package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> retrieveAll();

    void store(T book);

    void removeItemById(Integer bookIdToRemove);

    void removeAll();

    void removeAllBySize(int sizeForDelete);

    void removeAllByTitle(String titleForDelete);

    void removeAllByAuthor(String authorForDelete);

    void removeAllByTitleAndSize(String titleForDelete, int parseInt);

    void removeAllByAuthorAndTitle(String authorForDelete, String titleForDelete);

    void removeAllByAuthorAndSize(String authorForDelete, int parseInt);

    void removeAllByAuthorAndTitleAndSize(String authorForDelete, String titleForDelete, int parseInt);
}
