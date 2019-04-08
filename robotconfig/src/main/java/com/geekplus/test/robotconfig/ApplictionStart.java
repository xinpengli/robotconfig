package com.geekplus.test.robotconfig;



import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ApplictionStart {
	private  static  final Logger logger = LoggerFactory.getLogger(ApplictionStart.class);

	public static void main(String[] args) {
		SpringApplication.run(ApplictionStart.class, args);
		logger.info("start success");
	}


}
