> 本文基于jdk1.8

### 类签名
```java
// 继承AbstractList
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable{....}
```
### 默认构造函数
```java

    // 在 jdk1.7 之前，ArrayList 默认值为10， 在jdk1.7 之后默认值为空，在add时才会设置大小。

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }


```

### add() 方法
```java

   /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        // 检查容量
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
```

```java
 /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;


 private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // 当前容量为空时
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 和默认容量10 取最大值，避免空间浪费
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
    // 这里的minCapacity 为当前所需最小容量
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        // 此变量在AbstractList中，结构修改计数，对list size 修改做计数
        modCount++;

        // overflow-conscious code
        // 所需容量大于当前容量，做扩容
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

 /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // 容量double扩容
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 数组拷贝，转义原来数组中的数据
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```