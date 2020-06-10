package com.jane.builder.modules.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jane.builder.modules.dao.UserRepository;
import com.jane.builder.modules.dao.UserRoleRepository;
import com.jane.builder.modules.model.UserModel;
import com.jane.builder.modules.model.UserRoleModel;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Transactional
	public UserModel saveUser(UserModel userModel, Collection<Integer> roles) {
		saveRoles(roles, userModel.getUserNo());
		return saveUser(userModel);
	}
	
	public UserModel saveUser(UserModel userModel) {
		return userRepository.save(userModel);
	}
	
	public boolean existsUserById(String userNo) {
		return userRepository.existsById(userNo);
	}
	
	public void saveRoles(Collection<Integer> roles, String userNo) {
		userRoleRepository.deleteByUserNo(userNo);
		List<UserRoleModel> list = new ArrayList<>();
		for (Integer roleId : roles) {
			UserRoleModel urole = new UserRoleModel();
			urole.setRoleId(roleId);
			urole.setUserNo(userNo);
			list.add(urole);
		}
		userRoleRepository.saveAll(list);
	}
	
	public Optional<UserModel> findUserById(String userNo){
		return userRepository.findById(userNo);
	}
	
	@Transactional
	public void deleteUser(String userNo) {
		userRepository.deleteById(userNo);
		userRoleRepository.deleteByUserNo(userNo);
	}
}
