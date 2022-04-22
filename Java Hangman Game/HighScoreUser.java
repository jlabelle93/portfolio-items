// Jacob Labelle 300306856
public class HighScoreUser {
	private String name;
	private int score;

	public HighScoreUser(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String toString() {
		return name + " " + score;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
}
