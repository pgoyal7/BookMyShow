package com.book.my.show.cron;

import com.book.my.show.repository.BookShowSeatRepository;
import com.book.my.show.service.ICronService;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class BMSScheduler {
    private final ICronService cronService;

    public BMSScheduler(ICronService cronService) {
        this.cronService = cronService;
    }

    //@Scheduled(initialDelayString = "1000", fixedDelayString = "1000")
    public void makeBlockedSeatBackToOpenState() {
        cronService.unblockSeatsIfThresholdExceed(SeatStatus.BLOCKED, LocalDateTime.now());
    }
}
