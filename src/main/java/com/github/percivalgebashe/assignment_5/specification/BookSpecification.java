package com.github.percivalgebashe.assignment_5.specification;

import com.github.percivalgebashe.assignment_5.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5.entity.Book;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class BookSpecification {

    public static Specification<Book> filterBooks(BookFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Always true initially

            if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getPublishedDateStart() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("publishedDate"), filter.getPublishedDateStart()));
            }
            if (filter.getPublishedDateEnd() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("publishedDate"), filter.getPublishedDateEnd()));
            }
            if (filter.getAuthors() != null && !filter.getAuthors().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.join("authors").get("name"), "%" + filter.getAuthors() + "%"));
            }

            return predicate;
        };
    }
}

