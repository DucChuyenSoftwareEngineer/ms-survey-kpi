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
import java.io.Serializable;

@Data
@Entity
@Table(name = "PORTAL_SYSTEM_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "GROUP_CODE"))
public class PortalSystemGroup implements Serializable {

    private static final long serialVersionUID = 495069885273963255L;

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_GROUP_ID_SEQ")
    @SequenceGenerator(name = "SYSTEM_GROUP_ID_SEQ", sequenceName = "PORTAL_GROUP_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "GROUP_CODE")
    private String groupCode;

    @Column(name = "GROUP_NAME", length = 100)
    private String groupName;

    @Column(name = "STATUS", length = 1)
    private String status;
}
