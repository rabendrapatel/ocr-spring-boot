package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.MenuLvl2;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MenuLvl2Repository extends JpaRepository<MenuLvl2, Long> {

}
