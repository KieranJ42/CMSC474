package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

// This class contains a helper method used in the public tests, and two
// small example social networks that some of the tests call methods on.
//
// You can use these methods in writing your own tests if you want.

import socialNetwork.TikFace;
import java.util.Collection;

public class TestData {

  // In various tests we have to check the contents of a Collection returned
  // by a method, but we can't create a Collection that has the expected
  // values and use the equals() method to compare against the Collection,
  // because we don't even know what kind of Collection the methods will
  // return.  This method takes a Collection to check and another Collection
  // with the expected values (this Collection will actually be some type of
  // List).  Then it uses the Collection containsAll() method to compare the
  // two parameter Collections.  If we have two collections A and B, and A
  // contains all of the elements of B, and B contains all of the elements
  // of A, then it must be the case that they must have all the same
  // elements, and only the same elements.  Of course this is not
  // particularly efficient, but our goal is just to make it easy to check
  // the results of tests.
  public static <T> boolean compareCollections(Collection<T> collection,
                                               Collection<T> expected) {
    return collection.containsAll(expected) &&
           expected.containsAll(collection);
  }

  // example social networks ////////////////////////////////////////////

  // creates a new TikFace object with several users, but no friendships
  public static TikFace exampleSocialNetwork1() {
    TikFace socialNetwork= new TikFace();
    String[] users= new String[]{"Ethel", "Franz", "Gertrude", "Wallace",
                                 "Elmer", "Doris"};

    for (String user : users)
      socialNetwork.addUser(user);

    return socialNetwork;
  }

  // creates a new TikFace object with several users and several
  // friendships; note that adding the friendships will add the users also
  public static TikFace exampleSocialNetwork2() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.addFriends("Sheep", "Lion");
    socialNetwork.addFriends("Sheep", "Meerkat");
    socialNetwork.addFriends("Sheep", "Numbat");
    socialNetwork.addFriends("Lion", "Numbat");
    socialNetwork.addFriends("Meerkat", "Walrus");
    socialNetwork.addFriends("Numbat", "Penguin");
    socialNetwork.addFriends("Numbat", "Quokka");
    socialNetwork.addFriends("Penguin", "Otter");
    socialNetwork.addFriends("Quokka", "Walrus");

    return socialNetwork;
  }

}
