package ngSpring.demo;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@SpringBootApplication
public class AngularSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AngularSpringApplication.class, args);
    }

    // Swagger

    @Bean
    public Docket ngSpringApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ng-spring-boot-api")
                .apiInfo(apiInfo())
                .select()
                .paths(apiPaths())
                .build();
    }

    private Predicate<String> apiPaths() {
        return or(
                regex("/api/event.*"),
                regex("/api/login.*"),
                regex("/api/user.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Angular SpringBoot Demo API")
                .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum " +
                        "has been the industry's standard dummy text ever since the 1500s, when an unknown printer "
                        + "took a " +
                        "galley of type and scrambled it to make a type specimen book. It has survived not only five " +
                        "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                        "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum " +
                        "passages, and more recently with desktop publishing software like Aldus PageMaker including " +
                        "versions of Lorem Ipsum.")
                .termsOfServiceUrl("https://github.com/hypery2k/angular-spring-boot-sample")
                .contact("contact@martinreinhardt-online.de")
                .license("MIT")
                .licenseUrl("https://raw.githubusercontent.com/hypery2k/angular-spring-boot-sample/master/LICENSE")
                .version("0.2.0")
                .build();
    }

}
