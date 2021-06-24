package vn.vccb.mssurveykpi.sco;

import lombok.Data;

@Data
public class SurveyRateSco extends PaginationSco {
    private String period;
    private String cycle;
    private String cardStartYear;
    private String form;
}
