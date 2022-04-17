package com.example.BackEndRepo.repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIDOrTell(String ID, String UserTell); // 아이디, 전화번호 존재 확인

    Boolean existsByUsername(String ID); // 아이디 존재 확인

    Boolean existsByEmail(String Tell); // 전화번호 존재 확인
}