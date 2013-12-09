RSSLiker

The web app load the rss url after the start of the application (look the beans.xml)
Spring integration helps to read read and parse the rss url (each element is parsed into SyndEntry object) and look if new elements of the rss url are published, 
the time between each look is define with the fixed-rate property

An interceptor is define, it helps to store each syndEntry element read of the rss url in a unique list of syndEntry element. 

The account representation is just done with a String attribute passed to the home url (accountName)

A like is define by an account and a syndEntry

A total count of like is define by all the likes (of all account) for a same syndEntry

To access at the home page: (ebrigand is an example for an accountName)
http://localhost:8080/rss/home?accountName=ebrigand