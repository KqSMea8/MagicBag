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



    private static  TreeStructure root;

    static class TreeStructure<E extends String> {
        private E e;
        private TreeStructure<E> left;
        private TreeStructure<E> right;
        private TreeStructure<E> parent;
        public TreeStructure(){}

        public TreeStructure(E e){
            this.e = e;
        }
        public TreeStructure(E e, TreeStructure parent){
            this.e = e;
            this.parent = parent;
        }

        public TreeStructure(E e, TreeStructure<E> left, TreeStructure<E> right, TreeStructure<E> parent) {
            this.e = e;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public E getE() {
            return e;
        }
        public TreeStructure<E> getLeft() {
            return left;
        }

        public TreeStructure<E> getRight() {
            return right;
        }

        public TreeStructure<E> getParent() {
            return parent;
        }
        public void add(TreeStructure node){
            TreeStructure<E> t = root;
            int cmp;
            TreeStructure parent;

            if(t==null){
                root = node;
                return;
            }
            String currentValue = node.e;

            do{
                parent = t;
                cmp = currentValue.compareTo(t.e);
                if(cmp>1){
                    t =t.right;
                }else if(cmp<1){
                    t = t.left;
                }else {
                    break;
                }
            }
            while (t!=null);

            TreeStructure ts = new TreeStructure<>(node.e,parent);
            if(cmp<0){
                parent.left = ts;
            }else {
                parent.right = ts;
            }
            System.out.println("");
            return;
        }

    }
}
