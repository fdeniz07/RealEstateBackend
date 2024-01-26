package com.prettier.config;

import java.util.IdentityHashMap;

public class CycleAvoidingMappingContext {

    /*
        sonsuz bir döngü içine giren ve StackOverflowError'a yol açan bir döngüsel referans olabilir.
        Bu genellikle iki sınıf arasında karşılıklı referanslar oluşturulduğunda ortaya çıkar.
        MapStruct kullanırken, sınıflar arasında karşılıklı referanslar oluşturulabilir ve bu,
        sonsuz bir döngüye yol açabilir. Bu durumu engellemek için MapStruct'te @Context kullanarak
        bir CycleAvoidingMappingContext oluşturabilirsiniz.
     */
    private IdentityHashMap<Object, Object> knownInstances = new IdentityHashMap<>();

    public <T> T getMappedInstance(Object source, Class<T> targetClass) {
        return (T) knownInstances.get(getKey(source, targetClass));
    }

    public void storeMappedInstance(Object source, Object destination) {
        knownInstances.put(getKey(source, destination.getClass()), destination);
    }

    private Object getKey(Object source, Class<?> targetClass) {
        return source.getClass().getName() + "@" + targetClass.getName();
    }
}
