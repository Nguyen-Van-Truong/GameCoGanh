package com.nlu.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	List<Node> neighbours = new ArrayList<>();
	boolean visited = false;
	Node parent;
	private ArrayList<ArrayList<Integer>> arrBoard;

	public Node() {
	}

	public Node(Node parent, ArrayList<ArrayList<Integer>> arrBoard) {
		super();
		this.parent = parent;
		this.arrBoard = arrBoard;
	}

	public int heristics() {
		return countPiecesMan() - countPiecesBot();
	}

	// diem so quan co nguoi
	public int countPiecesMan() {
		int heristicMan = 0;
		for (ArrayList<Integer> row : arrBoard) {
			for (Integer value : row) {
				if (value == null)
					continue;
				if (value == 1)
					heristicMan++;
			}
		}
		return heristicMan;
	}

	// diem so quan co may
	public int countPiecesBot() {
		int heristicBot = 0;
		for (ArrayList<Integer> row : arrBoard) {
			for (Integer value : row) {
				if (value == null)
					continue;
				if (value == -1)
					heristicBot++;
			}
		}
		return heristicBot;
	}

	public void addNeighbours(Node neighbourNode) {
		this.neighbours.add(neighbourNode);
	}

	public List<Node> getNeighbours() {
		return neighbours;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public ArrayList<ArrayList<Integer>> getArrBoard() {
		return arrBoard;
	}

	public void setArrBoard(ArrayList<ArrayList<Integer>> arrBoard) {
		this.arrBoard = arrBoard;
	}

	public void setNeighbours(List<Node> neighbours) {
		this.neighbours = neighbours;
	}

}
