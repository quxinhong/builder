package com.jane.builder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jane.builder.common.util.HttpUtil;

@Component
public class InitRunner implements ApplicationRunner {

    @Autowired
    private ServerConfig ServerConfig;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        HttpUtil httpUtil = HttpUtil.init();
        httpUtil.get("http://localhost:"+ServerConfig.getServerPort()+"/builder/init/initCommon");
    }
}
