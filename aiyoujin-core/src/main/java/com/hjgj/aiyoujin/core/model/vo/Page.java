package com.hjgj.aiyoujin.core.model.vo;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**不进行count查询*/
    private static final int NO_SQL_COUNT = -1;
    /**进行count查询*/
    private static final int SQL_COUNT = 0;
    /**页码，从1开始*/
    private int pageNum;
    /**页面大小*/
    private int pageSize;
    /**起始行*/
    private int startRow;
    /**末行*/
    private int endRow;
    /**总数*/
    private long total;
    /**总页数*/
    private int pages;
    /**分页合理化*/
    private boolean reasonable;
    //结果集
    private List<T> list;

    public Page(){
        super();
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, SQL_COUNT);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT);
    }

    public Page(int pageNum, int pageSize, int total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        calculateStartAndEndRow();
    }


    public int getPages() {
        return pages;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        //分页合理化，针对不合理的页码自动处理
        this.pageNum = (reasonable && pageNum <= 0) ? 1 : pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (pageSize > 0) {
            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
        } else {
            pages = 0;
        }
        //分页合理化，针对不合理的页码自动处理
        if (reasonable && pageNum > pages) {
            pageNum = pages;
            calculateStartAndEndRow();
        }
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
        //分页合理化，针对不合理的页码自动处理
        if (this.reasonable && this.pageNum <= 0) {
            this.pageNum = 1;
            calculateStartAndEndRow();
        }
    }

    /**
     * 计算起止行号
     */
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
    }

    public boolean isCount() {
        return this.total > NO_SQL_COUNT;
    }
    

    public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
