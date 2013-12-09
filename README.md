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

The url is define in the config.xml file

The web app load the rss url after the start of the application
Spring integration (look the config.xml) helps to read and parse the rss url (each element is parsed into SyndEntry object) and look if new elements of the rss url are published, 
the time between each look is define with the fixed-rate property

An interceptor is define, it helps to store each syndEntry element read of the rss url in a static list of syndEntry element. 

The account representation is just done with a String attribute passed to the home url (accountName)

A like is define by an account and a syndEntry

A total count of like is define by all the likes (of all account) for a same syndEntry

To access at the home page: (ebrigand is an example for an accountName)
http://localhost:8080/rss/home?accountName=ebrigand

Technologies used:
- Spring MVC with RESTful JSON based services
- Spring integration
- Spring OXM
- JQuery front-end (with JSON AJAX requests)