package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
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
    public void removeAll() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", -1);
        jdbcTemplate.update("DELETE FROM books WHERE id <> :id", parameterSource);
        logger.info("In bookRepository successfully deleted all books");
    }

    @Override
    public void removeAllBySize(int sizeForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", sizeForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
        logger.info("In bookRepository successfully deleted all books with size=" + sizeForDelete);
    }

    @Override
    public void removeAllByTitle(String titleForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", titleForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
        logger.info("In bookRepository successfully deleted all books with title=" + titleForDelete);
    }

    @Override
    public void removeAllByAuthor(String authorForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", authorForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
        logger.info("In bookRepository successfully deleted all books with author=" + authorForDelete);
    }

    @Override
    public void removeAllByTitleAndSize(String titleForDelete, int sizeForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", titleForDelete);
        parameterSource.addValue("size", sizeForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE title = :title AND size = :size", parameterSource);
        logger.info("In bookRepository successfully deleted all books with title=" + titleForDelete +
                ", size=" + sizeForDelete);
    }

    @Override
    public void removeAllByAuthorAndTitle(String authorForDelete, String titleForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", authorForDelete);
        parameterSource.addValue("title", titleForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE author = :author AND title = :title", parameterSource);
        logger.info("In bookRepository successfully deleted all books with author=" + authorForDelete +
                ", title=" + titleForDelete);
    }

    @Override
    public void removeAllByAuthorAndSize(String authorForDelete, int sizeForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", authorForDelete);
        parameterSource.addValue("size", sizeForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE author = :author AND size = :size", parameterSource);
        logger.info("In bookRepository successfully deleted all books with author=" + authorForDelete +
                ", size=" + sizeForDelete);
    }

    @Override
    public void removeAllByAuthorAndTitleAndSize(String authorForDelete, String titleForDelete, int sizeForDelete) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", authorForDelete);
        parameterSource.addValue("title", titleForDelete);
        parameterSource.addValue("size", sizeForDelete);
        jdbcTemplate.update("DELETE FROM books WHERE author = :author AND title = :title AND size = :size", parameterSource);
        logger.info("In bookRepository successfully deleted all books with author=" + authorForDelete +
                ", title=" + titleForDelete + ", size=" + sizeForDelete);
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

