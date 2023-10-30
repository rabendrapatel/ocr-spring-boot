package com.source.dto.master.general;

import java.util.Set;

import com.source.entity.general.MenuLvl1;

import lombok.Data;

@Data
public class MenuListDto {
    
	private Long menuId;
	private String link;
	private String name;
	private String icon;
	private Long sortId;
	private String create;
	private String export;
	private String modify;
	private String print;
	private String view;
	private int status;
	private String delete;
	private String authName;
	private Long moduleId;
	private int menuType;
	private Set<MenuLvl1> subMenuLvlOne;
	
}
