package vn.vccb.mssurveykpi.vo.surveyForm;

import lombok.Data;

import java.util.Date;

@Data
public class Item {

    private Long id;
    private String title;
    private String cycle;
    private String nameCreated;
    private String idFormMail;
    private Date created_by;

}
