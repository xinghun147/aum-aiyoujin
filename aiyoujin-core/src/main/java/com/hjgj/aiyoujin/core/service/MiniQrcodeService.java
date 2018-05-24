package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.MiniQrcodeMapper;
import com.hjgj.aiyoujin.core.model.MiniQrcode;
import com.hjgj.aiyoujin.core.model.MiniQrcodeExample;
import com.hjgj.aiyoujin.core.model.UserAddress;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.vo.AddressVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MiniQrcodeService extends BaseService {

    @Autowired
    private MiniQrcodeMapper miniQrcodeMapper;


    public int insert(MiniQrcode miniQrcode) {
        miniQrcode.setId(UUIDGenerator.generate());
        miniQrcode.setCreateTime(new Date());
        return miniQrcodeMapper.insertSelective(miniQrcode);
    }



    public Page<MiniQrcode> queryPageMiniQrcode(MiniQrcode miniQrcode, Integer pageNum, Integer pageSize) {
        Page<MiniQrcode> page = new Page<MiniQrcode>(pageNum, pageSize, true);
        MiniQrcodeExample example = new MiniQrcodeExample();
        MiniQrcodeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(miniQrcode.getChannelName())) {
            criteria.andChannelNameEqualTo(miniQrcode.getChannelName());
        }
        example.setOrderByClause("create_time desc");
        int total = miniQrcodeMapper.countByExample(example);
        if (total > 0) {
            example.setLimitOffset(page.getStartRow());
            example.setLimitRows(pageSize);
            List<MiniQrcode> list = miniQrcodeMapper.selectByExample(example);
            page.setTotal(total);
            page.setList(list);
        }
        return page;
    }


    public int updateMiniQrcode(MiniQrcode miniQrcode) {
        miniQrcode.setUpdateTime(new Date());
        return miniQrcodeMapper.updateByPrimaryKeySelective(miniQrcode);
    }

    public MiniQrcode getMiniQrcodeById(String id) {
        MiniQrcode miniQrcode = miniQrcodeMapper.selectByPrimaryKey(id);
        return miniQrcode;
    }
}
