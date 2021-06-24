package vn.vccb.mssurveykpi.service;

import vn.vccb.mssurveykpi.sco.SurveyMailSco;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMail;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMailService;
import vn.vccb.mssurveykpi.vo.surveyFormMail.Register;
import vn.vccb.mssurveykpi.vo.surveyFormMail.SurveyMailPagingItem;

import java.util.List;

public interface SurveyMailService {

    List<ItemMailService> getAllSurveyMail();

    Long registerSurveyMail(Register item);

    ItemMail getSurveyMail(Long id);

    void updatedSurveyMail(Long id,Register item);

    void deleteDepartment(Long id);

    Long countSurveyMailPaging(SurveyMailSco sco);

    List<SurveyMailPagingItem> getSurveyMailPaging(SurveyMailSco sco);


}
