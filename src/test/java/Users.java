import java.util.ArrayList;
import java.util.Arrays;

public class Users {

	public ArrayList<String> admins;
	public ArrayList<String> mods;
	public ArrayList<String> members;

	public Users() {
		this.admins = new ArrayList<>(Arrays.asList("Jack", "Bhajan"));
		this.mods = new ArrayList<>(Arrays.asList("xSchmidtie", "Ave731"));
		this.members = new ArrayList<>(Arrays.asList("mukkar", "iHyperr"));
	}

	@Override
	public String toString() {
		return "\n" + admins.toString() + "\n" + mods.toString() + "\n" + members.toString();
	}

}
