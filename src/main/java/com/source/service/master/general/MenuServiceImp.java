package com.source.service.master.general;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.dto.master.general.MenuListDto;
import com.source.entity.general.Menu;
import com.source.repository.general.MenuRepository;
import com.source.utill.MapperUtils;

@Service
public class MenuServiceImp implements MenuService {


	@Autowired
	MenuRepository menuRepository;


	@Override
	public List<MenuListDto> getAllMenuList() {
		ModelMapper m = MapperUtils.getInstance();
		
		List<Menu> list = menuRepository.findAll();
		List<MenuListDto> resAL = list.stream().map(elm -> {
			MenuListDto dto = m.map(elm, MenuListDto.class);
			return dto;
		}).collect(Collectors.toList());
		return resAL;
	}
}
