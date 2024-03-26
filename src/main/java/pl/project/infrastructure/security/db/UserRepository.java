package pl.project.infrastructure.security.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT u.email FROM UserEntity u
            WHERE u.userName = ?1
            """)
    Optional<String> findEmailByUserName(String userName);

    @Query("""
                UPDATE UserEntity u
                SET u.email = ?1
                WHERE u.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeEmail(String newEmail, String oldEmail);
}
