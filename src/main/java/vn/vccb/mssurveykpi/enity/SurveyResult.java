package vn.vccb.mssurveykpi.enity;

import java.io.Serializable;
import java.util.Date;

public class SurveyResult implements Serializable {
    private static final long serialVersionUID = 5102092054792908815L;

    private Long id;
    private String transferType;
    private String note;
    private Date created_by;
    private Date updated_by;
}
