package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.MessageTokenMapper;
import com.hjgj.aiyoujin.core.model.MessageToken;
import com.hjgj.aiyoujin.core.model.MessageTokenExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageTokenService {

    @Autowired
    private MessageTokenMapper messageTokenMapper;

    /**
     * 获取最新的Token
     * @param beforeTime
     * @param nowTime
     * @return
     */
    public MessageToken getLatestToken(Date beforeTime, Date nowTime) {
        MessageTokenExample example = new MessageTokenExample();
        MessageTokenExample.Criteria criteria = example.createCriteria();
        criteria.andTokenTypeEqualTo(3).andRequireDateLessThanOrEqualTo(nowTime).
                andErrCodeIsNull().andRequireDateGreaterThanOrEqualTo(beforeTime);
        List<MessageToken> messageTokens = messageTokenMapper.selectByExample(example);
        if (messageTokens != null && messageTokens.size() > 0) {
            return messageTokens.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增AccessToken
     * @param messageToken
     */
    public int insertLatestToken(MessageToken messageToken) {
        int insert = messageTokenMapper.insert(messageToken);
        return insert;
    }

    /**
     * 获取AccessToken
     * @param id
     * @return
     */
    public MessageToken getMessageTokenById(String id) {
        return messageTokenMapper.selectByPrimaryKey(id);
    }
}
