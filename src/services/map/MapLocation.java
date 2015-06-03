/*******************************************************************************
 * Copyright (c) 2015 /// Project SWG /// www.projectswg.com
 *
 * ProjectSWG is the first NGE emulator for Star Wars Galaxies founded on
 * July 7th, 2011 after SOE announced the official shutdown of Star Wars Galaxies.
 * Our goal is to create an emulator which will provide a server for players to
 * continue playing a game similar to the one they used to play. We are basing
 * it on the final publish of the game prior to end-game events.
 *
 * This file is part of Holocore.
 *
 * --------------------------------------------------------------------------------
 *
 * Holocore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Holocore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Holocore.  If not, see <http://www.gnu.org/licenses/>
 ******************************************************************************/

package services.map;

import resources.network.BaselineBuilder;
import utilities.ByteUtilities;
import utilities.Encoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MapLocation implements BaselineBuilder.Encodable{
	private long id;
	private String name;
	private float x;
	private float y;
	private byte category;
	private byte subcategory;
	private boolean isActive;

	private byte[] data;

	public MapLocation() {
	}

	public MapLocation(long id, String name, float x, float y, byte category, byte subcategory, boolean isActive) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.category = category;
		this.subcategory = subcategory;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public byte getCategory() {
		return category;
	}

	public void setCategory(byte category) {
		this.category = category;
	}

	public byte getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(byte subcategory) {
		this.subcategory = subcategory;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public byte[] encode() {
		if (data != null)
			return data;

		ByteBuffer bb = ByteBuffer.allocate((name.length() * 2) + 23).order(ByteOrder.LITTLE_ENDIAN);
		bb.putLong(id);
		bb.put(Encoder.encodeUnicode(name));
		bb.putFloat(x);
		bb.putFloat(y);
		bb.put(category);
		bb.put(subcategory);
		bb.put((byte) (isActive ? 1 : 0));
		data = bb.array();
		return data;
	}
}
