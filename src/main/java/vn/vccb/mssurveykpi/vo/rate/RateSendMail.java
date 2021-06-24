package vn.vccb.mssurveykpi.vo.rate;

import lombok.Data;

import java.io.Serializable;

@Data
public class RateSendMail implements Serializable {
    private static final long serialVersionUID = 1412432197761372281L;
    private String period;
    private String cycle;
    private String cardStartYear;
    private String form;
}
