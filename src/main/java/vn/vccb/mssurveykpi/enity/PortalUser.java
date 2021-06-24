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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "PORTAL_USER", uniqueConstraints = @UniqueConstraint(columnNames = "USER_KEYCLOAK_ID"))
public class PortalUser implements Serializable {

    private static final long serialVersionUID = 5102092054792908815L;

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "PORTAL_USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "MSNV")
    private String staffId;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    @Where(clause = "status IS NOT NULL")
    private PortalDepartment department;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    @Where(clause = "status IS NOT NULL")
    private PortalTitle title;

    @Column(name = "MANAGER")
    private String manager;

    @Column(name = "USER_KEYCLOAK_ID")
    private String userKeycloakId;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "AVATAR_MINE_TYPE")
    private String avatarMineType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PORTAL_USER_GROUP",
            joinColumns = {@JoinColumn(name = "USERNAME", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false)})
    @Where(clause = "status IS NOT NULL")
    private List<PortalGroup> listGroup;

    @Column(name = "ENABLED")
    private Boolean status;
}