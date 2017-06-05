package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/** 
 * 菜单管理
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月23日 下午3:55:25   
 */
@Entity
@Table(name = "sys_menu")
public class SysMenuEntity implements Serializable {

	private static final long serialVersionUID = -3188062844023246119L;
	/**
	 * 菜单ID
	 */
//	@ManyToOne
//    @NotFound(action=NotFoundAction.IGNORE)
//    @JoinColumn(name="parent_id",insertable=false,updatable=false)
//    private SysMenuEntity parentMenu;
//	
//	public SysMenuEntity getParentMenu() {
//		return parentMenu;
//	}
//	public void setParentMenu(SysMenuEntity parentMenu) {
//		this.parentMenu = parentMenu;
//	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column(name = "parent_id")
	private Long parentId;
	
	
	/**
	 * 父菜单名称
	 */
	@Transient
	private String parentName;

	/**
	 * 菜单名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 菜单URL
	 */
	@Column(name = "url")
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@Column(name = "perms")
	private String perms;
	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	@Column(name = "type")
	private Integer type;
	/**
	 * 菜单图标
	 */
	@Column(name = "icon")
	private String icon;
	/**
	 * 排序
	 */
	@Column(name = "order_num")
	private Integer orderNum;
	/**
	 * 种类
	 */
	@Column(name = "sort")
	private String sort;

	/**
	 * ztree属性
	 */
	@Transient
	private Boolean open;
	
	@Transient
	private List<?> list;
		
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	/**
	 * 获取：父菜单ID，一级菜单为0
	 * @return Long
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：父菜单ID，一级菜单为0
	 * @param parentId 父菜单ID，一级菜单为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
}
