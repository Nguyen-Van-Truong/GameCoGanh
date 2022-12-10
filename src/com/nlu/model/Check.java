package com.nlu.model;

import java.util.*;

public class Check {
	static int count = 0;

	// nhung quan co bi vay chet
	public static ArrayList<Positon> encircle(Board board, int value) {
		// quan co phai la quan co khac gia tri voi quan co vua di
		ArrayList<Positon> result = new ArrayList<>();
		for (Chessman chess : board.getChesses()) {
//			System.out.println(isInclosed(board, chess));
			if (isInclosed(board, chess)) {
				System.out.println("aa:" + chess.getPositon());
				if (chess.getValue() == value)
					result.add(chess.getPositon());
			}
		}
		return result;
	}

	public static List<Positon> getNeighbors(Board board, Chessman chess, List<Positon> visit) {
		if (visit == null)
			visit = new ArrayList<Positon>();
		visit.add(chess.getPositon());
		List<Positon> result = new ArrayList<>();
		int row = chess.getRow();
		int col = chess.getColumn();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				int rowMove1 = row + i;// di chuyen 1 dong tiep theo
				int colMove1 = col + j;// di chuyen 1 cot tiep theo
				if (rowMove1 < 0 || rowMove1 > 4 || colMove1 < 0 || colMove1 > 4)
					continue;
				if (board.posValue(rowMove1, colMove1) == chess.getValue()) {
					Positon posMove1 = new Positon(rowMove1, colMove1);
					if (!isContainPos(visit, posMove1)) {
						result.add(posMove1);
						result.addAll(getNeighbors(board, board.posChess(rowMove1, colMove1), visit));
					}
				}
			}
		}
		return result;
	}

	public static boolean isContainPos(List<Positon> result, Positon positon) {
		for (Positon pos : result) {
			if (pos.equal(positon))
				return true;
		}
		return false;
	}

	// neu quan co va cac quan co lien ket voi quan co do, tat ca khong co nuoc di
	// thi quan co do bi vay
	private static boolean isInclosed(Board board, Chessman chess) {
		if (allPosCanGo(board, chess).size() > 0)
			return false;
		for (Positon pos : getNeighbors(board, chess, null)) {
			if (allPosCanGo(board, board.posChess(pos.getRow(), pos.getCol())).size() > 0)
				return false;
		}
		return true;
	}

	public static ArrayList<Chessman> allChessMustMove(Board board, int value) {
		ArrayList<Chessman> result = new ArrayList<>();
		for (Chessman chessman : board.getChesses()) {
			if (chessman.getValue() != value)
				continue;
			int row = chessman.getPositon().getRow();
			int col = chessman.getPositon().getCol();

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0)
						continue;
					int rowMove1 = row + i;// di chuyen 1 dong tiep theo
					int colMove1 = col + j;// di chuyen 1 cot tiep theo
					if (rowMove1 < 0 || rowMove1 > 4 || colMove1 < 0 || colMove1 > 4)
						continue;

					if (posSpecial(row, col, rowMove1, colMove1))
						continue;
					Integer v = board.posValue(rowMove1, colMove1);
					// vi tri co the di ke tiep phai la null va trong pham vi ban co
					if (v == null && rowMove1 >= 0 && rowMove1 <= 4 && colMove1 >= 0 && colMove1 <= 4) {
						ArrayList<Positon> listPosTake = listPosTake(board, chessman, rowMove1, colMove1);
						if (isTake(listPosTake)) {
							result.add(chessman);
						}
					}

				}
			}
		}

		return result;
	}

	// tat ca vi tri co the di cua 1 quan co
	public static ArrayList<Positon> allPosCanGo(Board board, Chessman chessman) {
		ArrayList<Positon> result = new ArrayList<>();
		ArrayList<Positon> mustMove = new ArrayList<>();

		int row = chessman.getPositon().getRow();
		int col = chessman.getPositon().getCol();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				int rowMove1 = row + i;// di chuyen 1 dong tiep theo
				int colMove1 = col + j;// di chuyen 1 cot tiep theo
				if (rowMove1 < 0 || rowMove1 > 4 || colMove1 < 0 || colMove1 > 4)
					continue;

				if (posSpecial(row, col, rowMove1, colMove1))
					continue;
				Integer v = board.posValue(rowMove1, colMove1);
				// vi tri co the di ke tiep phai la null va trong pham vi ban co
				if (v == null && rowMove1 >= 0 && rowMove1 <= 4 && colMove1 >= 0 && colMove1 <= 4) {
					result.add(new Positon(rowMove1, colMove1));
					ArrayList<Positon> listPosTake = listPosTake(board, chessman, rowMove1, colMove1);
					if (isTake(listPosTake)) {
						mustMove.add(new Positon(rowMove1, colMove1));
					}
				}

			}
		}
		if (mustMove.size() > 0) {
			System.out.println("doi phuong da mo duong, ban phai ganh");
			System.out.println(mustMove);
			return mustMove;
		}
		return result;
	}

	// xem trang thai ban co hien tai co phai ganh khong
	private static boolean isTake(ArrayList<Positon> listPosTake) {
		return listPosTake.size() > 0;
	}

	private static boolean posSpecial(int row, int col, int rowMove1, int colMove1) {
		// vi tri 0;1 va 0;3
		if ((row == 0 && col == 1)
				&& ((rowMove1 == row + 1 && colMove1 == col - 1) || (rowMove1 == row + 1 && colMove1 == col + 1)))
			return true;
		if ((row == 0 && col == 3)
				&& ((rowMove1 == row + 1 && colMove1 == col - 1) || (rowMove1 == row + 1 && colMove1 == col + 1)))
			return true;
		// vi tri 1;0 va 1;4
		if ((row == 1 && col == 0)
				&& ((rowMove1 == row - 1 && colMove1 == col + 1) || (rowMove1 == row + 1 && colMove1 == col + 1)))
			return true;
		if ((row == 1 && col == 4)
				&& ((rowMove1 == row - 1 && colMove1 == col - 1) || (rowMove1 == row + 1 && colMove1 == col - 1)))
			return true;
		// vi tri 3;0 va 3;4
		if ((row == 3 && col == 0)
				&& ((rowMove1 == row - 1 && colMove1 == col + 1) || (rowMove1 == row + 1 && colMove1 == col + 1)))
			return true;
		if ((row == 3 && col == 4)
				&& ((rowMove1 == row - 1 && colMove1 == col - 1) || (rowMove1 == row + 1 && colMove1 == col - 1)))
			return true;
		// vi tri 4;1 va 4;3
		if (((row == 4 && col == 1) || (row == 4 && col == 3))
				&& ((rowMove1 == row - 1 && colMove1 == col - 1) || (rowMove1 == row - 1 && colMove1 == col + 1)))
			return true;
		// vi tri 1;2 va 3;2 va 2;1 va 2;3
		if (((row == 1 && col == 2) || (row == 3 && col == 2) || (row == 2 && col == 1) || (row == 2 && col == 3))
				&& ((rowMove1 == row - 1 && colMove1 == col - 1) || (rowMove1 == row - 1 && colMove1 == col + 1)
						|| (rowMove1 == row + 1 && colMove1 == col - 1)
						|| (rowMove1 == row + 1 && colMove1 == col + 1)))
			return true;
		return false;
	}

	public static ArrayList<Positon> listPosTake(Board board, Chessman chessman, int rowMove1, int colMove1) {
		ArrayList<Positon> result = new ArrayList<>();
		// 2 ben tren duoi
		if (compareNumber(board.posValue(rowMove1 + 1, colMove1), (chessman.getValue() * -1))
				&& compareNumber(board.posValue(rowMove1 - 1, colMove1), (chessman.getValue() * -1))) {
			System.out.println(1);
			result.add(new Positon(rowMove1 + 1, colMove1));
			result.add(new Positon(rowMove1 - 1, colMove1));
		}
//			// 2 ben trai phai
		if (compareNumber(board.posValue(rowMove1, colMove1 + 1), (chessman.getValue() * -1))
				&& compareNumber(board.posValue(rowMove1, colMove1 - 1), (chessman.getValue() * -1))) {
			System.out.println(2);
			result.add(new Positon(rowMove1, colMove1 + 1));
			result.add(new Positon(rowMove1, colMove1 - 1));
		}
		// 2 ben duong cheo trai qua phai
		if ((compareNumber(board.posValue(rowMove1 + 1, colMove1 - 1), (chessman.getValue() * -1)))
				&& (compareNumber(board.posValue(rowMove1 - 1, colMove1 + 1), (chessman.getValue() * -1)))) {
			System.out.println(3);
			result.add(new Positon(rowMove1 + 1, colMove1 - 1));
			result.add(new Positon(rowMove1 - 1, colMove1 + 1));
		}
		// 2 ben duong cheo phai qua trai
		if ((compareNumber(board.posValue(rowMove1 + 1, colMove1 + 1), (chessman.getValue() * -1)))
				&& (compareNumber(board.posValue(rowMove1 - 1, colMove1 - 1), (chessman.getValue() * -1)))) {
			System.out.println(4);
			result.add(new Positon(rowMove1 + 1, colMove1 + 1));
			result.add(new Positon(rowMove1 - 1, colMove1 - 1));
		}
		return result;
	}

	private static boolean compareNumber(Integer n1, Integer n2) {
		try {
			return n1 == n2;
		} catch (Exception e) {
		}
		return false;
	}
}
