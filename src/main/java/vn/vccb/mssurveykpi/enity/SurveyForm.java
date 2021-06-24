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
@Table(name = "SURVEY_FORM")
public class SurveyForm implements Serializable {

    private static final long serialVersionUID = 5102092054792908815L;
    @Id
    @Column(name = "FORM_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORM_ID_SEQ")
    @SequenceGenerator(name = "FORM_ID_SEQ", sequenceName = "SURVEY_FORM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "TITLE", nullable = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "PERIODIC_ID")
    private SurveyPeriodic surveryPeriodic;

    @Column(name = "USERNAME", nullable = true)
    private String username;

    @Column(name = "STATUS", nullable = true)
    private Boolean status;

    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;

    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;

    @OneToMany
    @JoinColumn(name="FORM_ID")
    private List<SurveyFormOption> SurveyFormOptions;

    @ManyToOne
    @JoinColumn(name = "MAIL_FORM_ID")
    private SurveyMailForm surveyMailForm;
}
