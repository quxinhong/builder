package com.jane.builder.modules.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jane.builder.common.util.X;
import com.jane.builder.modules.dao.CommonItemRepository;
import com.jane.builder.modules.dao.CommonRepository;
import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

@Service
public class CommonService {

	@Autowired
	private CommonRepository commonRepository;
	
	@Autowired
	private CommonItemRepository commonItemRepository;
	
	private Map<String, CommonModel> commonMapping = new HashMap<>();
	
	private Map<String, List<CommonItemModel>> itemMapping = new HashMap<>();
	
	@Transactional
	public void addCommon(CommonModel commonModel, List<CommonItemModel> items) {
		saveCommon(commonModel);
		commonItemRepository.saveAll(items);
	}
	
	@Transactional
	public void modifyCommon(CommonModel commonModel, List<CommonItemModel> items) {
		deleteCommonItemsByComNo(commonModel.getComNo());
		addCommon(commonModel, items);
	}
	
	public List<CommonModel> searchCommom(String keyword){
		if(!X.isBlank(keyword)) {
    		keyword = "%"+keyword+"%";
    		return commonRepository.findByComNoLikeOrComNameLike(keyword, keyword);
    	}else {
    		return commonRepository.findAll();
    	}
	}
	
	public CommonModel getCommonModel(String comNo) {
		Optional<CommonModel> optional = commonRepository.findById(comNo);
    	if(optional.isPresent()) {
    		return optional.get();
    	}
    	return null;
	}
	
	public List<CommonItemModel> getCommonItems(String comNo) {
		return commonItemRepository.findByComNo(comNo);
	}
	
	public boolean existsCommonByComNo(String comNo) {
		return commonRepository.existsById(comNo);
	}
	
	public void deleteCommonByComNo(String comNo) {
		commonRepository.deleteById(comNo);
	}
	
	public void deleteCommonItemsByComNo(String comNo) {
		commonItemRepository.deleteByComNo(comNo);
	}
	
	public CommonModel saveCommon(CommonModel commonModel) {
		return commonRepository.save(commonModel);
	}
	
	public CommonModel getCommonSync(String comNo) {
		CommonModel commonModel = commonMapping.get(comNo);
		if(commonModel==null) {
			synchronized (commonMapping) {
				commonModel = commonMapping.get(comNo);
				if(commonModel==null) {
					Optional<CommonModel> optional = commonRepository.findById(comNo);
					if(optional.isPresent()) {
						commonModel = optional.get();
						commonMapping.put(comNo, commonModel);
					}
				}
			}
		}
		return commonModel;
	}
	
	public CommonItemModel getCommonItemSync(String comNo, String value) {
		List<CommonItemModel> items = getCommonItems(comNo);
		if(items!=null&&items.size()>0) {
			for (int i = 0; i < items.size(); i++) {
				if(value.equals(items.get(i).getValue())) {
					return items.get(i);
				}
			}
		}
		return null;
	}
	
	public List<CommonItemModel> getCommonItemsSync(String comNo) {
		List<CommonItemModel> items = itemMapping.get(comNo);
		if(items==null) {
			synchronized (itemMapping) {
				items = itemMapping.get(comNo);
				if(items==null) {
					items = commonItemRepository.findByComNo(comNo);
					itemMapping.put(comNo, items);
				}
			}
		}
		return items;
	}
	
	public synchronized void clearSync() {
		this.commonMapping = new HashMap<>();
		this.itemMapping = new HashMap<>();
	}
}
