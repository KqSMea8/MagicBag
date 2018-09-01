package org.github.helixcs.algorithm;

import java.util.*;

public class TreeMapImplement {
    // Red-black mechanics

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value, Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *         called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;

            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }
    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }


    static  class MyList<E> extends ArrayList<E> implements Comparable<E>{
        private E e;

        private MyList(){}

        private MyList(E... es){
           this.addAll(Arrays.asList(es));
        }

        @Override
        public int compareTo(E otherE) {

            if(null==otherE){return 1;}
            if(otherE==this||otherE.equals(this)){return 0;}
            if(otherE instanceof  List){
                List otherList = (List) otherE;
                List eList = this;

                if(otherList.size()!=eList.size()){return 1;}
                int size = eList.size();
                for(int i = 0;i<size;i++){
                    if(otherList.get(i).equals(eList.get(i))){
                        return 0;
                    }
                }
                return 1;
            }
            return 1;
        }
    }

    public static void main(String[] args) {


        Set<List> set  = new TreeSet<>();
        List<Integer> a = new MyList<>(1);
        List<Integer> b = new MyList<>(2);
        List<Integer> c = new MyList<>(2);
        List<Integer> d = new MyList<>(3);
        set.add(a);
        set.add(b);
        set.add(c);
        set.add(d);
        System.out.println(set);

    }
}
