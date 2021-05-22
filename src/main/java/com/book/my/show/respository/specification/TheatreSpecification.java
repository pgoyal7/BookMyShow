package com.book.my.show.respository.specification;

import com.book.my.show.entity.Theatre;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Builder
public class TheatreSpecification implements Specification<Theatre> {
    private static final long serialVersionUID = -6841749395292180007L;

    private final String cityName;
    private final String theatreName;
    private final String movieName;


    @Override
    public Predicate toPredicate(Root<Theatre> theatreRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(cityName != null && cityName.length() != 0) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("cities").get("name")), cityName));
        }
        if(theatreName != null && theatreName.length() != 0) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.get("name")), theatreName));
        }

        if(movieName != null && movieName.length() != 0) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("auditoriums").join("movie").get("name")), movieName));
        }

        if(predicateList.size() == 0) {
            return null;
        }
        Predicate resultantPredicate = predicateList.get(0);

        for(int counter = 1; counter < predicateList.size(); counter++) {
            resultantPredicate = criteriaBuilder.and(resultantPredicate, predicateList.get(counter));
        }

        return resultantPredicate;
    }
}
