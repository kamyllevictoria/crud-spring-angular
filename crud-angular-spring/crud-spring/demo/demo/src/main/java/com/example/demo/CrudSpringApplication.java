package com.example.demo;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular with Spring");
			c.setCategory("Frontend");
			courseRepository.save(c); //registro no banco de dados parte 1

			Course c2 = new Course();
			c2.setName("React Fundamentals");
			c2.setCategory("Frontend");
			courseRepository.save(c2);

			Course c3 = new Course();
			c3.setName("Spring Boot API");
			c3.setCategory("Backend");
			courseRepository.save(c3);

			Course c4 = new Course();
			c4.setName("Python for Data Science");
			c4.setCategory("Data");
			courseRepository.save(c4);

			System.out.println("Database initialized with sample courses");
		};
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}

}







