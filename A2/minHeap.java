public class minHeap{
	private int[] heap;
	private int size;
	private int count;
	public minHeap(int[] heap,int size){
		this.heap=heap;
		this.size=size;
	}
	public minHeap(){
		heap=new int[10000];
		size=0;
		count=0;
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
		count++;
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
	
	public static void main(String args[]){
		minHeap heap=new minHeap();
		heap.insert(1);
		heap.insert(10);
		heap.insert(5);
		heap.insert(4);
		heap.insert(9);
		heap.insert(7);
		heap.insert(6);

		
		System.out.println("count="+heap.count);
		
		for(int i=0;i<7;i++){
			System.out.println("hahaha"+"::"+heap.removeMin());
		}
		
		
	}
}