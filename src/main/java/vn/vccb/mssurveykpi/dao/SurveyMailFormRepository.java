package vn.vccb.mssurveykpi.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyMailForm;

import java.util.List;

@Repository
public interface SurveyMailFormRepository extends JpaRepository<SurveyMailForm,Long> {

    List<SurveyMailForm> getByStatusTrue();
}
