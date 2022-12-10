package com.nlu.model;

import java.util.ArrayList;

public class MiniMax {
	Board board;
	ArrayList<Node> listStatus = new ArrayList<>();
	int count = 0;
	ArrayList<Integer> statusPointMans = new ArrayList<>();
	ArrayList<Integer> statusPointBots = new ArrayList<>();
	ArrayList<Integer> heristicStatusBoards = new ArrayList<>();
	int level;

	public MiniMax(Board board) {
		super();
		this.board = board;
	}

	public MiniMax(Board board, int level) {
		super();
		this.board = board;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	private void miniMax() {

	}

}
