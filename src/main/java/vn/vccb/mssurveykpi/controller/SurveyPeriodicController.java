package vn.vccb.mssurveykpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.service.SurveyPeriodicService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.RestResultItem;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.ItemPeriodic;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.Register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("surveyPeriodic")
public class SurveyPeriodicController {

    @Autowired
    SurveyPeriodicService surveyPeriodicService;



    @PostMapping
    public ResponseEntity<RestResultItem> register(@RequestBody Register item) {
        RestResultItem result;
        Long resultId;
        Map<String, String> lstMessage;
        result = new RestResultItem();
        ItemPeriodic voItem;

        lstMessage = validateRegisterInput(item);
        if(lstMessage.isEmpty()){

            try {
                resultId = surveyPeriodicService.registerSurveyPeriodic(item);
                voItem = surveyPeriodicService.getItemSurveyPeriodic(resultId);

                result.setData(voItem);
                return ResponseEntity.ok(result);
            }catch (Exception e) {
                result.getListMessage().put("", e.getMessage());
            }
        }else {
            result.setListMessage(lstMessage);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<RestResultItem> get(@PathVariable Long id) {

        RestResultItem result;

        result = new RestResultItem();
        ItemPeriodic voItem;
        try {
            voItem = surveyPeriodicService.getItemSurveyPeriodic(id);
            result.setData(voItem);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping
    public ResponseEntity<RestResultItem> getAll() {
        RestResultItem result;

        result = new RestResultItem();
        List<ItemPeriodic> voItemList;

        try {
            voItemList = surveyPeriodicService.getAllSurveyPeriodic();
            result.setData(voItemList);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            result.getListMessage().put("", e.getMessage());
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
                            MessageConstant.MESSAGE_PARAM_SURVEY_PERIODIC_TITLE));
        }

        return lstMessage;
    }
}
