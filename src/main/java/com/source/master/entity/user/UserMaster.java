package com.source.master.entity.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.source.entity.BaseEntity;
import com.source.master.entity.company.CompanyMaster;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tbl_master_user")
@EqualsAndHashCode(callSuper=false)
public class UserMaster extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fld_user_id")
    private Long userId;

	@Lob
    @Column(name = "fld_user_name")
    private String userName;
    
    @Lob
    @Column(name = "fld_password")
    private String password;
    
    @Lob
    @Column(name = "fld_first_name")
    private String firstName;
    
    @Lob
    @Column(name = "fld_last_name")
    private String lastName;
    
    @Column(name = "fld_email")
    private String email;
    
    @Column(name = "fld_mobile_no")
    private String mobileNo;
    
    @Column(name = "fld_verifed_email")
    private String isEmailVerify="No";
    
    @Column(name = "fld_verifed_mob")
    private String isMobileVerify="No";
    
    @Column(name = "fld_status")
    private Integer status=0;
    
    @Column(name = "fld_role_id")
    private Long roleId;
    
    @Lob
    @Column(name = "fld_photo")
    private String photo;
    
    
    @Column(name = "fld_company_id")
    private Long companyId;
    
    @OneToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name="fld_company_id",referencedColumnName = "fld_company_id",insertable = false, updatable = false)
   	private CompanyMaster companyMaster;
    
}
