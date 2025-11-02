package com.example.store.repository;

import com.example.store.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Search customers where **any word** in the name contains the given substring (case-insensitive).
     *
     * @param substring the fragment to look for (e.g. "jo")
     * @return matching customers
     */
    @Query(
            value =
                    """
        SELECT c.* FROM customer c
        WHERE EXISTS (
            SELECT 1 FROM unnest(string_to_array(lower(c.name), ' ')) AS word
            WHERE word LIKE lower('%' || :substring || '%')
        )
        """,
            nativeQuery = true)
    List<Customer> searchByNameSubstring(@Param("substring") String substring);
}
