package vn.vccb.mssurveykpi.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "SURVEY_RATE")
public class SurveyRate implements Serializable {

    private static final long serialVersionUID = 1629971073632246084L;

    @Id
    @Column(name = "RATE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RATE_ID_SEQ")
    @SequenceGenerator(name = "RATE_ID_SEQ", sequenceName = "SURVEY_RATE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CYCLE", nullable = true)
    private String cycle;

    @Column(name = "CARD_START_YEAR", nullable = true)
    private int cardStartYear;

    @Column(name = "STATUS", nullable = true)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "FORM_ID")
    private SurveyForm surveyForm;

    @ManyToOne
    @JoinColumn(name = "PERIODIC_ID")
    private SurveyPeriodic surveryPeriodic;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private PortalUser user;

    @OneToMany(mappedBy = "surveyRate")
    private List<SurveyRateDetail> surveyRateDetails;


    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;

    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;

}
