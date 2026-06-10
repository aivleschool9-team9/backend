package com.aivle.bookapp.service;


import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.exception.BookNotFoundException;
import com.aivle.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    // 전체 도서 목록 조회
    @Transactional(readOnly = true)
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    // 특정 도서 단건 조회
    @Transactional(readOnly = true)
    public Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // 새 도서 등록 + 태그 저장 + 임베딩 저장
    @Transactional
    public Book create(Book book, List<String> tags, float[] embedding){
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        Book saved = bookRepository.save(book);
        return saved;
    }


    // 도서 부분 수정
    @Transactional
    public Book update(Long id, Book book, List<String> tags, float[] embedding){
        Book existing = findById(id);

        if (book.getTitle() != null) existing.setTitle(book.getTitle());
        if (book.getAuthor() != null) existing.setAuthor(book.getAuthor());
        if (book.getSummary() != null) existing.setSummary(book.getSummary());
        if (book.getContent() != null) existing.setContent(book.getContent());
        if (book.getCoverImageUrl() != null) existing.setCoverImageUrl(book.getCoverImageUrl());
        if (book.getLikes() != null) existing.setLikes(book.getLikes());
        existing.setUpdatedAt(LocalDateTime.now());

        Book updated = bookRepository.save(existing);
        return updated;
    }

    // 도서 삭제
    @Transactional
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException(id);
        }
    }

    // 좋아요 증가/감소
    @Transactional
    public Book updateLikes(Long id, int likes) {

        return null;
    }

    // 특정 태그에 속한 도서 목록 조회
    @Transactional(readOnly = true)
    public List<Book> findBooksByTagId(Long tagId){
        return List.of();
    }

    // 키워드 검색 + 정렬
    @Transactional(readOnly = true)
    public List<Book> findAllWithFilter(String keyword, String sort){

        return List.of();
    }

    // AI 의미 검색 + 코사인 유사도 계산
    @Transactional(readOnly = true)
    public List<Book> semanticSearch(float[] queryVector){
        // bookEmbeddingService에서 전체 임베딩 조회 후 코사인 유사도 계산
        // searchLogService.saveSearchLog() 호출 (searchType: "SEMANTIC")
        return List.of();
    }
}
