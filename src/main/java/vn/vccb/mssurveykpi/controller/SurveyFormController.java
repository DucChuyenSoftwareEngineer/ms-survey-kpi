package vn.vccb.mssurveykpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.service.SurveyFormService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.RestResultItem;
import vn.vccb.mssurveykpi.vo.surveyForm.Item;
import vn.vccb.mssurveykpi.vo.surveyForm.Register;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("survey")
public class SurveyFormController {

    @Autowired
    private SurveyFormService surveyFormService;



    @PostMapping
    public ResponseEntity<RestResultItem> register(@RequestBody Register item) {
        RestResultItem result;
        Map<String, String> lstMessage;
        result = new RestResultItem();
        Item voItem;
        Long resultId;

        lstMessage = validateRegisterInput(item);

        if(lstMessage.isEmpty()){
            try {
              resultId = surveyFormService.registerSurveyForm(item);
            }catch (Exception e) {
                result.getListMessage().put("", e.getMessage());
            }

        }else {
            result.setListMessage(lstMessage);
        }

        return ResponseEntity.badRequest().body(result);
    }


    private Map<String, String> validateRegisterInput(Register item) {
        Map<String, String> lstMessage;

        lstMessage = new HashMap<>();


        if (CheckUtil.isNullOrEmpty(item.getTitle())) {
            lstMessage.put("title",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_TITLE));
        }

        if (CheckUtil.isNullOrEmpty(item.getCycle())) {
            lstMessage.put("cycle",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_CYCLE));
        }

        if (CheckUtil.isNullOrEmpty(item.getIdFormMail()) ) {
            lstMessage.put("idFormMail",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_IDMAIL));
        }


        return lstMessage;
    }

}
