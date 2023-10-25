package com.source.entity.master.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.source.entity.general.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tbl_master_user_password_reste_link")
@EqualsAndHashCode(callSuper=false)
public class UserPasswordResetLink extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fld_reset_id")
    private Long resetId;

	@Lob
    @Column(name = "fld_token")
    private String token;
    
    @Lob
    @Column(name = "fld_link")
    private String link;
    
    
    @Column(name = "fld_is_reset")
    private String isPasswordReset="No";
}
