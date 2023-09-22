/*
 *     Copyright (C) 2023  Jack Barter
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
