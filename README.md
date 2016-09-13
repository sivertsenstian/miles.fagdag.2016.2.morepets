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

### Create Docker image for REST API
'npm install' will run as part of the Docker image build.

```
docker build -t api-server . --force-rm
docker run -d -p 3000:3000 --name api-server api-server
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
