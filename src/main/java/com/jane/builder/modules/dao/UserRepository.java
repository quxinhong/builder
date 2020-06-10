package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {

}
