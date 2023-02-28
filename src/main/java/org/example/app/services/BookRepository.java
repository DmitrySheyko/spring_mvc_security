package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("In bookRepository successfully saved book id=" + book.getId());
        repo.add(book);
    }

    @Override
    public void removeItemById(String bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                repo.remove(book);
                logger.info("In bookRepository successfully deleted book id=" + bookIdToRemove);
            }
        }
    }

    @Override
    public void removeByRegex(String authorForDelete, String titleForDelete, String sizeForDelete) {
        List<Book> listForDelete = new ArrayList<>();
        int size;
        for (Book book : repo) {
            boolean shouldBeDeletedByAuthor = authorForDelete.equals("*") || authorForDelete.equals(book.getAuthor());
            boolean shouldBeDeletedByTitle = titleForDelete.equals("*") || titleForDelete.equals(book.getTitle());
            boolean shouldBeDeletedBySize;
            if (sizeForDelete.equals("*")) {
                shouldBeDeletedBySize = true;
            } else if (sizeForDelete.charAt(0) == '>') {
                size = Integer.parseInt(sizeForDelete.substring(1));
                shouldBeDeletedBySize = book.getSize() > size;
            } else if (sizeForDelete.charAt(0) == '<') {
                size = Integer.parseInt(sizeForDelete.substring(1));
                shouldBeDeletedBySize = book.getSize() < size;
            } else {
                size = Integer.parseInt(sizeForDelete);
                shouldBeDeletedBySize = size == book.getSize();
            }
            if (shouldBeDeletedByAuthor && shouldBeDeletedByTitle && shouldBeDeletedBySize) {
                listForDelete.add(book);
            }
        }
        repo.removeAll(listForDelete);
        logger.info("In bookRepository successfully completed deletion by regex=");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit() {
        logger.info("default init in book repo");
    }

    public void defaultDestroy() {
        logger.info("default destroy in book repo");
    }
}

