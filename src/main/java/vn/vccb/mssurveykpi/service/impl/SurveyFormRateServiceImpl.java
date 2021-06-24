package vn.vccb.mssurveykpi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.dao.SurveyFormRateDao;
import vn.vccb.mssurveykpi.dao.SurveyFormRepository;
import vn.vccb.mssurveykpi.dao.SurveyFormOptionRepository;
import vn.vccb.mssurveykpi.dao.SurveyMailFormRepository;
import vn.vccb.mssurveykpi.dao.SurveyPeriodicRepository;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.enity.SurveyFormOption;
import vn.vccb.mssurveykpi.enity.SurveyMailForm;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;
import vn.vccb.mssurveykpi.exception.BusinessException;
import vn.vccb.mssurveykpi.sco.SurveyFormSco;
import vn.vccb.mssurveykpi.service.SurveyFormRateService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.util.SortUtil;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.ItemFormRate;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.Register;
import vn.vccb.mssurveykpi.vo.merge.surveyFormRate.SurveyFormRatePagingItem;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMail;
import vn.vccb.mssurveykpi.vo.surveyFormOption.ItemFormOption;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.ItemPeriodic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyFormRateServiceImpl implements SurveyFormRateService {

    @Autowired
    SurveyMailFormRepository surveyMailFormRepository;

    @Autowired
    SurveyFormRepository surveyFormRepository;

    @Autowired
    SurveyPeriodicRepository surveyPeriodicRepository;

    @Autowired
    SurveyFormOptionRepository surveyFormOptionRepository;

    @Autowired
    SurveyFormRateDao surveyFormRateDao;


    @Override
    public Long registerSurveyFormRate(Register item) {

        SurveyForm enity = new SurveyForm();
        SurveyMailForm enityMail = surveyMailFormRepository.findById(item.getIdFormMail()).orElse(null);
        SurveyPeriodic enityPeriodic = surveyPeriodicRepository.findById(item.getCycle()).orElse(null);
        List<SurveyFormOption> enityFormOptions = new ArrayList<>();

        if(CheckUtil.isNullOrEmpty(enityMail)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_ISSET));
        }
        if(CheckUtil.isNullOrEmpty(enityPeriodic)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_PERIODIC_ISSET));
        }

        enity.setTitle(item.getTitle());
        enity.setCreated_by(new Date());
        enity.setStatus(Boolean.TRUE);
        enity.setSurveryPeriodic(enityPeriodic);
        enity.setSurveyMailForm(enityMail);

        surveyFormRepository.save(enity);


        enityFormOptions = item.getItemFormOptions()
                .stream()
                .map(o->{
                            SurveyFormOption surveyFormOption = new SurveyFormOption();
                            surveyFormOption.setContent(o.getContent());
                            surveyFormOption.setCreated_by(new Date());
                            surveyFormOption.setStatus(Boolean.TRUE);
                            surveyFormOption.setSurveyForm(enity);
                            return surveyFormOption;
                    }).collect(Collectors.toList());

        surveyFormOptionRepository.saveAll(enityFormOptions);

        return enity.getId();
    }

    @Override
    public ItemFormRate getSurveyFormRate(Long id) {

        ItemFormRate itemResult = new ItemFormRate();

        SurveyForm surveyForm = new SurveyForm();

        surveyForm = surveyFormRepository.findById(id).orElse(null);

        if(!CheckUtil.isNullOrEmpty(surveyForm)){
            itemResult.setId(surveyForm.getId());
            itemResult.setTitle(surveyForm.getTitle());

            ItemPeriodic itemPeriodicConvert = convertSurveyPeriodicFormToVo(surveyForm.getSurveryPeriodic());
            ItemMail itemMailConvert = convertSurveyMailFormToVo(surveyForm.getSurveyMailForm());
            List<ItemFormOption> itemFormOptionsConvert = convertSurveyFormOptionsToVo(surveyForm.getSurveyFormOptions());

            if(!CheckUtil.isNullOrEmpty(itemPeriodicConvert)){
                itemResult.setCycle(itemPeriodicConvert.getId());
            }
            if(!CheckUtil.isNullOrEmpty(itemFormOptionsConvert)){
                itemResult.setItemFormOptions(itemFormOptionsConvert);
            }

            if(!CheckUtil.isNullOrEmpty(itemMailConvert)){
                itemResult.setIdFormMail(itemMailConvert.getId());
            }


            return itemResult;

        }

        return null;
    }

    @Override
    public Long countSurveyFormRatePaging(SurveyFormSco sco) {
        return surveyFormRateDao.count(sco);
    }

    @Override
    public List<SurveyFormRatePagingItem> getSurveyFormRatePaging(SurveyFormSco sco) {
        SurveyFormRatePagingItem voItem;
        List<SurveyForm> lstEnity;
        List<SurveyFormRatePagingItem> lstResult;
        lstResult = new ArrayList<>();

        lstEnity= surveyFormRateDao.get(sco);

        if(!CheckUtil.isNullOrEmptyCollection(lstEnity)){

            for(SurveyForm item : lstEnity){
                voItem = new SurveyFormRatePagingItem();
                voItem.setId(item.getId());
                voItem.setTitle(item.getTitle());
                voItem.setCycle(item.getSurveryPeriodic().getTitle());
                voItem.setMail(item.getSurveyMailForm().getTitle());
                voItem.setDateCreate(item.getCreated_by());
                voItem.setUserCreate("chuyennd");
                lstResult.add(voItem);
            }
            SortUtil.fillIndex(lstResult, sco.getPage(), sco.getLimit());
        }


        return lstResult;
    }

    @Override
    public void updatedSurveyFormRate(Long id, Register item) {

        SurveyForm enity ;
        SurveyMailForm enityMail;
        SurveyPeriodic enityPeriodic;
        List<SurveyFormOption> listSurveyOptionCreate ;
        List<SurveyFormOption> listSurveyOptionDB;
        List<SurveyFormOption> listSurveyOptionUpdateDelete;
        List<SurveyFormOption> listSurveyOptionUpdate;
        List<SurveyFormOption> listSurveyOptionDelete;


        enity = new SurveyForm();
        enityMail = surveyMailFormRepository.findById(item.getIdFormMail()).orElse(null);
        enityPeriodic = surveyPeriodicRepository.findById(item.getCycle()).orElse(null);
        enity = surveyFormRepository.findById(id).orElse(null);

        // validation
        if(CheckUtil.isNullOrEmpty(enityMail)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_MAIL_ISSET));
        }
        if(CheckUtil.isNullOrEmpty(enityPeriodic)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_PERIODIC_ISSET));
        }

        if(CheckUtil.isNullOrEmpty(enity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_ID));
        }



        listSurveyOptionDB = surveyFormOptionRepository.getBySurveyFormAndStatusTrue(enity);
        listSurveyOptionCreate = new ArrayList<>();
        listSurveyOptionUpdate = new ArrayList<>();
        listSurveyOptionDelete = new ArrayList<>();

        // check db option null
        if(CheckUtil.isNullOrEmptyCollection(listSurveyOptionDB)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_FORM_OPTION_EMPTY));
        }

        // divide create and UpdatedDelete
        for(int i=0;i<item.getItemFormOptions().size();i++){
            if(CheckUtil.isNullOrEmpty(item.getItemFormOptions().get(i).getId())){
                // enity create
                SurveyFormOption enityCreated = new SurveyFormOption();
                enityCreated.setCreated_by(new Date());
                enityCreated.setSurveyForm(enity);
                enityCreated.setStatus(Boolean.TRUE);
                enityCreated.setContent(item.getItemFormOptions().get(i).getContent());
                listSurveyOptionCreate.add(enityCreated);
            }else{
                // enity UpdatedDelete
                SurveyFormOption enityUpdated = new SurveyFormOption();
                enityUpdated.setId(item.getItemFormOptions().get(i).getId());
                enityUpdated.setUpdated_by(new Date());
                enityUpdated.setSurveyForm(enity);
                enityUpdated.setStatus(Boolean.TRUE);
                enityUpdated.setContent(item.getItemFormOptions().get(i).getContent());
                listSurveyOptionUpdate.add(enityUpdated);

            }
        }

        // check inputUpdate compare to DB
        if(listSurveyOptionUpdate.size()>listSurveyOptionDB.size()){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_FORM_OPTION_SIZE_LIMIT));
        }


        // check input update isset DB
        for(SurveyFormOption itemUpdate : listSurveyOptionUpdate){
            Boolean flat = true;
            for(SurveyFormOption itemDB : listSurveyOptionDB){
                if(itemDB.getId().equals(itemUpdate.getId())){
                    flat = false;
                    break;
                }
            }
            if(flat){
                throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_FORM_OPTION_NOT_ISSET));
            }

        }



    // divide updated delete
        for(int j=0;j<listSurveyOptionDB.size();j++){
            boolean flat = true;
            for(int i=0;i<listSurveyOptionUpdate.size();i++){
               if(listSurveyOptionDB.get(j).getId()==listSurveyOptionUpdate.get(i).getId()){
                   flat = false;
                   break;
               }
            }
            if(flat){
                listSurveyOptionDelete.add(listSurveyOptionDB.get(j));
            }
        }

        // save db
        // set enity form
        enity.setSurveryPeriodic(enityPeriodic);
        enity.setSurveyMailForm(enityMail);
        enity.setTitle(item.getTitle());
        enity.setUpdated_by(new Date());

        surveyFormRepository.save(enity);
        if(listSurveyOptionUpdate.size()>0){
            surveyFormOptionRepository.saveAll(listSurveyOptionUpdate);
        }

        if(listSurveyOptionDelete.size()>0){
            surveyFormOptionRepository.deleteAll(listSurveyOptionDelete);
        }

        if(listSurveyOptionCreate.size()>0){
            surveyFormOptionRepository.saveAll(listSurveyOptionCreate);
        }





    }

    @Override
    public void deleteSurveyFormRate(Long id) {
        ItemFormRate searchFormRate;
        searchFormRate = new ItemFormRate();
        searchFormRate=getSurveyFormRate(id);
        if(CheckUtil.isNullOrEmpty(searchFormRate)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_FORM_RATE_ID));
        }

        surveyFormRepository.deleteById(id);
    }

    public ItemPeriodic convertSurveyPeriodicFormToVo(SurveyPeriodic surveyPeriodic){
        ItemPeriodic itemResult = new ItemPeriodic();
        if(!CheckUtil.isNullOrEmpty(surveyPeriodic)){
            itemResult.setId(surveyPeriodic.getId());
            itemResult.setTitle(surveyPeriodic.getTitle());
            return itemResult;
        }
        return null;
    }

    public ItemMail convertSurveyMailFormToVo(SurveyMailForm surveyMail){
        ItemMail itemResult = new ItemMail();
        if(!CheckUtil.isNullOrEmpty(surveyMail)){
            itemResult.setId(surveyMail.getId());
            itemResult.setTitle(surveyMail.getTitle());
            return itemResult;
        }
        return null;
    }

    public List<ItemFormOption> convertSurveyFormOptionsToVo(List<SurveyFormOption> surveyFormList){

        if(!CheckUtil.isNullOrEmpty(surveyFormList)){

            return surveyFormList.stream().map(o->{
                ItemFormOption itemFormOption = new ItemFormOption();
                itemFormOption.setId(o.getId());
                itemFormOption.setContent(o.getContent());
                return itemFormOption;
            }).collect(Collectors.toList());

        }
        return null;
    }
}
