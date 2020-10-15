package cn.gp.designpattern.f.decoration.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class JsonLoggerFactory {

    public static JsonLogger getLogger(Class clazz){
        Logger logger = LoggerFactory.getLogger(clazz);
        return new JsonLogger(logger);
    }
}