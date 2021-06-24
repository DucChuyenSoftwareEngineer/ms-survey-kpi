package vn.vccb.mssurveykpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;
import vn.vccb.mssurveykpi.service.SurveyRateService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.RestResultItem;
import vn.vccb.mssurveykpi.vo.common.DataTableItem;
import vn.vccb.mssurveykpi.vo.rate.RateBrowser;
import vn.vccb.mssurveykpi.vo.rate.RateBrowserPagingItem;
import vn.vccb.mssurveykpi.vo.rate.RateRegisterItem;
import vn.vccb.mssurveykpi.vo.rate.RateSendMail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("surveyRate")
public class SurveyRateController {

    @Autowired
    SurveyRateService surveyRateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestResultItem> uploadExcelReader(RateRegisterItem item) {
        RestResultItem result;
        DataTableItem dataTableItem;
        result = new RestResultItem();
        Map<String, String> lstMessage;
        List<RateBrowser> listRateBrowser;

        lstMessage = validateRegisterInput(item);
        if(lstMessage.isEmpty()) {
            try {
                listRateBrowser = new ArrayList<>();
                listRateBrowser = surveyRateService.uploadExcelFullStock(item);
                dataTableItem = new DataTableItem();
                dataTableItem.setTotal(Long.valueOf(listRateBrowser.size()));
                dataTableItem.setData(listRateBrowser);
                result.setData(dataTableItem);
                return ResponseEntity.ok(result);

            }catch (Exception e){
                result.getListMessage().put("", e.getMessage());
            }
        }else {
            result.setListMessage(lstMessage);
        }


        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("sendMail")
    public ResponseEntity<RestResultItem> sendMail(RateSendMail item) {
        RestResultItem result;
        result = new RestResultItem();
        Map<String, String> lstMessage;
        int numberMailSucces;
        lstMessage = validateSendMailInput(item);
        if(lstMessage.isEmpty()) {
            try{
                numberMailSucces = surveyRateService.sendMail(item);
                result.setData(numberMailSucces);
            }catch (Exception e){
                result.getListMessage().put("", e.getMessage());
            }
        }else {
            result.setListMessage(lstMessage);
        }
        return ResponseEntity.badRequest().body(result);
    }



    @GetMapping("paging")
    public ResponseEntity<RestResultItem> paging(SurveyRateSco sco) {
        Long count;
        RestResultItem result;
        DataTableItem dataTableItem;
        List<RateBrowserPagingItem> lstData;

        result = new RestResultItem();

        try{
            //count = surveyRateService.countSurveyRateBrowserMailPaging(sco);
            lstData = surveyRateService.getSurveyRateBrowserMailPaging(sco);

            dataTableItem = new DataTableItem();
            dataTableItem.setData(lstData);
           // dataTableItem.setTotal(count);

            result.setData(dataTableItem);

            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }


        return ResponseEntity.badRequest().body(result);
    }



    private Map<String, String> validateRegisterInput(RateRegisterItem item) {
        Map<String, String> lstMessage;

        lstMessage = new HashMap<>();

        if (CheckUtil.isNullOrEmpty(item.getPeriod())) {
            lstMessage.put("period",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
        }else{
            if (item.getPeriod().equals(String.valueOf("1"))) {
                if(CheckUtil.isNullOrEmpty(item.getCycle())){
                    lstMessage.put("cycle",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CYCLE));
                }
                if(CheckUtil.isNullOrEmpty(item.getCardStartYear())){
                    lstMessage.put("cardStartYear",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CARD_START_YEAR));
                }

            }else if(item.getPeriod().equals(String.valueOf('5'))){
                if(!CheckUtil.isNullOrEmpty(item.getCycle())){
                    lstMessage.put("cycle",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CYCLE_NOT_CHOOSE));
                }
                if(CheckUtil.isNullOrEmpty(item.getCardStartYear())){
                    lstMessage.put("cardStartYear",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CARD_START_YEAR));
                }

            }else {
                lstMessage.put("period",
                        MessageUtil.getMessageAndParam(
                                MessageConstant.VALIDATE_INPUT_REQUIRED,
                                MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
            }
        }

        if(CheckUtil.isNullOrEmpty(item.getForm())){
            lstMessage.put("form",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FORM));
        }

        if(item.getFile().isEmpty()){
            lstMessage.put("file",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FILE));
        }else{
            String fileName = item.getFile().getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            if(!suffix.equals(".xlsx")){
                MessageUtil.getMessageAndParam(
                        MessageConstant.VALIDATE_INPUT_REQUIRED,
                        MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FILE_ERROR_FORMAT);
            }
        }







        return lstMessage;
    }
    private Map<String, String> validateSendMailInput(RateSendMail item) {
        Map<String, String> lstMessage;

        lstMessage = new HashMap<>();

        if (CheckUtil.isNullOrEmpty(item.getPeriod())) {
            lstMessage.put("period",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
        }else{
            if (item.getPeriod().equals(String.valueOf("1"))) {
                if(CheckUtil.isNullOrEmpty(item.getCycle())){
                    lstMessage.put("cycle",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CYCLE));
                }
                if(CheckUtil.isNullOrEmpty(item.getCardStartYear())){
                    lstMessage.put("cardStartYear",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CARD_START_YEAR));
                }

            }else if(item.getPeriod().equals(String.valueOf('5'))){
                if(!CheckUtil.isNullOrEmpty(item.getCycle())){
                    lstMessage.put("cycle",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CYCLE_NOT_CHOOSE));
                }
                if(CheckUtil.isNullOrEmpty(item.getCardStartYear())){
                    lstMessage.put("cardStartYear",
                            MessageUtil.getMessageAndParam(
                                    MessageConstant.VALIDATE_INPUT_REQUIRED,
                                    MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_CARD_START_YEAR));
                }

            }else {
                lstMessage.put("period",
                        MessageUtil.getMessageAndParam(
                                MessageConstant.VALIDATE_INPUT_REQUIRED,
                                MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
            }
        }

        if(CheckUtil.isNullOrEmpty(item.getForm())){
            lstMessage.put("form",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FORM));
        }








        return lstMessage;
    }
}
