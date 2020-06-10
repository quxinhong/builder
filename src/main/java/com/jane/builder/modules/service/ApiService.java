package com.jane.builder.modules.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.jane.builder.common.constant.DicConsts;
import com.jane.builder.common.standard.Operator;
import com.jane.builder.common.util.SqlX;
import com.jane.builder.modules.SqlExecutor;
import com.jane.builder.modules.dao.ApiRepository;
import com.jane.builder.modules.dao.DictionaryRepository;
import com.jane.builder.modules.dao.StepRepository;
import com.jane.builder.modules.form.DictionaryForm;
import com.jane.builder.modules.form.QueryParamForm;
import com.jane.builder.modules.model.ApiModel;
import com.jane.builder.modules.model.ApiPK;
import com.jane.builder.modules.model.DictionaryModel;
import com.jane.builder.modules.model.StepModel;
import com.jane.builder.modules.model.StepPK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private ApiRepository apiRepository;

	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	@Autowired
	private SqlExecutor sqlExecutor;
	
	public List<DictionaryModel> generateOutDictionarys(QueryParamForm queryParam, Operator operator) throws SQLException{
		String moduleNo = queryParam.getModuleNo();
		String apiNo = queryParam.getApiNo();
		List<StepModel> steps = getSteps(moduleNo, apiNo);
		DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setModuleNo(moduleNo);
        dictionaryModel.setApiNo(apiNo);
        dictionaryModel.setInOut(DicConsts.IN);
        Example<DictionaryModel> example = Example.of(dictionaryModel);
		List<DictionaryModel> dictionarys = dictionaryRepository.findAll(example);
		Map<String, DictionaryModel> dictionaryMap = new HashMap<>();
		Map<String, Object> keyParam = new HashMap<>();
		for(int i=0; i<dictionarys.size(); i++){
			dictionaryMap.put(dictionarys.get(i).getFieldName(), dictionarys.get(i));
		}
		Map<String, List<Map<String, Object>>> params = sqlExecutor.getDefParam(steps, dictionaryMap);
		Map<String, List<Map<String, Object>>> result = null;
		
		result = sqlExecutor.getMateDataAndSetKeyMap(steps, params, keyParam);
		
		String outJson = JSON.toJSONString(result, true);
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		Optional<ApiModel> opt = findApiById(apiPK);
		ApiModel apiModel = opt.get();
		apiModel.setOutJson(outJson);
		operator.update(apiModel);
		saveApi(apiModel);
		return getDefaultDictionary(keyParam.keySet(), moduleNo, apiNo, DicConsts.OUT);
	}
	
	public List<DictionaryModel> getDefaultDictionary(Collection<String> coll, String moduleNo, String apiNo, String inOut){
		List<DictionaryModel> res = new ArrayList<>(coll.size());
		for (String fieldName : coll) {
			DictionaryModel dic = new DictionaryModel();
			dic.setFieldName(fieldName);
			Example<DictionaryModel> example = Example.of(dic);
			List<DictionaryModel> list = dictionaryRepository.findAll(example);
			DictionaryModel dic0 = null, dic1 = null, dic2 = null;
			for(int i=0; i<list.size(); i++) {
				DictionaryModel item = list.get(i);
				if(item.getModuleNo().equals(moduleNo)) {
					dic0 = item;
					if(item.getApiNo().equals(apiNo)) {
						dic1 = item;
						if(item.getInOut().equals(inOut)) {
							dic2 = item;
							break;
						}
					}
				}
			}
			DictionaryModel addDic = dic2;
			if(addDic==null) {
				addDic = dic1;
			}
			if(addDic==null) {
				addDic = dic0;
			}
			if(addDic==null) {
				addDic = dic;
			}
			res.add(addDic);
		}
		return res;
	}
	
	@Transactional
	public void saveDictionarys(String inOut, DictionaryForm dictionaryForm, Operator operator) {
		ApiModel apiModel = dictionaryForm.getApi();
		String moduleNo = apiModel.getModuleNo();
		String apiNo = apiModel.getApiNo();
		
		DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setModuleNo(moduleNo);
        dictionaryModel.setApiNo(apiNo);
        dictionaryModel.setInOut(inOut);
        Example<DictionaryModel> example = Example.of(dictionaryModel);
		
		List<DictionaryModel> list = dictionaryRepository.findAll(example);
		dictionaryRepository.deleteAll(list);
		
		List<DictionaryModel> dictionarys = dictionaryForm.getDictionarys();
		Map<String, DictionaryModel> dictionaryMap = new HashMap<>(dictionarys.size());
		for(int i=0; i<dictionarys.size(); i++) {
			DictionaryModel dictionary = dictionarys.get(i);
			dictionary.setModuleNo(moduleNo);
			dictionary.setApiNo(apiNo);
			dictionary.setInOut(inOut);
			dictionaryMap.put(dictionary.getFieldName(), dictionary);
		}
		dictionaryRepository.saveAll(dictionarys);

		if(DicConsts.IN.equals(inOut)){
			List<StepModel> steps = getSteps(moduleNo, apiNo);
			Map<String, Object> jsonMap = new HashMap<>();
			for(int i=0; i<steps.size(); i++) {
				StepModel stepModel = steps.get(i);
				Map<String, Object> map = SqlX.searchParamsMap(stepModel.getScript());
				System.out.println(JSON.toJSONString(map));
				if(!map.isEmpty()) {
					for (String key : map.keySet()) {
						Object value = dictionaryMap.get(key).getExample();
						if(value==null) {
							value = "";
						}
						map.put(key, value);
					}
					jsonMap.put(stepModel.getStepNo(), new Object[] {map});
				}
			}
			String json = JSON.toJSONString(jsonMap, true);
			ApiPK apiPK = new ApiPK();
			apiPK.setModuleNo(moduleNo);
			apiPK.setApiNo(apiNo);
			ApiModel old = getApiById(apiPK);
			old.setInJson(json);
			operator.update(old);
			saveApi(old);
		}
	}
	
	public boolean existsApiById(ApiPK apiPK) {
		return apiRepository.existsById(apiPK);
	}
	
	public boolean existsApiByExample(Example<ApiModel> example) {
		return apiRepository.exists(example);
	}
	
	public ApiModel saveApi(ApiModel apiModel) {
		return apiRepository.save(apiModel);
	}
	
	public ApiModel getApiById(ApiPK apiPK) {
		return apiRepository.getOne(apiPK);
	}
	
	public Integer getStepCountByExample(Example<StepModel> example) {
		return Integer.valueOf(String.valueOf(stepRepository.count(example)));
	}
	
	public StepModel saveStep(StepModel stepModel) {
		return stepRepository.save(stepModel);
	}
	
	public Optional<ApiModel> findApiById(ApiPK apiPK){
		return apiRepository.findById(apiPK);
	}
	
	public Optional<StepModel> findStepById(StepPK stepPK){
		return stepRepository.findById(stepPK);
	}
	
	public List<ApiModel> getApisByExample(Example<ApiModel> example){
		return apiRepository.findAll(example);
	}
	
	public Integer findMaxStepIdx(String moduleNo, String apiNo) {
		return stepRepository.findMaxIdx(moduleNo, apiNo);
	}
	
	public List<StepModel> getSteps(String moduleNo, String apiNo){
		StepModel stepModel = new StepModel();
		stepModel.setModuleNo(moduleNo);
		stepModel.setApiNo(apiNo);
		Example<StepModel> example = Example.of(stepModel);
		Sort sort = Sort.by("idx");
		return stepRepository.findAll(example, sort);
	}
	
	@Transactional
	public void delApi(String moduleNo, String apiNo) {
		DictionaryModel dictionaryModel = new DictionaryModel();
		dictionaryModel.setApiNo(apiNo);
		dictionaryModel.setModuleNo(moduleNo);
		Example<DictionaryModel> example = Example.of(dictionaryModel);
		List<DictionaryModel> list = dictionaryRepository.findAll(example);
		dictionaryRepository.deleteAll(list);
		stepRepository.deleteByModuleNoAndApiNo(moduleNo, apiNo);
		ApiPK apiPK = new ApiPK();
		apiPK.setModuleNo(moduleNo);
		apiPK.setApiNo(apiNo);
		apiRepository.deleteById(apiPK);
	}
	
	@Transactional
	public void delStep(String moduleNo, String apiNo, Integer idx) {
		StepPK stepPK = new StepPK();
		stepPK.setModuleNo(moduleNo);
		stepPK.setApiNo(apiNo);
		stepPK.setIdx(idx);
		stepRepository.deleteById(stepPK);
	}

	public List<DictionaryModel> getDictionarys(String moduleNo, String apiNo, String inOut){
		DictionaryModel dictionaryModel = new DictionaryModel();
		dictionaryModel.setModuleNo(moduleNo);
		dictionaryModel.setApiNo(apiNo);
		dictionaryModel.setInOut(inOut);
		Example<DictionaryModel> example = Example.of(dictionaryModel);
		return dictionaryRepository.findAll(example);
	}
}
