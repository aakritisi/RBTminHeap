import java.util.ArrayList;
public class Node
{
	private int degree;
	private Node rsibling;
	private String color;
	private Node parentRB;
	private Node rChild;
	private Node lChild;
	private int degreeRB;
	private Node parent;
	private ArrayList<Node> child = new ArrayList<Node>();
	private int builduingNum;
	private int executed_time;
	private int total_time;

	public Node(){}

	public Node(int degree, int builduingNum, int executed_time, int total_time){
		this.degree = degree;
		this.builduingNum = builduingNum;
		this.executed_time = executed_time;
		this.total_time = total_time;
	}

	public Node(int degree,Node rsibling, int builduingNum, int executed_time, int total_time){
		this.degree = degree;
		this.rsibling = rsibling;
		this.builduingNum = builduingNum;
		this.executed_time = executed_time;
		this.total_time = total_time;
	}

	public Node(int degree, Node rsibling, Node parent, ArrayList<Node> child, int builduingNum, int executed_time, int total_time) {

		this.degree = degree;
		this.rsibling = rsibling;
		this.parent = parent;
		this.child = child;
		this.builduingNum = builduingNum;
		this.executed_time = executed_time;
		this.total_time = total_time;

	}
	public Node getParentRB(){
		return parentRB;
	}

	public Node getRightChild(){
		return rChild;
	}

	public Node getLeftChild(){
		return lChild;
	}

	public String getColor(){
		return color;
	}

	public int getDegree(){
		return degree;
	}

	public int getDegreeRB(){
		return degreeRB;
	}

	public Node getRightSibling(){
		return rsibling;
	}

	public Node getParent(){
		return parent;
	} 

	public ArrayList<Node> getChild(){
		return child;
	}

	public int getBuildNum(){
		return builduingNum;
	}

	public int getExecTime(){
		return executed_time;
	}

	public int getTotalTime(){
		return total_time;
	}

	public void setDegree(int degree){
		this.degree = degree;
	}

	public void setRightSibling(Node rsibling){
		this.rsibling = rsibling;
	}

	public void setParent(Node parent){
		this.parent = parent;
	}

	public void setChild(ArrayList<Node> newChildList)
	{
		this.child = newChildList;
	}
	public void addChild(Node newChild){
		child.add(newChild);
	}

	public void setBuildNum(int builduingNum){
		this.builduingNum = builduingNum;
	}

	public void setExecTime(int executed_time){
		this.executed_time = executed_time;
	}

	public void setTotalTime(int total_time){
		this.total_time = total_time;
	}

	public void increaseDegreeRB(int val){
		this.degree = val + this.degree;
	}

	public void setDegreeRB(int degree){
		this.degree = degree;
	}

	public void setParentRB(Node parentRB){
		this.parentRB = parentRB;
	}

	public void setRightChild(Node rChild){
		this.rChild = rChild;
	}

	public void setLeftChild(Node lChild){
		this.lChild = lChild;
	}

	public void setColor(String color){
		this.color = color;
	}

}