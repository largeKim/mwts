# mindmaps

mindmaps is a prototype of an HTML5 based mind mapping application. It lets you create neat looking mind maps in the browser. 

## Try it out

* The latest stable build is hosted [here](https://lgg.github.io/mindmaps/).
* Also check out [Desktop version](https://github.com/lgg/mindmaps-desktop)

## HTML5 goodness

- Written entirely in JavaScript
- 100% offline capable thanks to ApplicationCache
- Stores mind maps in LocalStorage
- FileReader API reads stored mind maps from the hard drive
- Canvas API draws the mind map

## Host yourself

All you need is a web server for static files. Copy all files from /bin into your web directory and 
launch the app with index.html.
Make sure your web server serves .appcache files with the mime type `text/cache-manifest` for the application to
be accessible offline.

In Apache add the following line to your .htaccess:

```
AddType text/cache-manifest .appcache
```

In nginx add this to conf/mime.types:

```
text/cache-manifest appcache; 
```

Alternatively, you can launch a local debug server with `npm start` which starts a server on localhost:8080.

### Installation steps

#### Install from built branch

* `git clone -b gh-pages --single-branch https://github.com/lgg/mindmaps`
* now, you have latest version in `YOURPATH/mindmaps` folder

#### Install from sources

* `git clone https://github.com/lgg/mindmaps && cd mindmaps`
* `npm run build`
* now, you have latest version in `YOURPATH/mindmaps/bin/` folder

Also you can host it on your Github Pages

* Fork
* `git clone https://github.com/YOURNICKNAME/mindmaps && cd mindmaps`
* `npm run deploy`

### Development notes

* Although the application runs fine if you launch `/src/index.html`, be aware that this is just the DEBUG mode for development. 
In debug mode quite a lot of output is sent to the console, 
ApplicationCache is deactivated and all script files are served individually and uncompressed.
* List of used JS libraries you can find [here](./js-libs.md). 

## License

* mindmaps is licensed under AGPL V3, see [LICENSE](./LICENSE) for more information.
* Author of mindmaps is [David](https://github.com/drichard)
* Some fixes and updates [lgg](https://github.com/lgg)
