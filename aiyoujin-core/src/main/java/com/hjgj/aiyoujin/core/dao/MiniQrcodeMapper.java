package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.MiniQrcode;
import com.hjgj.aiyoujin.core.model.MiniQrcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MiniQrcodeMapper {
    int countByExample(MiniQrcodeExample example);

    int deleteByExample(MiniQrcodeExample example);

    int deleteByPrimaryKey(String id);

    int insert(MiniQrcode record);

    int insertSelective(MiniQrcode record);

    MiniQrcode selectOneByExample(MiniQrcodeExample example);

    List<MiniQrcode> selectByExample(MiniQrcodeExample example);

    MiniQrcode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MiniQrcode record, @Param("example") MiniQrcodeExample example);

    int updateByExample(@Param("record") MiniQrcode record, @Param("example") MiniQrcodeExample example);

    int updateByPrimaryKeySelective(MiniQrcode record);

    int updateByPrimaryKey(MiniQrcode record);
}