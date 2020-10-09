import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class DataStruct

{
	ArrayList<Node> arr = new ArrayList<Node>();
	Node min;
	ArrayList<Node> root = new ArrayList<Node>();
	Node rootRBT;
	int time = 0;
	int timeLeft =0;
	Node nodeLeft;
	Boolean del = false;
	int countPr = 0;

	//Works on the builduing for 5 days or days given until finished.
	public void operate(int currtime, int n, int bn1, int bn2)
	{
 		 	
		if(root.size() == 0)
			return;
		int operTime = currtime - time;
		int bn;
		Node node;
		int timeRequired = min.getTotalTime()-min.getExecTime();
		if(timeLeft != 0)
			timeRequired = nodeLeft.getTotalTime()-nodeLeft.getExecTime();
			

			if(operTime >= 5 && timeLeft == 0)
			{	
				if(timeRequired > 5)
				{	
					
					changeET(5,min);

					time += 5;
				}	
				else
				{
					
					node = min;
					bn = min.getBuildNum();
					
					if(root.size() == 0)
					{
						time += operTime;
						if(time == currtime && n ==1)
						{
						changeET(operTime,min);
						search(bn1,bn2);
						del = true;
						}
					}
					else
					{
						time += timeRequired;
						if(time == currtime && n ==1)
						{
						changeET(timeRequired,min);
						search(bn1,bn2);
						del = true;
						}
					}
					delete(node);

					System.out.println("(" + bn + "," + time + ")");
					
				}
					
			}
			else
			{
				if(timeRequired > operTime)
				{
					
					if(timeLeft == 0)
					{
						int prevTime = min.getExecTime();
						min.setExecTime(prevTime + operTime);
						timeLeft = 5-operTime;
						nodeLeft = min;
						time += operTime;
					}
					else
					{
						if(operTime < timeLeft)
							{
								int lastTime = nodeLeft.getExecTime();
								nodeLeft.setExecTime(lastTime + operTime);
								timeLeft = timeLeft-operTime;
								time += operTime;
							}
						else{
								changeET(timeLeft,nodeLeft);

								time += timeLeft;
								timeLeft = 0;
								
							}
							
					}
						
					
				}
				else
				{
					
					if(timeLeft == 0)
					{	
						node = min;
						bn = min.getBuildNum();
						

						if(root.size() == 0)
						{
							time += operTime;
							if(time == currtime && n ==1)
							{
							changeET(operTime,min);
							search(bn1,bn2);
							del = true;
							}
						}
						else
						{
							time += timeRequired;
							if(time == currtime && n ==1)
							{
							changeET(timeRequired,min);
							search(bn1,bn2);
							del = true;
							}
						}
						delete(node);
						System.out.println("(" + bn + "," + time + ")");
					}
					else
					{	
						
						if(nodeLeft.getExecTime() + timeLeft == nodeLeft.getTotalTime())
						{

							
							
							if(root.size() == 0)
							{
								time += operTime;
								if(time == currtime && n ==1)
								{
								changeET(timeLeft,min);
								search(bn1,bn2);
								del = true;
								}
							}
							else
							{
								time += timeLeft;
								if(time == currtime && n ==1)
								{
								changeET(timeLeft,min);
								search(bn1,bn2);
								del = true;
								}
							}
							timeLeft = 0;
							delete(nodeLeft);
							System.out.println("(" + nodeLeft.getBuildNum() + "," + time + ")");
						}
						else
						{
							changeET(timeLeft,nodeLeft);
							if(root.size() == 0)
								time += operTime;
							else
							{
								time += timeLeft;
							}
							timeLeft = 0;
						}

						
						
					}

				}

		}
		if(currtime != time && root.size() >0)
			operate(currtime,n,bn1,bn2);
			
		
	}

	

	// Function to increase the execution time of a builduing.
	public void changeET(int time, Node node){
		int execTime = time + node.getExecTime();
		node.setExecTime(execTime);
		
		heapify(node);
		changeMin();
	}
	
	//Function to heapify the heap.
	public void heapify(Node node){

		if(node.getChild() != null)
		{
				ArrayList<Node> childList = node.getChild();
				Node smallest = node;
				for(int i =0; i<childList.size(); i++)
				{
					if(smallest.getExecTime() > childList.get(i).getExecTime())
					{
					smallest = childList.get(i);
					}
				else if(smallest.getExecTime() == childList.get(i).getExecTime())
					{
					if(smallest.getBuildNum() > childList.get(i).getBuildNum())
						smallest = childList.get(i);
					}
				}
			if(smallest != node)
			{
			
			
			swap(node,smallest);
			
			heapify(node);
			}
		}
		
	}


	// Function to swap two nodes.
	public void swap(Node parent, Node child){
		
		Node parentClone = parent;
		ArrayList<Node> childofChild = child.getChild();
		ArrayList<Node> childList = parent.getChild();
		int childDegre = child.getDegree();
		Node rSiblingChild = child.getRightSibling();
		int ind = childList.indexOf(child);
		childList.set(ind,parent);
		if(parent.getParent() != null)
		{
			ArrayList<Node> parentSibling = parent.getParent().getChild();
			int indParent = parentSibling.indexOf(parent);
			parentSibling.set(indParent,child);
			child.setRightSibling(parent.getRightSibling());
			if(parentSibling.size() >1)
			{
				
				
				if(indParent == 0)
					parentSibling.get(parentSibling.size()-1).setRightSibling(child);
				else
					parentSibling.get(indParent-1).setRightSibling(child);
				
			}
			child.setParent(parent.getParent());

				
		}	
		else
		{
			int indRoot = root.indexOf(parent);
			root.set(indRoot,child);
			child.setRightSibling(parent.getRightSibling());
			if(root.size() >1)
			{
				if(indRoot == 0)
				root.get(root.size()-1).setRightSibling(child);
			else
				root.get(indRoot-1).setRightSibling(child);

			}
			

			child.setParent(null); 
		}
		child.setChild(childList);
		child.setDegree(parent.getDegree());
		if(childList.size()>1)
		{
			if(ind == 0)
				childList.get(childList.size()-1).setRightSibling(parent);
			else 
				childList.get(ind-1).setRightSibling(parent);
		}
			

		parent.setRightSibling(rSiblingChild);
		parent.setParent(child);
		parent.setChild(childofChild);
		parent.setDegree(childDegre);
	}


	//Funtion to delete a node from heap and RB tree.
	public void delete(Node node){

		 arr.remove(node);
		 root.remove(node);

		 Node[] count = new Node[10];
		 if(min == node)
		 	min = null;
		
		 if(node.getDegree()>0)
		 {
		 	ArrayList<Node> childList = node.getChild();
			 for(int i =0; i<childList.size(); i++)
		 	{
		 		childList.get(i).setParent(null);
		 		root.add(childList.get(i));
		 	}
		 }

		 if(root.size() == 1)
		 {	
		 	min = root.get(0);
		 }
		 	

		if(root.size() > 1)
		{
			mergeInit(0,count);

			 
		 	for(int i =0; i<root.size()-1; i++)
		 	{
		 		root.get(i).setRightSibling(root.get(i+1));
		 
			}
		 	root.get(root.size()-1).setRightSibling(root.get(0));
		 	changeMin();
		 	
		} 

		deleteRB(node);
		


	}

	//Funtion to delete a node/builduing from the RB tree.
	public void deleteRB(Node node)

	{		
		Node smallest;
		Node parent = node.getParentRB();
		String childLR = "";
		Boolean check;
		if(node == rootRBT)
		{
			if(node.getLeftChild() == null && node.getRightChild() == null)
				rootRBT = null;
			else if(node.getLeftChild() != null && node.getRightChild() == null)
			{

				node.getLeftChild().setParentRB(null);
				rootRBT = node.getLeftChild();
				node.getLeftChild().setColor("black");
			}
			else if(node.getLeftChild() == null && node.getRightChild() != null)
			{

				node.getRightChild().setParentRB(null);
				rootRBT = node.getRightChild();
				node.getRightChild().setColor("black");
			}
			else if(node.getLeftChild() != null && node.getRightChild() != null)
			{
				smallest = smallestRight(node.getRightChild());
				Node smr = smallest.getRightChild();
				Node sml = smallest.getLeftChild();
				String col = smallest.getColor();
				smallest.setRightChild(node.getRightChild());
				node.getRightChild().setParentRB(smallest);
				smallest.setLeftChild(node.getLeftChild());
				node.getRightChild().setParentRB(smallest);
				node.setRightChild(smr);
				node.setLeftChild(sml);
				node.setParentRB(smallest.getParentRB());
				if(smallest.getParentRB().getRightChild() == smallest)
					smallest.getParentRB().setRightChild(node);
				else
					smallest.getParentRB().setLeftChild(node);
				smallest.setParentRB(null);
				smallest.setColor(node.getColor());
				node.setColor(col);
				if(smr != null)
					smr.setParentRB(node);
				if(sml != null)
					sml.setParentRB(node);
				rootRBT = smallest;
				deleteRB(node);
				
			}

		}

		else 
		{
			if(parent.getLeftChild() == node)
			{
				childLR = "left";
				
			}	
			else
			{
				childLR = "right";
				
			}

			if(node.getLeftChild() == null && node.getRightChild() == null)
			{
				if(childLR == "left")
				{
						parent.setLeftChild(null);

				}
				else
				{
					parent.setRightChild(null);

					


				}		
				if(node.getColor() == "black")
					rebalancing(parent,childLR);
			}
				
			else if(node.getLeftChild() != null && node.getRightChild() == null)
			{
				if(node.getColor() == "red" || node.getLeftChild().getColor() == "red")
				{
					if(childLR == "left")
					{
						parent.setLeftChild(node.getLeftChild());
						node.getLeftChild().setParentRB(parent);
						node.getLeftChild().setColor("black");
					}
					else
					{
						parent.setRightChild(node.getLeftChild());
						node.getLeftChild().setParentRB(parent);
						node.getLeftChild().setColor("black");
					}
				}
				

				else if(node.getColor() == "black" && node.getLeftChild().getColor() == "black")
				{	
					if(childLR == "left")
					{
						parent.setLeftChild(node.getLeftChild());
						node.getLeftChild().setParentRB(parent);
						rebalancing(node.getLeftChild(),childLR);
						
					}
					else
					{
						parent.setRightChild(node.getLeftChild());
						node.getLeftChild().setParentRB(parent);
						
						rebalancing(node.getLeftChild(),childLR);
					}
				}
			}
			else if(node.getLeftChild() == null && node.getRightChild() != null)
			{

					
				if(node.getColor() == "red" || node.getRightChild().getColor() == "red")
				{
					if(childLR == "left")
					{
						parent.setLeftChild(node.getRightChild());
						node.getRightChild().setParentRB(parent);
						node.getRightChild().setColor("black");
					}
					else
					{
						parent.setRightChild(node.getRightChild());
						node.getRightChild().setParentRB(parent);
						node.getRightChild().setColor("black");
					}
				}
				

				else if(node.getColor() == "black" && node.getRightChild().getColor() == "black")
				{	
					if(childLR == "left")
					{
						parent.setLeftChild(node.getRightChild());
						node.getRightChild().setParentRB(parent);
						rebalancing(node.getRightChild(),childLR);
						
					}
					else
					{
						parent.setRightChild(node.getRightChild());
						node.getRightChild().setParentRB(parent);
						
						rebalancing(node.getRightChild(),childLR);
					}
				}
			}
			else
			{

				smallest = smallestRight(node.getRightChild());
				Node smr = smallest.getRightChild();
				Node sml = smallest.getLeftChild();
				Node par = smallest.getParentRB();
				String col = smallest.getColor();
				smallest.setRightChild(node.getRightChild());
				node.getRightChild().setParentRB(smallest);
				smallest.setLeftChild(node.getLeftChild());
				node.getRightChild().setParentRB(smallest);
				if(childLR == "left")
					node.getParentRB().setLeftChild(smallest);
				else
					node.getParentRB().setRightChild(smallest);
				smallest.setParentRB(node.getParentRB());
				node.setParentRB(par);
				if(par.getRightChild() == smallest)
					par.setRightChild(node);
				else
					par.setLeftChild(node);
				node.setRightChild(smr);
				node.setLeftChild(sml);
				if(smr != null)
					smr.setParentRB(node);
				if(sml != null)
					sml.setParentRB(node);
				deleteRB(node);
					
			}
		}
	}

	//Finds the smallest node of the right subtree.
	public Node smallestRight(Node node)
	{

		if(node.getLeftChild() == null)
		{	
			
			return node;
		}
		else
		{
			return smallestRight(node.getLeftChild());
		}
	}


	//Rebalance the RB tree after deletion of the node.
	public void rebalancing(Node parent, String side )
	{
		
		if(side == "right")
		{
			Node anChild = parent.getLeftChild();
			
					if(anChild.getColor() == "black")
					{
							
							if(anChild.getLeftChild() == null && anChild.getRightChild() == null)
							{
								if(parent.getColor() == "black")
								{
									anChild.setColor("red");
									parent.setColor("black");
								if(parent.getParentRB() != null)
									if(parent.getParentRB().getRightChild() == parent)
										rebalancing(parent.getParentRB(),"right");
									else 
										rebalancing(parent.getParentRB(),"left");
								}
								else
								{
									anChild.setColor("red");
									parent.setColor("black");
								}
								
							}
							else if(anChild.getRightChild().getColor() == "red" && anChild.getLeftChild() == null)
							{
								String pc = parent.getColor();
								Node rn = anChild.getRightChild();
								
								leftRightRot(anChild.getRightChild());
								parent.setColor("black");
								rn.setColor(pc);
							}
							else if(anChild.getRightChild() == null && anChild.getLeftChild().getColor() == "red")
							{

								String pc = parent.getColor();
								
								anChild.getLeftChild().setColor("black");
								leftLeftRot(anChild.getLeftChild());
								parent.setColor("black");
								anChild.setColor(pc);

							}
							else if(anChild.getRightChild().getColor() == "red" && anChild.getLeftChild().getColor() == "black")
							{

								String pc = parent.getColor();
								Node rn = anChild.getRightChild();
								
								leftRightRot(anChild.getRightChild());
								parent.setColor("black");
								rn.setColor(pc);
							}
							else if(anChild.getRightChild().getColor() == "black" && anChild.getLeftChild().getColor() == "red")
							{

								String pc = parent.getColor();
								
								anChild.getLeftChild().setColor("black");
								leftLeftRot(anChild.getLeftChild());
								parent.setColor("black");
								anChild.setColor(pc);

							}
							else if(anChild.getLeftChild().getColor() == "black" && anChild.getRightChild().getColor() == "black")
							{
								if(parent.getColor() == "black")
								{
									anChild.setColor("red");
									parent.setColor("black");
								if(parent.getParentRB() != null)
									if(parent.getParentRB().getRightChild() == parent)
										rebalancing(parent.getParentRB(),"right");
									else 
										rebalancing(parent.getParentRB(),"left");
								}
								else
								{
									anChild.setColor("red");
									parent.setColor("black");
								}
							}
							else
							{
								String pc = parent.getColor();
								Node rn = anChild.getRightChild();
								leftRightRot(anChild.getRightChild());
								rn.setColor(pc);
							}
					}
					else
					{
						
							if(anChild.getRightChild().getColor() == "black" && anChild.getLeftChild().getColor() == "black")
							{
								anChild.getRightChild().setColor("red");
								leftLeftRot(anChild.getLeftChild());
								anChild.getRightChild().setColor("black");
							}
							else if(anChild.getRightChild().getLeftChild().getColor() == "black" && anChild.getRightChild().getRightChild().getColor() == "black")
							{
								anChild.getRightChild().setColor("red");
								leftLeftRot(anChild.getLeftChild());
								
							}
							else if(anChild.getRightChild().getLeftChild().getColor() == "red" && anChild.getRightChild().getRightChild().getColor() == "black")
							{
								anChild.getRightChild().getLeftChild().setColor("black");
								leftRightRot(anChild.getRightChild());
							}
							else 
							{
								rotateLR(anChild.getRightChild().getRightChild());
							}

					}

							

		}
		
		else
		{
			Node anChild = parent.getRightChild();

			if(anChild.getColor() == "black")
			{     
							if(anChild.getLeftChild() == null && anChild.getRightChild() == null)
							{

								if(parent.getColor() == "black")
								{
									anChild.setColor("red");
									parent.setColor("black");
									if(parent.getParentRB().getRightChild() == parent)
										rebalancing(parent.getParentRB(),"right");
									else 
										rebalancing(parent.getParentRB(),"left");
								}
								else 
								{
									anChild.setColor("red");
									parent.setColor("black");
								}
									
								
							}
							else if(anChild.getRightChild() == null && anChild.getLeftChild().getColor() == "red")
							{

								String pc = parent.getColor();
								Node ln = anChild.getLeftChild();
								
								rightLeftRot(anChild.getLeftChild());
								parent.setColor("black");
								ln.setColor(pc);
							}
							else if(anChild.getLeftChild() == null && anChild.getRightChild().getColor() == "red")
							{

								String pc = parent.getColor();
								
								anChild.getRightChild().setColor("black");
								rightRightRot(anChild.getRightChild());
								parent.setColor("black");
								anChild.setColor(pc);
							}

							else if(anChild.getLeftChild().getColor() == "black" && anChild.getRightChild().getColor() == "black")
							{
								anChild.setColor("red");
								parent.setColor("black");
								if(parent != rootRBT)
									if(parent.getParentRB().getRightChild() == parent)
										rebalancing(parent.getParentRB(),"right");
									else 
										rebalancing(parent.getParentRB(),"left");
							}
							else if(anChild.getRightChild().getColor() == "black" && anChild.getLeftChild().getColor() == "red")
							{
								String pc = parent.getColor();
								Node ln = anChild.getLeftChild();
								
								rightLeftRot(anChild.getLeftChild());
								parent.setColor("black");
								ln.setColor(pc);	
							}
							else if(anChild.getLeftChild().getColor() == "black" && anChild.getRightChild().getColor() == "red")
							{
								String pc = parent.getColor();
								
								anChild.getRightChild().setColor("black");
								rightRightRot(anChild.getRightChild());
								parent.setColor("black");
								anChild.setColor(pc);
							}
							else 
							{
								String pc = parent.getColor();
								Node ln = anChild.getLeftChild();
								
								
								parent.setColor("black");
								rightLeftRot(anChild.getLeftChild());
								ln.setColor(pc);
							}
			}
			else
			{
							if(anChild.getLeftChild().getColor() == "black" && anChild.getRightChild().getColor() == "black")
							{
								anChild.getLeftChild().setColor("red");
								rightRightRot(anChild.getRightChild());
								anChild.getLeftChild().setColor("black");
								
							}
							else if(anChild.getLeftChild().getLeftChild().getColor() == "black" && anChild.getLeftChild().getRightChild().getColor() == "black")
							{
								anChild.getLeftChild().setColor("red");
								rightRightRot(anChild.getRightChild());
								
							}
							else if(anChild.getLeftChild().getLeftChild().getColor() == "red" && anChild.getLeftChild().getRightChild().getColor() == "black")
							{
								
								rotateRL(anChild.getLeftChild().getLeftChild());
								
								
							}
							else 
							{
								anChild.getRightChild().getRightChild().setColor("black");
								rightLeftRot(anChild.getLeftChild());
							}
			}
							

		}

	}

	//Performs the LR rotation for RB tree. 
	public void rotateLR(Node node)
	{
		Node parent = node.getParentRB();
		Node gp = parent.getParentRB();
		Node ggp = gp.getParentRB();

		parent.setRightChild(node.getLeftChild());
		node.getLeftChild().setParentRB(parent);
		ggp.setLeftChild(node.getRightChild());
		node.getRightChild().setParentRB(ggp);
		node.setLeftChild(gp);
		gp.setParentRB(node);
		node.setRightChild(ggp);
		if(ggp.getParentRB() == null)
		{
			node.setParentRB(ggp.getParentRB());
			rootRBT = node;	
		}
		else if(ggp.getParentRB().getRightChild() == ggp )
		{
			ggp.getParentRB().setRightChild(node);
			node.setParentRB(ggp.getParentRB());

		}
		else 
		{
			ggp.getParentRB().setLeftChild(node);
			node.setParentRB(ggp.getParentRB());
		}
		
		ggp.setParentRB(node);
		node.setColor("black");
	}

	//Performs the RL rotation for RB tree. 
	public void rotateRL(Node node)
	{
		Node parent = node.getParentRB();
		Node gp = parent.getParentRB();
		Node ggp = gp.getParentRB();

		parent.setLeftChild(node.getRightChild());
		node.getRightChild().setParentRB(parent);
		ggp.setRightChild(node.getLeftChild());
		node.getLeftChild().setParentRB(ggp);
		if(ggp.getParentRB() == null)
		{
			node.setParentRB(ggp.getParentRB());
			rootRBT = node;	
		}
		else if(ggp.getParentRB().getRightChild() == ggp )
		{
			ggp.getParentRB().setRightChild(node);
			node.setParentRB(ggp.getParentRB());

		}
		else 
		{
			ggp.getParentRB().setLeftChild(node);
			node.setParentRB(ggp.getParentRB());
		}
		node.setLeftChild(ggp);
		ggp.setParentRB(node);
		node.setRightChild(gp);
		gp.setParentRB(node);
		node.setColor("black");

	}
	//Function to change the minimum node of the min heap.
	public void changeMin()
	{
		if(root.size() > 1)
		{
			min = root.get(0); 

			for(int i =1; i<root.size(); i++)
		 	{
		 		if(min.getExecTime() > root.get(i).getExecTime())
		 		{
		 			min = root.get(i);
		 		}
		 		else if(min.getExecTime() == root.get(i).getExecTime())
		 		{
		 			if(min.getBuildNum() > root.get(i).getBuildNum())
		 				min = root.get(i);
		 		}
		 	}
		}
		else if(root.size() == 1)
		{
			min = root.get(0); 
		}
		else
		{
			min = null;
		}
			
	}

	//Initialize function to merge two trees of a min heap.
	public void mergeInit(int n, Node[] count){
		Node mergeRoot = new Node();
		Node rootAdd = new Node();
		 
		for(int i = n; i<root.size(); i++)
		 {

		 	if(count[root.get(i).getDegree()] == null)
		 		count[root.get(i).getDegree()] = root.get(i);
		 	else
		 	{
		 		rootAdd = count[root.get(i).getDegree()];
		 		count[root.get(i).getDegree()] = null;


		 		mergeRoot = merge(root.get(i),rootAdd);

		 		int ind = root.indexOf(mergeRoot);


		 		mergeInit(ind, count);
		 	}

		 }

	}

	//Function to merge two trees of a min heap.
	public Node merge(Node firstTreeRoot, Node secondTreeRoot)
	{
		

		if(firstTreeRoot.getExecTime() > secondTreeRoot.getExecTime())
		{
			secondTreeRoot.addChild(firstTreeRoot);
			linkSibling(firstTreeRoot,secondTreeRoot.getChild());
			secondTreeRoot.setDegree(secondTreeRoot.getDegree()+1);
			firstTreeRoot.setParent(secondTreeRoot);
			root.remove(firstTreeRoot);
			return secondTreeRoot;
		}
		else if (firstTreeRoot.getExecTime() < secondTreeRoot.getExecTime())
		{
			firstTreeRoot.addChild(secondTreeRoot);
			linkSibling(secondTreeRoot,firstTreeRoot.getChild());
			firstTreeRoot.setDegree(firstTreeRoot.getDegree()+1);
			secondTreeRoot.setParent(firstTreeRoot);
			root.remove(secondTreeRoot);
			return firstTreeRoot;
		}
		else
		{
			if(firstTreeRoot.getBuildNum() > secondTreeRoot.getBuildNum())
			{
				secondTreeRoot.addChild(firstTreeRoot);
				linkSibling(firstTreeRoot,secondTreeRoot.getChild());
				secondTreeRoot.setDegree(secondTreeRoot.getDegree()+1);
				firstTreeRoot.setParent(secondTreeRoot);
				root.remove(firstTreeRoot);
				return secondTreeRoot;
			}
			else
			{

				firstTreeRoot.addChild(secondTreeRoot);
				linkSibling(secondTreeRoot,firstTreeRoot.getChild());
				firstTreeRoot.setDegree(firstTreeRoot.getDegree()+1);
				secondTreeRoot.setParent(firstTreeRoot);
				root.remove(secondTreeRoot);
				return firstTreeRoot;

			}
		}

	}
	//Links two siblings to each other after merge.
	public void linkSibling(Node newChild, ArrayList<Node> child )
	{

		int n = child.size();
		if(child.size() > 1)
			{
				child.get(n-2).setRightSibling(newChild);
				newChild.setRightSibling(child.get(0));
			}
	}

	//Init function to find builduing between buildNum1 and buildNum2.
	public void search(int bn1, int bn2)
	{
		countPr = 0;
		if(rootRBT == null)
			System.out.print("(" + 0 + "," + 0 + "," + 0 + "," + ")");
		else
		{
			searchX(rootRBT,bn1,bn2,0);
			if(countPr == 0)
				System.out.print("(" + 0 + "," + 0 + "," + 0 + "," + ")");

		}
		System.out.println();
		
	}
	//Function to find builduing between buildNum1 and buildNum2.
	public void searchX(Node node, int bn1, int bn2, int n)
	{	
			
		if(node != null)
		{
			if(node.getBuildNum() == bn1)
			{
				countPr = 1;
				if(n==0)
					System.out.print("(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime() + ")");
				else
					System.out.print(",(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime() + ")");
				searchX(node.getRightChild(),bn1,bn2,n+1);
			}
			else if (node.getBuildNum() == bn2)
			{
				countPr = 1;
				if(n==0)
					System.out.print("(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime()+ ")");
				else
					System.out.print(",(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime() + ")");
				searchX(node.getLeftChild(),bn1,bn2,n+1); 
			}	
			else if(node.getBuildNum() > bn1 && node.getBuildNum() < bn2)
			{
				countPr = 1;
				if(n==0)
					System.out.print("(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime() + ")");
				else
					System.out.print(",(" + node.getBuildNum() + "," + node.getExecTime() + "," + node.getTotalTime() + ")");
				searchX(node.getLeftChild(),bn1,bn2,n+1);
				searchX(node.getRightChild(),bn1,bn2,n+1);

			}
	    	else if(node.getBuildNum() > bn2)
				searchX(node.getLeftChild(),bn1,bn2,n);
			else if(node.getBuildNum() < bn1)
				searchX(node.getRightChild(),bn1,bn2,n);
			

		}	 

	}
	//Function to form RB tree after insertion.
	public void formRBT(Node newNode)
	{
		if(arr.size()==1)
		{

			newNode.setColor("black");
			rootRBT = newNode;
			newNode.setDegreeRB(0);
		}
		else
		{
			newNode.setColor("red");
			traverse(newNode,rootRBT);
		}
		
	}
	
	//Function to find a node to insert a node.
	public void traverse(Node newNode, Node checkNode)
	{
		if(checkNode.getBuildNum() > newNode.getBuildNum())
			if(checkNode.getLeftChild() == null)
			{
				checkNode.setLeftChild(newNode);
				newNode.setParentRB(checkNode);
				checkNode.increaseDegreeRB(1);
				if(checkNode.getColor() == "red" && checkNode != rootRBT)
					balance(newNode);
			}
			else
				traverse(newNode,checkNode.getLeftChild());
		else
			if(checkNode.getRightChild() == null)
			{
				checkNode.setRightChild(newNode);
				newNode.setParentRB(checkNode);
				checkNode.increaseDegreeRB(1);
				if(checkNode.getColor() == "red" && checkNode != rootRBT)
					balance(newNode);
			}
			else
				traverse(newNode,checkNode.getRightChild());
	}
	//Function to balance the RB tree after insertion.
	public void balance(Node node)
	{
		
		Node parent = node.getParentRB();
		Node grandParent = parent.getParentRB();
		if(grandParent.getLeftChild() == parent)
			if(grandParent.getRightChild() == null || grandParent.getRightChild().getColor() == "black")
			{
				if(parent.getRightChild() == node)
					leftRightRot(node);
				else
					leftLeftRot(node);
			}
			else
			{
				
				recolor(node);
			}
		else if(grandParent.getRightChild() == parent)
			if(grandParent.getLeftChild() == null || grandParent.getLeftChild().getColor() == "black")
			{
				if(parent.getRightChild() == node)
					rightRightRot(node);
				else
					rightLeftRot(node);
			}
			else
			{
				recolor(node);
			}

	}
	//Funtion to recolor the nodes of RB tree.
	public void recolor(Node node)
	{
		if(node.getParentRB() == rootRBT)
			node.setColor("black");
		else
		{
			node.getParentRB().setColor("black");
			if(node.getParentRB().getParentRB() != rootRBT)
				node.getParentRB().getParentRB().setColor("red");
			if(node.getParentRB().getParentRB().getLeftChild() == node.getParentRB())
				node.getParentRB().getParentRB().getRightChild().setColor("black");
			else
				node.getParentRB().getParentRB().getLeftChild().setColor("black");
			if(node.getParentRB().getParentRB().getParentRB() != rootRBT && node.getParentRB().getParentRB() != rootRBT && node.getParentRB().getParentRB().getParentRB().getColor() != "black")
			{
				balance(node.getParentRB().getParentRB());
			}
							
		}
			
	}
	//Performs Left Right rotation on a RB Tree.
	public void leftRightRot(Node node)
	{
		Node parent = node.getParentRB();
		Node grandParent = parent.getParentRB();
		Node ggp = grandParent.getParentRB();
		Node nlc = node.getLeftChild();
		Node nrc = node.getRightChild();

		if(grandParent == rootRBT)
			;
		else if(grandParent.getParentRB().getLeftChild() == grandParent)
			grandParent.getParentRB().setLeftChild(node);
		else
			grandParent.getParentRB().setRightChild(node);

		node.setParentRB(ggp);
		node.setLeftChild(parent);
		node.setRightChild(grandParent);
		parent.setParentRB(node);
		parent.setRightChild(nlc);
		if(nlc != null)
			nlc.setParentRB(parent);
		grandParent.setParentRB(node);
		grandParent.setLeftChild(nrc);
		if(nrc != null)
			nrc.setParentRB(grandParent);
		node.setColor("black");
		grandParent.setColor("red");

		if(node.getParentRB() == null)
		{
			rootRBT = node;
			
		}

		
	}


	//Performs right left rotation on a RB Tree.
	public void rightLeftRot(Node node)
	{
		Node parent = node.getParentRB();
		Node grandParent = parent.getParentRB();
		Node ggp = grandParent.getParentRB();
		Node nlc = node.getLeftChild();
		Node nrc = node.getRightChild();

		if(grandParent == rootRBT)
			;
		else if(grandParent.getParentRB().getLeftChild() == grandParent)
			grandParent.getParentRB().setLeftChild(node);
		else
			grandParent.getParentRB().setRightChild(node);

		node.setParentRB(ggp);
		node.setLeftChild(grandParent);
		node.setRightChild(parent);
		parent.setParentRB(node);
		parent.setLeftChild(nrc);
		if(nrc != null)
			nrc.setParent(parent);
		grandParent.setParentRB(node);
		grandParent.setRightChild(nlc);
		if(nlc != null)
			nlc.setParentRB(grandParent);
		node.setColor("black");
		grandParent.setColor("red");

		if(node.getParentRB() == null)
		{
			rootRBT = node;
			
		}
	}

	//Performs right right rotation on a RB Tree.
	public void rightRightRot(Node node)
	{
		Node parent = node.getParentRB();
		Node grandParent = parent.getParentRB();
		Node plc = parent.getLeftChild();
		
		if(grandParent == rootRBT)
			;
		else if(grandParent.getParentRB().getLeftChild() == grandParent)
		{
			grandParent.getParentRB().setLeftChild(parent);
		}

		else
			grandParent.getParentRB().setRightChild(parent);


		parent.setParentRB(grandParent.getParentRB());
		parent.setLeftChild(grandParent);
		grandParent.setParentRB(parent);
		grandParent.setRightChild(plc);
		if(plc != null)
			plc.setParentRB(grandParent);
		parent.setColor("black");
		grandParent.setColor("red");
		if(parent.getParentRB() == null)
		{
			rootRBT = parent;
			
		}
		
	}

	//Performs left left rotation on a RB Tree.
	public void leftLeftRot(Node node)
	{
		Node parent = node.getParentRB();
		Node grandParent = parent.getParentRB();
		Node plc = parent.getRightChild();

		if(grandParent == rootRBT)
			;
		else if(grandParent.getParentRB().getLeftChild() == grandParent)
			grandParent.getParentRB().setLeftChild(parent);
		else
			grandParent.getParentRB().setRightChild(parent);

		parent.setParentRB(grandParent.getParentRB());
		parent.setRightChild(grandParent);
		grandParent.setParentRB(parent);
		grandParent.setLeftChild(plc);
		if(plc != null)
			plc.setParentRB(grandParent);
		parent.setColor("black");
		grandParent.setColor("red");

		if(parent.getParentRB() == null)
		{
			rootRBT = parent;
			
		}
	}

	//Function to insert a node.
	public void insert(int buildNum, int execTime, int total_time)
	{


		for(int i =0; i<arr.size(); i++)
		{
			if(arr.get(i).getBuildNum() == buildNum)
			{
				System.out.println("duplicate buildNum");
				System.exit(0);
			}
		}
		int n = arr.size();
		if(n>0)
		{	Node rsibling = arr.get(0);
			Node lsibling = arr.get(n-1);
			Node newNode = new Node(0,rsibling,buildNum,execTime,total_time);
			lsibling.setRightSibling(newNode);
			arr.add(newNode);
			root.add(newNode);
			if( execTime < min.getExecTime())
			{
				min = newNode;

			}
			else if( execTime == min.getExecTime())
			{
				if( min.getBuildNum() > buildNum)
					min = newNode;
			}
			formRBT(newNode);
		}
		
		else
		{
			Node newNode = new Node(0,buildNum,execTime,total_time);

			arr.add(newNode);
			root.add(newNode);

			min = newNode;
			formRBT(newNode);
		}
		
	}

	public static void main(String [] args)
	{
		
		DataStruct x = new DataStruct();
		try{
		PrintStream o = new PrintStream(new File("output_file.txt"));
		System.setOut(o); 
		} 
		catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
      	}
		
		try {
		String fileName = args[0];
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String[] arrOfStr;
		String[] arrOfStr1;
		String[] arrOfStr2;
		String s;
		int timeR = 0;
		while ((s = bufferedReader.readLine()) != null) {

			arrOfStr = s.split(":", 2);
		    arrOfStr1 = arrOfStr[1].split(",",2);
			arrOfStr2 = arrOfStr1[0].split("\\(",2);
			String num2 = arrOfStr1[1].substring(0,arrOfStr1[1].length()-1); 
			if(arrOfStr[1].contains("Insert"))
				{
					x.operate(Integer.parseInt(arrOfStr[0]),0,0,0);
					x.insert(Integer.parseInt(arrOfStr2[1]),0,Integer.parseInt(num2));
				}
			else 
				{
					
					x.operate(Integer.parseInt(arrOfStr[0]),1,Integer.parseInt(arrOfStr2[1]),Integer.parseInt(num2));
					if(x.del == false)
						x.search(Integer.parseInt(arrOfStr2[1]),Integer.parseInt(num2));
					else
						x.del = false;
				}
		}
			for(int i = 0; i<x.arr.size(); i++)
			{
				timeR += x.arr.get(i).getTotalTime() - x.arr.get(i).getExecTime();
			}
			x.operate(x.time+timeR,0,0,0);
		}
		catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch(IOException ex) {
            System.out.println("Error reading file");
        }
		
		
	}

}