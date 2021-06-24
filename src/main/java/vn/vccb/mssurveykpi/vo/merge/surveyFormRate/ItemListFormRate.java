package vn.vccb.mssurveykpi.vo.merge.surveyFormRate;

import lombok.Data;

import java.util.Date;

@Data
public class ItemListFormRate {
    private long id;
    private String title;
    private String cycle;
    private String mail;
    private String userCreate;
    private Date dateCreate;
}
