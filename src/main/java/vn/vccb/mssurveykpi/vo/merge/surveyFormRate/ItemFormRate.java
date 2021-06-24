package vn.vccb.mssurveykpi.vo.merge.surveyFormRate;

import lombok.Data;
import vn.vccb.mssurveykpi.vo.surveyFormOption.ItemFormOption;

import java.util.List;

@Data
public class ItemFormRate {
    private Long id;

    private String title;

    private long cycle;

    private Boolean status;

    private long idFormMail;

    List<ItemFormOption> itemFormOptions ;
}
