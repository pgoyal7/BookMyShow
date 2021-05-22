package com.book.my.show.respository.specification;

import com.book.my.show.entity.Auditorium;
import com.book.my.show.entity.Theatre;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Builder
public class AuditoriumSpecification implements Specification<Auditorium> {
    private static final long serialVersionUID = -1589324014475816874L;

    private final String cityName;
    private final String theatreName;

    @Override
    public Predicate toPredicate(Root<Auditorium> auditoriumRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        final Join<Auditorium, Theatre> theatreRoot = auditoriumRoot.join("theatre");
        if(theatreName != null && theatreName.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.get("name")), theatreName));
        }

        if(cityName != null && cityName.length() != 0) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(theatreRoot.join("cities").get("name")), cityName));
        }

        if(predicates.size() == 0) {
            return null;
        }

        Predicate resultantPredicate = predicates.get(0);
        for(int counter = 1; counter < predicates.size(); counter++) {
            resultantPredicate = criteriaBuilder.and(resultantPredicate, predicates.get(counter));
        }
        return resultantPredicate;
    }
}
