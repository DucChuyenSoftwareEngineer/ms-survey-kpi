package vn.vccb.mssurveykpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.sco.SurveyFormSco;
import vn.vccb.mssurveykpi.service.SurveyFormRateService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.RestResultItem;
import vn.vccb.mssurveykpi.vo.common.DataTableItem;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.ItemFormRate;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.Register;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.SurveyFormRatePagingItem;
import vn.vccb.mssurveykpi.vo.surveyFormOption.ItemFormOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("surveyFormRate")
public class SurveyFormRateController {

    @Autowired
    SurveyFormRateService surveyFormRateService;




    @GetMapping("paging")
    public ResponseEntity<RestResultItem> paging(SurveyFormSco sco){
        Long count;
        RestResultItem result;
        DataTableItem dataTableItem;
        List<SurveyFormRatePagingItem> lstData;

        result = new RestResultItem();
        try{
            count = surveyFormRateService.countSurveyFormRatePaging(sco);
            lstData  = surveyFormRateService.getSurveyFormRatePaging(sco);
            dataTableItem = new DataTableItem();
            dataTableItem.setData(lstData);
            dataTableItem.setTotal(count);

            result.setData(dataTableItem);

            return ResponseEntity.ok(result);
        }catch (Exception e){
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<RestResultItem> get(@PathVariable Long id) {
        RestResultItem result;
        result = new RestResultItem();

        ItemFormRate itemVo ;

        try {
            itemVo = surveyFormRateService.getSurveyFormRate(id);
            result.setData(itemVo);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            result.getListMessage().put("", e.getMessage());
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping
    public ResponseEntity<RestResultItem> register(@RequestBody Register item) {

        RestResultItem result;
        result = new RestResultItem();
        Map<String, String> lstMessage;
        Long resultId;
        ItemFormRate voItem;

        lstMessage = validateRegisterInput(item);

        if(lstMessage.isEmpty()){

            try{
                resultId = surveyFormRateService.registerSurveyFormRate(item);
                voItem = surveyFormRateService.getSurveyFormRate(resultId);
                result.setData(voItem);
                return ResponseEntity.ok(result);

            }catch (Exception e){
                result.getListMessage().put("", e.getMessage());
            }

        }else {
            result.setListMessage(lstMessage);
        }


        return ResponseEntity.badRequest().body(result);
    }






    @PutMapping("{id}")
    public ResponseEntity<RestResultItem> updated(@PathVariable Long id,@RequestBody Register item) {
        RestResultItem result;
        result = new RestResultItem();
        Map<String, String> lstMessage;
        ItemFormRate voItem;

        lstMessage = validateRegisterInput(item);

        if(lstMessage.isEmpty()){
            try {
                surveyFormRateService.updatedSurveyFormRate(id, item);
                voItem = surveyFormRateService.getSurveyFormRate(id);
                result.setData(voItem);
                return ResponseEntity.ok(result);
            }catch (Exception e){
                result.getListMessage().put("", e.getMessage());
            }
        }else {
            result.setListMessage(lstMessage);
        }

        return ResponseEntity.badRequest().body(result);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<RestResultItem> delete(@PathVariable Long id){
        RestResultItem result;
        result = new RestResultItem();
        try {
            surveyFormRateService.deleteSurveyFormRate(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            result.getListMessage().put("",e.getMessage());
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
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_TITLE));
        }
        if(CheckUtil.isNullOrEmpty(item.getCycle()) || item.getCycle() <=0){
            lstMessage.put("cycle",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_CYCLE));
        }
        if(CheckUtil.isNullOrEmpty(item.getIdFormMail()) || item.getIdFormMail() <= 0){
            lstMessage.put("idFormMail",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_IDMAIL));
        }

        if(CheckUtil.isNullOrEmptyCollection(item.getItemFormOptions())){
            lstMessage.put("itemFormOptions",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_IDOPTIONS));
        }else {
            Boolean flat = false;
            for( ItemFormOption itemFormOption : item.getItemFormOptions()){
                    if(CheckUtil.isNullOrEmpty(itemFormOption)||CheckUtil.isNullOrEmpty(itemFormOption.getContent())){
                        flat=true;
                        break;
                    }
            }
            if(flat){
                lstMessage.put("itemFormOptions",
                        MessageUtil.getMessageAndParam(
                                MessageConstant.VALIDATE_INPUT_REQUIRED,
                                MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_IDOPTIONS));
            }
        }


        return lstMessage;
    }


}
