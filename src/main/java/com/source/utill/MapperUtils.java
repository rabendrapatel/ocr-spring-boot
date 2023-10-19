package com.source.utill;

import org.modelmapper.ModelMapper;

public class MapperUtils {
	
	public static ModelMapper getInstance() {
	  ModelMapper mapper = new ModelMapper();
      return mapper;
    }
	
	public static ModelMapper getInstance(String stretgy,boolean isNullAllow) {
		  ModelMapper mapper = new ModelMapper();
	      return mapper;
	}

}
