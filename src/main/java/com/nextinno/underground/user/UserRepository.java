package com.nextinno.underground.user;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author rsjung
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
