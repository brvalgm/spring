package org.example.app.services;

import org.example.web.dto.Book;
import org.example.web.dto.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(@Qualifier("bookRepository") ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public List<Book> getBookList() {
        List<Book> filterBooks = getAllBooks();

        if (Filter.author != "" || Filter.title != "" || Filter.size != null) {
            if (Filter.author  != "") {
                filterBooks = filterBooks.stream().filter(b -> b.getAuthor().matches(Filter.author )).collect(Collectors.toList());
            }

            if (Filter.title != "") {
                filterBooks = filterBooks.stream().filter(b -> b.getTitle().matches(Filter.title)).collect(Collectors.toList());
            }

            if (Filter.size != null) {
                filterBooks = filterBooks.stream().filter(b -> b.getSize().equals(Filter.size)).collect(Collectors.toList());
            }
        }
        return filterBooks;
    }

    public void saveBook(Book book) {
        if (book.getAuthor() != "" || book.getTitle() != "" || book.getSize() != null) {
            bookRepo.store(book);
        }
    }

    public void removeBook(Integer bookIdToRemove, String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove) {
        List<Book> removeBooks = getBookList();

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

    public void searchBook(String bookAuthorToSearch, String bookTitleToSearch, Integer bookSizeToSearch) {
        Filter.author = bookAuthorToSearch;
        Filter.title = bookTitleToSearch;
        Filter.size = bookSizeToSearch;
    }
}
