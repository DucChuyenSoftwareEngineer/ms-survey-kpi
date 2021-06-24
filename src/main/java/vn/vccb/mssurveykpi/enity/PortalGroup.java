package vn.vccb.mssurveykpi.enity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "PORTAL_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "GROUP_NAME"))
public class PortalGroup implements Serializable {

    private static final long serialVersionUID = 488095923303266588L;

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PORTAL_GROUP_ID_SEQ")
    @SequenceGenerator(name = "PORTAL_GROUP_ID_SEQ", sequenceName = "PORTAL_GROUP_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "GROUP_NAME")
    private String groupCode;

    @Column(name = "GROUP_DESCRIPTION")
    private String groupName;

    @Column(name = "NOTES")
    private String description;

    @Column(name = "GROUP_KEYCLOAK_ID")
    private String keycloakId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PORTAL_GROUP_ROLE",
            joinColumns = {@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", nullable = false, updatable = false)})
    @Where(clause = "status IS NOT NULL")
    private List<PortalRole> listRole;

    @Column(name = "STATUS", length = 1)
    private String status;
}
