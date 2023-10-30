package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.Menu;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
