package vn.vccb.mssurveykpi.vo.rate;

import lombok.Data;

import java.io.Serializable;

@Data
public class RateBrowser implements Serializable {
    private String username;
    private String nameEmpl;
    private String manager;
    private String department;
    private String departmentReview;
    private String titleForm;
    private String cycle;
    private String created;
    private String userCreated;
}
