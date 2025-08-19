package com.group8.library_management.specification;

import com.group8.library_management.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> build(
            List<String> titles,
            List<Long> authorIds,
            List<Long> publisherIds,
            List<Long> genreIds
    ) {
        return (root, query, cb) -> {
            List<Predicate> andPredicates = new ArrayList<>();

            // Title: OR nhiều từ khóa (LIKE, case-insensitive)
            if (titles != null && !titles.isEmpty()) {
                List<Predicate> condition = new ArrayList<>();
                for (String title : titles) {
                    if (title != null && !title.trim().isEmpty()) {
                        condition.add(
                                cb.like(cb.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%")
                        );
                    }
                }
                if (!condition.isEmpty()) {
                    andPredicates.add(cb.or(condition.toArray(new Predicate[0])));
                }
            }

            // Author: IN danh sách id
            if (authorIds != null && !authorIds.isEmpty()) {
                andPredicates.add(root.join("author").get("id").in(authorIds));
            }

            // Publisher: IN danh sách id
            if (publisherIds != null && !publisherIds.isEmpty()) {
                andPredicates.add(root.join("publisher").get("id").in(publisherIds));
            }

            // Genre: IN danh sách id
            if (genreIds != null && !genreIds.isEmpty()) {
                andPredicates.add(root.join("genre").get("id").in(genreIds));
            }

            return andPredicates.isEmpty()
                    ? cb.conjunction()
                    : cb.and(andPredicates.toArray(new Predicate[0]));
        };
    }
}
