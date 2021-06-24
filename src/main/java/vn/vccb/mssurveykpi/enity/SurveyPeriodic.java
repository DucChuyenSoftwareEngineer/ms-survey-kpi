package vn.vccb.mssurveykpi.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "SURVEY_PERIODIC")
public class SurveyPeriodic {
    private static final long serialVersionUID = 5102092054792908815L;
    @Id
    @Column(name = "PERIODIC_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERIODIC_ID_SEQ")
    @SequenceGenerator(name = "PERIODIC_ID_SEQ", sequenceName = "SURVEY_PERIODIC_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = true)
    private String title;

    @Column(name = "STATUS", nullable = true)
    private Boolean status;

    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;

    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;
}
