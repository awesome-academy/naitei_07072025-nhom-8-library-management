package com.group8.library_management.repository;


import com.group8.library_management.entity.Follow;
import com.group8.library_management.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}
