package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void save(Book book) {
        logger.info("Attempt of saving book in bookService");
        if (isAuthorCorrect(book.getAuthor()) || isTitleCorrect(book.getTitle()) || isSizeCorrect(book.getSize())) {
            bookRepo.store(book);
        } else {
            logger.info("Book hasn't saved. Book description is incorrect");
        }
    }

    public void removeBookId(Integer bookIdToRemove) {
        logger.info("Attempt of removing book by id in bookService");
        if (isBookIdCorrect(bookIdToRemove)) {
            bookRepo.removeItemById(bookIdToRemove);
        } else {
            logger.info("Book hasn't removed. Book id is incorrect");
        }
    }

    public void removeBooksByRegex(String regex) {
        logger.info("Attempt of removing book by regex in bookService");
        String[] searchParameters = regex.split("/");
        String authorForDelete = searchParameters[0];
        String titleForDelete = searchParameters[1];
        String sizeForDelete = searchParameters[2];
        if (authorForDelete.equals("*") && titleForDelete.equals("*") && sizeForDelete.equals("*")) {
            bookRepo.removeAll();
        } else if (authorForDelete.equals("*") && titleForDelete.equals("*") && !sizeForDelete.equals("*")) {
            bookRepo.removeAllBySize(Integer.parseInt(sizeForDelete));
        } else if (authorForDelete.equals("*") && !titleForDelete.equals("*") && sizeForDelete.equals("*")) {
            bookRepo.removeAllByTitle(titleForDelete);
        } else if (!authorForDelete.equals("*") && titleForDelete.equals("*") && sizeForDelete.equals("*")) {
            bookRepo.removeAllByAuthor(authorForDelete);
        } else if (authorForDelete.equals("*") && !titleForDelete.equals("*") && !sizeForDelete.equals("*")) {
            bookRepo.removeAllByTitleAndSize(titleForDelete, Integer.parseInt(sizeForDelete));
        } else if (!authorForDelete.equals("*") && !titleForDelete.equals("*") && sizeForDelete.equals("*")) {
            bookRepo.removeAllByAuthorAndTitle(authorForDelete, titleForDelete);
        } else if (!authorForDelete.equals("*") && titleForDelete.equals("*") && !sizeForDelete.equals("*")) {
            bookRepo.removeAllByAuthorAndSize(authorForDelete, Integer.parseInt(sizeForDelete));
        } else if (!authorForDelete.equals("*") && !titleForDelete.equals("*") && !sizeForDelete.equals("*")) {
            bookRepo.removeAllByAuthorAndTitleAndSize(authorForDelete, titleForDelete, Integer.parseInt(sizeForDelete));
        }
    }

    private boolean isAuthorCorrect(String author) {
        return !StringUtils.isEmptyOrWhitespace(author);
    }

    private boolean isTitleCorrect(String title) {
        return !StringUtils.isEmptyOrWhitespace(title);
    }

    private boolean isSizeCorrect(Integer size) {
        return size != null && size >= 0;
    }

    private boolean isBookIdCorrect(Integer bookId) {
        return (bookId != null);
    }
    
    private void defaultInit() {
        logger.info("default INIT in bookService");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in bookService");
    }

}