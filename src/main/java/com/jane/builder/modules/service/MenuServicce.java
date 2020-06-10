package com.jane.builder.modules.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jane.builder.common.util.X;
import com.jane.builder.modules.dao.ApiRepository;
import com.jane.builder.modules.dao.DictionaryRepository;
import com.jane.builder.modules.dao.MenuRepository;
import com.jane.builder.modules.dao.ModuleRepository;
import com.jane.builder.modules.dao.StepRepository;
import com.jane.builder.modules.model.MenuModel;
import com.jane.builder.modules.model.ModuleModel;

@Service
public class MenuServicce {

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private ApiRepository apiRepository;
	
	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	public MenuModel saveMenu(MenuModel menuModel) {
		return menuRepository.save(menuModel);
	}
	
	public Optional<MenuModel> findMenuById(Integer menuId){
		return menuRepository.findById(menuId);
	}
	
	public List<MenuModel> findAllMenu(){
		return menuRepository.findAll();
	}
	
	@Transactional
	public MenuModel addMenu(MenuModel menuModel) {
		menuModel = saveMenu(menuModel);
		String moduleNo = menuModel.getModuleNo();
		if(!X.isBlank(moduleNo)) {
			ModuleModel module = new ModuleModel();
			module.setModuleNo(moduleNo);
			module.setModuleName(menuModel.getMenuName());
			module.setRemark(menuModel.getRemark());
			module.setCreateDate(menuModel.getCreateDate());
			module.setCreateUser(menuModel.getCreateUser());
			saveModule(module);
		}
		return menuModel;
	}
	
	@Transactional
	public MenuModel modifyMenu(MenuModel menuModel) {
		String moduleNo = menuModel.getModuleNo();
		if(!X.isBlank(moduleNo)) {
			Optional<ModuleModel> opt = findModuleById(moduleNo);
			ModuleModel module = opt.get();
			module.setModuleName(menuModel.getMenuName());
			module.setRemark(menuModel.getRemark());
			module.setUpdateDate(menuModel.getUpdateDate());
			module.setUpdateUser(menuModel.getUpdateUser());
			saveModule(module);
		}
		return saveMenu(menuModel);
	}
	
	@Transactional
	public void deleteMenu(Integer menuId){
		MenuModel m = new MenuModel();
		m.setParentId(menuId);
		Example<MenuModel> e = Example.of(m);
		if(menuRepository.exists(e)) {
			List<MenuModel> ms = menuRepository.findAll(e);
			for (MenuModel menuModel : ms) {
				deleteMenu(menuModel.getMenuId());
			}
		}
		MenuModel menu = menuRepository.findById(menuId).get();
		String moduleNo = menu.getModuleNo();
		if(!X.isBlank(moduleNo)) {
			deleteModule(moduleNo);
		}
		menuRepository.deleteById(menuId);
	}
	
	public ModuleModel saveModule(ModuleModel moduleModel) {
		return moduleRepository.save(moduleModel);
	}
	
	public Optional<ModuleModel> findModuleById(String moduleNo){
		return moduleRepository.findById(moduleNo);
	}
	
	@Transactional
	public void deleteModule(String moduleNo) {
		dictionaryRepository.deleteByModuleNo(moduleNo);
		stepRepository.deleteByModuleNo(moduleNo);
		apiRepository.deleteByModuleNo(moduleNo);
		moduleRepository.deleteById(moduleNo);
	}
}
