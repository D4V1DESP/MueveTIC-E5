package edu.uclm.esi.ds.webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/*
 * Clase lanzador que inicia la apliacion 
 */
@SpringBootApplication 
@ServletComponentScan
@EnableMongoRepositories
public class Lanzador  extends SpringBootServletInitializer{
	public static void main(String [] args) {
		SpringApplication.run(Lanzador.class, args);
	}
	
}