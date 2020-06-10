package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.UserRoleModel;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleModel> {

	void deleteByUserNo(String userNo);
}
