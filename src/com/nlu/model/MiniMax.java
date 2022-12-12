package com.nlu.model;

import java.util.ArrayList;

public class MiniMax {
	Board board;
	ArrayList<Node> TreeNode = new ArrayList<>();
	int count = 0;
	int level;

	public MiniMax(Board board) {
		super();
		this.board = board;
	}

	public MiniMax(Board board, int level) {
		super();
		this.board = board;
		this.level = level;
		createTreeNode();
	}

	public void createTreeNode() {
		Board boardClone = board;
//		TreeNode.add(new Node(null, boardClone.getArrBoard()));
		Node initial = new Node(null, boardClone);
		Node parent = initial;
		while (count == level) {
			for (Chessman chess : boardClone.getChesses()) {
				if (chess.getValue() == boardClone.getTurn().getTurn())
					for (Positon pos : Check.allPosCanGo(boardClone, chess)) {
						if (boardClone.chessMove(chess, pos)) {
							Node child = new Node(parent, boardClone);
							parent.addNeighbours(child);
						}
					}
			}
			count++;
		}
	}


	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
