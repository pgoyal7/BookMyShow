package com.book.my.show.service;

import com.book.my.show.request.BookMovieTicketRequest;
import com.book.my.show.response.BookMovieTicketResponse;

public interface IBookMovieTicketService {
    BookMovieTicketResponse bookMovieTicket(BookMovieTicketRequest bookMovieTicketRequest) throws Exception;
}
