package com.book.my.show.repository;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.type.SeatStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookShowSeatRepository extends PagingAndSortingRepository<BookShowSeat, Long>, JpaSpecificationExecutor<BookShowSeat> {
    /*@Query("SELECT COUNT(bss.id) FROM bookShowSeat bss WHERE bss.status = ?1 AND bss.updatedOn - ?3 > threshold AND bss.createdOn > ?3")
    long findCountByStatusAndThresholdAndCreatedDate(@Param("status") SeatStatus status,
                                                     @Param("threshold") LocalDateTime threshold,
                                                     @Param("currentDateTime") LocalDateTime currentDateTime);

    @Query("SELECT COUNT(bss.id) FROM bookShowSeat bss WHERE bss.status = ?1 AND bss.updatedOn - ?3 > threshold AND bss.createdOn > ?3")
    List<BookShowSeat> findAllByStatusAndThresholdAndCreatedDate(@Param("status") SeatStatus status,
                                                                 @Param("threshold") LocalDateTime threshold,
                                                                 @Param("currentDateTime") LocalDateTime currentDateTime, Pageable pageable);*/
}
