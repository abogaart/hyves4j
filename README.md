hyves4j
=======

Hyves4j is a Java API that makes it easy to incorporate content from www.hyves.nl (Holland's biggest social networking website) into your own application by wrapping the complexity and logic of OAuth based http requests to the Hyves API, and returning strong typed implementations of Hyves domain models (like users, photos, etc.) to the end user.

Hyves4j depends heavily on the [http://oauth.googlecode.com/svn/code/java/ OAuth Java implementation] for all the OAuth stuff. Also it's project structure and examples helped me a lot in setting up the Hyves4j project.

#summary How to use Hyves4j

*Hyves4j is currently considered alpha software so use at own risk*

=== Prerequisites ===

 * Java5 or higher
 * Maven2

=== Building H4j ===

H4j heavily depends on the OAuth java project (http://oauth.googlecode.com/svn/code/java/) and has a dependency on oauth-core. Unfortunately, Oauth java doesn't deploy artifacts into a mvn repository nor do they tag/release. This makes maintenance kind of hard.

For now (14/09/2009) H4j is working with Oauth java version 20090531, which will be copied into H4j's source tree. 

Until this is done Oauth java will have to be checked out manually and deployed into a local maven repository. On the command line type:

  # svn co http://oauth.googlecode.com/svn/code/java oauth-java
  # cd oauth-java
  # mvn install
 
Now you can build H4j with `mvn install`. The tests will fail if you haven't configured them correctly. This is done by adding your own values to core/src/test/conf/setup.properties and core/src/test/conf/consumer.properties. 
You can also skip the tests with `mvn install -DskipTests`

=== Example ===

First off you should take a look at the example. My HttpServlet/JSP skills are not that good so please don't be scared off by my code. 
Make sure you add your consumer key and secret to the /example/webapp/src/main/conf/consumer.properties file before you start. Then, open up a console, cd into /example/webapp and type 'mvn jetty:run'. If all goes well you should be able to view the example at http://localhost:8080/Example.

`You'll probably get some debug messages in your console. This is because Hyves4j uses [http://logback.qos.ch/ Logback] as a logger and Logback defaults to the debug loglevel.`

The example webapp has two pages. The first page mimics the Hyves-api console; you can specify a method, an accessToken, a tokenSecret, responseFields and 5 key:value parameters and then do a request on the Hyves-api. The response is drawn beneath the console form.
The second page helps you acquire a new accessToken. You can specify the methods that should be authorised and the expiration type for this accessToken. 
Upon submit you'll be redirected to Hyves.nl to make the authorisation. When you do, hyves.nl will redirected you back to http://localhost:8080/Example/request and here you're new accessToken and tokenSecret will be shown. 
You can then open then console page of the example and use the new accessToken + tokenSecret to do some authorised requests on the hyves-api. 


=== Usage ===
The main approach to working with Hyves4j is:

  # create and configure a new H4jClient
  # with it create a new instance of Hyves4j
  # ask the new Hyves4j instance for an api interface, like Users or Media
  # use the public methods of the interfaces to retrieve data from the Hyves-api in form of the domain classes (com.spikylee.hyves4j.model)

Hyves4j depends on the [http://oauth.googlecode.com/svn/code/java/ OAuth java] project for most of the OAuth related code. OAuth configures the Consumer with a property file, by default Hyves4j will look at "/consumer.properties" for this file, you can of course provide your own location.

The most simple form of creating a new Hyves4j instance is
{{{
Hyves4j h4j = new Hyves4j();
}}}
This will create a new H4jClientConfig by calling Hyves4j.createAnonymousClient() that looks in "/consumer.properties" for the Consumer configuration data and uses "hyves" as the consumerName.
With this configuration a new H4jClient is instantiated which in turn is used to create a new Hyves4j object. 
You can than start getting some data out of the Hyves-api.
{{{
H4jUsers users = h4j.getUsers();
User user = users.getByUsername("spikylee");
System.out.println("Retrieved user " + user.getFirstName() + " " + user.getLastName());
}}}

The hyves-api request in the above example is done as an 'anonymous client', which simply means their is no accessToken+tokenSecret specified and thus Hyves will return less information.

The steps for retrieving an 'authenticated client' are a bit more complicated. This is because their will be a redirect to hyves.nl during the process. It comes down to:

  # Create a new H4jClientConfig and specify which methods we should authenticate for.
  # With this config ask Hyves4j to create a new authenticated client
  # If no valid accessToken is found in the configuration a RedirectException will be thrown
  # Catch this exception, and put a reference to the H4jClientConfig in your session/cookie/thread-local
  # When you successfully return from the Hyves authorisation page get the H4jClientConfig from the session/cookie/thread-local
  # Read the accessToken from the request and put it into the H4jClientConfig
  # Ask Hyves4j again to create a new authenticated client and you should be good to go.

Below is a simplified example of a doGet page that will redirect the browser to hyves and after returning to the same page, extracts the accessToken from the request and finishes the createAuthenticatedClient proces.
{{{
    H4jClientConfig config = tLocal.get();
    if(config == null) {
        URL consumerPropertiesURL = getClass().getResource("/consumer.properties");
        config = new H4jClientConfig("hyves", consumerPropertiesURL);
        config.addMethod("users.get");
        config.setExpirationType("infinite");
        tLocal.set(config);
    }
    
    String oauthToken = request.getParameter("oauth_token");
    if(oauthToken != null)
        config.setAccessToken(oauthToken);
    
    try {
        H4jClient client = Hyves4j.createAuthenticatedClient(config);
        //if we made it here we should have a valid accessToken
        logger.debug("Got accessToken: " + client.getConfig().getAccessToken());
    catch(RedirectException re) {
        response.sendRedirect(re.getMessage());
    } catch (H4jException e) {
        logger.error(e.getErrorCode());
        logger.error(e.getErrorMessage());
    }

}}}