package com.book.my.show.service.impl;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.repository.BookShowSeatRepository;
import com.book.my.show.service.ICronService;
import com.book.my.show.type.BookStatus;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CronService implements ICronService {
    private final BookShowSeatRepository bookShowSeatRepository;

    public CronService(BookShowSeatRepository bookShowSeatRepository) {
        this.bookShowSeatRepository = bookShowSeatRepository;
    }

    @Transactional
    @Override
    public void unblockSeatsIfThresholdExceed(SeatStatus seatStatus, LocalDateTime thresholdTime) {
        int offset = 0, limit = 20;

        /*long count = bookShowSeatRepository
                .findCountByStatusAndThresholdAndCreatedDate(seatStatus, thresholdTime, LocalDateTime.now());*/
        long count = 0;

        log.info("Total number of blocked seats that has exceeds its threshold is {}", count);

        Pageable pageable = PageRequest.of(offset, limit);

        do {
            /*List<BookShowSeat> bookShowSeats = bookShowSeatRepository
                    .findAllByStatusAndThresholdAndCreatedDate(seatStatus, thresholdTime, LocalDateTime.now(), pageable);*/
            List<BookShowSeat> bookShowSeats = new ArrayList<>();

            log.info("Processing batch number : {}", pageable.getPageNumber());
            bookShowSeats.forEach((bookShowSeat) -> {
                bookShowSeat.setStatus(SeatStatus.OPEN);
                bookShowSeat.setTicket(null);
                bookShowSeat.getTicket().setStatus(BookStatus.SYSTEM_CANCELLED);
            });

            bookShowSeatRepository.saveAll(bookShowSeats);
            pageable = pageable.next();
        } while(pageable.getOffset() < count);
    }
}
