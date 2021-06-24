package vn.vccb.mssurveykpi.enity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PORTAL_SYSTEM", uniqueConstraints = @UniqueConstraint(columnNames = "SYSTEM_CODE"))
public class PortalSystem implements Serializable {

    private static final long serialVersionUID = 581944354703200779L;

    @Id
    @Column(name = "SYSTEM_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_ID_SEQ")
    @SequenceGenerator(name = "SYSTEM_ID_SEQ", sequenceName = "PORTAL_SYSTEM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "SYSTEM_CODE")
    private String systemCode;

    @Column(name = "SYSTEM_NAME", length = 50)
    private String systemName;

    @Column(name = "URL", length = 500)
    private String url;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "ICON_NEW")
    private String iconNew;

    @Column(name = "ICON_MINE_TYPE")
    private String iconMineType;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    @Where(clause = "status IS NOT NULL")
    private PortalSystemGroup group;

    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;

    @Column(name = "KEYCLOAK_CLIENT_ID")
    private String keycloakClientId;

    @Column(name = "STATUS", length = 1)
    private String status;
}
