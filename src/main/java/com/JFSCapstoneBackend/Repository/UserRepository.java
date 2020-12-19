package com.JFSCapstoneBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JFSCapstoneBackend.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUserEmail(String userEmail);

	public void deleteByUserEmail(String userEmail);

	public boolean existsByUserEmail(String userEmail);

}
