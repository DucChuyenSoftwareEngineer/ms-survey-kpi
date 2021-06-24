package vn.vccb.mssurveykpi.dao;

import vn.vccb.mssurveykpi.enity.SurveyRate;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;

import java.util.List;

public interface SurveyRateDao {

    Long count (SurveyRateSco sco);

    List<SurveyRate> get(SurveyRateSco sco);
}
