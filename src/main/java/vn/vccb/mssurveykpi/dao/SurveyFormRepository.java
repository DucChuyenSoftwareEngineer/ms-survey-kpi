package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyForm;

@Repository
public interface SurveyFormRepository extends JpaRepository<SurveyForm,Long> {
}
