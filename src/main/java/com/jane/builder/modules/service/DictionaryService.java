package com.jane.builder.modules.service;

import com.jane.builder.modules.dao.DictionaryRepository;
import com.jane.builder.modules.model.DictionaryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public List<DictionaryModel> getDictionarys(String moduleNo, String apiNo, String inOut){
        DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setModuleNo(moduleNo);
        dictionaryModel.setApiNo(apiNo);
        dictionaryModel.setInOut(inOut);
        Example<DictionaryModel> example = Example.of(dictionaryModel);
        return getDictionarysByExample(example);
    }
    
    public List<DictionaryModel> getDictionarysByExample(Example<DictionaryModel> example){
    	return dictionaryRepository.findAll(example);
    }
    
    public void deleteAll(List<DictionaryModel> list) {
    	dictionaryRepository.deleteAll(list);
    }
    
    public List<DictionaryModel> saveAll(List<DictionaryModel> dictionarys) {
    	return dictionaryRepository.saveAll(dictionarys);
    }
}
