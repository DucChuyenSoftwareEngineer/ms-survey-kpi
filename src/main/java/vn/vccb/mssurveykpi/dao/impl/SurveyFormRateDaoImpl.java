package vn.vccb.mssurveykpi.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import vn.vccb.mssurveykpi.dao.SurveyFormRateDao;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.sco.SurveyFormSco;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.SortUtil;

import java.util.List;

@Service
public class SurveyFormRateDaoImpl extends DaoImpl implements SurveyFormRateDao {

    @Override
    public Long count(SurveyFormSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    public List<SurveyForm> get(SurveyFormSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
        SortUtil.setCriteriaSortInfo(criteria, sco);

        return criteria.list();
    }

    private Criteria buildCriteria(SurveyFormSco sco) {
        Criteria criteria;

        criteria = createCriteria(SurveyForm.class,"surveyForm");
//        criteria.createAlias("surveyForm.surveryPeriodic","surveryPeriodic", JoinType.LEFT_OUTER_JOIN);
//        criteria.createAlias("surveyForm.surveyMailForm","surveyMailForm", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.isNotNull("status"));

        if (!CheckUtil.isNullOrEmpty(sco.getTitle())) {
            criteria.add(Restrictions.ilike("title", sco.getTitle(), MatchMode.ANYWHERE));
        }
        if (!CheckUtil.isNullOrEmpty(sco.getIdFormMail()) &&!sco.getIdFormMail().equals("0")) {
            criteria.add(Restrictions.eq("surveyMailForm.id",Long.parseLong(sco.getIdFormMail())));
        }
        if (!CheckUtil.isNullOrEmpty(sco.getCycle()) && !sco.getCycle().equals("0")) {
            criteria.add(Restrictions.eq("surveryPeriodic.id",Long.parseLong(sco.getCycle())));
        }

        return criteria;
    }
}
