package vn.vccb.mssurveykpi.enity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "PORTAL_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = "ROLE_CODE"))
public class PortalRole implements Serializable {

    private static final long serialVersionUID = 1629971073632246084L;

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_ID_SEQ")
    @SequenceGenerator(name = "ROLE_ID_SEQ", sequenceName = "PORTAL_ROLE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_NAME", length = 100)
    private String roleName;

    @Column(name = "REMARK")
    private String description;

    @Column(name = "ROLE_KEYCLOAK_ID")
    private String keycloakId;

    @ManyToOne
    @JoinColumn(name = "SYSTEM_ID")
    @Where(clause = "status IS NOT NULL")
    private PortalSystem system;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "listRole")
    @Where(clause = "status IS NOT NULL")
    private List<PortalGroup> listGroup;

    @Column(name = "STATUS", length = 1)
    private String status;
}