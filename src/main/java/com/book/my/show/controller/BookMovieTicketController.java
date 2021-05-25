package com.book.my.show.controller;

import com.book.my.show.request.BookMovieTicketRequest;
import com.book.my.show.response.BookMovieTicketResponse;
import com.book.my.show.service.IBookMovieTicketService;
import com.book.my.show.validator.ValidationUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bookmyshow")
public class BookMovieTicketController {
    private final HttpServletRequest servletRequest;
    private final IBookMovieTicketService bookMovieTicketService;

    public BookMovieTicketController(HttpServletRequest servletRequest, IBookMovieTicketService bookMovieTicketService) {
        this.servletRequest = servletRequest;
        this.bookMovieTicketService = bookMovieTicketService;
    }

    @PostMapping("/ext/v1/book/movie/ticket")
    public ResponseEntity<BookMovieTicketResponse> bookMovieTicket(
            @Validated @RequestBody BookMovieTicketRequest bookMovieTicketRequest) throws Exception {
        //Validation layer
        ValidationUtility.validate(servletRequest);

        return ResponseEntity
                .accepted().body(bookMovieTicketService.bookMovieTicket(bookMovieTicketRequest));
    }
}
