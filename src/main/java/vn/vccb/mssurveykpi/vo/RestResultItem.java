package vn.vccb.mssurveykpi.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class RestResultItem implements Serializable {

    private static final long serialVersionUID = -7241172283245733555L;

    private Object data;

    private Map<String, String> listMessage;

    public RestResultItem() {
        listMessage = new HashMap<>();
    }
}
