package com.book.my.show.respository;

import com.book.my.show.entity.Ticket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
}