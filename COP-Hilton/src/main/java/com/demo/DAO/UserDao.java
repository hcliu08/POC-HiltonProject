package com.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.User;

public interface UserDao extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
}