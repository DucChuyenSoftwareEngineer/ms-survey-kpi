package vn.vccb.mssurveykpi.dao;

import vn.vccb.mssurveykpi.enity.SurveyRateDetail;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;

import java.util.List;

public interface SurveyRateDetailDao {

    Long count (SurveyRateSco sco);

    List<SurveyRateDetail> get(SurveyRateSco sco);
}
