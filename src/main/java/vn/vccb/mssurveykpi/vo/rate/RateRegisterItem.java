package vn.vccb.mssurveykpi.vo.rate;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class RateRegisterItem implements Serializable {

    private static final long serialVersionUID = 1412432197761372281L;
    private String period;
    private String cycle;
    private String cardStartYear;
    private String form;
    private MultipartFile file;

}
