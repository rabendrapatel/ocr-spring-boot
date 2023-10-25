package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.SettingsMaster;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SettingsMasterRepository extends JpaRepository<SettingsMaster, Long> {

	Optional<SettingsMaster> findByCompanyId(Long companyId);

}
