package com.ligen;

import com.ligen.mapper.prod.ProdUserMapper;
import com.ligen.mapper.test.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@RestController
public class SpringbootMultiDatasourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMultiDatasourceDemoApplication.class, args);
	}

	@Autowired
	ProdUserMapper prodUserMapper;

	@Autowired
	TestUserMapper testUserMapper;

	@PostConstruct
	public void init() {
		System.out.println(prodUserMapper.getCount());
		System.out.println(testUserMapper.getCount());
	}
}
