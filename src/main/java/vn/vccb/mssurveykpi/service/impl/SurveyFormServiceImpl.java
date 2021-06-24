package vn.vccb.mssurveykpi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.dao.SurveyFormRepository;
import vn.vccb.mssurveykpi.dao.SurveyMailFormRepository;
import vn.vccb.mssurveykpi.dao.SurveyPeriodicRepository;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.enity.SurveyMailForm;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;
import vn.vccb.mssurveykpi.exception.BusinessException;
import vn.vccb.mssurveykpi.service.SurveyFormService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.surveyForm.Item;
import vn.vccb.mssurveykpi.vo.surveyForm.Register;

import java.util.Date;

@Service
public class SurveyFormServiceImpl implements SurveyFormService {

    @Autowired
    SurveyMailFormRepository surveyMailFormRepository;

    @Autowired
    SurveyFormRepository surveyFormRepository;

    @Autowired
    SurveyPeriodicRepository surveyPeriodicRepository;

    @Override
    public Long registerSurveyForm(Register item) {

        SurveyForm enity = new SurveyForm();
        SurveyMailForm enityMail = surveyMailFormRepository.findById(item.getIdFormMail()).orElse(null);
        SurveyPeriodic enityPeriodic = surveyPeriodicRepository.findById(item.getCycle()).orElse(null);

        if(CheckUtil.isNullOrEmpty(enityMail)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_ISSET));
        }
        if(CheckUtil.isNullOrEmpty(enityPeriodic)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_PERIODIC_ISSET));
        }


        enity.setTitle(item.getTitle());

        enity.setSurveyMailForm(enityMail);
        enity.setStatus(Boolean.TRUE);
        enity.setCreated_by(new Date());

        surveyFormRepository.save(enity);

        return enity.getId();
    }

    @Override
    public Item getSurveyForm(Long id) {

        Item itemResult = new Item();

        SurveyForm enity = surveyFormRepository.findById(id).orElse(null);



        return null;
    }


}
