package com.nlu.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	List<Node> neighbours = new ArrayList<>();
	boolean visited = false;
	Node parent;
	int level;
	Board board;

	public Node() {
	}

	public Node(Node parent, Board board) {
		super();
		this.parent = parent;
		this.board = board;
	}

	public int heristics() {
		return countPiecesMan() - countPiecesBot();
	}

	// diem so quan co nguoi
	public int countPiecesMan() {
		int heristicMan = 0;
		for (ArrayList<Integer> row : board.getArrBoard()) {
			for (Integer value : row) {
				if (value == null)
					continue;
				if (value == 1)
					heristicMan++;
			}
		}
		return heristicMan;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	// diem so quan co may
	public int countPiecesBot() {
		int heristicBot = 0;
		for (ArrayList<Integer> row : board.getArrBoard()) {
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

	public void setNeighbours(List<Node> neighbours) {
		this.neighbours = neighbours;
	}

	@Override
	public String toString() {
		return "Node [board=" + board + "level:" + level + "]";
	}

}
