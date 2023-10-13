package edu.uclm.esi.ds.webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication 
@ServletComponentScan

public class Lanzador {
	public static void main(String [] args) {
		SpringApplication.run(Lanzador.class, args);
	}
	
}
