package com.kedang.fenxiao.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Page;

public class Pager{
	
	private int page = 0;
	private int size = 20;
	private long totalCounts = 0;
	private int totalPages = 0;
	private Object rows;
	private Object row;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page>=0){
			this.page = page;
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		if(size>0){
			this.size = size;
		}
	}
	public long getTotalCounts() {
		return totalCounts;
	}
	public void setTotalCounts(long totalCounts) {
		this.totalCounts = totalCounts;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	public Object getRow() {
		return row;
	}
	public void setRow(Object row) {
		this.row = row;
	}
	public void setPageable(@SuppressWarnings("rawtypes") Page page){
		this.page=page.getNumber();
		this.size=page.getSize();
		this.totalCounts=page.getTotalElements();
		this.totalPages=page.getTotalPages();
		this.rows=page.getContent();
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("page",getPage())
			.append("size",getSize())
			.append("totalCounts",getTotalCounts())
			.append("totalPages",getTotalPages())
			.append("rows",getRows())
			.append("row",getRow())
			.toString();
	}
}
