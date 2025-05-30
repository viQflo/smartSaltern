package com.okjk.smartSaltern.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okjk.smartSaltern.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	public Optional<User> findById(String userId);
	
	 User findByUserId(String userId);  // ✅ 추가
	
}


