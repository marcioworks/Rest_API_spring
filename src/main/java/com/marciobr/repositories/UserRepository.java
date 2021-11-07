package com.marciobr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marciobr.data.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u FROM User u WHERE 	u.userName =:userName")
	User findByUsername(@Param("userName") String UserName);
}
