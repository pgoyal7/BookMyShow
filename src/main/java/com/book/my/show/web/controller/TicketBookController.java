package com.book.my.show.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmyshow")
public class TicketBookController {
    @PostMapping("/ext/v1/")
    public void bookMovieTicket() {

    }
}
