package com.hjgj.aiyoujin.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderNotifyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitOffset = 0;

    protected Integer limitRows = 0;

    public OrderNotifyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitOffset(Integer limitOffset) {
        this.limitOffset=limitOffset;
    }

    public Integer getLimitOffset() {
        return limitOffset;
    }

    public void setLimitRows(Integer limitRows) {
        this.limitRows=limitRows;
    }

    public Integer getLimitRows() {
        return limitRows;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgIsNull() {
            addCriterion("notify_msg is null");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgIsNotNull() {
            addCriterion("notify_msg is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgEqualTo(String value) {
            addCriterion("notify_msg =", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgNotEqualTo(String value) {
            addCriterion("notify_msg <>", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgGreaterThan(String value) {
            addCriterion("notify_msg >", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgGreaterThanOrEqualTo(String value) {
            addCriterion("notify_msg >=", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgLessThan(String value) {
            addCriterion("notify_msg <", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgLessThanOrEqualTo(String value) {
            addCriterion("notify_msg <=", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgLike(String value) {
            addCriterion("notify_msg like", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgNotLike(String value) {
            addCriterion("notify_msg not like", value, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgIn(List<String> values) {
            addCriterion("notify_msg in", values, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgNotIn(List<String> values) {
            addCriterion("notify_msg not in", values, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgBetween(String value1, String value2) {
            addCriterion("notify_msg between", value1, value2, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifyMsgNotBetween(String value1, String value2) {
            addCriterion("notify_msg not between", value1, value2, "notifyMsg");
            return (Criteria) this;
        }

        public Criteria andNotifysIsNull() {
            addCriterion("notifys is null");
            return (Criteria) this;
        }

        public Criteria andNotifysIsNotNull() {
            addCriterion("notifys is not null");
            return (Criteria) this;
        }

        public Criteria andNotifysEqualTo(Integer value) {
            addCriterion("notifys =", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysNotEqualTo(Integer value) {
            addCriterion("notifys <>", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysGreaterThan(Integer value) {
            addCriterion("notifys >", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysGreaterThanOrEqualTo(Integer value) {
            addCriterion("notifys >=", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysLessThan(Integer value) {
            addCriterion("notifys <", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysLessThanOrEqualTo(Integer value) {
            addCriterion("notifys <=", value, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysIn(List<Integer> values) {
            addCriterion("notifys in", values, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysNotIn(List<Integer> values) {
            addCriterion("notifys not in", values, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysBetween(Integer value1, Integer value2) {
            addCriterion("notifys between", value1, value2, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifysNotBetween(Integer value1, Integer value2) {
            addCriterion("notifys not between", value1, value2, "notifys");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNull() {
            addCriterion("notify_time is null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIsNotNull() {
            addCriterion("notify_time is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeEqualTo(Date value) {
            addCriterion("notify_time =", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotEqualTo(Date value) {
            addCriterion("notify_time <>", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThan(Date value) {
            addCriterion("notify_time >", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("notify_time >=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThan(Date value) {
            addCriterion("notify_time <", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("notify_time <=", value, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeIn(List<Date> values) {
            addCriterion("notify_time in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotIn(List<Date> values) {
            addCriterion("notify_time not in", values, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeBetween(Date value1, Date value2) {
            addCriterion("notify_time between", value1, value2, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("notify_time not between", value1, value2, "notifyTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeIsNull() {
            addCriterion("notify_call_time is null");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeIsNotNull() {
            addCriterion("notify_call_time is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeEqualTo(Date value) {
            addCriterion("notify_call_time =", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeNotEqualTo(Date value) {
            addCriterion("notify_call_time <>", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeGreaterThan(Date value) {
            addCriterion("notify_call_time >", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("notify_call_time >=", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeLessThan(Date value) {
            addCriterion("notify_call_time <", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeLessThanOrEqualTo(Date value) {
            addCriterion("notify_call_time <=", value, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeIn(List<Date> values) {
            addCriterion("notify_call_time in", values, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeNotIn(List<Date> values) {
            addCriterion("notify_call_time not in", values, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeBetween(Date value1, Date value2) {
            addCriterion("notify_call_time between", value1, value2, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyCallTimeNotBetween(Date value1, Date value2) {
            addCriterion("notify_call_time not between", value1, value2, "notifyCallTime");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIsNull() {
            addCriterion("notify_state is null");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIsNotNull() {
            addCriterion("notify_state is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyStateEqualTo(Integer value) {
            addCriterion("notify_state =", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotEqualTo(Integer value) {
            addCriterion("notify_state <>", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateGreaterThan(Integer value) {
            addCriterion("notify_state >", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("notify_state >=", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateLessThan(Integer value) {
            addCriterion("notify_state <", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateLessThanOrEqualTo(Integer value) {
            addCriterion("notify_state <=", value, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateIn(List<Integer> values) {
            addCriterion("notify_state in", values, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotIn(List<Integer> values) {
            addCriterion("notify_state not in", values, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateBetween(Integer value1, Integer value2) {
            addCriterion("notify_state between", value1, value2, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyStateNotBetween(Integer value1, Integer value2) {
            addCriterion("notify_state not between", value1, value2, "notifyState");
            return (Criteria) this;
        }

        public Criteria andNotifyByIsNull() {
            addCriterion("notify_by is null");
            return (Criteria) this;
        }

        public Criteria andNotifyByIsNotNull() {
            addCriterion("notify_by is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyByEqualTo(String value) {
            addCriterion("notify_by =", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByNotEqualTo(String value) {
            addCriterion("notify_by <>", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByGreaterThan(String value) {
            addCriterion("notify_by >", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByGreaterThanOrEqualTo(String value) {
            addCriterion("notify_by >=", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByLessThan(String value) {
            addCriterion("notify_by <", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByLessThanOrEqualTo(String value) {
            addCriterion("notify_by <=", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByLike(String value) {
            addCriterion("notify_by like", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByNotLike(String value) {
            addCriterion("notify_by not like", value, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByIn(List<String> values) {
            addCriterion("notify_by in", values, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByNotIn(List<String> values) {
            addCriterion("notify_by not in", values, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByBetween(String value1, String value2) {
            addCriterion("notify_by between", value1, value2, "notifyBy");
            return (Criteria) this;
        }

        public Criteria andNotifyByNotBetween(String value1, String value2) {
            addCriterion("notify_by not between", value1, value2, "notifyBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}