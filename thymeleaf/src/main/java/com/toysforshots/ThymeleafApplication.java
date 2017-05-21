package com.toysforshots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class ThymeleafApplication {

    /*
    * Con este bean estoy haciendo que el error 404 sea redirigido a la vista 404, en vez de ser tratado por el
    * CustomErrorController. Sinceramente, prefiero usar el customerrorcontroller para gestionarlos todos y redigir en vez
    * de usar este bean pero para el 404 no esta mal
    * */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return ( container -> {
            ErrorPage custom404Page = new ErrorPage(HttpStatus.NOT_FOUND,"/404");
            container.addErrorPages(custom404Page);
        });
    }
    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args);
    }
}
