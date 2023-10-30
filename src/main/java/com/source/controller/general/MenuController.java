package com.source.controller.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.source.dto.master.general.MenuListDto;
import com.source.response.ResponseRes;
import com.source.service.master.general.MenuService;

@RestController
@RequestMapping(value = "/api/master/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	@GetMapping("/all-menu-list")
	public ResponseEntity<?> getAllMenuList() {
		try {
			List<MenuListDto> list = menuService.getAllMenuList();

			if (!list.isEmpty()) {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "Record found", list));
			} else {
				return ResponseEntity
						.ok(new ResponseRes<>(HttpStatus.OK.value(), HttpStatus.OK.name(), "No record found", list));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseRes<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error " + e.getMessage()));
		}
	}
}
