package com.source.repository.general;

import org.springframework.stereotype.Repository;

import com.source.entity.general.Settings;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

	Optional<Settings> findByCompanyId(Long companyId);

}
