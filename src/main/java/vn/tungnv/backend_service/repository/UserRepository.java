package vn.tungnv.backend_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.tungnv.backend_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("""
                   SELECT u FROM User u
                   WHERE u.userStatus = 'ACTIVE'
                   AND(lower(u.firstName) like :keyword
                   OR lower(u.lastName) like :keyword
                   OR lower(u.username) like :keyword
                   OR lower(u.phone) like :keyword)
            """)
    Page<User> searchByKeyword(String keyword, Pageable pageable);
}
