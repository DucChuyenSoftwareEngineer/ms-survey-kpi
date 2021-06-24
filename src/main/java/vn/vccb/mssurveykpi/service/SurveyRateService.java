package vn.vccb.mssurveykpi.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import vn.vccb.mssurveykpi.sco.SurveyRateSco;
import vn.vccb.mssurveykpi.vo.rate.RateBrowser;
import vn.vccb.mssurveykpi.vo.rate.RateBrowserPagingItem;
import vn.vccb.mssurveykpi.vo.rate.RateRegisterItem;
import vn.vccb.mssurveykpi.vo.rate.RateSendMail;

import java.io.IOException;
import java.util.List;

public interface SurveyRateService {

    List<RateBrowser> uploadExcelFullStock(RateRegisterItem item) throws IOException, InvalidFormatException;

    Integer sendMail(RateSendMail item);

    Long countSurveyRateBrowserMailPaging(SurveyRateSco sco);

    List<RateBrowserPagingItem> getSurveyRateBrowserMailPaging(SurveyRateSco sco);
}
