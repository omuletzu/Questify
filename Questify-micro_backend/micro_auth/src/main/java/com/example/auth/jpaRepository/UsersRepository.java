package com.example.auth.jpaRepository;

import com.example.auth.models.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findUsersByUsername(String username);

    public List<Users> findAllByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Users e SET e.banned = :banned WHERE e.id = :id")
    public void updateIsActive(@Param("id") Long id, @Param("banned") boolean banned);

    @Query("SELECT u.id FROM Users u WHERE u.username = :username")
    public List<Long> findUserIdsByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users WHERE username LIKE %:subusername%", nativeQuery = true)
    public List<Users> findAllBySubUsername(@Param("subusername") String subUsername);

}
