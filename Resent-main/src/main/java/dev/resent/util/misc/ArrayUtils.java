package dev.resent.util.misc;

import net.minecraft.src.KeyBinding;

@SuppressWarnings("all")
public class ArrayUtils {
    public static <T> KeyBinding[] clone(final KeyBinding[] array) {
        return array != null ? array.clone() : null;
    }

    public static <T> KeyBinding[] addAll(final KeyBinding[] array1, final KeyBinding... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }

        final KeyBinding[] joinedArray = new KeyBinding[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
}
