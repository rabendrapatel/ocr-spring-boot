package com.source.entity.general;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tbl_master_setting")
@EqualsAndHashCode(callSuper=false)
public class SettingsMaster extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fld_setting_id")
    private Long settingId;

	@Lob
    @Column(name = "fld_gen_username_by")
    private String genUserNameBy;
    
}
