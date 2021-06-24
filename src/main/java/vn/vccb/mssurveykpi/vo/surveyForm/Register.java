package vn.vccb.mssurveykpi.vo.surveyForm;

import lombok.Data;

@Data
public class Register {

        private String title;

        private Long cycle;

        private Boolean status;

        private long idFormMail;
}
