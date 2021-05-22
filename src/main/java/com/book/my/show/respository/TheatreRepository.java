package com.book.my.show.respository;

import com.book.my.show.entity.Theatre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends PagingAndSortingRepository<Theatre, Long>, JpaSpecificationExecutor<Theatre> {
}
