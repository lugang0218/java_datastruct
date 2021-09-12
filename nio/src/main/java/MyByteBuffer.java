public class MyByteBuffer {
    private int position;
    private int capacity;
    private int limit;
    private byte[] buffer=null;
    private final int offset;
    public MyByteBuffer(int position,int offset,int capacity,int limit){
        this.position=position;
        this.capacity=capacity;
        this.limit=capacity;
        this.buffer=new byte[this.capacity];
        /**
         * offset偏移量
         */
        this.offset=offset;
    }
    public void put(byte value){
        buffer[ix(nextIndex())]=value;
    }
    /**
     * 计算一个索引
     * @param i
     * @return
     */

    public int ix(int i){
        return i+offset;
    }
    public int nextIndex(){
        if(position>=limit){
            throw new RuntimeException("数组下标越界");
        }
        return position++;
    }

    public static MyByteBuffer allocate(int capacity){
        MyByteBuffer buffer=new MyByteBuffer(0,0,capacity,capacity);
        return buffer;
    }
    public static void main(String[] args) {


        MyByteBuffer buffer=MyByteBuffer.allocate(12);
        buffer.put((byte)0);
        buffer.put((byte)1);
        buffer.put((byte)2);
        buffer.put((byte)3);
        buffer.flip();
    }

    public int getPosition() {
        return position;
    }

    public int getLimit() {
        return limit;
    }

    public void flip(){
        /**
         * 代表能够读的位置
         */
        limit=position;
        position=0;
    }
    public void clear(){
        position=0;
        limit=capacity;
    }
}
