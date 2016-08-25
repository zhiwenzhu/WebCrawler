## WebCrawler

A high performance distributed web page crawler. 

### crawler-bootstrap
The main entry of the crawler. Program could be packed as a war which could running in a tomcat or jetty.

### crawler-fetcher
Which takes Urls as input, downloading pages and pass the URL Page pair to crawler-file-parser. Instead of short response time, this module focus on high throughput more.
 
### crawler-file-parser
The file parser takes files as input, parsing them in different ways. And then call crawler file store to save the pages.

#### crawler-url-store
The URL store hold all the URLs downloaded by fetcher and URLs to be downloaded. The store can tell you whether a url has been accessed or not.