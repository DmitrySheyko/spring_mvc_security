package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    //    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author, title, size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("In bookRepository successfully saved book");
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("id", bookIdToRemove);
            jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
            logger.info("In bookRepository successfully deleted book id=" + bookIdToRemove);
        }
    }

    @Override
    public void removeByRegex(String authorForDelete, String titleForDelete, String sizeForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", authorForDelete);
        parameterSource.addValue("title", titleForDelete);
        parameterSource.addValue("size", Integer.parseInt(sizeForDelete));
        jdbcTemplate.update("DELETE FROM books WHERE author = :author and title = :title and size  = :size;", parameterSource);
//        List<Book> listForDelete = new ArrayList<>();
//        int size;
//        for (Book book : retrieveAll()) {
//            boolean shouldBeDeletedByAuthor = authorForDelete.equals("*") || authorForDelete.equals(book.getAuthor());
//            boolean shouldBeDeletedByTitle = titleForDelete.equals("*") || titleForDelete.equals(book.getTitle());
//            boolean shouldBeDeletedBySize;
//            if (sizeForDelete.equals("*")) {
//                shouldBeDeletedBySize = true;
//            } else if (sizeForDelete.charAt(0) == '>') {
//                size = Integer.parseInt(sizeForDelete.substring(1));
//                shouldBeDeletedBySize = book.getSize() > size;
//            } else if (sizeForDelete.charAt(0) == '<') {
//                size = Integer.parseInt(sizeForDelete.substring(1));
//                shouldBeDeletedBySize = book.getSize() < size;
//            } else {
//                size = Integer.parseInt(sizeForDelete);
//                shouldBeDeletedBySize = size == book.getSize();
//            }
//            if (shouldBeDeletedByAuthor && shouldBeDeletedByTitle && shouldBeDeletedBySize) {
//                listForDelete.add(book);
//            }
//        }
//        repo.removeAll(listForDelete);
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

