package dev.resent.util.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class FuncUtils {

    public static <T> boolean removeIf(Collection<T> collection, Predicate<T> pre) {
        boolean ret = false;
        Iterator<T> itr = collection.iterator();
        while (itr.hasNext()) {
            if (pre.test(itr.next())) {
                itr.remove();
                ret = true;
            }
        }
        return ret;
    }

    public static boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (mouseX > x && mouseX < (x + width)) && (mouseY > y && mouseY < (y + height));
    }   
    
}
