package com.unitech.unitech.repository;

import com.unitech.unitech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByPin(String pin);
}
