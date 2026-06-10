package com.aivle.bookapp.controller;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 도서 관련 REST API 요청을 처리하는 컨트롤러입니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 1. 전체 도서 목록 조회 (GET /api/books)
     * - 키워드가 있을 경우 제목 검색을 수행합니다.
     * @param keyword 검색할 제목 키워드 (선택 사항)
     * @return 도서 목록과 함께 200 OK 응답
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String keyword) {
        log.info("Request to get all books with keyword: {}", keyword);
        List<Book> books = bookService.findAll(keyword);
        return ResponseEntity.ok(books);
    }

    /**
     * 2. 특정 도서 상세 조회 (GET /api/books/{id})
     * @param id 조회할 도서의 식별자
     * @return 도서 정보와 함께 200 OK 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.info("Request to get book by id: {}", id);
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * 3. 신규 도서 등록 (POST /api/books)
     * @Valid를 통해 입력 데이터의 유효성을 검증합니다.
     * @param book 등록할 도서 정보
     * @return 생성된 도서의 위치(Location 헤더)와 함께 201 Created 응답
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        log.info("Request to create book: {}", book.getTitle());
        Book savedBook = bookService.save(book);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(savedBook);
    }

    /**
     * 4. 도서 정보 수정 (PATCH /api/books/{id})
     * @param id 수정할 도서의 식별자
     * @param book 수정할 내용이 담긴 도서 객체
     * @return 수정된 도서 정보와 함께 200 OK 응답
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        log.info("Request to update book id: {}", id);
        Book updatedBook = bookService.update(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * 5. 특정 도서 삭제 (DELETE /api/books/{id})
     * @param id 삭제할 도서의 식별자
     * @return 응답 본문 없이 204 No Content 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Request to delete book id: {}", id);
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * 6. 특정 태그가 포함된 모든 도서 목록 조회(GET /books/tag)
     * @param tagName 클릭한 태그 이름(퀄리 스트림)
     * @return 해당 태그를 보유한 도서 목록과 함께 200 OKAy 확인
     */

    @GetMapping("/tag")
    public ResponseEntity<List<Book>> getBooksByTag(@RequestParam String tagName) {
        log.info("Request to get books by tag name: {}", tagName);

        List<Book> books = bookService.findByTagName(tagName);

        return ResponseEntity.ok(books);
    }
}
