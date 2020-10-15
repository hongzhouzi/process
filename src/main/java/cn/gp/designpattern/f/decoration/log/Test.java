package cn.gp.designpattern.f.decoration.log;

import org.slf4j.Logger;

import java.io.*;

/**
 * @author hongzhou.wei
 * @date 2020/10/15
 */
public class Test {
    private static final Logger logger = JsonLoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
//        logger.error("系统错误");

        logger.info("info");
        try {
            InputStream in = new FileInputStream("");

            BufferedInputStream bis = new BufferedInputStream(in);

            bis.read();
            bis.close();

            BufferedReader br = new BufferedReader(new FileReader(""));
            br.readLine();

            BufferedReader bs = new BufferedReader(new StringReader(""));
            bs.readLine();

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.toString());
        }

    }
}
