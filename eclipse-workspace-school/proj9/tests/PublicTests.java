package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

/* To avoid having to include too many data files in everyone's CVS
 * repository, or use up too much disk space with data files, the last three
 * public tests create data files, write randomly generated user data to
 * them, call your readUserData() method using the randomly generated data
 * files, and then remove the data files afterwards.  They do this using two
 * static auxiliary helper methods that are defined in a class FileCreator,
 * which is included in a jarfile that is part of the project distribution
 * (named FileCreator.jar).  The helper methods also return a data structure
 * containing the information in the files, so tests can use the data
 * structure to check that your readUserData() method is creating threads to
 * read the data files correctly.  These helper methods are written so
 * although the user and friend relationships are randomly generated, the
 * same random data will be created every time these tests are run.  (The
 * methods use what is called a pseudorandom number sequence.)  This means
 * that if you want to look at the files that are created (see below for
 * how) you can be confident that the files will be created with the same
 * contents every time these tests are run.
 *
 * The first method is
 *
 *   static List<String> createUserFiles(int numFiles, int numUsers,
 *                                       int maxUserSuffix, String baseName)
 *
 *   It creates numFiles different files that have only user additions
 *   (lines with adduser).  There will be numUsers total user additions in
 *   all of the files.  The names of the users will all begin with "person"
 *   and will have a suffix between 1 and maxUserSuffix appended (for
 *   example person1, person2, etc.).  The files will all start with the
 *   string baseName and have a suffix between 1 and numfiles appended.  For
 *   example, if numFiles is 2, numUsers is 18, maxUserSuffix is 100, and
 *   baseName is "test", this method will create two files, each with nine
 *   "adduser" lines, containing 18 randomly generated user names between
 *   "person1" and "person100", and the two files will be named "test1" and
 *   "test2".  There will not be any duplicated user additions in the files.
 *
 *   The method returns a list of strings which are the names of all of the
 *   users in the created files, in a random order.
 *
 * The second method is
 *
 *   static HashMap<String, List<String>> createFriendFiles(int numFiles,
 *                                                          int numUsers,
 *                                                          int numFriends,
 *                                                          int maxUserSuffix,
 *                                                          String baseName)
 *
 *   It creates numFiles different files that have only friend additions
 *   (lines with addfriends).  There will be numFriends total friend
 *   additions in all of the files, between numUsers different users.  The
 *   names of the users will all begin with "person" and have a suffix
 *   between 1 and maxUserSuffix appended (for example person1, person2,
 *   etc.).  The files will all start with the string baseName and have a
 *   suffix between 1 and numfiles appended.  For example, if numFiles is 3,
 *   numUsers is 20, numFriends is 45, maxUserSuffix is 80, and baseName is
 *   "afile", this method will create three files, each with fifteen
 *   "addfriends" lines, containing 20 randomly generated user names between
 *   "person1" and "person80", and the three files will be named "afile1",
 *   "afile2", and "afile3".  The friend relationships in the created files
 *   (or the returned HashMap) will not have any duplicates and there will
 *   not be any friend relationships in the files that are between the same
 *   person.
 *
 *   The method returns a map whose keys are the names of all of the users
 *   in the friend relationships in the files, and the value for each key is
 *   a list of the names of all of the other users who they are friends with
 *   in the files.  If a and b are friends then b will be in the list with a
 *   as a key, and a will be in the list with b as a key.
 *
 * If you want to see the data structures that are returned by these methods
 * you can look at them in the Eclipse debugger, or print them using
 * toString().
 *
 * If you want to see the data files that are created by the tests that use
 * these methods, without them being deleted when the tests finish, just
 * change the value of KEEP_INPUT_FILES to true at the beginning of the
 * class below and rerun the tests.
 *
 * You can use these methods in writing your own tests if you want.
 */

import socialNetwork.TikFace;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  // set this to true to retain all of the automatically generated input
  // data files
  public final static boolean KEEP_INPUT_FILES= false;

  // Tests that the getAllUsers() method returns the names of all of the
  // users of a social network that has users but no friend relationships.
  // Note that this test does not create or use any threads.
  @Test public void testPublic1() {
    TikFace socialNetwork= TestData.exampleSocialNetwork1();

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Doris", "Elmer",
                                                      "Ethel", "Franz",
                                                      "Gertrude",
                                                      "Wallace")));
  }

  // Tests that the getFriends() method returns the names of all of the
  // friends of a user.  Note that this test does not create or use any
  // threads.
  @Test public void testPublic2() {
    TikFace socialNetwork= TestData.exampleSocialNetwork2();

    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Numbat"),
                                        Arrays.asList("Sheep", "Lion",
                                                      "Penguin", "Quokka")));
  }

  // Tests the peopleWhoMayWantToKnowYou() method.  Note that this test does
  // not create or use any threads.
  @Test public void testPublic3() {
    TikFace socialNetwork= TestData.exampleSocialNetwork2();

    assertTrue(TestData.compareCollections(
      socialNetwork.peopleWhoMayWantToKnowYou("Walrus"),
      Arrays.asList("Sheep", "Numbat")));
  }

  // Creates one thread, which reads one list of user data, which only
  // contains one user addition, just to ensure that one thread can be
  // created and manipulated correctly.
  @Test public void testPublic4() {
    TikFace socialNetwork= new TikFace();
    socialNetwork.readUserData(Arrays.asList("userdata-public4"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Sheep")));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions.  Theresults are checked by calling
  // getAllUsers().
  @Test public void testPublic5() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public5"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Doris", "Elmer",
                                                      "Ethel", "Franz",
                                                      "Gertrude",
                                                      "Wallace")));
  }

  // Creates one thread, which reads one list of user data, which only
  // contains one friend relationship.  Note that creating the friend
  // relationship will add or create the two users in the process.  The
  // results are checked by calling getAllUsers() and getFriends().  This
  // also checks that creating a friendship works both ways (both users
  // become friends of each other).
  @Test public void testPublic6() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public6"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Aardvark",
                                                      "Platypus")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Aardvark"),
                                        Arrays.asList("Platypus")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Platypus"),
                                        Arrays.asList("Aardvark")));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions and several friend relationships.  The results
  // are checked by calling getAllUsers(), and getFriends() on some of the
  // users.
  @Test public void testPublic7() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public7+8"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Sheep", "Lion",
                                                      "Meerkat", "Numbat",
                                                      "Otter", "Penguin",
                                                      "Quokka", "Walrus")));

    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Otter"),
                                        Arrays.asList("Penguin")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Sheep"),
                                        Arrays.asList("Lion", "Numbat",
                                                      "Meerkat")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Numbat"),
                                        Arrays.asList("Sheep", "Lion",
                                                      "Penguin", "Quokka")));
  }

  // Creates one thread, which reads one list of user data, which contains
  // several user additions and several friend relationships, and calls the
  // peopleWhoMayWantToKnowYou() method.
  @Test public void testPublic8() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public7+8"));

    assertTrue(TestData.compareCollections(
      socialNetwork.peopleWhoMayWantToKnowYou("Walrus"),
      Arrays.asList("Sheep", "Numbat")));
  }

  // Creates two threads, which each independently read the same list of
  // user data, which contains two user additions for the same person; the
  // duplicate addition, even when performed concurrently by two threads,
  // should have no effect.
  @Test public void testPublic9() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public9",
                                             "userdata-public9"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Sheep")));
  }

  // Creates two threads, which each independently read the same list of
  // user data, which contains two friend relationships for the same two
  // people; the duplicate friend relationship, even when performed
  // concurrently by two threads, should have no effect.
  @Test public void testPublic10() {
    TikFace socialNetwork= new TikFace();

    socialNetwork.readUserData(Arrays.asList("userdata-public10",
                                             "userdata-public10"));

    assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                        Arrays.asList("Aardvark",
                                                      "Platypus")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Aardvark"),
                                        Arrays.asList("Platypus")));
    assertTrue(TestData.compareCollections(socialNetwork.getFriends("Platypus"),
                                        Arrays.asList("Aardvark")));
  }

  // Creates two threads, which each read a list of user data, which contain
  // many user additions for different people, and calls getAllUsers().
  @Test public void testPublic11() {
    TikFace socialNetwork= new TikFace();
    int numFiles= 2, numUsers= 60, maxUserSuffix= 500, i;
    String baseFilename= "userdata-public11-";
    List<String> filenames= new ArrayList<>();
    List<String> expectedResults;

    // even if the test has a fatal exception we still want to remove the
    // data files that were generated, unless the field KEEP_INPUT_FILES is
    // set to true above.
    try {
      // create the list of all filenames
      for (i= 1; i <= numFiles; i++)
        filenames.add(baseFilename + i);

      // call the helper method that actually writes the files
      expectedResults= FileCreator.createUserFiles(numFiles, numUsers,
                                                   maxUserSuffix,
                                                   baseFilename);

      // create threads to read the files concurrently and add their data to
      // the social network
      socialNetwork.readUserData(filenames);

      // check the users
      assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                          expectedResults));
    } finally {
      // remove the files after the test is done.  (If you want to see the
      // files later, just change KEEP_INPUT_FILES to true at the top.)
      if (!KEEP_INPUT_FILES)
        for (String eachFile : filenames)
          new File(eachFile).delete();
    }
  }

  // Creates two threads, which each read a list of user data, which contain
  // many friend relationships for different people.
  @Test public void testPublic12() {
    TikFace socialNetwork= new TikFace();
    int numFiles= 2, numUsers= 30, maxUserSuffix= 1000, numFriends= 40, i;
    String baseFilename= "userdata-public12-";
    List<String> filenames= new ArrayList<>();
    HashMap<String, List<String>> expectedResults;

    // even if the test has a fatal exception we still want to remove the
    // data files that were generated, unless the field KEEP_INPUT_FILES is
    // set to true above.
    try {
      // create the list of all filenames
      for (i= 1; i <= numFiles; i++)
        filenames.add(baseFilename + i);

      // call the helper method that actually writes the files
      expectedResults= FileCreator.createFriendFiles(numFiles, numUsers,
                                                     numFriends,
                                                     maxUserSuffix,
                                                     baseFilename);

      // create threads to read the files concurrently and add their data to
      // the social network
      socialNetwork.readUserData(filenames);

      // check the users
      assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                          expectedResults.keySet()));

      // check the friends
      for (String user : expectedResults.keySet())
        assertTrue(TestData.compareCollections(socialNetwork.getFriends(user),
                                            expectedResults.get(user)));
    } finally {
      // remove the files after the test is done.  (If you want to see the
      // files later, just change KEEP_INPUT_FILES to true at the top.)
      if (!KEEP_INPUT_FILES)
        for (String eachFile : filenames)
          new File(eachFile).delete();
    }
  }

  // Creates ten threads, which each read a list of user data, which contain
  // several friend relationships for different people.
  @Test public void testPublic13() {
    TikFace socialNetwork= new TikFace();
    int numFiles= 10, numUsers= 30, maxUserSuffix= 2000, numFriends= 50, i;
    String baseFilename= "userdata-public13-";
    List<String> filenames= new ArrayList<>();
    HashMap<String, List<String>> expectedResults;

    // even if the test has a fatal exception we still want to remove the
    // data files that were generated, unless the field KEEP_INPUT_FILES is
    // set to true above.
    try {
      // create the list of all filenames
      for (i= 1; i <= numFiles; i++)
        filenames.add(baseFilename + i);

      // call the helper method that actually writes the files
      expectedResults= FileCreator.createFriendFiles(numFiles, numUsers,
                                                     numFriends,
                                                     maxUserSuffix,
                                                     baseFilename);

      // create threads to read the files concurrently and add their data to
      // the social network
      socialNetwork.readUserData(filenames);

      // check the users
      assertTrue(TestData.compareCollections(socialNetwork.getAllUsers(),
                                          expectedResults.keySet()));

      // check the friends
      for (String user : expectedResults.keySet())
        assertTrue(TestData.compareCollections(socialNetwork.getFriends(user),
                                            expectedResults.get(user)));
    } finally {
      // remove the files after the test is done.  (If you want to see the
      // files later, just change KEEP_INPUT_FILES to true at the top.)
      if (!KEEP_INPUT_FILES)
        for (String eachFile : filenames)
          new File(eachFile).delete();
    }
  }

}
