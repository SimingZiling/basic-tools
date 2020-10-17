package org.yiming.localtools.conversion;

public class LongUtil {

    public static boolean isNull(Long lon){
        if(lon == null || lon.equals(0L)){
            return true;
        }
        return false;
    }

}
