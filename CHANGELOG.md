Groupe : 
	HELBERT Tanguy
	PARIS Nicolas
	PINCON Maxime

Changements : 

	Package io : 
		- Tests unitaires réalisés sur l'ensemble des classes de ce package : 
			- ByteArrayBufferTest.java
			- LabelLengthTest.java
			- UnsignedByteTest.java
			- UnsignedIntTest.java
			- UnsignedShortTest.java
	Classe Cache : 
		 - Tests unitaires et tests des TTL
		
		
		NOTE : Lorsque l'implémentation de la suppression du link après expiration du TTL sera en place, les tests en commentaires à la fin des méthodes 
					- testGetServersForInstanceTTLExpire
					- testGetServersForAddressTTLExpire
					- tesGetAddressesForServerTTLExpire
					- testGetInstancesForServiceTTLExpire
		Pourront être décommentés pour tester le fonctionnement de cette implémentation.