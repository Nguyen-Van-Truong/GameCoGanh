package com.nlu.model;

import java.util.*;

public class Board {
	private ArrayList<ArrayList<Integer>> arrBoard = new ArrayList<>(5);
	private ArrayList<Chessman> chesses = new ArrayList<>();
	private BoardStatus boardStatus;
	private Turn turn;
	private ArrayList<Chessman> chessesMustMove = new ArrayList<>();

	public Board() {
		turn = new Turn();
		turn.setTurn(-1);
		arrBoard = createArrBoard();
		boardStatus = new BoardStatus(this);
		start();
	}

	public ArrayList<Chessman> getChessesMustMove() {
		return chessesMustMove;
	}

	public void setChessesMustMove(ArrayList<Chessman> chessesMustMove) {
		this.chessesMustMove = chessesMustMove;
	}

	public ArrayList<ArrayList<Integer>> getArrBoard() {
		return arrBoard;
	}

	public ArrayList<ArrayList<Integer>> createArrBoard() {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>(5);
		for (int j = 0; j < 5; j++) {
			ArrayList<Integer> liChild = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				liChild.add(null);
			}
			result.add(liChild);
		}
		return result;
	}

	public boolean isGameOver() {
		if (heristics() == 18)
			return true;
		return false;
	}

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

	public int heristics() {
		return countPiecesMan() - countPiecesBot();
	}

	public void start() {
		for (int i = 0; i < 5; i++) {
			getChesses().add(new Chessman(1, new Positon(0, i)));
		}
		for (int i = 0; i < 5; i++) {
			getChesses().add(new Chessman(-1, new Positon(4, i)));
		}
		getChesses().add(new Chessman(1, new Positon(1, 0)));
		getChesses().add(new Chessman(1, new Positon(1, 4)));
		getChesses().add(new Chessman(1, new Positon(2, 4)));
		getChesses().add(new Chessman(-1, new Positon(2, 0)));
		getChesses().add(new Chessman(-1, new Positon(3, 0)));
		getChesses().add(new Chessman(-1, new Positon(3, 4)));
		update();

	}

	private void update() {
		ArrayList<ArrayList<Integer>> newArrBoard = createArrBoard();
		for (Chessman chessman : getChesses()) {
			newArrBoard.get(chessman.getPositon().getRow()).set(chessman.getPositon().getCol(), chessman.getValue());
		}
		arrBoard = newArrBoard;
		boardStatus.addStatus();
	}

	private void updateNotAddStatus() {
		ArrayList<ArrayList<Integer>> newArrBoard = createArrBoard();
		for (Chessman chessman : getChesses()) {
			newArrBoard.get(chessman.getPositon().getRow()).set(chessman.getPositon().getCol(), chessman.getValue());
		}
		arrBoard = newArrBoard;
	}

	public Chessman posChess(int row, int col) {
		for (Chessman chessman : getChesses()) {
			if (chessman.getPositon().getCol() == col && chessman.getPositon().getRow() == row) {
				return chessman;
			}
		}
		return null;
	}

	public void minimax(Node root, int dept) {
		if (dept == 0 || isGameOver()) {
			return;
		}
		Board boardClone = root.getBoard();
		System.out.println(root);
		for (Chessman chess : boardClone.getChesses()) {
			if (chess.getValue() == boardClone.getTurn().getTurn())
				for (Positon pos : Check.allPosCanGo(boardClone, chess)) {
					if (boardClone.chessMove(chess, pos)) {
						Node child = new Node(root, boardClone);
						child.setLevel(dept);
						System.out.println(child);
						root.addNeighbours(child);
						minimax(child, dept - 1);
					}
				}
		}

	}

	public Integer posValue(int row, int col) {
		if (row > 4 || col > 4 || row < 0 || col < 0)
			return null;
		return arrBoard.get(row).get(col);
	}

	public Turn getTurn() {
		return turn;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < arrBoard.size(); i++) {
			result += arrBoard.get(i) + "\r";
		}
		return result;
	}

	// control
	public boolean chessMove(Chessman chessman, Positon pos) {
		if (chessman.getValue() == turn.getTurn() * -1) {
//			System.out.println("chua den luot:");
			return false;
		}
		ArrayList<Chessman> allChessMustMove = Check.allChessMustMove(this, turn.getTurn());
		if (allChessMustMove.size() > 0 && !allChessMustMove.contains(chessman))
			return false;

		for (Positon posTrue : Check.allPosCanGo(this, chessman)) {
			if (posTrue.equal(pos)) {
				chessman.move(pos);
				int col = chessman.getPositon().getCol();
				int row = chessman.getPositon().getRow();
				ganh(Check.listPosTake(this, chessman, row, col));
				updateNotAddStatus();
				ganh(Check.encircle(this, chessman.getValue() * -1));// quan co phai khac voi quan co moi di
				update();
				turn.setTurn(turn.getTurn() * -1);
				return true;
			}
		}
		return false;
	}

	public void ganh(ArrayList<Positon> listPosTake) {
		for (Positon pos : listPosTake) {
			for (Chessman chessman : getChesses()) {
				if (chessman.getPositon().equal(pos)) {
					chessman.setValue(chessman.getValue() * -1);
				}
			}
		}
	}

	public ArrayList<Chessman> getChesses() {
		return chesses;
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.minimax(new Node(null, board), 5);
		
//		Chessman chess42 = board.posChess(4, 2);
//		Chessman chess02 = board.posChess(0, 2);
//		Chessman chess01 = board.posChess(0, 1);
//		Chessman chess44 = board.posChess(4, 4);
//		Chessman chess24 = board.posChess(2, 4);
//
//		board.chessMove(chess42, new Positon(3, 2));
//		board.chessMove(chess02, new Positon(1, 2));
//		board.chessMove(chess24, new Positon(3, 3));

//		board.chessMove(chess42, new Positon(2, 2));
//		System.out.println("chess02");
//		board.chessMove(chess02, new Positon(1, 1));
//		board.chessMove(chess44, new Positon(3, 3));
//
//		board.chessMove(chess01, new Positon(0, 2));

//		board.chessMove(chess42, new Positon(1, 3));
//		board.chessMove(chess44, new Positon(3, 2));

//		System.out.println(board.boardStatus);
//		System.out.println(Check.getNeighbors(board, chess02, null));
//		System.out.println(Check.encircle(board, 1));
//		for (Chessman c : Check.allChessMustMove(board, 1)) {
//			System.out.print(c.getPositon() + ";");
		
//		}
	}
	
}
