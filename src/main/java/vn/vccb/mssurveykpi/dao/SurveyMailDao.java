package vn.vccb.mssurveykpi.dao;

import vn.vccb.mssurveykpi.enity.SurveyMailForm;
import vn.vccb.mssurveykpi.sco.SurveyMailSco;

import java.util.List;

public interface SurveyMailDao {

    Long count (SurveyMailSco sco);

    List<SurveyMailForm> get(SurveyMailSco sco);

}
