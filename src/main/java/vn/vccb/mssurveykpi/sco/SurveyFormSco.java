package vn.vccb.mssurveykpi.sco;

import lombok.Data;

@Data
public class SurveyFormSco extends PaginationSco {
    private  String title;
    private  String cycle;
    private  String idFormMail;
}
