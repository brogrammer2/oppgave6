class OrdnetLenkeliste<T extends Comparable<T>> extends LenkeListe<T>{

  @Override
  public void settInn(T element){
    size++;
    boolean added=false;
    Node newnode=new Node(element);
    if(top==null){
      top=newnode;
      added=true;
    }
    Node node1=top;
    Node node2=null;
    while(added==false){
      if(newnode.data.compareTo(node1.data)<=0){
        if(node2==null){
          newnode.neste=node1;
          added=true;
          top=newnode;
        }
        else{
          node2.neste=newnode;
          newnode.neste=node1;
          added=true;
        }
      }
      else{
        if(node1.neste!=null){
          node2=node1;
          node1=node1.neste;
        }
        else{
          node1.neste=newnode;
          added=true;
        }
      }
    }
  }
}
