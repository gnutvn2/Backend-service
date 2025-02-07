package vn.tungnv.backend_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.tungnv.backend_service.model.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("FROM Address WHERE userID.id = :userId AND addressType = :type")
    Address findByUserIDAndType(Long userId, Integer type);

    @Query("SELECT a.id FROM Address a WHERE a.userID.id = :userId")
    List<Long> getAllIdsByUserID(Long userId);
}
