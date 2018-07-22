import java.util.Scanner;
public class median{
	static minHeap min;
	static maxHeap max;
	
	public median(){
		min = new minHeap();
		max = new maxHeap();
	}
	
	public static int calculateMedian(int x){
		max.insert(x);
		if(max.size()-min.size()==2){
			min.insert(max.removeMax());
		}
		if(max.size()-min.size()==1){
			if(min.size()!=0){
				if(max.peek()>min.peek()){
					int max_pop=max.removeMax();
					int min_pop=min.removeMin();
					max.insert(min_pop);
					min.insert(max_pop);
				}
			}
		}
		return max.peek();
	}
	
	public static void main(String[] args){
		median m = new median();
		
		System.out.println("Enter a list of non negative integers. To end enter a negative integers.");
		Scanner s = new Scanner(System.in);
		int current = s.nextInt();

		while(current >=0){
			System.out.println("current median:" + m.calculateMedian(current));
			current = s.nextInt();
			if(current<0)break;
			m.calculateMedian(current);
			current = s.nextInt();
			
		}	
	}
}
class minHeap{
	private int[] heap;
	private int size;
	public minHeap(int[] heap,int size){
		this.heap=heap;
		this.size=size;
	}
	public minHeap(){
		heap=new int[10000];
		size=0;
	}
	public int left(int index){
		return 2*index+1;
	}
	public int right(int index){
		return 2*index+2;
	}
	public int parent(int index){
		return (index-1)/2;
	}
	public boolean isEmpty(){
		return (size==0);
	}

	public int size(){
		return size;
	}

	public void insert(int x){
		heap[size]=x;
		if(size>0){
			bubbleup(size);
		}
		size++;	
	}
	public int[] getHeap(){
		return heap;
	}
	public void bubbleup(int k){
		int pos=k;
		while(heap[pos]<=heap[parent(pos)]){
			exchange(pos,parent(pos));
			pos=parent(pos);
			if(pos==0)break;
		}
	}
	public void exchange(int i,int j){
		if(i>=0&&j>=0){
			int temp= heap[i];
			heap[i]=heap[j];
			heap[j]=temp;
		}else{
			System.out.println("Exchange error");
		}
	}
	public void bubbledown(int k){
		int pos=k;
		while(true){
			boolean flag_L=heap[pos]>=heap[left(pos)]&&left(pos)<=size-1;
			boolean flag_R=heap[pos]>=heap[right(pos)]&&right(pos)<=size-1;
			if(flag_L&&!flag_R){
				exchange(pos,left(pos));
				pos=left(pos);
			}else if(flag_R&&!flag_L){
				exchange(pos,right(pos));
				pos=right(pos);
			}else if(flag_L&&flag_R){
				if(heap[left(pos)]<heap[right(pos)]){
					exchange(pos,left(pos));
					pos=left(pos);
				}else{
					exchange(pos,right(pos));
					pos=right(pos);
				}
			}
			else{
				break;
			}
		}
	}
	public int peek(){
		return heap[0];
	}
	public int removeMin(){
		int min=heap[0];
		exchange(0,size-1);
		size--;
		bubbledown(0);
		return min;
	}
}	
class maxHeap{
	private int[] heap;
	private int size;
	public maxHeap(int[] heap,int size){
		this.heap=heap;
		this.size=size;
	}
	public maxHeap(){
		heap=new int[10000];
		size=0;
	}
	public int left(int index){
		return 2*index+1;
	}
	public int right(int index){
		return 2*index+2;
	}
	public int parent(int index){
		return (index-1)/2;
	}
	public boolean isEmpty(){
		return (size==0);
	}

	public int size(){
		return size;
	}

	public void insert(int x){
		heap[size]=x;
		if(size>0){
			bubbleup(size);
		}
		size++;	
	}
	public int[] getHeap(){
		return heap;
	}
	public void bubbleup(int k){
		int pos=k;
		while(heap[pos]>=heap[parent(pos)]){
			exchange(pos,parent(pos));
			pos=parent(pos);
			if(pos==0)break;
		}
	}
	public void exchange(int i,int j){
		if(i>=0&&j>=0){
			int temp= heap[i];
			heap[i]=heap[j];
			heap[j]=temp;
		}else{
			System.out.println("Exchange error");
		}
	}
	public void bubbledown(int k){
		int pos=k;
		while(true){
			boolean flag_L=heap[pos]<=heap[left(pos)]&&left(pos)<=size-1;
			boolean flag_R=heap[pos]<=heap[right(pos)]&&right(pos)<=size-1;
			if(flag_L&&!flag_R){
				exchange(pos,left(pos));
				pos=left(pos);
			}else if(flag_R&&!flag_L){
				exchange(pos,right(pos));
				pos=right(pos);
			}else if(flag_L&&flag_R){
				if(heap[left(pos)]>heap[right(pos)]){
					exchange(pos,left(pos));
					pos=left(pos);
				}else{
					exchange(pos,right(pos));
					pos=right(pos);
				}
			}
			else{
				break;
			}
		}
	}
	public int peek(){
		return heap[0];
	}
	public int removeMax(){
		int max=heap[0];
		exchange(0,size-1);
		size--;
		bubbledown(0);
		return max;
	}
}