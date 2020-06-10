package com.jane.builder.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jane.builder.modules.model.MenuModel;

public interface MenuRepository extends JpaRepository<MenuModel, Integer> {

}
