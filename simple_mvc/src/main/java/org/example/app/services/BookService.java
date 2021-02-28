package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private List<Book> bookList;

    @Autowired
    public BookService(@Qualifier("bookRepository") ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
        this.bookList = bookRepo.retrieveAll();
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void saveBook(Book book) {
        if (book.getAuthor() != "" || book.getTitle() != "" || book.getSize() != null) {
            bookRepo.store(book);
        }
    }

    public void removeBook(Integer bookIdToRemove, String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove) {
        List<Book> removeBooks = getAllBooks();

        if (bookIdToRemove != null || bookAuthorToRemove != "" || bookTitleToRemove != "" || bookSizeToRemove != null) {
            if (bookIdToRemove != null) {
                removeBooks = removeBooks.stream().filter(b -> b.getId().equals(bookIdToRemove)).collect(Collectors.toList());
            }

            if (bookAuthorToRemove != "") {
                removeBooks = removeBooks.stream().filter(b -> b.getAuthor().matches(bookAuthorToRemove)).collect(Collectors.toList());
            }

            if (bookTitleToRemove != "") {
                removeBooks = removeBooks.stream().filter(b -> b.getTitle().matches(bookTitleToRemove)).collect(Collectors.toList());
            }

            if (bookSizeToRemove != null) {
                removeBooks = removeBooks.stream().filter(b -> b.getSize().equals(bookSizeToRemove)).collect(Collectors.toList());
            }

            for (Book book : removeBooks) {
                bookRepo.removeItemById(book.getId());
            }
        }
    }

    public List<Book> searchBook(String bookAuthorToSearch, String bookTitleToSearch, Integer bookSizeToSearch) {
        List<Book> searchBooks = getAllBooks();

        if (bookAuthorToSearch != "" || bookTitleToSearch != "" || bookSizeToSearch != null) {
            if (bookAuthorToSearch != "") {
                searchBooks = searchBooks.stream().filter(b -> b.getAuthor().matches(bookAuthorToSearch)).collect(Collectors.toList());
            }

            if (bookTitleToSearch != "") {
                searchBooks = searchBooks.stream().filter(b -> b.getTitle().matches(bookTitleToSearch)).collect(Collectors.toList());
            }

            if (bookSizeToSearch != null) {
                searchBooks = searchBooks.stream().filter(b -> b.getSize().equals(bookSizeToSearch)).collect(Collectors.toList());
            }
        }
        return searchBooks;
    }
}
