/*
package cn.gp.designpattern.d.proxy.dynamic.write;

import cn.gp.designpattern.d.proxy.dynamic.jdk.ISubject;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class $Proxy0 implements ISubject {
    MyInvocationHandler h;

    public $Proxy0(MyInvocationHandler var1) {
        this.h = var1;
    }

    public void business() {
        try {
            Method var1 = ISubject.class.getMethod("business");
            this.h.invoke(this, var1, new Object[0]);
        } catch (Error var2) {
            ;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }

    }
}
*/
