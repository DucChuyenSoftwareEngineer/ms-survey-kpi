package vn.vccb.mssurveykpi.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import vn.vccb.mssurveykpi.common.CommonConstant;
import vn.vccb.mssurveykpi.sco.PaginationSco;

import java.lang.reflect.Field;
import java.util.List;

public class SortUtil {

    public static void setCriteriaSortInfo(Criteria criteria, PaginationSco sco) {
        Order order;

        if (!CheckUtil.isNullOrEmpty(sco.getLimit())) {
            criteria.setMaxResults(sco.getLimit());
        }
        if (!CheckUtil.isNullOrEmpty(sco.getPage())) {
            criteria.setFirstResult((sco.getPage() - 1) * sco.getLimit());
        }

        if (!CheckUtil.isNullOrEmpty(sco.getSortOrder())
                && !CheckUtil.isNullOrEmpty(sco.getSortColumn())) {
            if (CommonConstant.SORT_ASC.equals(sco.getSortOrder())) {
                order = Order.asc(sco.getSortColumn());
            } else {
                order = Order.desc(sco.getSortColumn());
            }

            criteria.addOrder(order.ignoreCase());
        }
    }

    public static void fillIndex(List list, Integer page, Integer limit) {
        fillIndex(list,  page, limit, null);
    }

    public static void fillIndex(List list, Integer page, Integer limit, String columnName) {
        int start;
        Field field;

        start = 0;
        if (!CheckUtil.isNullOrEmpty(page)
                && !CheckUtil.isNullOrEmpty(limit)) {
            start = (page - 1) * limit;
        }

        if (CheckUtil.isNullOrEmpty(columnName)) {
            columnName = "index";
        }

        try {
            for (int i = 0; i < list.size(); i++) {
                field = list.get(i).getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(list.get(i), start + i + 1);
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }
}
