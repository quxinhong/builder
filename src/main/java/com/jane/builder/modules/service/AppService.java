package com.jane.builder.modules.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jane.builder.common.util.X;
import com.jane.builder.modules.dao.AppRepository;
import com.jane.builder.modules.model.AppModel;

@Service
public class AppService {

	@Autowired
	private AppRepository appRepository;
	
	public AppModel save(AppModel appModel) {
		return appRepository.save(appModel);
	}
	
	public Optional<AppModel> findById(String appNo) {
		return appRepository.findById(appNo);
	}
	
	public boolean existsById(String appNo) {
		return appRepository.existsById(appNo);
	}
	
	@Transactional
	public void deleteById(String appNo) {
		appRepository.deleteById(appNo);
	}
	
	public List<AppModel> findAll(){
		return appRepository.findAll();
	}
	
	public List<AppModel> search(String keyword){
		if(X.isBlank(keyword)) {
			return findAll();
		}else {
			keyword = "%"+keyword+"%";
			return appRepository.findByAppNoLikeOrAppNameLikeOrAppShortNameLike(keyword, keyword, keyword);
		}
	}
}
