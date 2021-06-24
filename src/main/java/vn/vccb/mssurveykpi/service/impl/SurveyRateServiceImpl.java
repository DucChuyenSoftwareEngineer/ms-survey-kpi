package vn.vccb.mssurveykpi.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vn.vccb.mssurveykpi.common.CommonConstant;
import vn.vccb.mssurveykpi.common.MessageConstant;
import vn.vccb.mssurveykpi.dao.PortalDepartmentRepository;
import vn.vccb.mssurveykpi.dao.PortalUserRepository;
import vn.vccb.mssurveykpi.dao.SurveyFormRepository;
import vn.vccb.mssurveykpi.dao.SurveyPeriodicRepository;
import vn.vccb.mssurveykpi.dao.SurveyRateDetailDao;
import vn.vccb.mssurveykpi.dao.SurveyRateDetailRepository;
import vn.vccb.mssurveykpi.dao.SurveyRateRepository;
import vn.vccb.mssurveykpi.enity.PortalDepartment;
import vn.vccb.mssurveykpi.enity.PortalUser;
import vn.vccb.mssurveykpi.enity.SurveyForm;
import vn.vccb.mssurveykpi.enity.SurveyPeriodic;
import vn.vccb.mssurveykpi.enity.SurveyRate;
import vn.vccb.mssurveykpi.enity.SurveyRateDetail;
import vn.vccb.mssurveykpi.exception.BusinessException;
import vn.vccb.mssurveykpi.model.SendMailReq;
import vn.vccb.mssurveykpi.model.SendMailRes;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;
import vn.vccb.mssurveykpi.service.SurveyRateService;
import vn.vccb.mssurveykpi.util.CheckUtil;
import vn.vccb.mssurveykpi.util.MessageUtil;
import vn.vccb.mssurveykpi.util.SortUtil;
import vn.vccb.mssurveykpi.vo.deparment.DepartmentExcel;
import vn.vccb.mssurveykpi.vo.rate.RateBrowser;
import vn.vccb.mssurveykpi.vo.rate.RateBrowserPagingItem;
import vn.vccb.mssurveykpi.vo.rate.RateRegisterItem;
import vn.vccb.mssurveykpi.vo.rate.RateSendMail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyRateServiceImpl implements SurveyRateService {

    @Autowired
    PortalDepartmentRepository portalDepartmentRepository;

    @Autowired
    PortalUserRepository portalUserRepository;

    @Autowired
    SurveyFormRepository surveyFormRepository;

    @Autowired
    SurveyPeriodicRepository surveyPeriodicRepository;

    @Autowired
    SurveyRateRepository surveyRateRepository;

    @Autowired
    SurveyRateDetailRepository surveyRateDetailRepository;

    @Autowired
    SurveyRateDetailDao surveyRateDetailDao;

    private  RestTemplate restTemplate =new RestTemplate();

    @Override
    @Transactional
    public List<RateBrowser> uploadExcelFullStock(RateRegisterItem item) throws IOException, InvalidFormatException {

        ByteArrayInputStream stream;
        RateBrowser rateBrowser;
        SurveyRateDetail surveyRateDetail;
        List<DepartmentExcel> listDepartmentExcel;
        List<RateBrowser> listResult;
        SurveyForm surveyFormEnity;
        SurveyPeriodic surveyPeriodicEnity;
        SurveyRate surveyRateEnitySearch;
        SurveyRate surveyRateEnity;
        List<SurveyRateDetail> surveyRateDetailEnityList;
        Year itemYear,currentYear;
        LocalDate currentdate = LocalDate.now();

        listResult = new ArrayList<>();
        surveyRateDetailEnityList = new ArrayList<>();
        listDepartmentExcel = new ArrayList<>();
        stream = new  ByteArrayInputStream(item.getFile().getBytes());

        surveyFormEnity = surveyFormRepository.findById(Long.parseLong(item.getForm())).orElse(null);
        surveyPeriodicEnity = surveyPeriodicRepository.findById(Long.parseLong(item.getPeriod())).orElse(null);


         itemYear = Year.of(Integer.parseInt(item.getCardStartYear()));
         currentYear = Year.of(currentdate.getYear());

         // check year input >= input current
         if(itemYear.compareTo(currentYear)<0){
             throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_CARD_START_YEAR));
         }
        // check input form isset db
         if(CheckUtil.isNullOrEmpty(surveyFormEnity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FORM_NOT_ISSET));
         }

         // check input periodic isset db
         if(CheckUtil.isNullOrEmpty(surveyPeriodicEnity)){
             throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_NOT_ISSET));
         }
        // check rule periodic
         if(surveyPeriodicEnity.getId()==5){
             surveyRateEnitySearch = surveyRateRepository.getByCardStartYearAndCycleAndSurveryPeriodicAndSurveyForm(Integer.parseInt(item.getCardStartYear()),Integer.parseInt(item.getCycle()),surveyPeriodicEnity,surveyFormEnity);

         }else if(surveyPeriodicEnity.getId()==1){
             surveyRateEnitySearch = surveyRateRepository.getByCardStartYearAndSurveryPeriodicAndSurveyForm(Integer.parseInt(item.getCardStartYear()),surveyPeriodicEnity,surveyFormEnity);
         }else{
             throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
         }

         if(!CheckUtil.isNullOrEmpty(surveyRateEnitySearch)&& surveyRateEnitySearch.getId()>0){
             throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_ISSET));
         }

         // read excel

        Workbook workbook = WorkbookFactory.create(stream);

        Sheet sheet = workbook.getSheetAt(0);

        // Getting the evaluator
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        for (Row row: sheet) {
            int i = 0;
            DepartmentExcel departmentExcel = new DepartmentExcel();
            for(Cell cell: row) {

                if(cell.getAddress().formatAsString().indexOf("A")<0&&cell.getAddress().formatAsString().indexOf("1")<0){

                    if(i==0){
                        departmentExcel.setUsername((String) getCellValue(cell,evaluator));
                    }else if(i==1){
                        departmentExcel.setDeparmentCode((String) getCellValue(cell,evaluator));
                    }else if(i==2){
                        departmentExcel.setDeparmentName((String) getCellValue(cell,evaluator));
                    }else {
                        throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_UPLOAD_ERROR));
                    }
                    ++i;
                }

            }
            if(!CheckUtil.isNullOrEmpty(departmentExcel)&&!CheckUtil.isNullOrEmpty(departmentExcel.getUsername())){
                listDepartmentExcel.add(departmentExcel);
            }

        }

        if(listDepartmentExcel.isEmpty()){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_UPLOAD_ERROR));
        }


        // Check username file excel with db
        for(DepartmentExcel valueObject : listDepartmentExcel){
            PortalUser portalUser = portalUserRepository.getByUsername(valueObject.getUsername().trim());
            PortalDepartment portalDepartment = portalDepartmentRepository.getByDepartmentCode(valueObject.getDeparmentCode().trim());
            if(CheckUtil.isNullOrEmpty(portalUser)||CheckUtil.isNullOrEmpty(portalDepartment)){
                throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_UPLOAD_ERROR_DATA));
            }
            if(!(portalUser.getId()>0)||!(portalDepartment.getId()>0)){
                throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_UPLOAD_ERROR_DATA));
            }
            // render client
            rateBrowser = new RateBrowser();
            rateBrowser.setUsername(portalUser.getUsername());
            rateBrowser.setCycle(item.getCycle());
            rateBrowser.setDepartment(portalUser.getDepartment().getDepartmentName());
            rateBrowser.setManager(portalUser.getManager());
            rateBrowser.setDepartmentReview(portalDepartment.getDepartmentName());
            rateBrowser.setNameEmpl(portalUser.getFullName());
            rateBrowser.setTitleForm(item.getForm());
            rateBrowser.setUserCreated(new Date().toString());
            rateBrowser.setUserCreated("chuyennd");


            // save system
            surveyRateDetail = new SurveyRateDetail();
            surveyRateDetail.setStatus("CREATE");
            surveyRateDetail.setCreated_by(new Date());
            surveyRateDetail.setUser(portalUser);
            surveyRateDetail.setDepartment(portalDepartment);


            surveyRateDetailEnityList.add(surveyRateDetail);

            listResult.add(rateBrowser);
        }


        if(!(listResult.size()>0&&surveyRateDetailEnityList.size()>0&&listResult.size()==surveyRateDetailEnityList.size())){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_SYSTEM));
        }

        surveyRateEnity = new SurveyRate();
        surveyRateEnity.setSurveyForm(surveyFormEnity);
        surveyRateEnity.setCardStartYear(Integer.parseInt(item.getCardStartYear()));
        surveyRateEnity.setCreated_by(new Date());
        surveyRateEnity.setCycle(item.getCycle());
        surveyRateEnity.setStatus(Boolean.TRUE);
        surveyRateEnity.setSurveryPeriodic(surveyPeriodicEnity);


        surveyRateRepository.save(surveyRateEnity);

        for(int indexModifySurveyRateDetail=0; indexModifySurveyRateDetail<surveyRateDetailEnityList.size();indexModifySurveyRateDetail++){
            
            SurveyRateDetail modifySurveyRateDetail = surveyRateDetailEnityList.get(indexModifySurveyRateDetail);
            modifySurveyRateDetail.setSurveyRate(surveyRateEnity);
            surveyRateDetailEnityList.set(indexModifySurveyRateDetail,modifySurveyRateDetail);
        }

        surveyRateDetailRepository.saveAll(surveyRateDetailEnityList);


        return listResult.stream().limit(10)
                .collect(Collectors.toList());


    }

    @Override
    public Integer sendMail(RateSendMail item) {

        SurveyForm surveyFormEnity;
        SurveyPeriodic surveyPeriodicEnity;
        Year itemYear,currentYear;
        SurveyRate surveyRateEnitySearch;
        List<SurveyRateDetail> surveyRateDetailCreateList;
        surveyRateDetailCreateList = new ArrayList<>();

        int result;

        LocalDate currentdate = LocalDate.now();


        surveyFormEnity = surveyFormRepository.findById(Long.parseLong(item.getForm())).orElse(null);
        surveyPeriodicEnity = surveyPeriodicRepository.findById(Long.parseLong(item.getPeriod())).orElse(null);

        itemYear = Year.of(Integer.parseInt(item.getCardStartYear()));
        currentYear = Year.of(currentdate.getYear());

        // check year input >= input current
        if(itemYear.compareTo(currentYear)<0){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_CARD_START_YEAR));
        }
        // check input form isset db
        if(CheckUtil.isNullOrEmpty(surveyFormEnity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_FORM_NOT_ISSET));
        }

        // check input periodic isset db
        if(CheckUtil.isNullOrEmpty(surveyPeriodicEnity)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC_NOT_ISSET));
        }
        // check rule periodic
        if(surveyPeriodicEnity.getId()==5){
            surveyRateEnitySearch = surveyRateRepository.getByCardStartYearAndCycleAndSurveryPeriodicAndSurveyForm(Integer.parseInt(item.getCardStartYear()),Integer.parseInt(item.getCycle()),surveyPeriodicEnity,surveyFormEnity);

        }else if(surveyPeriodicEnity.getId()==1){
            surveyRateEnitySearch = surveyRateRepository.getByCardStartYearAndSurveryPeriodicAndSurveyForm(Integer.parseInt(item.getCardStartYear()),surveyPeriodicEnity,surveyFormEnity);
        }else{
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_PERIODIC));
        }





        if(CheckUtil.isNullOrEmpty(surveyRateEnitySearch)){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_ISSET));
        }

        if(surveyRateEnitySearch.getSurveyRateDetails().isEmpty()){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_RATE_DETAIL));
        }


        surveyRateDetailCreateList = surveyRateEnitySearch.getSurveyRateDetails().stream()
                                    .filter(o->o.getStatus().equals("CREATE"))
                                    .collect(Collectors.toList());


        if(surveyRateDetailCreateList.isEmpty()){
            throw new BusinessException(MessageUtil.getMessage(MessageConstant.MESSAGE_PARAM_SURVEY_RATE_EXCEL_RATE_DETAIL_MAIL));
        }

        for( SurveyRateDetail surveyRateDetailMail: surveyRateDetailCreateList){
            SendMailReq sendMailReq = SendMailReq.builder()
                    .to(surveyRateDetailMail.getUser().getUsername()+"@vietcapitalbank.com.vn")
                    .subject("Khảo sát  "+surveyRateDetailMail.getSurveyRate().getSurveyForm().getSurveyMailForm().getTitle()+" đang chờ đánh giá")
                    .content(surveyRateDetailMail.getSurveyRate().getSurveyForm().getSurveyMailForm().getContent())
                    .function("send mail Khảo sát")
                    .source("Khảo sát")
                    .makerTime(new Date())
                    .cc("")
                    .bcc("")
                    .build();
            String url = MessageUtil.getMessage(CommonConstant.SERVICE_MAIL) + MessageUtil.getMessage(CommonConstant.SERVICE_MAIL_PATH);

            SendMailRes response = restTemplate.postForObject(url, sendMailReq,  SendMailRes.class);



            if(response.getResultCode().equals(String.valueOf("00"))){
//                surveyRateDetailMail.setStatus("MAIL");
//                surveyRateDetailRepository.save(surveyRateDetailMail);
            }

        }


        result = surveyRateDetailRepository.getByStatus("MAIL").size();



        return result ;
    }

    @Override
    public Long countSurveyRateBrowserMailPaging(SurveyRateSco sco) {

        return surveyRateDetailDao.count(sco);
    }

    @Override
    public List<RateBrowserPagingItem> getSurveyRateBrowserMailPaging(SurveyRateSco sco) {
        RateBrowserPagingItem voItem;
        List<RateBrowserPagingItem> lstResult;
        List<SurveyRateDetail> lstEnity;
        lstResult = new ArrayList<>();
        lstEnity = surveyRateDetailDao.get(sco);

        if(!CheckUtil.isNullOrEmptyCollection(lstEnity)){
            for(SurveyRateDetail item : lstEnity){
                voItem = new RateBrowserPagingItem();
                voItem.setCreated(item.getCreated_by().toString());
                voItem.setCycle(item.getSurveyRate().getSurveryPeriodic().getTitle());
                voItem.setDepartment(item.getUser().getDepartment().getDepartmentName());
                voItem.setDepartmentReview(item.getDepartment().getDepartmentName());
                voItem.setCreated(item.getCreated_by().toString());
                voItem.setManager(item.getUser().getManager());
                voItem.setUsername(item.getUser().getUsername());
                voItem.setNameEmpl(item.getUser().getFullName());
                voItem.setTitleForm(item.getSurveyRate().getSurveyForm().getTitle());
                lstResult.add(voItem);
            }
            SortUtil.fillIndex(lstResult, sco.getPage(), sco.getLimit());
        }
        return lstResult;
    }


    private static Object getCellValue(Cell cell, FormulaEvaluator evaluator) {
        CellValue cellValue = evaluator.evaluate(cell);
        switch (cellValue.getCellTypeEnum()) {
            case BOOLEAN:
                return cellValue.getBooleanValue();
            case NUMERIC:
                return cellValue.getNumberValue();
            case STRING:
                return cellValue.getStringValue();
            case BLANK:
                return "";
            case ERROR:
                return cellValue.getError(cell.getErrorCellValue()).getStringValue();
            // CELL_TYPE_FORMULA will never happen
            case FORMULA:
            default:
                return null;
        }
    }
}
