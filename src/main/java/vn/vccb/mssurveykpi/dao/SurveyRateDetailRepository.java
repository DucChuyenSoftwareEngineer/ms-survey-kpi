package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyRateDetail;

import java.util.List;

@Repository
public interface SurveyRateDetailRepository extends JpaRepository<SurveyRateDetail,Long> {
    List<SurveyRateDetail> getByStatus(String status);
}
