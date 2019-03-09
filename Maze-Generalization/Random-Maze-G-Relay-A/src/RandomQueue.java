import java.util.ArrayList;

public class RandomQueue<E> {

    private ArrayList<E> queue;

    public RandomQueue(){
        queue = new ArrayList<>();
    }

    public void add(E e){
        queue.add(e);
    }

    public E remove(){
        if(queue.size() ==0)
            throw new IllegalArgumentException("no e");

        int randIndex =(int)(Math.random()*queue.size());

        E randElement = queue.get(randIndex);
        queue.set(randIndex,queue.get(queue.size()-1));
        queue.remove(queue.size()-1);
        return randElement;
    }

    public int size(){
        return queue.size();
    }

    public boolean empty(){
        return size() ==0;
    }
}
