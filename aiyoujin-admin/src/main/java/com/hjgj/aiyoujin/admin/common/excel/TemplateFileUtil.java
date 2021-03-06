package com.hjgj.aiyoujin.admin.common.excel;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;

public class TemplateFileUtil {
    public static InputStream getTemplates(String tempName) {
        InputStream is = null;
        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = patternResolver.getResources("classpath:templates/excel/" + tempName);
            if (resources != null && resources.length > 0) {
                is = resources[0].getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
