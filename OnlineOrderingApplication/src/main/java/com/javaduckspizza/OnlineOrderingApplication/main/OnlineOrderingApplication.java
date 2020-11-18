package com.javaduckspizza.OnlineOrderingApplication.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javaduckspizza.OnlineOrderingApplication.common.TypesCache;

@SpringBootApplication
public class OnlineOrderingApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineOrderingApplication.class, args);
//		initCache();
	}

//	protected static void initCache() {
//		TypesCache.getActiveTypesByCategory("SIZE");
//		TypesCache.getActiveTypesByCategory("CRST");
//		TypesCache.getActiveTypesByCategory("SAUC");
//		TypesCache.getActiveTypesByCategory("CHES");
//		TypesCache.getActiveTypesByCategory("TOPP");
//	}
}