package com.yonyou.commodity.dto;

import cn.hutool.core.util.ReflectUtil;
import com.yonyou.iuap.baseservice.support.condition.Condition;
import com.yonyou.iuap.baseservice.support.condition.Match;
import com.yonyou.iuap.ucf.common.entity.Identifier;
import com.yonyou.iuap.ucf.common.rest.SearchParams;
import com.yonyou.iuap.ucf.core.utils.EntityUtil;
import com.yonyou.iuap.ucf.dao.support.SqlParam;
import com.yonyou.iuap.ucf.dao.utils.FieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

 /**
  * 用于构造查询条件
  * @since v5.0.0
  * @date 2019-10-8 17:00:35
  */
public class SimpleSearchDTO {
    private static Logger logger = LoggerFactory.getLogger(SimpleSearchDTO.class);
    private static String PARAM_SEARCH_PREFIX = "search_";
        private String search_price;     //商品价格

        public void setSearch_price(String price){
            this.search_price = price;
        }
        public String getSearch_price(){
            return this.search_price;
        }

        private String search_content;     //商品内容

        public void setSearch_content(String content){
            this.search_content = content;
        }
        public String getSearch_content(){
            return this.search_content;
        }

        private String search_effacicy;     //商品功效

        public void setSearch_effacicy(String effacicy){
            this.search_effacicy = effacicy;
        }
        public String getSearch_effacicy(){
            return this.search_effacicy;
        }

        private String search_name;     //商品名称

        public void setSearch_name(String name){
            this.search_name = name;
        }
        public String getSearch_name(){
            return this.search_name;
        }

        private String search_brand;     //商品品牌

        public void setSearch_brand(String brand){
            this.search_brand = brand;
        }
        public String getSearch_brand(){
            return this.search_brand;
        }

        private String search_type;     //商品类型

        public void setSearch_type(String type){
            this.search_type = type;
        }
        public String getSearch_type(){
            return this.search_type;
        }

        private String search_link;     //商品链接

        public void setSearch_link(String link){
            this.search_link = link;
        }
        public String getSearch_link(){
            return this.search_link;
        }


        private String search_NA;     //外键

        public void setSearch_NA(String fk){
            this.search_NA = fk;
        }
        public String getSearch_NA(){
            return this.search_NA;
        }

    private Set<String> likeFields = new HashSet<>(); // 使用like的field名
    private Set<String> inFields = new HashSet<>();  //使用in的field名
    private LinkedHashMap<String, String> sorts = new LinkedHashMap<>();

    public Set<String> getLikeFields() {
        return likeFields;
    }

    public void setLikeFields(Set<String> likeFields) {
        this.likeFields = likeFields;
    }

    public Set<String> getInFields() {
        return inFields;
    }

    public void setInFields(Set<String> inFields) {
        this.inFields = inFields;
    }

    public LinkedHashMap<String, String> getSorts() {
        return sorts;
    }

    public void setSorts(LinkedHashMap<String, String> sorts) {
        this.sorts = sorts;
    }


    public SearchParams toSearchParams(Class<? extends Identifier> entityClass) {

        String table=EntityUtil.getTableName(entityClass);
        SqlParam result = SqlParam.of().table(table);
        for (Field searchField : ReflectUtil.getFields(SimpleSearchDTO.class)) {
            if (ReflectUtil.getFieldValue(this, searchField) != null) {
                Field keyField;
                if (searchField.getName().toLowerCase().startsWith(PARAM_SEARCH_PREFIX)) {
                    keyField = ReflectUtil.getField(entityClass, searchField.getName().replace(PARAM_SEARCH_PREFIX, ""));
                } else {
                    keyField = ReflectUtil.getField(entityClass, searchField.getName());
                }
                if (keyField != null) {
                    Condition cond = keyField.getAnnotation(Condition.class);
                    String keyCol = FieldUtil.getColumnName(keyField);
                    if (cond == null || cond.match() == Match.EQ) {
                        result.eq(keyCol, ReflectUtil.getFieldValue(this, searchField));
                    } else if (cond.match() == Match.IN) {
                        try {
                            @SuppressWarnings("unchecked")
                            List<Object> ls = (List<Object>) ReflectUtil.getFieldValue(this, searchField);
                            result.in(keyCol, ls);
                        } catch (Exception e) {
                            logger.error("error happened while reading IN param from search params:" + keyField.getName(), e);
                        }
                    } else if (cond.match() == Match.LIKE) {
                        try {
                            result.like(keyCol,ReflectUtil.getFieldValue(this, searchField).toString());
                        } catch (Exception e) {
                            logger.error("error happened while reading Like param from search params:" + keyField.getName(), e);
                        }
                    } else if (cond.match() == Match.BETWEEN) {
                        try {
                            Object[] values = (Object[]) ReflectUtil.getFieldValue(this, searchField);
                            result.between(keyCol, values[0], values[1]);
                        } catch (Exception e) {
                            logger.error("error happened while reading BETWEEN param from search params:" + keyField.getName(), e);
                        }
                    } else {
                        result.and(keyCol + " " + cond.match()+ " "  + ReflectUtil.getFieldValue(this, searchField));
                    }

                }

            }

        }


        return result;
    }
}
