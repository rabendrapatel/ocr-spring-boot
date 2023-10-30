package com.source.entity.general;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_general_menu_lvl_three")
public class MenuLvl3 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fld_sub_menu_three_id")
	private Long subMenuThreeId;

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
	private Long menuLvl;

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
	private MenuLvl2 subMenuTwoId;

	@Column(name = "fld_status")
	private int status = 1;

	

}
