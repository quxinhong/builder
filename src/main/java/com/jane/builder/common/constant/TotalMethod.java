package com.jane.builder.common.constant;

import java.util.*;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

public class TotalMethod {

    public static final String SUM = "SUM";

    public static final String COUNT = "COUNT";

    public static final String comNo = "totalMethod";

    public static final CommonModel COMMON = new CommonModel();

    public static final List<CommonItemModel> items = new ArrayList<>();

    public static final Map<String, String> methodSqlMapping = new HashMap<>();

    static {
        methodSqlMapping.put(SUM, " SUN(%s) %s ");
        methodSqlMapping.put(COUNT, " COUNT(%s) %s ");
        COMMON.setComNo(comNo);
        COMMON.setComName("合计行算法");
        COMMON.setEditable(false);
        COMMON.setCreateDate(new Date());
        COMMON.setCreateUser(C.SYSTEM);
        items.add(new CommonItemModel(comNo, SUM, "SUM", 1, null, null, null));
        items.add(new CommonItemModel(comNo, COUNT, "COUNT", 2, null, null, null));
    }
}
