package cn.gp.distributed;

/**
 * @author hongzhou.wei
 * @date 2021/1/5
 */
public enum Demo {
    /*A("whz"),B("zz");
    String name;

    Demo(String s) {

    }*/
    A("whz", "s"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    B("whz", "s");

    Demo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String name;
    String type;
}


