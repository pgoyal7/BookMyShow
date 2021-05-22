package com.book.my.show.respository;

import com.book.my.show.entity.Seat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SeatRepository extends PagingAndSortingRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
}
