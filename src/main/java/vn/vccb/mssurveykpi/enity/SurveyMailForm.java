package vn.vccb.mssurveykpi.enity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "SURVEY_MAIL_FORM")
public class SurveyMailForm implements Serializable {

    private static final long serialVersionUID = 5102092054792908815L;
    @Id
    @Column(name = "MAIL_FORM_ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAIL_FORM_ID_SEQ")
    @SequenceGenerator(name = "MAIL_FORM_ID_SEQ", sequenceName = "SURVEY_MAIL_FORM_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "TITLE", nullable = true)
    private String title;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "CONTENT", nullable = true)
    @Lob
    private String content;

    @Column(name = "STATUS", nullable = true)
    private Boolean status;

    @Column(name = "CREATED_BY", nullable = true)
    private Date created_by;

    @Column(name = "UPDATED_BY", nullable = true)
    private Date updated_by;


}
