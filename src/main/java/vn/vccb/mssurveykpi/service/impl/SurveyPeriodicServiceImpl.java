package vn.vccb.mssurveykpi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.dao.SurveyPeriodicRepository;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;
import vn.vccb.mssurveykpi.exception.BusinessException;
import vn.vccb.mssurveykpi.service.SurveyPeriodicService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.ItemPeriodic;
import vn.vccb.mssurveykpi.vo.surveyPeriodic.Register;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyPeriodicServiceImpl implements SurveyPeriodicService {

    @Autowired
    SurveyPeriodicRepository surveyPeriodicRepository;

    @Override
    public Long registerSurveyPeriodic(Register item) {

        SurveyPeriodic enity = new SurveyPeriodic();
        enity.setTitle(item.getTitle());
        enity.setStatus(Boolean.TRUE);
        enity.setCreated_by(new Date());
        surveyPeriodicRepository.save(enity);

        return enity.getId();

    }

    @Override
    public ItemPeriodic getItemSurveyPeriodic(Long id) {

        ItemPeriodic itemResult = new ItemPeriodic();
        SurveyPeriodic enity = surveyPeriodicRepository.findById(id).orElse(null);
        if(CheckUtil.isNullOrEmpty(enity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.VALIDATE_INPUT_ID_ISSET));
        }
        itemResult = convertSurveyPeriodicToVo(enity);

        return itemResult;
    }

    @Override
    public List<ItemPeriodic> getAllSurveyPeriodic() {

        List<ItemPeriodic> items = new ArrayList<ItemPeriodic>();

        List<SurveyPeriodic> enityList = surveyPeriodicRepository.getByStatusTrue();

        return enityList.stream().map(o->{
             return convertSurveyPeriodicToVo(o);

        }).sorted(Comparator.comparingLong(ItemPeriodic::getId).reversed()).collect(Collectors.toList());


    }


    protected ItemPeriodic convertSurveyPeriodicToVo(SurveyPeriodic enity){

        ItemPeriodic itemResult = new ItemPeriodic();

        if(!CheckUtil.isNullOrEmpty(itemResult)){
            itemResult.setId(enity.getId());
            itemResult.setTitle(enity.getTitle());
            return itemResult;
        }

        return null;
    }
}
