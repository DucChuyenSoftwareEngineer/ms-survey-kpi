package vn.vccb.mssurveykpi.sco;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationSco implements Serializable {

    private static final long serialVersionUID = -5333070861500967320L;

    private Integer page;

    private Integer limit;

    private String sortColumn;

    private String sortOrder;
}
