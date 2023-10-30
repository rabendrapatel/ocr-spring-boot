package com.source.entity.general;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_general_menu")
public class Menu  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fld_menu_id")
	private Long menuId;

	@Basic
	@Column(name = "fld_link")
	private String link;

	@Basic
	@Column(name = "fld_name")
	private String name;

	@Basic
	@Column(name = "fld_icon")
	private String icon;

	@Basic
	@Column(name = "fld_sort_id")
	private Long sortId;

	@Basic
	@Column(name = "fld_create")
	private String create;

	@Basic
	@Column(name = "fld_export")
	private String export;

	@Basic
	@Column(name = "fld_modify")
	private String modify;

	@Basic
	@Column(name = "fld_print")
	private String print;

	@Basic
	@Column(name = "fld_view")
	private String view;

	@Column(name = "fld_status")
	private int status = 1;

	@Basic
	@Column(name = "fld_delete")
	private String delete;

	@Basic
	@Column(name = "fld_auth_name")
	private String authName;

	@Basic
	@Column(name = "fld_module_id")
	private Long moduleId;

	@Column(name = "fld_menu_type")
	private int menuType = 0;
	
	@OrderBy("sortId")
	@OneToMany(mappedBy = "menuId", fetch = FetchType.LAZY)
	private Set<MenuLvl1> subMenuLvlOne = new LinkedHashSet<>();
	
	
}
