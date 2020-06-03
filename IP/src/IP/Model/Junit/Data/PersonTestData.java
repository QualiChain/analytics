package IP.Model.Junit.Data;

import java.util.ArrayList;
import java.util.List;

import IP.Model.JobPosting;
import IP.Model.Person;

public class PersonTestData {

	List<Person> Ps = new ArrayList<Person>();
	
	public PersonTestData() {
		Ps.add(new Person("Person1", "FirstName1", "Surname1", "Comment 1", "male", "123456789", "Person1@knowledgeBiz.pt",
				"personalPageURL1.com", "Portuguese", "Address1", true, "cv:id1", "CompetenceArea1", "CompetenceAreaDesc1", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person2", "FirstName2", "Surname2", "Comment 2", "female", "234567891", "Person2@knowledgeBiz.pt",
				"personalPageURL2.uk", "British", "Address2", true, "cv:id2", "CompetenceArea2", "CompetenceAreaDesc2", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person3", "FirstName3", "Surname3", "Comment 3", "male", "345678912", "Person3@knowledgeBiz.pt",
				"personalPageURL3.co.uk", "British", "Address3", true, "cv:id3", "CompetenceArea3", "CompetenceAreaDesc3", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person4", "FirstName4", "Surname4", "Comment 4", "female", "456789123", "Person4@knowledgeBiz.pt",
				"personalPageURL4.pt", "Portuguese", "Address4", true, "cv:id4", "CompetenceArea4", "CompetenceAreaDesc4", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person5", "FirstName5", "Surname5", "Comment 5", "male", "567891234", "Person5@knowledgeBiz.pt",
				"personalPageURL5.it", "Italian", "Address5", true, "cv:id5", "CompetenceArea5", "CompetenceAreaDesc5", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person6", "FirstName6", "Surname6", "Comment 6", "female", "678912345", "Person6@knowledgeBiz.pt",
				"personalPageURL6.gov.pt", "Spanish", "Address6", true, "cv:id6", "CompetenceArea6", "CompetenceAreaDesc6", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person7", "FirstName7", "Surname7", "Comment 7", "male", "789123456", "Person7@knowledgeBiz.pt",
				"personalPageURL7.xyz", "French", "Address7", true, "cv:id7", "CompetenceArea7", "CompetenceAreaDesc7", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person8", "FirstName8", "Surname8", "Comment 8", "female", "891234567", "Person8@knowledgeBiz.pt",
				"personalPageURL8.gg", "United States of America", "Address8", true, "cv:id8", "CompetenceArea8", "CompetenceAreaDesc8", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person9", "FirstName9", "Surname9", "Comment 9", "male", "912345678", "Person9@knowledgeBiz.pt",
				"personalPageURL9.nz", "New Zealand", "Address9", true, "cv:id9", "CompetenceArea9", "CompetenceAreaDesc9", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person10", "FirstName10", "Surname10", "Comment 10", "female", "987654321", "Person10@knowledgeBiz.pt",
				"personalPageURL10.au", "Australian", "Address10", true, "cv:id10", "CompetenceArea10", "CompetenceAreaDesc10", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person11", "FirstName11", "Surname11", "Comment 11", "male", "876543219", "Person11@knowledgeBiz.pt",
				"personalPageURL11.fr", "French", "Address11", true, "cv:id11", "CompetenceArea11", "CompetenceAreaDesc11", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person12", "FirstName12", "Surname12", "Comment 12", "female", "765432198", "Person12@knowledgeBiz.pt",
				"personalPageURL12.edu.com", "British", "Address12", true, "cv:id12", "CompetenceArea12", "CompetenceAreaDesc12", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person13", "FirstName13", "Surname13", "Comment 13", "male", "654321987", "Person13@knowledgeBiz.pt",
				"personalPageURL13.com", "Canadian", "Address13", true, "cv:id13", "CompetenceArea13", "CompetenceAreaDesc13", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("Person14", "FirstName14", "Surname14", "Comment 14", "female", "543219876", "Person14@knowledgeBiz.pt",
				"personalPageURL14.pt", "German", "Address14", true, "cv:id14", "CompetenceArea14", "CompetenceAreaDesc14", "Employee",
				null, null, null, null, null));
		Ps.add(new Person("p98173918", "Alberto", "Cabrita", "Alberto personal info", "Male", "922283384", "asc.99@jimail.com", "albertoS.com", "Portuguese", null, true,
				"cv:id98173918", null, null, "employee", null, null, null, null, null));
		Ps.add(new Person("p17623814", "Joaquim", "Cabral", "Joaquim personal info", "Male", "922245684", "jc.92@jimail.com", "JoJosCentral.com", "Portuguese", null, false,
				"cv:id17623814", null, null, "employee", null, null, null, null, null));
		Ps.add(new Person("p57168721", "Guilherme", "Belo", "Guilherme personal info", "Male", "939445684", "ggb.88@jimail.com", "PressFreeN.com", "Portuguese", null, true,
				"cv:id57168721", null, null, "employee", null, null, null, null, null));
	}
	
	public List<Person> getPersons(){
		return Ps;
	}
	
	public Person getPersonByURI(String URI) {
		for(Person p: Ps) {
			if(p.getURI().equals(URI))
				return p;
		}
		return null;
	}
	
	public Person getPersonByLabel(String label) {
		for(Person p: Ps) {
			if(p.getLabel().equals(label))
				return p;
		}
		return null;
	}
	
}
