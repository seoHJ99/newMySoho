package com.study.springboot.other;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import java.io.File;

public class TomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        //톰캣의 docBase 를 변경함
        factory.setDocumentRoot(new File("C:\\Users\\tjoeun\\Desktop\\mysoho-back\\newMySoho\\3.10\\src\\main\\resources\\static"));
    }
}
