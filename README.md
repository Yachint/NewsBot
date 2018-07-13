# NewsBot

A News app that uses API provided by [Newsapi.org](https://newsapi.org/) to fetch latest news articles based on Categories like World, Country, Sports, Buisness etc.

## Getting Started

Get familiar with the API used by testing various queries with your country/language and copy the URL's somewhere so that its easier for you later on. The libraries used for the Buttons and to grab articles from web pages are mentioned in the 'Built with' section below. Other than that, everything required is already available in android studio. See the build.gradle file for further queries.

### Prerequisites

In your build.gradle file add these additional libraries. Links for them are given below.

```
    implementation 'com.github.clans:fab:1.6.4'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'me.angrybyte.goose:goose:1.8.0'
    implementation 'com.github.jd-alexander:LikeButton:0.2.3'
```

## Built With

* [Clans/FloatingActionButton](https://github.com/Clans/FloatingActionButton) - The Floating Button Used
* [OK Http](http://square.github.io/okhttp/) - Used for parsing the http
* [Gson](https://github.com/google/gson) - Used to convert to JSON models
* [Picasso](http://square.github.io/picasso/) - Used to for rendering/loading images 
* [Goose](https://jitpack.io/p/milosmns/goose) - For Extracting Articles from Web pages 
* [jd-alexander/LikeButton](https://github.com/jd-alexander/LikeButton) - For Custom Animated Buttons

## Versioning

I used [Github](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/Yachint/NewsBot/tags). 

## Authors

* **Yachint Yadav** - *Complete work* - [Yachint](https://github.com/Yachint)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* [Arnav Sir](https://github.com/championswimmer)


