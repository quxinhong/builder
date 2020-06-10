package com.jane.builder.common.constant;

import java.util.*;

import com.jane.builder.modules.model.CommonItemModel;
import com.jane.builder.modules.model.CommonModel;

public class OrderRule {

    public static final String DESC = "DESC";

    public static final String ASC = "ASC";

    public static final String comNo = "orderRule";

    public static final CommonModel COMMON = new CommonModel();

    public static final List<CommonItemModel> items = new ArrayList<>();

    public static final Map<String, String> ruleSqlMapping = new HashMap<>();

    static {
        ruleSqlMapping.put(ASC, " %s ASC ");
        ruleSqlMapping.put(DESC, " %s DESC ");
        COMMON.setComNo(comNo);
        COMMON.setComName("排序规则");
        COMMON.setEditable(false);
        COMMON.setCreateDate(new Date());
        COMMON.setCreateUser(C.SYSTEM);
        items.add(new CommonItemModel(comNo, ASC, "正序", 1, null, null, null));
        items.add(new CommonItemModel(comNo, DESC, "倒序", 2, null, null, null));
    }
}
