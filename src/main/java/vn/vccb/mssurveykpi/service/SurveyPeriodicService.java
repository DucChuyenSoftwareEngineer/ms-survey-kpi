package vn.vccb.mssurveykpi.service;


import vn.vccb.mssurveykpi.vo.surveyPeriodic.ItemPeriodic;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.Register;

import java.util.List;

;

public interface SurveyPeriodicService {

    Long registerSurveyPeriodic(Register item);

    ItemPeriodic getItemSurveyPeriodic(Long id);

    List<ItemPeriodic> getAllSurveyPeriodic();
}
