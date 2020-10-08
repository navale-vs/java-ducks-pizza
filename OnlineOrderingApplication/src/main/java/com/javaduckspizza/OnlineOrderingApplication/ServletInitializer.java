package com.javaduckspizza.OnlineOrderingApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.javaduckspizza.OnlineOrderingApplication.main.OnlineOrderingApplication;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OnlineOrderingApplication.class);
	}

}
