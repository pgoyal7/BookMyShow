package com.book.my.show.respository.specification;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.entity.Seat;
import com.book.my.show.entity.Theatre;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SeatSpecification implements Specification<Seat> {
    private static final long serialVersionUID = 2324943510422081269L;

    private final String cityName;
    private final String theatreName;
    private final String movieName;
    private final String showTime;
    private final LocalDate showDay;

    @Override
    public Predicate toPredicate(Root<Seat> seatRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        final Join<Seat, Theatre> auditoriumRoot = seatRoot.join("auditorium");
        final Join<Seat, BookShowSeat> bookShowSeatRoot = seatRoot.join("bookShowSeats");

        if(movieName != null && movieName.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(
                    auditoriumRoot.join("movie").get("name")), movieName));
        }

        if(showTime != null && showTime.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(bookShowSeatRoot
                    .join("show").get("showTime")), showTime));
        }

        if(theatreName != null && theatreName.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(auditoriumRoot
                    .join("theatre").get("name")), theatreName));
        }
        if(showDay != null) {
            predicates.add(criteriaBuilder.equal(bookShowSeatRoot.get("showDay"), showDay));
        }

        if(cityName != null && cityName.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(auditoriumRoot
                    .join("theatre").join("cities").get("name")), cityName));
        }

        if(predicates.size() == 0) {
            return null;
        }
        Predicate resultantPredicate = predicates.get(0);
        for(int counter = 0; counter < predicates.size(); counter++) {
            resultantPredicate = criteriaBuilder.and(resultantPredicate, predicates.get(counter));
        }
        return resultantPredicate;
    }
}
