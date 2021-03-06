package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.ProductPictureMapper;
import com.hjgj.aiyoujin.core.model.ProductPicture;
import com.hjgj.aiyoujin.core.model.ProductPictureExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lin on 2017/9/18.
 */
@Service
public class ProductPictureService extends BaseService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductPictureMapper productPictureMapper;

    public List<ProductPicture> queryProductPicture(String productId) {
        ProductPictureExample example = new ProductPictureExample();
        ProductPictureExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        List<ProductPicture> data = productPictureMapper.selectByExample(example);
        return data;
    }
    
    public List<ProductPicture> queryProductPicture(String productId,Integer type) {
    	ProductPictureExample example = new ProductPictureExample();
    	ProductPictureExample.Criteria criteria = example.createCriteria();
    	criteria.andProductIdEqualTo(productId);
    	criteria.andTypeEqualTo(type);
    	List<ProductPicture> data = productPictureMapper.selectByExample(example);
    	return data;
    }

    public ProductPicture queryProductPictureById(String id) {
        return productPictureMapper.selectByPrimaryKey(id);
    }

    public int delProductPicture(List<String> ids) {
        if (ids == null) {
            return 0;
        }
        for (String id : ids) {
            productPictureMapper.deleteByPrimaryKey(id);
        }
        return ids.size();
    }

    public int addOrUpdateProductPic(ProductPicture productPicture) {
        if (StringUtils.isBlank(productPicture.getId())) {
            productPicture.setId(UUIDGenerator.generate());
            return productPictureMapper.insert(productPicture);
        } else {
            return productPictureMapper.updateByPrimaryKey(productPicture);
        }
    }
}
