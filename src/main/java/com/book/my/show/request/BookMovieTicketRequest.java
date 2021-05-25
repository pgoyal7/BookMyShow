package com.book.my.show.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class BookMovieTicketRequest implements Serializable {
    private static final long serialVersionUID = 1524680640404367184L;

    private BookMovieTicket bookMovieTicket;
}
