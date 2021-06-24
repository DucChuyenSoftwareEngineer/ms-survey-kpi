package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;

import java.util.List;

@Repository
public interface SurveyPeriodicRepository extends JpaRepository<SurveyPeriodic,Long> {

    List<SurveyPeriodic> getByStatusTrue();

}
