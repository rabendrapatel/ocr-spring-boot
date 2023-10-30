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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_general_menu_lvl_two")
public class MenuLvl2  {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fld_sub_menu_two_id")
	private Long subMenuTwoId;

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
	@Column(name = "fld_menu_lvl")
	private String menuLvl;

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

	@Basic
	@Column(name = "fld_delete")
	private String delete;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "fld_ref_id")
	private MenuLvl1 subMenuOneId;

	@OrderBy("sortId")
	@OneToMany(mappedBy = "subMenuTwoId", fetch = FetchType.LAZY)
	private Set<MenuLvl3> subMenuLvlThree =  new LinkedHashSet<>();

	@Column(name = "fld_status")
	private int status = 1;
}
