package org.yiming.localtools.conversion;

public class FloatUtil {

    public static boolean isNull(Float flo){
        if(flo == null || flo.equals(0F)){
            return true;
        }
        return false;
    }

}
