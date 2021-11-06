package showcase;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;


/**
 * Random Object generator
 * https://tuhrig.de/create-random-test-objects-with-java-reflection/
 */
public class RandomObjectFiller {

    private static Random random = new Random();

    public static <T> T createAndFill(Class<T> clazz) throws Exception {
        if( Collection.class.isAssignableFrom(clazz)) return clazz.getDeclaredConstructor().newInstance();
        T instance = clazz.getDeclaredConstructor().newInstance();
        for(Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = getRandomValueForField(field);
            field.set(instance, value);
        }
        return instance;
    }

    private static Object getRandomValueForField(Field field) throws Exception {
        Class<?> type = field.getType();

        // Note that we must handle the different types here! This is just an
        // example, so this list is not complete! Adapt this to your needs!
        if(type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if(type.equals(String.class)) {
            return UUID.randomUUID().toString();
        } else if(type.equals(BigInteger.class)){
            return BigInteger.valueOf(random.nextInt());
        }
        return createAndFill(type);
    }
}