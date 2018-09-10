package org.github.helixcs.algorithm;

import java.io.Serializable;

public class LinkedList<T> implements Serializable {
    private LinkNode head;

    private LinkNode tail;

    public LinkedList(){ }


    // 链头结点
    public void addHead(T data){
        LinkNode<T> currentNode= new LinkNode<>(data);
        if(null==this.getHead()){
            this.head = currentNode;
            this.tail = this.head;
        }else {
            this.head.next = currentNode;
            this.head = currentNode;
        }

    }

    // 链尾节点
    public  void addTail(T data){
        if(null==this.getHead()){
            this.head  = new LinkNode<T>(data);
            if(null==this.getTail()){
                this.tail = head;
            }
        }else {
            LinkNode<T> currentNode = new LinkNode<T>(data);
            this.tail.next = currentNode;
            this.tail = currentNode;
        }
    }

    private void print(){
        this.getHead();


    }


    public LinkNode getHead() {
        return head;
    }

    public void setHead(LinkNode head) {
        this.head = head;
    }

    public LinkNode getTail() {
        return tail;
    }

    public void setTail(LinkNode tail) {
        this.tail = tail;
    }

    // 单链表节点
    private static class LinkNode<T> {
        private T data;
        private LinkNode next;

        public LinkNode(T data , LinkNode next){
            this.data = data;
            this.next = next;
        }

        public LinkNode(T data){
            this.data = data;
        }

        public LinkNode getNext() {
            return next;
        }

        public void setData(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setNext(LinkNode next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedList<String> a = new LinkedList<>();
        a.addHead("a");
        a.addHead("b");
        a.addHead("c");
        System.out.println(a.getTail().data);


    }
}
