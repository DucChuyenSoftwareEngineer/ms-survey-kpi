package vn.vccb.mssurveykpi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.dao.SurveyMailDao;
import vn.vccb.mssurveykpi.dao.SurveyMailFormRepository;
import vn.vccb.mssurveykpi.enity.SurveyMailForm;
import vn.vccb.mssurveykpi.exception.BusinessException;
import vn.vccb.mssurveykpi.sco.SurveyMailSco;
import vn.vccb.mssurveykpi.service.SurveyMailService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.util.SortUtil;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMail;
import vn.vccb.mssurveykpi.vo.surveyFormMail.ItemMailService;
import vn.vccb.mssurveykpi.vo.surveyFormMail.Register;
import vn.vccb.mssurveykpi.vo.surveyFormMail.SurveyMailPagingItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyMailServiceImpl implements SurveyMailService {

    @Autowired
    SurveyMailFormRepository surveyMailFormRepository;

    @Autowired
    SurveyMailDao surveyMailDao;

    @Override
    public List<ItemMailService> getAllSurveyMail() {

        List<ItemMail> items = new ArrayList<ItemMail>();
        List<SurveyMailForm> enityList = surveyMailFormRepository.getByStatusTrue();


        return enityList
                .stream()
                .map(o-> {
                    return convertSurveyMailFormServiceToVo(o);
                })
                .sorted(Comparator.comparingLong(ItemMailService::getId).reversed())
                .collect(Collectors.toList());
    }



    @Override
    public Long registerSurveyMail(Register item) {

        SurveyMailForm enity = new SurveyMailForm();
        enity.setContent(item.getContent());
        enity.setName(item.getName());
        enity.setTitle(item.getTitle());
        enity.setStatus(Boolean.TRUE);
        enity.setCreated_by(new Date());
        surveyMailFormRepository.save(enity);

        return enity.getId();
    }

    @Override
    public ItemMail getSurveyMail(Long id) {
        ItemMail itemResult = new ItemMail();

        SurveyMailForm enity = surveyMailFormRepository.findById(id).orElse(null);

        itemResult = convertSurveyMailFormToVo(enity);

        return itemResult;


    }

    @Override
    public void updatedSurveyMail(Long id,Register item) {

        SurveyMailForm enity = surveyMailFormRepository.findById(id).orElse(null);

        if(CheckUtil.isNullOrEmpty(enity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.VALIDATE_INPUT_ID_ISSET));
        }

        enity.setContent(item.getContent());
        enity.setName(item.getName());
        enity.setTitle(item.getTitle());
        enity.setUpdated_by(new Date());
        surveyMailFormRepository.save(enity);

    }

    @Override
    public void deleteDepartment(Long id) {
        ItemMail itemResult = getSurveyMail(id);

        if(CheckUtil.isNullOrEmpty(itemResult)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.VALIDATE_INPUT_ID_ISSET));
        }
        surveyMailFormRepository.deleteById(id);

    }

    @Override
    public Long countSurveyMailPaging(SurveyMailSco sco) {
        return surveyMailDao.count(sco);
    }

    @Override
    public List<SurveyMailPagingItem> getSurveyMailPaging(SurveyMailSco sco) {
        SurveyMailPagingItem voItem;
        List<SurveyMailForm> lstEnity;
        List<SurveyMailPagingItem> lstResult;

        lstResult = new ArrayList<>();
        lstEnity= surveyMailDao.get(sco);

        if(!CheckUtil.isNullOrEmptyCollection(lstEnity)){
            for(SurveyMailForm item : lstEnity){
                voItem = new SurveyMailPagingItem();
                voItem.setId(item.getId());
                voItem.setContent(item.getContent());
                voItem.setName(item.getName());
                voItem.setTitle(item.getTitle());
                voItem.setCreated_by(item.getCreated_by());
                lstResult.add(voItem);
            }
            SortUtil.fillIndex(lstResult, sco.getPage(), sco.getLimit());
        }


        return lstResult;
    }




    // convert vo <-> enity

    protected ItemMail convertSurveyMailFormToVo(SurveyMailForm enity) {
        ItemMail itemResult = new ItemMail();
        if(!CheckUtil.isNullOrEmpty(enity)){
            itemResult.setId(enity.getId());
            itemResult.setContent(enity.getContent());
            itemResult.setName(enity.getName());
            itemResult.setTitle(enity.getTitle());
            itemResult.setUpdated_by(enity.getUpdated_by());
            itemResult.setCreated_by(enity.getCreated_by());
            return itemResult;
        }

        return null;

    }


    protected ItemMailService convertSurveyMailFormServiceToVo(SurveyMailForm enity) {
        ItemMailService itemResult = new ItemMailService();
        if(!CheckUtil.isNullOrEmpty(enity)){

            itemResult.setId(enity.getId());
            itemResult.setTitle(enity.getTitle());
            return itemResult;
        }

        return null;

    }
}
