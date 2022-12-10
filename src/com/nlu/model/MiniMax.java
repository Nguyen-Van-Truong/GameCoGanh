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
		while (count == level) {
			for (Chessman chess : boardClone.getChesses()) {
				for (Positon pos : Check.allPosCanGo(boardClone, chess)) {
					boardClone.chessMove(chess, pos);
					TreeNode.add(new Node(null, boardClone.getArrBoard()));
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
