
import java.lang.Math;
public class AVL_BST{
	public static boolean checkAVL(BST b){
		return b.heightComp(b.root);
	}
	
	public static BST createBST(int[] a){
		BST tree=new BST();
		for(int i=0;i<a.length;i++){
			tree.root=tree.insert(a[i],tree.root);
		}
		return tree;
	}
	public static void main(String[] args){
		int[] A = {82, 85, 153, 195, 124, 66, 200, 193, 185, 243, 73, 153, 76};
		BST b = createBST(A);
		b.printBST(b.root);
		System.out.println(checkAVL(b));
		System.out.println("Size= "+b.size(b.root));
		System.out.println("Height= "+b.height(b.root));
		System.out.println("Range num"+b.countAllInRange(243,66,b.root));
	}
}

class BST{
	node root;
	public BST(node root){
		this.root=root;
	}
	
	public BST(){
		this.root=null;
	}
	public node insert(int x,node root){
		node xNode=new node(x);
		if (root==null){
			root=xNode;
		}else if (x>root.getValue()){
			root.right=insert(x,root.right);
		}else {
			root.left=insert(x,root.left);
		}
		return root;
	}
	
	public void printBST(node root){
		//modified code from https://www.cnblogs.com/hapjin/p/5564552.html for testing only
	    if(root == null){
			System.out.println("root null");
			return;
		}
        Queue<node> queue = new LinkedList<>();
        queue.offer(root);
        int current=1;
        int next=0;
		while(!queue.isEmpty()){
			node currentNode = queue.poll();
			System.out.printf("%-4d", currentNode.getValue());
			current--;
			if(currentNode.left!= null){
				queue.offer(currentNode.left);
				next++;
			}
			if(currentNode.right!= null){
				queue.offer(currentNode.right);
				next++;
			}
			if(current ==0){
				System.out.println();
				current = next;
				next = 0;
			}
		}
	}
	
	public boolean find(int x,node curr){
		if(curr==null)return false;
		else if(curr.getValue()==x)return true;
		else return find(x,curr.left)||find(x,curr.right);
	}
	public int height(node curr){
		if(curr==null||(curr.left==null&&curr.right==null))return 0;
		else return 1+Math.max(height(curr.left),height(curr.right));
	}
	/*public void delete(int x,node curr){
		if(curr==null)return;
		else if(curr.getValue()==x)return true;
		else return find(x,curr.left)||find(x,curr.right);
	}*/
	public void removeAll(){
		this.root=null;
	}
	public boolean isEmpty(){
		return this.root==null;
	}
	public int size(node curr){
		if(curr==null)return 0;
		else return 1+size(curr.left)+size(curr.right) ;
	}
	public boolean heightComp(node curr){
		if(curr==null)return true;
		boolean curr_balanced=(Math.abs(height(curr.left)-height(curr.right))>1)?false:true;
		return curr_balanced&&heightComp(curr.left)&&heightComp(curr.right);
	}
	public int countAllInRange(int k1,int k2,node curr){
		if(curr==null){
			return 0;
		}
		int val=curr.getValue();
		if(val<=k2&&val>=k1){
			return 1+countAllInRange(k1,k2,curr.left)+countAllInRange(k1,k2,curr.right);
		}else if(val<k1){
			return countAllInRange(k1,k2,curr.right);
		}else if(val>k2){
			return countAllInRange(k1,k2,curr.left);
		}else{
			return -1;
		}
	}
}
class node{
	private int value;
	node left;
	node right;
	public node(){
		this.value=-1;
		this.left=null;
		this.right=null;
	}
	public node(int value,node left,node right){
		this.value=value;
		this.left=left;
		this.right=right;
	}
	public node(int value){
		this.value=value;
		this.left=null;
		this.right=null;
	}
	public int getValue(){
		return this.value;
	}
}