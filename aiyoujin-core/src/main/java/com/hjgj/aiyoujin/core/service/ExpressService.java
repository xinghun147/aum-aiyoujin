package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.ExpressMapper;
import com.hjgj.aiyoujin.core.model.Express;
import com.hjgj.aiyoujin.core.model.ExpressExample;
import com.hjgj.aiyoujin.core.model.ExpressExample.Criteria;
import com.hjgj.aiyoujin.core.vo.ExpressVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ExpressService
 * @Description: 快递
 */
@Service
public class ExpressService extends BaseService {

    protected final Logger LOGGER = LoggerFactory.getLogger(super.getClass().getName());

    @Autowired
    private ExpressMapper expressMapper;

    /**
     * 详情
     *
     * @param vo
     * @return
     */
    public Express queryOneExpress(ExpressVo vo) {
        List<Express> list = queryExpressList(vo);
        if (null == list || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 列表
     *
     * @param vo
     * @return
     */
    public List<Express> queryExpressList(ExpressVo vo) {
        ExpressExample example = new ExpressExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(vo.getCode())) {
            criteria.andCodeEqualTo(vo.getCode());
        }
        if (StringUtils.isNotBlank(vo.getName())) {
            criteria.andNameEqualTo(vo.getName());
        }
        return expressMapper.selectByExample(example);
    }

    /**
     * 获取快递公司名称列表
     */
    public List<Express> findExpressName() {
        return expressMapper.findExpressName();
    }
}