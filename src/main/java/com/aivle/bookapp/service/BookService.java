package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            // 실제 구현 시에는 repository.findByTitleContaining(keyword) 등을 호출
            return bookRepository.findAll(); // 임시로 전체 반환
        }
        return bookRepository.findAll();
    }

    public List<Book> findByTagName(String tagName) {
        log.info("Searching books by tag: {}", tagName);
        // TODO: 태그 기능 구현 후 repository 호출 로직 추가 필요
        return bookRepository.findAll(); // 임시로 전체 반환
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
                    if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
                    if (book.getIsbn() != null) existingBook.setIsbn(book.getIsbn());
                    if (book.getDescription() != null) existingBook.setDescription(book.getDescription());
                    return existingBook; // 트랜잭션 종료 시 더티 체킹으로 저장됨
                }).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
