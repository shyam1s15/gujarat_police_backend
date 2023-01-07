package com.shyam.gujarat_police;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class Student extends Exception { }

class Hostelller extends Student {}

@SpringBootApplication
public class GujaratPoliceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GujaratPoliceApplication.class, args);
	}

}
