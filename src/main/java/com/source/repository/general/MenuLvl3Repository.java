package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.MenuLvl3;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MenuLvl3Repository extends JpaRepository<MenuLvl3, Long> {

}
