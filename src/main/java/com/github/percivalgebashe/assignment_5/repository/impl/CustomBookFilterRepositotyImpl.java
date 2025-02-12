package com.github.percivalgebashe.assignment_5.repository.impl;

import com.github.percivalgebashe.assignment_5.dto.BookFilterDTO;
import com.github.percivalgebashe.assignment_5.entity.Book;
import com.github.percivalgebashe.assignment_5.repository.CustomBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomBookFilterRepositotyImpl implements CustomBookRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<Book> findBookByFilters(BookFilterDTO filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (null != filter.getAuthors() && !filter.getAuthors().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("authors"), filter.getAuthors()));
        }
        if (null != filter.getTitle() && !filter.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("title"), filter.getTitle()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
