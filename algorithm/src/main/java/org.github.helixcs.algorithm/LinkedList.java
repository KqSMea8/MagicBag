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
    public  void addTail(T data) {
        if (null == this.getHead()) {
            this.head = new LinkNode<T>(data);
            if (null == this.getTail()) {
                this.tail = head;
            }
        } else {
            LinkNode<T> currentNode = new LinkNode<T>(data);
            this.tail.next = currentNode;
            this.tail = currentNode;
        }
    }

    // 链尾
    public void linkLast(T data){
        LinkNode<T> currentNode = new LinkNode<>(data);
        if(null==this.head){
            this.head= currentNode;
            this.tail = this.head;
        }else {
            this.tail.next = currentNode;
            this.tail = currentNode;
        }
    }

    // 打印每个节点
    private void print(){
        LinkNode h = this.head;
        while (h!=null){
            System.out.println(h.data);
            h = h.next;
        }
    }


    // 逆置
    public LinkNode myReverse(LinkNode currentNode){
        LinkNode preNode = null, nextNode = null;
        while (currentNode!=null){
            nextNode = currentNode.next;
            currentNode.next = preNode;
            preNode  = currentNode;
            currentNode = nextNode;
        }
        LinkNode tmpNode =  this.head;
        this.head = this.tail;
        this.tail = tmpNode;
        return preNode;
    }

    public LinkNode reverse(LinkNode current) {
        //initialization
        LinkNode previousNode = null;
        LinkNode nextNode = null;

        while (current != null) {
            //save the next node
            nextNode = current.next;
            //update the value of "next"
            current.next = previousNode;
            //shift the pointers
            previousNode = current;
            current = nextNode;
        }
        return previousNode;
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

        public LinkNode(){
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
        a.linkLast("a");
        a.linkLast("b");
        a.linkLast("c");
        a.myReverse(a.head);

        a.print();
    }
}
