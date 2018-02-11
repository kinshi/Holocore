/***********************************************************************************
* Copyright (c) 2015 /// Project SWG /// www.projectswg.com                        *
*                                                                                  *
* ProjectSWG is the first NGE emulator for Star Wars Galaxies founded on           *
* July 7th, 2011 after SOE announced the official shutdown of Star Wars Galaxies.  *
* Our goal is to create an emulator which will provide a server for players to     *
* continue playing a game similar to the one they used to play. We are basing      *
* it on the final publish of the game prior to end-game events.                    *
*                                                                                  *
* This file is part of Holocore.                                                   *
*                                                                                  *
* -------------------------------------------------------------------------------- *
*                                                                                  *
* Holocore is free software: you can redistribute it and/or modify                 *
* it under the terms of the GNU Affero General Public License as                   *
* published by the Free Software Foundation, either version 3 of the               *
* License, or (at your option) any later version.                                  *
*                                                                                  *
* Holocore is distributed in the hope that it will be useful,                      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of                   *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                    *
* GNU Affero General Public License for more details.                              *
*                                                                                  *
* You should have received a copy of the GNU Affero General Public License         *
* along with Holocore.  If not, see <http://www.gnu.org/licenses/>.                *
*                                                                                  *
***********************************************************************************/
package com.projectswg.holocore.resources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.projectswg.holocore.resources.collections.TestSWGBitSet;
import com.projectswg.holocore.resources.collections.TestSWGFlag;
import com.projectswg.holocore.resources.collections.TestSWGList;
import com.projectswg.holocore.resources.network.TestLittleEndianDataOutputStream;
import com.projectswg.holocore.resources.objects.TestObjects;
import com.projectswg.holocore.resources.persistable.TestPersistable;
import com.projectswg.holocore.resources.services.TestConfig;

@RunWith(Suite.class)
@SuiteClasses({
	TestSWGList.class,
	TestSWGFlag.class,
	TestSWGBitSet.class,
	TestWeatherType.class,
	TestPersistable.class,
	TestConfig.class,
	TestPoint3D.class,
	TestQuaternion.class,
	TestLittleEndianDataOutputStream.class,
	TestObjects.class
})
public class TestResources {
	
}