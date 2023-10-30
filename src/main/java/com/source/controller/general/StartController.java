package com.source.controller.general;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

	@GetMapping(value = "/")
	public String start() {
		return "Running successfully";
	}

}
