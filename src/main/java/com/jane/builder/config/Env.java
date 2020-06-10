package com.jane.builder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Env {

	@Value("${customer.db-type}")
	private String dbType;
	
}
