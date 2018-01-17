package com.hjgj.aiyoujin.admin.common;

public class Constants {

    /**
     * 实时金价key
     */
    public static final String CURRENT_GOLD_PRICE_REDIS_KEY = "CURRENT_GOLD_PRICE";
    public static final String WORKDAYS = "workdays";//工作日
    public static final String HOLIDAYS = "holidays";//节假日
    public static final String WITHDRAW_SECOND_FEE = "WITHDRAW_SECOND_FEE";//提现第二笔收费

    public static final String ACT_SMRZ = "SMRZ";//实名认证活动编码
    public static final String ACT_BDYHK = "BDYHK";//绑定银行卡活动编码


    public static final String DICTIONARY_KEY = "DICTIONARY_";//数据字典key前缀


    public static final String TOPIC_EXCHANGE = "topicExchange";

    //账户处理消息QUEUE
    public static final String TRADE_TO_ACCOUNT_QUEUE = "trade.to.account.queue";


    public enum DelFlag {
        NO,//否
        YES//是
    }

    public enum DefaultFlag {
        NO,//否
        YES//是
    }

    public enum Readed {
        NO,//否
        YES//是
    }

    public enum BankType {
        JJK,//借记卡
        DJK//贷记卡
    }

    public enum MsgStatus {
        NO,//草稿
        YES//已发送
    }

    public enum FrozenStatus {
        NO,//未冻结
        YES//已冻结
    }

    public enum LockStatus {
        NO,//
        YES//
    }

    public enum RegularStauts {
        execution,//执行中
        complete,//已完成
        terminating//已终止
    }

    public enum RecordStauts {
        success,//成功
        failure,//失败
    }

    /**
     * 消息类型
     */
    public enum MsgType {
        goldNews {
            @Override
            public String getDesc() {
                return "新闻资讯";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        system {
            @Override
            public String getDesc() {
                return "系统公告";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        activity {
            @Override
            public String getDesc() {
                return "营销活动";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        goldBeanMature {
            @Override
            public String getDesc() {
                return "金豆收取";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        public abstract String getDesc();

        public abstract String getCode();
    }

    public static MsgType getMsgType(Integer code) {
        for (MsgType type : MsgType.values()) {
            if (type.ordinal() == code) {
                return type;
            }
        }
        return null;
    }


    /**
     * 产品状态
     */
    public enum ProdStatus {
        draft {
            @Override
            public String getDesc() {
                return "草稿未上架";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        putaway {
            @Override
            public String getDesc() {
                return "上架未开售";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        saling {
            @Override
            public String getDesc() {
                return "正在销售中";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        soldout {
            @Override
            public String getDesc() {
                return "售磬未下架";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        },
        withdraw {
            @Override
            public String getDesc() {
                return "下架";
            }

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        public abstract String getDesc();

        public abstract String getCode();
    }

    public static ProdStatus getProdStatus(Integer code) {
        for (ProdStatus stauts : ProdStatus.values()) {
            if (stauts.ordinal() == code) {
                return stauts;
            }
        }
        return null;
    }

    public enum prodPicType {
        thumb,//缩略图
        large,//大图
        middle//中图
    }

}
