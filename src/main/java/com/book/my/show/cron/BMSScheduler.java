package com.book.my.show.cron;

import com.book.my.show.respository.BookShowSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class BMSScheduler {
    private final BookShowSeatRepository bookShowSeatRepository;

    public BMSScheduler(BookShowSeatRepository bookShowSeatRepository) {
        this.bookShowSeatRepository = bookShowSeatRepository;
    }

    @Scheduled(initialDelayString = "1000", fixedDelayString = "1000")
    public void makeBlockedSeatBackToOpenState() {
        System.out.print("Helo----------"); //pageable impelment
    }
}
