package jcj.application.bigs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jcj.application.bigs.util.BigsWeather;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(Application.class, args);	
	    
		
	    BigsWeather.weathertTest(new Date());
	}

}
