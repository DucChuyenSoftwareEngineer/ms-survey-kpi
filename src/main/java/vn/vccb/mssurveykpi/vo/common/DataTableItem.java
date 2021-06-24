package vn.vccb.mssurveykpi.vo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DataTableItem implements Serializable {

    private static final long serialVersionUID = 2549929415486782209L;

    private List data;

    private Long total;
}

