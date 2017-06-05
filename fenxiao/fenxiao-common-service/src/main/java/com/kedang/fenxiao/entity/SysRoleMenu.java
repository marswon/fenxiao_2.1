package com.kedang.fenxiao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/** 
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月29日 下午5:52:26   
 */
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu extends IdEntity{
	
	public SysRoleMenu(){
		super();
	}

	public SysRoleMenu(
		Long id
	){
		this.id = id;
	}
    

    @Column(name="role_id") 	 
	@Max(9223372036854775807L)
	private Long roleId;
    

    @ManyToOne
   	@NotFound(action=NotFoundAction.IGNORE)
   	@JoinColumn(name="menu_id")
	private SysMenuEntity sysmenu;
    
    

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		return "SysRoleMenu [roleId=" + roleId + ", sysmenu=" + sysmenu + "]";
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public SysMenuEntity getSysmenu() {
		return sysmenu;
	}

	public void setSysmenu(SysMenuEntity sysmenu) {
		this.sysmenu = sysmenu;
	}
	
	
    
    


	
    
}
