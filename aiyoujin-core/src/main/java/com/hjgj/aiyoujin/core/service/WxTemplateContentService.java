package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.ContentTemplateMapper;
import com.hjgj.aiyoujin.core.model.ContentTemplate;
import com.hjgj.aiyoujin.core.model.ContentTemplateExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxTemplateContentService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContentTemplateMapper contentTemplateMapper;

    public int insertTemplate(ContentTemplate contentTemplate) {
        logger.info("new insert contentTemplate,the contentTemplate`s is:" + contentTemplate);
        int insert = contentTemplateMapper.insert(contentTemplate);
        logger.info("new insert contentTemplate,the result is:" + contentTemplate);
        return insert;
    }

    public ContentTemplate getTemplateContentByWxtempId(String tempId) {
        logger.info("select ContentTemplate,the ContentTemplate`s tempId is:" + tempId);
        ContentTemplateExample example = new ContentTemplateExample();
        ContentTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andWxtemplateIdEqualTo(tempId);
        List<ContentTemplate> contentTemplates = contentTemplateMapper.selectByExample(example);
        if (contentTemplates != null && contentTemplates.size() > 0) {
            logger.info("select ContentTemplate,the ContentTemplate`s size: "+contentTemplates.size());

            return contentTemplates.get(0);
        } else {
            logger.info("select ContentTemplate,the ContentTemplate`s size: 0");
            return null;
        }
    }

}
