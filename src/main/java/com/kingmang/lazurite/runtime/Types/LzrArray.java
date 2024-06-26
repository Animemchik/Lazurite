package com.kingmang.lazurite.runtime.Types;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LzrValue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class LzrArray implements LzrValue, Iterable<LzrValue> {

    public static LzrArray of(byte[] array) {
        final int size = array.length;
        final LzrArray result = new LzrArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, LzrNumber.of(array[i]));
        }
        return result;
    }

    public static LzrArray of(String[] array) {
        final int size = array.length;
        final LzrArray result = new LzrArray(size);
        for (int i = 0; i < size; i++) {
            result.set(i, new LzrString(array[i]));
        }
        return result;
    }
    
    public static LzrArray add(LzrArray array, LzrValue value) {
        final int last = array.elements.length;
        final LzrArray result = new LzrArray(last + 1);
        System.arraycopy(array.elements, 0, result.elements, 0, last);
        result.elements[last] = value;
        return result;
    }
    
    public static LzrArray merge(LzrArray array1, LzrArray array2) {
        final int length1 = array1.elements.length;
        final int length2 = array2.elements.length;
        final int length = length1 + length2;
        final LzrArray result = new LzrArray(length);
        System.arraycopy(array1.elements, 0, result.elements, 0, length1);
        System.arraycopy(array2.elements, 0, result.elements, length1, length2);
        return result;
    }
    
    public static LzrString joinToString(LzrArray array, String delimiter, String prefix, String suffix) {
        final StringBuilder sb = new StringBuilder();
        for (LzrValue value : array) {
            if (sb.length() > 0) sb.append(delimiter);
            else sb.append(prefix);
            sb.append(value.asString());
        }
        sb.append(suffix);
        return new LzrString(sb.toString());
    }
    
    private final LzrValue[] elements;

    public LzrArray(int size) {
        this.elements = new LzrValue[size];
    }

    public LzrArray(LzrValue[] elements) {
        this.elements = new LzrValue[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }
    
    public LzrArray(List<LzrValue> values) {
        final int size = values.size();
        this.elements = values.toArray(new LzrValue[size]);
    }
    
    public LzrArray(LzrArray array) {
        this(array.elements);
    }

    public LzrValue[] getCopyElements() {
        final LzrValue[] result = new LzrValue[elements.length];
        System.arraycopy(elements, 0, result, 0, elements.length);
        return result;
    }
    
    @Override
    public int type() {
        return Types.ARRAY;
    }

    public int size() {
        return elements.length;
    }

    public LzrValue get(int index) {
        return elements[index];
    }

    public LzrValue get(LzrValue index) {
        final String prop = index.asString();
        switch (prop) {
            case "length":
                return LzrNumber.of(size());

            case "isEmpty":
                return Converters.voidToBoolean(() -> size() == 0);
            default:
                return get(index.asInt());
        }
    }


    public void set(int index, LzrValue value) {
        elements[index] = value;
    }

    @Override
    public Object raw() {
        return elements;
    }

    @Override
    public int asInt() {
        throw new LZRException("TypeExeption","Cannot cast array to integer");
    }

    @Override
    public double asNumber() {
        throw new LZRException("TypeExeption","Cannot cast array to number");
    }

    @Override
    public String asString() {
        return Arrays.toString(elements);
    }

    @Override
    public int[] asArray() {
        return new int[0];
    }

    @Override
    public Iterator<LzrValue> iterator() {
        return Arrays.asList(elements).iterator();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Arrays.deepHashCode(this.elements);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())
            return false;
        final LzrArray other = (LzrArray) obj;
        return Arrays.deepEquals(this.elements, other.elements);
    }

    @Override
    public int compareTo(LzrValue o) {
        if (o.type() == Types.ARRAY) {
            final int lengthCompare = Integer.compare(size(), ((LzrArray) o).size());
            if (lengthCompare != 0) return lengthCompare;
        }
        return asString().compareTo(o.asString());
    }

    @Override
    public String toString() {
        return asString();
    }
}
