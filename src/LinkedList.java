public class LinkedList {

	public class Node{
		String[] data;
		Node link;
	}
	
	Node front;
	Node rear;
	
	public LinkedList(){
		front = null;
		rear = null;
	}
	public void insert(String[] data){
		Node temp = new Node();
		if(front==null){
			front=rear=temp;
		}
		else{
			rear.link = temp;
			rear = temp;
		}
		rear.data = data;
	}
	public int size(){
		int length = 0;
		Node temp = front;
		while(temp!=null){
			length++;
			temp=temp.link;
		}
		return length;
	}
	public String[] get(int pos){
		Node temp = front;
		String[] tempArr = temp.data;
		int count=0;
		while(temp!=null){
			if(count==pos){
				tempArr = temp.data;
				break;
			}
			else{
				count++;
				temp = temp.link;
			}				
		}
		return tempArr;
	}
	public void set(int pos, String[] data){
		Node temp = front;
		int count=0;
		while(temp!=null){
			if(count==pos){
				temp.data = data;
				break;
			}
			else{
				count++;
				temp = temp.link;
			}				
		}
	}
}
