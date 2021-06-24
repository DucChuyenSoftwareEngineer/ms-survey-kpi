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
import java.util.Date;

@Data
@Entity
@Table(name = "SURVEY_RATE_DETAIL")
public class SurveyRateDetail {

    private static final long serialVersionUID = 1629971073632246084L;

    @Id
    @Column(name = "RATE_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATE_DETAIL_ID_SEQ")
    @SequenceGenerator(name = "RATE_DETAIL_ID_SEQ", sequenceName = "SURVEY_RATE_DETAIL_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private PortalUser user;

    @ManyToOne
    @JoinColumn(name = "RATE_ID")
    private SurveyRate surveyRate;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    @Where(clause = "status IS NOT NULL")
    private PortalDepartment department;

    @Column(name = "STATUS", nullable = true)
    private String status;

    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;

    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;



}
