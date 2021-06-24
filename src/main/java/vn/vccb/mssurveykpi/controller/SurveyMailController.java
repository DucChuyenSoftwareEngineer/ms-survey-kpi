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
import vn.vccb.mssurveykpi.sco.SurveyMailSco;
import vn.vccb.mssurveykpi.service.SurveyMailService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.RestResultItem;
import vn.vccb.mssurveykpi.vo.common.DataTableItem;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMail;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMailService;
import vn.vccb.mssurveykpi.vo.surveyFormMail.Register;
import vn.vccb.mssurveykpi.vo.surveyFormMail.SurveyMailPagingItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("surveyMail")
public class SurveyMailController {

    @Autowired
    private SurveyMailService surveyMailService;

    @GetMapping
    public ResponseEntity<RestResultItem> getAll(){
        RestResultItem result;

        result = new RestResultItem();
        List<ItemMailService> voItemList;

        try {
            voItemList = surveyMailService.getAllSurveyMail();
            result.setData(voItemList);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("paging")
    public ResponseEntity<RestResultItem> paging(SurveyMailSco sco) {
        Long count;
        RestResultItem result;
        DataTableItem dataTableItem;
        List<SurveyMailPagingItem> lstData;

        result = new RestResultItem();

        try{
            count = surveyMailService.countSurveyMailPaging(sco);
            lstData = surveyMailService.getSurveyMailPaging(sco);

            dataTableItem = new DataTableItem();
            dataTableItem.setData(lstData);
            dataTableItem.setTotal(count);

            result.setData(dataTableItem);

            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }


        return ResponseEntity.badRequest().body(result);
    }
    @PostMapping
    public ResponseEntity<RestResultItem> register(@RequestBody Register item) {
        RestResultItem result;
        Long resultId;
        Map<String, String> lstMessage;
        result = new RestResultItem();
        ItemMail voItem;

        lstMessage = validateRegisterInput(item);

        if(lstMessage.isEmpty()){

            try {
                resultId = surveyMailService.registerSurveyMail(item);
                voItem = surveyMailService.getSurveyMail(resultId);
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
        ItemMail voItem;

        try {
            voItem = surveyMailService.getSurveyMail(id);
            result.setData(voItem);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<RestResultItem> updated(@PathVariable Long id,@RequestBody Register item) {

        RestResultItem result;

        result = new RestResultItem();


        try {
            surveyMailService.updatedSurveyMail(id,item);
            result.setData(item);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<RestResultItem> delete(@PathVariable Long id) {

        RestResultItem result;

        result = new RestResultItem();

        try {
            surveyMailService.deleteDepartment(id);
            result.setData("Xóa Thành công");
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            result.getListMessage().put("", e.getMessage());
        }

        return ResponseEntity.badRequest().body(result);
    }


//    @GetMapping("paging")
//    public ResponseEntity<RestResultItem> get(ContactSco sco) {
//
//    }

    private Map<String, String> validateRegisterInput(Register item) {
        Map<String, String> lstMessage;

        lstMessage = new HashMap<>();

        if (CheckUtil.isNullOrEmpty(item.getName())) {
            lstMessage.put("name",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_NAME));
        }
        if (CheckUtil.isNullOrEmpty(item.getTitle())) {
            lstMessage.put("title",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_TITLE));
        }

        if (CheckUtil.isNullOrEmpty(item.getContent())) {
            lstMessage.put("content",
                    MessageUtil.getMessageAndParam(
                            MessageConstant.VALIDATE_INPUT_REQUIRED,
                            MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_CONTENT));
        }



        return lstMessage;
    }




}
