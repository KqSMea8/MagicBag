package org.github.helixcs.java;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: helix
 * @Time:9/14/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class ForkJoinSample {
    private static class SumTask extends RecursiveTask<Integer>{
        private int [] array;
        private int start;
        private int end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(end-start<4){
                int sumResult = 0;
                for(int i =start;i<end;i++){
                    sumResult+=array[i];
                }
                return sumResult;

            }
            int size = end+start;
            int middle = size /2 ;
            RecursiveTask<Integer> task1 = new SumTask(array,start, middle);
            RecursiveTask<Integer> task2 = new SumTask(array,middle,end);
            invokeAll(task1,task2);
            int  task1Result = task1.join();
            int  task2Result = task2.join();
            int  result = task1Result+task2Result;
            return result;
        }
    }
    public static void main(String[] args) {

        int [] intArray = new int[]{1,232,43,53,54,65,65,7,67,68,76,87,86,8,6,43,43,53,534,654,6};
        ForkJoinPool forkJoinPool  = new ForkJoinPool(4);
        ForkJoinTask<Integer> forkJoinTask = new SumTask(intArray,0,intArray.length);
        int result = forkJoinPool.invoke(forkJoinTask);
        System.out.println(result);


    }
}
