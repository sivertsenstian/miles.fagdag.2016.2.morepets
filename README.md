# morepets

A [re-frame](https://github.com/Day8/re-frame) application designed to manage your pets!

## Development Mode

### Start REST API
Install npm packages (first time only).  
navigate to "/api-server" in cmd/terminal
.  
```
npm install
```

Start API-SERVER at http://localhost:3000
```
npm start
```

### Compile css:

Compile css file once.

```
lein less once
```

Automatically recompile css file on change.

```
lein less auto
```

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```

### Run in Docker
Compile production build. Docker compose will build and start Docker images for the REST API and the web application:

```
docker-compose up
```


