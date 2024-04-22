package com.ProjectAPI.APIRestUsers.repository;

import com.ProjectAPI.APIRestUsers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
