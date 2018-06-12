
package finalprojectB;

import junit.framework.TestCase;
import java.util.*;
import java.util.Random;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!





public class UrlValidatorTest extends TestCase {


   public UrlValidatorTest(String testName) {
      super(testName);
   }



   public void testManualTest()
   {
      //You can use this function to implement your manual testing
      UrlValidator urlValidator = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
      assertTrue(urlValidator.isValid("http://www.google.com"));
      assertTrue(urlValidator.isValid("http://www.go.au"));
      assertFalse(urlValidator.isValid("ftp://www.google.com"));
      assertTrue(urlValidator.isValid("ftp://ftp.example.com"));
      assertTrue(urlValidator.isValid("https://www.google.com"));
      assertTrue(urlValidator.isValid("file:///foo.txt"));

   }

   //Test partitioning for files
   public void testYourFirstPartition()
   {
	 //You can use this function to implement your First Partition testing
   UrlValidator urlValidator = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
   assertTrue(urlValidator.isValid("file:///foo.txt"));
   assertTrue(urlValidator.isValid("file:///foo.txt"));
   assertTrue(urlValidator.isValid("file://etc/host/path"));
   assertFalse(urlValidator.isValid("http:///etc/path"));

   }

   //Test partitioning for different Paths and Authorities
   public void testYourSecondPartition(){
		 //You can use this function to implement your Second Partition testing
     UrlValidator urlValidator = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
     assertFalse(urlValidator.isValid("http://255.com:80"));
     assertFalse(urlValidator.isValid("http://255.com:65535/test1"));
     assertFalse(urlValidator.isValid("http://255.com:65535/t123?action=view"));
     assertFalse(urlValidator.isValid("http://255.255.255.255:80/test1/file?action=view"));

   }


   public void testIsValid()
   {
	   //You can use this function for programming based testing
     //String[] schemes = {"http","https"};
     UrlValidator urlValidator;
     long randomseed = System.currentTimeMillis();
     Random random = new Random(randomseed);

     int i = 0;
    while (i < 300){

      int r = random.nextInt(3);
      ArrayList<String> schemes = new ArrayList();
      for (int x = 0; x <= r; x++){
        schemes.add(goodSchemes.get(x));
      }
      String[] schemeArray = new String[schemes.size()];
      schemeArray = schemes.toArray(schemeArray);
      urlValidator = new UrlValidator(schemeArray);

      boolean valid = random.nextBoolean();
      String url;
      if (valid){
        String[] parts = buildValidUrl(schemes, valid, random);
        if (!schemes.contains(parts[0])){
          valid = false;
        }
        url = parts[0] + parts[1] + parts[2] + parts[3];
      }else{
        url = buildInvalidUrl(random);
      }
      boolean res = urlValidator.isValid(url);
      assertEquals("url: " + url + " should have been: " + valid, valid, res);
      i++;


    }


   }

  public String[] buildValidUrl(ArrayList<String> schemes, boolean valid, Random random){
    int r = random.nextInt(goodSchemes.size());
    String scheme = goodSchemes.get(r);

    r = random.nextInt(goodAuthority.size());
    String auth = goodAuthority.get(r);
    r = random.nextInt(goodPort.size());
    String port = goodPort.get(r);
    r = random.nextInt(goodPath.size());
    String path = goodPath.get(r);

    String[] parts = {scheme, auth, port, path};
    return parts;
  }

public String buildInvalidUrl(Random random){
  boolean valid = true;
  String scheme = "";
  String port = "";
  String auth = "";
  String path = "";
  String url = "";
  while(valid){
    scheme = generateUrlPart(random, goodSchemes, badSchemes);
    valid = badSchemes.contains(scheme);
    auth = generateUrlPart(random, goodAuthority, badAuthority);
    valid = badAuthority.contains(auth);
    port = generateUrlPart(random, goodPort, badPort);
    valid = badPort.contains(port);
    path = generateUrlPart(random, goodPath, badPath);
    valid = badPath.contains(path);
  }
  url = scheme + auth + port + path;
  return url;
}



public String generateUrlPart(Random random, ArrayList<String> good, ArrayList<String> bad){
  boolean isGood = random.nextBoolean();
  String part;
  int r = random.nextInt(good.size());
  if (isGood){
    part = good.get(r);
  }else{
    part = bad.get(r);
  }
  return part;
}


  public ArrayList<String> goodSchemes = new ArrayList<String>(Arrays.asList("http://",
                                "ftp://",
                                "h3t://",
                                ""));

  public ArrayList<String> badSchemes = new ArrayList<String>(Arrays.asList("3hc://",
                                "http:/",
                                "http//",
                                "//::/"));

  public ArrayList<String> goodAuthority = new ArrayList<String>(Arrays.asList("www.google.com",
                                  "go.au",
                                  "255.255.255.255",
                                  "112.com"
                                ));
  
  public ArrayList<String> badAuthority = new ArrayList<String>(Arrays.asList(".ww.google.com",
                                "go.aaaaa",
                                "252.555.955.256",
                                "11.2.com"
                              ));

  public ArrayList<String> goodPort = new ArrayList<String>(Arrays.asList(":80",
                              ":65535",
                              ":0",
                              ""
                            ));

  public ArrayList<String> badPort = new ArrayList<String>(Arrays.asList(":-1",
                            ":75630",
                            ":abcd",
                            ":!!!"
                          ));


  public ArrayList<String> goodPath = new ArrayList<String>(Arrays.asList("/test1",
                              "/$123",
                              "/test/test1",
                              ""));

  public ArrayList<String> badPath = new ArrayList<String>(Arrays.asList("test1",
                              "/#",
                              "/test/..",
                              "/#/../"));


  //found a failed url, using it to debug and find the error!
  // public void main(){
  //   ArrayList<String> schemes = new ArrayList();
  //   for (int x = 0; x <= 3; x++){
  //     schemes.add(goodSchemes.get(x));
  //   }
  //   String[] schemeArray = new String[schemes.size()];
  //   schemeArray = schemes.toArray(schemeArray);
  //   urlValidator = new UrlValidator(schemeArray);
  //   url = "http://255.255.255.255:80";
  //   boolean val = urValidator.isValid(url);

  // }

}




