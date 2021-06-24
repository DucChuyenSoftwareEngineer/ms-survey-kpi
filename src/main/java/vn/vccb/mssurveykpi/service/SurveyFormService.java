package vn.vccb.mssurveykpi.service;

import vn.vccb.mssurveykpi.vo.surveyForm.Item;
import vn.vccb.mssurveykpi.vo.surveyForm.Register;

public interface SurveyFormService {

    Long registerSurveyForm(Register item);

    Item getSurveyForm(Long id);



}
