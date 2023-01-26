package fr.isep.game7wonderarch.domain;

/**
 * classe représentant une carte
 */
public class Card {

	/**
	 * recto de la carte
	 */
	public final CardType front;

	/**
	 * verso de la carte
	 */
	public final CardBack back;

	/**
	 * constructeur de la classe
	 * @param front le verso de la carte
	 * @param back le recto de la carte
	 */
	public Card(CardType front, CardBack back) {
		this.front = front;
		this.back = back;
	}

	/**
	 * détail d'une carte
	 * @return une représentation sous la forme d'une string d'une carte
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(100);
		sb.append("---- CARD ----").append("\n");
		sb.append("\tfront = ").append(front).append("\n");
		sb.append("\tback = ").append(back).append("\n");
		sb.append("---- **** ----").append("\n");
		return sb.toString();
	}
	
}
