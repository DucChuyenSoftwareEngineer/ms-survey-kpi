package vn.vccb.mssurveykpi.vo.surveyFormMail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItemMail implements Serializable {

    private static final long serialVersionUID = 7200107599968351621L;
    private Long id;
    private String title;
    private String name;
    private String content;
    private Date created_by;
    private Date updated_by;

}
