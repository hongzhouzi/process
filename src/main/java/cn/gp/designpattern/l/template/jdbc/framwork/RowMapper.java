package cn.gp.designpattern.l.template.jdbc.framwork;

import java.sql.ResultSet;

/**
 * @author hongzhou.wei
 * @date 2020/10/17
 */
public interface RowMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws Exception;

}
