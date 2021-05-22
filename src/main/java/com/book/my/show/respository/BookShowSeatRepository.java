package com.book.my.show.respository;

import com.book.my.show.entity.BookShowSeat;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookShowSeatRepository extends PagingAndSortingRepository<BookShowSeat, Long> {
    //findByStatusAndBlockedTime();
}
