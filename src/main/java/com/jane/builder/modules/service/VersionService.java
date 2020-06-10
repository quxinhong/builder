package com.jane.builder.modules.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jane.builder.common.standard.Operator;
import com.jane.builder.modules.dao.ApiRepository;
import com.jane.builder.modules.dao.CommonItemRepository;
import com.jane.builder.modules.dao.CommonRepository;
import com.jane.builder.modules.dao.DDLRepository;
import com.jane.builder.modules.dao.DictionaryRepository;
import com.jane.builder.modules.dao.ModuleRepository;
import com.jane.builder.modules.dao.StepRepository;
import com.jane.builder.modules.dao.VersionRepository;
import com.jane.builder.modules.dao.version.ApiVersionRepository;
import com.jane.builder.modules.dao.version.CommonItemVersionRepository;
import com.jane.builder.modules.dao.version.CommonVersionRepository;
import com.jane.builder.modules.dao.version.DictionaryVersionRepository;
import com.jane.builder.modules.dao.version.ModuleVersionRepository;
import com.jane.builder.modules.dao.version.StepVersionRepository;
import com.jane.builder.modules.model.ApiModel;
import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;
import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.model.ModuleModel;
import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.model.VersionModel;
import com.jane.builder.modules.model.version.ApiVersionModel;
import com.jane.builder.modules.model.version.CommonItemVersionModel;
import com.jane.builder.modules.model.version.CommonVersionModel;
import com.jane.builder.modules.model.version.DictionaryVersionModel;
import com.jane.builder.modules.model.version.ModuleVersionModel;
import com.jane.builder.modules.model.version.StepVersionModel;

@Service
public class VersionService {

	@Autowired
	private VersionRepository versionRepository;
	
	@Autowired
	private DDLRepository ddlRepository;
	
	@Autowired
	private ApiVersionRepository apiVersionRepository;
	
	@Autowired
	private ApiRepository apiRepository;
	
	@Autowired
	private CommonItemVersionRepository commonItemVersionRepository;
	
	@Autowired
	private CommonVersionRepository commonVersionRepository;
	
	@Autowired
	private CommonItemRepository commonItemRepository;
	
	@Autowired
	private CommonRepository commonRepository;
	
	@Autowired
	private DictionaryVersionRepository dictionaryVersionRepository;
	
	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	@Autowired
	private ModuleVersionRepository moduleVersionRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private StepVersionRepository stepVersionRepository;
	
	@Autowired
	private StepRepository stepRepository;
	
	public List<VersionModel> getVersions(){
		return versionRepository.findAll();
	}
	
	public VersionModel getVersion(Integer versionId){
		Optional<VersionModel> optional = versionRepository.findById(versionId);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
	
	public VersionModel saveVersionModel(VersionModel versionModel, Operator operator) {
		Integer last = ddlRepository.findLastId();
		versionModel.setDdlId(last);
		operator.create(versionModel);
		versionModel = versionRepository.save(versionModel);
		return versionModel;
	}
	
	@Transactional
	public VersionModel modifyVersionModel(VersionModel versionModel, Operator operator) {
		VersionModel old = versionRepository.getOne(versionModel.getVersionId());
		old.setRemark(versionModel.getRemark());
		old.setUsable(versionModel.getUsable());
		operator.update(old);
		old = versionRepository.save(old);
		return old;
	}
	
	@Transactional
	public VersionModel exportVersion(VersionModel versionModel, Operator operator) {
		versionModel = saveVersionModel(versionModel, operator);
		Integer version = versionModel.getVersionId();
		copyApi(version);
		copyCommon(version);
		copyCommonItem(version);
		copyDictionary(version);
		copyModule(version);
		copyStep(version);
		return versionModel;
	}
	
	private void copyApi(Integer version) {
		List<ApiModel> list = apiRepository.findAll();
		List<ApiVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			ApiVersionModel nModel = new ApiVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		apiVersionRepository.saveAll(nList);
	}
	
	private void copyCommon(Integer version) {
		List<CommonModel> list = commonRepository.findAll();
		List<CommonVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			CommonVersionModel nModel = new CommonVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		commonVersionRepository.saveAll(nList);
	}
	
	private void copyCommonItem(Integer version) {
		List<CommonItemModel> list = commonItemRepository.findAll();
		List<CommonItemVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			CommonItemVersionModel nModel = new CommonItemVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		commonItemVersionRepository.saveAll(nList);
	}
	
	private void copyDictionary(Integer version) {
		List<DictionaryModel> list = dictionaryRepository.findAll();
		List<DictionaryVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			DictionaryVersionModel nModel = new DictionaryVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		dictionaryVersionRepository.saveAll(nList);
	}
	
	private void copyModule(Integer version) {
		List<ModuleModel> list = moduleRepository.findAll();
		List<ModuleVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			ModuleVersionModel nModel = new ModuleVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		moduleVersionRepository.saveAll(nList);
	}
	
	private void copyStep(Integer version) {
		List<StepModel> list = stepRepository.findAll();
		List<StepVersionModel> nList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			StepVersionModel nModel = new StepVersionModel();
			BeanUtils.copyProperties(list.get(i), nModel);
			nModel.setVersion(version);
			nList.add(nModel);
		}
		stepVersionRepository.saveAll(nList);
	}
}
