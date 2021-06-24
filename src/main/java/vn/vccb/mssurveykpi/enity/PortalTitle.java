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
@Table(name = "PORTAL_TITLE", uniqueConstraints = @UniqueConstraint(columnNames = "TITLE_CODE"))
public class PortalTitle implements Serializable {

    private static final long serialVersionUID = 6616656988612358283L;

    @Id
    @Column(name = "TITLE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITLE_ID_SEQ")
    @SequenceGenerator(name = "TITLE_ID_SEQ", sequenceName = "PORTAL_TITLE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "TITLE_CODE")
    private String titleCode;

    @Column(name = "TITLE_NAME", length = 100)
    private String titleName;

    @Column(name = "CONTACT_PRIORITY")
    private Long contactPriority;

    @Column(name = "STATUS", length = 1)
    private String status;
}