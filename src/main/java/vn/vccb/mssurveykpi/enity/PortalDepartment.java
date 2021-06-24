package vn.vccb.mssurveykpi.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "PORTAL_DEPARTMENT", uniqueConstraints = @UniqueConstraint(columnNames = "DEPARTMENT_CODE"))
public class PortalDepartment {

    private static final long serialVersionUID = -812935241186505528L;

    @Id
    @Column(name = "DEPARTMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_ID_SEQ")
    @SequenceGenerator(name = "DEPARTMENT_ID_SEQ", sequenceName = "PORTAL_DEPARTMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    @Column(name = "DEPARTMENT_NAME", length = 100)
    private String departmentName;

    @Column(name = "DEPARTMENT_TYPE", length = 3)
    private String departmentType;

    @Column(name = "MANAGER")
    private String manager;
}
