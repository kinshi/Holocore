/************************************************************************************
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
package resources.persistable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.projectswg.common.persistable.InputPersistenceStream;
import com.projectswg.common.persistable.OutputPersistenceStream;

import resources.encodables.OutOfBandPackage;
import resources.encodables.ProsePackage;
import resources.encodables.StringId;
import resources.encodables.player.Mail;
import resources.objects.SpecificObject;
import resources.objects.waypoint.WaypointObject;
import services.objects.ObjectCreator;

@RunWith(JUnit4.class)
public class TestMailPersistable {
	
	@Test
	public void testMailStringIdPersistable() throws IOException {
		Mail mail = new Mail("sender", "subject", "message", 100);
		mail.setOutOfBandPackage(new OutOfBandPackage(new StringId("key", "value")));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new StringId("key", "value"), new StringId("key2", "value2")));
		test(mail);
	}
	
	@Test
	public void testMailProsePackagePersistable() throws IOException {
		Mail mail = new Mail("sender", "subject", "message", 100);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DI", 50)));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DF", 10f)));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DI", 50), new ProsePackage(new StringId("key", "value"), "DI", 25)));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DF", 10f), new ProsePackage(new StringId("key", "value"), "DF", 20f)));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DI", 50), new ProsePackage(new StringId("key", "value"), "DF", 25f)));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(new ProsePackage(new StringId("key", "value"), "DF", 10f), new ProsePackage(new StringId("key", "value"), "DI", 20)));
		test(mail);
	}
	
	@Test
	public void testMailWaypointPersistable() throws IOException {
		Mail mail = new Mail("sender", "subject", "message", 100);
		WaypointObject waypoint1 = (WaypointObject) ObjectCreator.createObjectFromTemplate(SpecificObject.SO_WAYPOINT_BLUE.getTemplate());
		WaypointObject waypoint2 = (WaypointObject) ObjectCreator.createObjectFromTemplate(SpecificObject.SO_WAYPOINT_BLUE.getTemplate());
		mail.setOutOfBandPackage(new OutOfBandPackage(waypoint1));
		test(mail);
		mail.setOutOfBandPackage(new OutOfBandPackage(waypoint1, waypoint2));
		test(mail);
	}
	
	private void test(Mail mail) throws IOException {
		byte [] written = write(mail);
		Mail read = read(written);
		test(mail, read);
	}
	
	private void test(Mail write, Mail read) {
		Assert.assertEquals(write.getSender(), read.getSender());
		Assert.assertEquals(write.getSubject(), read.getSubject());
		Assert.assertEquals(write.getReceiverId(), read.getReceiverId());
		Assert.assertEquals(write.getMessage(), read.getMessage());
		Assert.assertEquals(write.getLength(), read.getLength());
		Assert.assertEquals(write.getOutOfBandPackage().getLength(), read.getOutOfBandPackage().getLength());
		Assert.assertEquals(write.getOutOfBandPackage().getPackages(), read.getOutOfBandPackage().getPackages());
	}
	
	private byte [] write(Mail mail) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputPersistenceStream os = new OutputPersistenceStream(baos);
		os.write(mail, Mail::save);
		os.close();
		return baos.toByteArray();
	}
	
	private Mail read(byte [] data) throws IOException {
		try (InputPersistenceStream is = new InputPersistenceStream(new ByteArrayInputStream(data))) {
			Mail mail = is.read(Mail::create);
			Assert.assertEquals(0, is.available());
			return mail;
		}
	}
	
}
