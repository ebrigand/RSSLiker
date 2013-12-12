RSSLiker
=========================================================================================================================================================
Exercise:

We’d like you to create a one-page web site that allows users to view a list of stories from the BBC news site, and to vote on their favourite stories.
You can use the RSS feed for the BBC site from this URL: http://newsrss.bbc.co.uk/rss/newsonline_uk_edition/front_page/rss.xml

Home Page
The story list should sit on the home page. Give your home page a title and an introductory paragraph.

Story List
The list component should consume the RSS feed, display the stories and allow the site user to select which stories they like. Give the user a Vote button to register their votes.

Storing Votes
Create a webservice in your site which will be called when the user clicks the Vote button. 
Store this data as an XML file on your site.

=========================================================================================================================================================
Project Details:

The RSS url of the BBC is defined in the config.xml file.

Sometimes the server having trouble accessing the RSS url (if for example the BBC server hosting the url doesn't reply),
 so no feeds are display in the home page, try it again later...

How it works:
The web app load the RSS url after the start of the application. Spring integration (look the config.xml) helps to read and parse the RSS url, 
each element is parsed and saved into SyndEntry object (ROME API), and a check and retrieve happens if some new elements of the RSS url are published. 
The time between each checking and retrieving is defined with the "fixed-rate" property

An interceptor is defined, it helps to store each syndEntry element read of the RSS url in a static list of syndEntry element. 

The account representation is just done with a String attribute passed to the home url (accountName)

A like is defined by an account and a syndEntry

A total count of like is defined by all the likes (of all account) for a same syndEntry
A Long Polling technique, with DeferredResult (with Spring 3.2) is used for the total count of like.
So if a second user like a story, the first one will have the new value of the like count without refresh the page.

To access at the home page: (ebrigand is an example for an accountName)
http://localhost:8080/rss/home?accountName=ebrigand

The like action and like count are saved in a xml file (the file name is defined in a properties file)

Technologies used:
- Spring MVC with RESTful JSON based services
- Spring integration and Spring integration feed
- Spring OXM
- JQuery front-end (with JSON AJAX requests)
- Long Polling (DeferredResult)
- API ROME
