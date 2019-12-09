import java.util.Iterator;

abstract class LenkeListe<T> implements Liste<T>{
  int size=0;
  Node top=null;
  Node bottom=null;

  public int storrelse(){
    return size;
  }

  public boolean erTom(){
    return top==null;
  }

  public void settInn(T element){
      size++;
      Node newNode=new Node(element);
      newNode.neste=top;
      top=newNode;
    }

  public T fjern(){
    if(top==null){
      return null;
    }
    else{
      Node temp=top;
      top=top.neste;
      size--;
      return temp.data;
    }
  }

   public class Node{
    public T data=null;
    public Node neste=null;

    public Node(T data){
      this.data=data;
    }
  }

  public Iterator<T> iterator(){
    return new ListeIterator();
  }

  private class ListeIterator implements Iterator<T>{
    Node place=top;

    public ListeIterator(){}

    public T next(){
      Node temp=place;
      place=temp.neste;
      return temp.data;
    }

    public boolean hasNext(){
      if(place!=null){
        return place.neste!=null;
      }
      else{
        return false;
      }
    }

    public void remove(){}
  }
}
