package org.atlanmod.salut;

import java.util.ArrayList;
import java.util.Collection;

public class Myclass implements  Cloneable {

    public Collection<String> tags = new ArrayList<>();

    public Myclass clone() throws CloneNotSupportedException {

        Myclass other = (Myclass) super.clone();
        return other;
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        Myclass k = new Myclass();
        k.tags.add(("AAA"));
        Myclass l = k.clone();
        l.tags.stream().forEach(System.out::println);

        System.out.println(String.class.equals("aa".getClass()));
        System.out.println("aa".getClass().isAssignableFrom(String.class));
    }
}