package com.okjk.smartSaltern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okjk.smartSaltern.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
