package com.book.my.show.repository.specification;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.response.SeatInfo;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Builder
public class BookShowSeatSpecification extends SpecificationHelper implements Specification<BookShowSeat> {
    private static final long serialVersionUID = -8237719561246567834L;

    private final String cityName;
    private final String theatreName;
    private final String auditoriumName;
    private final String movieName;
    private final String showDay;
    private final String showTime;
    private final List<SeatInfo> seatInfoList;

    @Override
    public Predicate toPredicate(Root<BookShowSeat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
