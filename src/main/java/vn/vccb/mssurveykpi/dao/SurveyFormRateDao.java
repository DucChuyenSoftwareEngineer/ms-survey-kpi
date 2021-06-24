package vn.vccb.mssurveykpi.dao;

import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.sco.SurveyFormSco;

import java.util.List;

public interface SurveyFormRateDao {

    Long count (SurveyFormSco sco);

    List<SurveyForm> get(SurveyFormSco sco);

}
