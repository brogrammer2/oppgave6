public class Koe<T> extends LenkeListe<T>{


  @Override
  public void settInn(T element){
    Node newnode=new Node(element);
    size++;
    if(top==null){
      top=newnode;
      bottom=newnode;
    }
    else{
      bottom.neste=newnode;
      bottom=newnode;
    }
  }

  @Override
  public T fjern(){
    if(top==null){
      return null;
    }
    else{
      size--;
      Node temp=top;
      top=top.neste;
      return temp.data;
    }
  }
}
