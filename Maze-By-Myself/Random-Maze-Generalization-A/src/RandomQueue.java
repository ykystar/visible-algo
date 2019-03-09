import java.util.ArrayList;

public class RandomQueue<E> {

    private ArrayList<E> queue;

    public RandomQueue(){

        queue= new ArrayList<>();
    }

    public void add(E e){
        queue.add(e);
    }

    public E remove(){

        int randomIndex = (int)(Math.random()*queue.size());

        E randomElement = queue.get(randomIndex);

        queue.set(randomIndex,queue.get(queue.size()-1));

        queue.remove(queue.size()-1);

        return randomElement;
    }

    public int size(){
        return queue.size();
    }

    public boolean empty(){
        return queue.size() == 0;
    }
}
