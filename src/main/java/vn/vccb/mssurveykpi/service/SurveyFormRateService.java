package vn.vccb.mssurveykpi.service;


import vn.vccb.mssurveykpi.sco.SurveyFormSco;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.ItemFormRate;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.Register;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.SurveyFormRatePagingItem;

import java.util.List;

public interface SurveyFormRateService {

    Long registerSurveyFormRate(Register item);

    ItemFormRate getSurveyFormRate(Long id);

    Long countSurveyFormRatePaging(SurveyFormSco sco);

    List<SurveyFormRatePagingItem> getSurveyFormRatePaging(SurveyFormSco sco);

    void updatedSurveyFormRate(Long id, Register item);

    void deleteSurveyFormRate(Long id);


}
