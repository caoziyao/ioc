package com.flask.mybatis;

/**
 * Description: mapper sql的封装对象
 *      对应 mapper.xml 的一个 sql 节点
 * @author csy
 * @version 1.0.0
 * @since 2020/11/21
 */
public final class MappedStatement {

    private String namespace;

    private String sourceId;

    private String resultType;

    private String sql;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
