package vn.vccb.mssurveykpi.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "SURVEY_FORMOPTIONS")
public class SurveyFormOption implements Serializable {
    private static final long serialVersionUID = 5102092054792908815L;
    @Id
    @Column(name = "FORMOPTIONS_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORMOPTIONS_ID_SEQ")
    @SequenceGenerator(name = "FORMOPTIONS_ID_SEQ", sequenceName = "SURVEY_FORMOPTIONS_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CONTENT", nullable = true)
    private String content;
    @Column(name = "STATUS", nullable = true)
    private Boolean status;
    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;
    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;

    @ManyToOne
    @JoinColumn(name = "FORM_ID")
    private SurveyForm surveyForm;
}
