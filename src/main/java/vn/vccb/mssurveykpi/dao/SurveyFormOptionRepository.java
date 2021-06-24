package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.enity.SurveyFormOption;

import java.util.List;

@Repository
public interface SurveyFormOptionRepository extends JpaRepository<SurveyFormOption,Long> {

    List<SurveyFormOption> getByStatusTrue();

    List<SurveyFormOption> getBySurveyFormAndStatusTrue(SurveyForm surveyForm);
}
