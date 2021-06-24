package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;
import vn.vccb.mssurveykpi.enity.SurveyRate;

@Repository
public interface SurveyRateRepository extends JpaRepository<SurveyRate,Long> {

    SurveyRate getByCardStartYearAndCycleAndSurveryPeriodicAndSurveyForm(int cardStartYear, int cycle, SurveyPeriodic surveyPeriodic, SurveyForm surveyForm);

    SurveyRate getByCardStartYearAndSurveryPeriodicAndSurveyForm(int cardStartYear, SurveyPeriodic surveyPeriodic, SurveyForm surveyForm);

}
