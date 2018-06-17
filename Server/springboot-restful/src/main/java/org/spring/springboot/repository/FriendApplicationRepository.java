package org.spring.springboot.repository;

import org.spring.springboot.entity.FriendApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendApplicationRepository extends JpaRepository<FriendApplication, Long> {

}
