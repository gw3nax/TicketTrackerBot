package ru.gw3nax.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gw3nax.scrapper.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u.clientName FROM UserEntity u WHERE u.userId = :userId")
    String getClientNameByUserId(@Param("userId") String userId);

    Optional<UserEntity> findByUserId(String userId);
}
