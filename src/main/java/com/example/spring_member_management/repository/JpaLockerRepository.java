package com.example.spring_member_management.repository;

import com.example.spring_member_management.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLockerRepository extends JpaRepository<Locker,Long> {
    boolean existsByNumber(String lockerNumber);
}