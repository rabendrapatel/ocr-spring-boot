package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.MenuLvl1;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MenuLvl1Repository extends JpaRepository<MenuLvl1, Long> {

}
