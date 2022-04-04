package vn.com.multiplechoice;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;

import vn.com.multiplechoice.MultipleChoiceApplication.CustomGenerator;

@SpringBootApplication
@ComponentScan(nameGenerator = CustomGenerator.class)
public class MultipleChoiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MultipleChoiceApplication.class, args);
	}

	public static class CustomGenerator extends AnnotationBeanNameGenerator {
		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}

}
