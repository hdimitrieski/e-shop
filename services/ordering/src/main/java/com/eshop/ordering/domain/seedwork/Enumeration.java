package com.eshop.ordering.domain.seedwork;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Enumeration {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    public String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    public int id;

    protected Enumeration(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

//    public static IEnumerable<T> GetAll<T>() where T : Enumeration
//    {
//        var fields = typeof(T).GetFields(BindingFlags.Public | BindingFlags.Static | BindingFlags.DeclaredOnly);
//
//        return fields.Select(f => f.GetValue(null)).Cast<T>();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enumeration that = (Enumeration) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static int absoluteDifference(Enumeration firstValue, Enumeration secondValue)
    {
        return Math.abs(firstValue.getId() - secondValue.getId());
    }

//    public static <T extends Enumeration> T fromValue(int value) {
//
//        var matchingItem = Parse<T, int>(value, "value", item => item.Id == value);
////        return matchingItem;
//        Collections
//        return 1;
//    }

//    public static T FromDisplayName<T>(string displayName) where T : Enumeration
//    {
//        var matchingItem = Parse<T, string>(displayName, "display name", item => item.Name == displayName);
//        return matchingItem;
//    }
//
//    private static <T extends Enumeration, K> T Parse(K value, string description, Predicate<T> predicate){
//        var matchingItem = GetAll<T>().FirstOrDefault(predicate);
//
//        if (matchingItem == null)
//            throw new InvalidOperationException($"'{value}' is not a valid {description} in {typeof(T)}");
//
//        return matchingItem;
//    }
//
//    public int CompareTo(object other) => Id.CompareTo(((Enumeration)other).Id);
}
