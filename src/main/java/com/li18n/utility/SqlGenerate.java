package com.li18n.utility;

import com.alibaba.fastjson.JSONObject;
import com.li18n.utility.annotation.BeanLi18n;
import com.li18n.utility.annotation.ID;
import com.li18n.utility.annotation.Table;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public class SqlGenerate {


    private Object target;

    private List<Field> fields;

    public SqlGenerate(@NotNull Object target) {
        this.target = target;
        fields = new Vector<Field>();
        getFields(target.getClass());
    }

    public List<String> getLi18nKey() {
        List<String> li18nKey = new ArrayList<String>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(BeanLi18n.class)) {
                li18nKey.add(field.getAnnotation(BeanLi18n.class).Li18n());
            }
        }
        return (ArrayList<String>) li18nKey.stream().distinct().collect(Collectors.toList());
    }


    public ModelToSQL getModelToSQL(Map<String, String> key) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(BeanLi18n.class)) {
                String attributeName = field.getName();
                String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
                BeanSetProperty(field,"set" + methodName,key.get(field.getAnnotation(BeanLi18n.class).Li18n()));
            }else if (field.isAnnotationPresent(ID.class)){
                String attributeName = field.getName();
                String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
                ID id=field.getAnnotation(ID.class);
                BeanSetProperty(field,"set" + methodName,id.Li18n()+key.get(id.name()));
            }
        }
        return ModelToSQLInternal();
    }

    private void BeanSetProperty(Field field,String methodName,String value) {
            try {
                Method setMethod = target.getClass().getMethod("set" + methodName, String.class);
                setMethod.invoke(target, value);
            } catch (NoSuchMethodException e) {
                try {
                    field.setAccessible(true);
                    field.set(target, value);
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

    }


    protected ModelToSQL ModelToSQLInternal() {
        ModelToSQL modelToSQL = new ModelToSQL(this.target, this.fields);
        return modelToSQL;
    }

    private void getFields(Class<?> clazz) {
        if (Object.class.equals(clazz)) {
            return;
        }
        Field[] fieldArray = clazz.getDeclaredFields();
        for (Field file : fieldArray) {
            fields.add(file);
        }
    }

    public static class ModelToSQL {

        private Object target;

        private List<String> param;

        private List<Field> fields;

        private String tableName;

        protected ModelToSQL(Object target, List<Field> fields) {
            super();
            param = new Vector<String>();
            this.fields = fields;
            this.target = target;
            tableName = getTableName();
        }

        /**
         * 创建跟删除
         */
        public String createDelete() {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("DELETE FROM ").append(tableName).append(" WHERE ");
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    ID id = field.getAnnotation(ID.class);
                    if (null != id) {
                        sqlBuffer.append(field.getName()).append(" = ").append(JSONObject.toJSONString(readField(field)));
                    }
                }
            }
            return sqlBuffer.toString();
        }


        /**
         * 创建查询基于id
         */
        public String createSelect() {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("SELECT * from").append(tableName).append(" WHERE ");
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    ID id = field.getAnnotation(ID.class);
                    if (null != id) {
                        sqlBuffer.append(field.getName()).append(" = ").append(JSONObject.toJSONString(readField(field)));
                    }
                }
            }
            return sqlBuffer.toString();
        }


        /**
         * 创建更新语句
         */
        public String createUpdate() {
            String idName = null;
            Object idValue = null;
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("UPDATE ").append(tableName).append(" SET ");
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    ID id = field.getAnnotation(ID.class);
                    if (id == null) {
                        sqlBuffer.append(field.getName()).append("=").append(JSONObject.toJSONString(readField(field))).append(", ");
                    } else {
                        idName = field.getName();
                        idValue = readField(field);
                    }
                }
            }
            sqlBuffer.replace(sqlBuffer.length() - 2, sqlBuffer.length() - 1, " ");
            if (idName == null) {
                throw new RuntimeException("not found of " + target.getClass() + "'s ID");
            }
            sqlBuffer.append(" WHERE ").append(idName).append("=").append(JSONObject.toJSONString(idValue));

            return sqlBuffer.toString();
        }


        /**
         * 创建插入语句
         */
        public String createInsert() {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append("INSERT INTO ").append(tableName).append("(");
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    ID id = field.getAnnotation(ID.class);
                    if (id == null) {
                        sqlBuffer.append(field.getName()).append(",");
                        param.add(JSONObject.toJSONString(readField(field)));
                    }
                }
            }
            int length = sqlBuffer.length();
            sqlBuffer.delete(length - 1, length).append(")values(");
            int size = param.size();
            for (int x = 0; x < size; x++) {
                if (x != 0) {
                    sqlBuffer.append(",");
                }
                if (null != param.get(x)) {
                    sqlBuffer.append(param.get(x));
                } else {
                    sqlBuffer.append("");
                }

            }
            sqlBuffer.append(")");
            System.err.println("insert:\t" + sqlBuffer.toString());
            return sqlBuffer.toString();
        }


        private Object readField(Field field) {
            try {
                return FieldUtils.readField(field, target, true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private String getTableName() {
            String tableName = null;
            Class<?> clazz = target.getClass();
            tableName = getTableNameForClass(clazz);
            return tableName;
        }

        private String getTableNameForClass(@NotNull Class<?> clazz) {
            String tableName;
            Table table = clazz.getAnnotation(Table.class);
            if (null != table) {
                tableName = table.name();
                if ("".equalsIgnoreCase(tableName)) {
                    tableName = clazz.getSimpleName();
                }
            } else {
                tableName = clazz.getSimpleName();
            }
            return tableName;
        }

    }


}
