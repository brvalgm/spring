package org.example.app.services;

import org.example.web.dto.Book;
import org.example.web.dto.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                filterBooks = filterBooks.stream()
                                         .filter(b -> {
                                             Pattern pattern = Pattern.compile(Filter.author);
                                             Matcher match = pattern.matcher(b.getAuthor());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
            }

            if (Filter.title != "") {
                filterBooks = filterBooks.stream()
                                         .filter(b ->  {
                                             Pattern pattern = Pattern.compile(Filter.title);
                                             Matcher match = pattern.matcher(b.getTitle());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
            }

            if (Filter.size != null) {
                filterBooks = filterBooks.stream()
                                         .filter(b ->  {
                                             Pattern pattern = Pattern.compile(Filter.size.toString());
                                             Matcher match = pattern.matcher(b.getSize().toString());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
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
                removeBooks = removeBooks.stream()
                                         .filter(b -> {
                                             Pattern pattern = Pattern.compile(bookIdToRemove.toString());
                                             Matcher match = pattern.matcher(b.getSize().toString());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
            }

            if (bookAuthorToRemove != "") {
                removeBooks = removeBooks.stream()
                                         .filter(b -> {
                                             Pattern pattern = Pattern.compile(bookAuthorToRemove);
                                             Matcher match = pattern.matcher(b.getAuthor());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
            }

            if (bookTitleToRemove != "") {
                removeBooks = removeBooks.stream()
                                         .filter(b -> {
                                             Pattern pattern = Pattern.compile(bookTitleToRemove);
                                             Matcher match = pattern.matcher(b.getTitle());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
            }

            if (bookSizeToRemove != null) {
                removeBooks = removeBooks.stream()
                                         .filter(b -> {
                                             Pattern pattern = Pattern.compile(bookSizeToRemove.toString());
                                             Matcher match = pattern.matcher(b.getSize().toString());
                                             return match.lookingAt();
                                         })
                                         .collect(Collectors.toList());
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
