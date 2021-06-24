package vn.vccb.mssurveykpi.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.dao.SurveyMailDao;
import vn.vccb.mssurveykpi.enity.SurveyMailForm;
import vn.vccb.mssurveykpi.sco.SurveyMailSco;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.SortUtil;

import java.util.List;

@Repository
public class SurveyMailDaoImpl extends DaoImpl implements SurveyMailDao {

    @Override
    public Long count(SurveyMailSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    public List<SurveyMailForm> get(SurveyMailSco sco) {
        Criteria criteria;

        criteria = buildCriteria(sco);
        SortUtil.setCriteriaSortInfo(criteria, sco);

        return criteria.list();

    }


    private Criteria buildCriteria(SurveyMailSco sco) {
        Criteria criteria;

        criteria = createCriteria(SurveyMailForm.class);
        criteria.add(Restrictions.isNotNull("status"));

        if (!CheckUtil.isNullOrEmpty(sco.getTitle())) {
            criteria.add(Restrictions.ilike("title", sco.getTitle(), MatchMode.ANYWHERE));
        }

        return criteria;
    }
}
