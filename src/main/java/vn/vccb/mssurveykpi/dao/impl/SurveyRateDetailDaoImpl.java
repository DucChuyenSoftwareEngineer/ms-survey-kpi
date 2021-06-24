package vn.vccb.mssurveykpi.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.dao.SurveyRateDetailDao;
import vn.vccb.mssurveykpi.enity.SurveyRateDetail;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;
import vn.vccb.mssurveykpi.util.CheckUtil;

import java.util.List;

@Repository
public class SurveyRateDetailDaoImpl extends DaoImpl implements SurveyRateDetailDao {

    @Override
    public Long count(SurveyRateSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    public List<SurveyRateDetail> get(SurveyRateSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
     //   SortUtil.setCriteriaSortInfo(criteria, sco);

        return criteria.list();
    }

    private Criteria buildCriteria(SurveyRateSco sco) {
        Criteria criteria;

        criteria = createCriteria(SurveyRateDetail.class,"surveyRateDetail");
        //criteria.createAlias("surveyRateDetail.surveyRate","surveyRate", JoinType.LEFT_OUTER_JOIN);
//        criteria.createAlias("surveyRate.surveryPeriodic","surveryPeriodic", JoinType.LEFT_OUTER_JOIN);
//        criteria.createAlias("surveyRate.surveyForm","surveyForm", JoinType.LEFT_OUTER_JOIN);
//        criteria.add(Restrictions.eq("surveyRateDetail.status","CREATE"));

        if (!CheckUtil.isNullOrEmpty(sco.getCycle())) {
            criteria.add(Restrictions.ilike("surveyRate.cycle", sco.getCycle(), MatchMode.ANYWHERE));
        }
        if (!CheckUtil.isNullOrEmpty(sco.getCardStartYear())) {
            criteria.add(Restrictions.ilike("surveyRate.cardStartYear", sco.getCardStartYear(), MatchMode.ANYWHERE));
        }
        if (!CheckUtil.isNullOrEmpty(sco.getForm()) &&!sco.getForm().equals("0")) {
            criteria.add(Restrictions.eq("surveyForm.id",Long.parseLong(sco.getForm())));
        }
        if (!CheckUtil.isNullOrEmpty(sco.getCycle()) && !sco.getCycle().equals("0")) {
            criteria.add(Restrictions.eq("surveryPeriodic.id",Long.parseLong(sco.getCycle())));
        }

        return criteria;
    }
}
