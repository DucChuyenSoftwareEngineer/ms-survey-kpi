package vn.vccb.mssurveykpi.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SendMailReq {
    private Long emailId;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String content;
    private String maker;
    private String source;
    private String function;
    private Long campaignId;
    private Date makerTime;
    private String recordStat;
    private String recordStatDesc;
    private Integer retry;
    private String emailResult;
    private Date timeConsumer;
    private Date timeProducer;
    private String ref;
    private String rootRef;
    private String ipAddress;
    private String kafkaGroupId;
    private String kafkaTopic;
    private boolean skipDelay;
}
