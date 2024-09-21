package uz.pdp.task1.springframeworkjavaconfig.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {  // root web application context shu yerda bajariladi
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {  // servlet web application context shu yerda bajariladi
        return new Class[]{WebMVCConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {  // kerakli controller larga request ni uzatadi
        return new String[]{"/"};
    }
}
