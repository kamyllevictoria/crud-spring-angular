package com.example.demo;

import com.example.demo.enums.Category;
import com.example.demo.enums.Status;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
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
	CommandLineRunner initDatabase (CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for(int i = 0; i < 20; i++){
				Course c = new Course();
				c.setName("Angular with Spring" + i);
				c.setCategory((Category.FRONTEND));
				c.setStatus(Status.ACTIVE);

				Lesson l = new Lesson();
				l.setName("Introdução");
				l.setYoutubeUrl("watch?v=12312");
				l.setCourse(c);
				c.getLessons().add(l);

				Lesson l2 = new Lesson();
				l2.setName("Angular");
				l2.setYoutubeUrl("watch?v=27878");
				l2.setCourse(c);
				c.getLessons().add(l2);

				courseRepository.save(c);
			}

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







