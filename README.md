# Pescador MVP

Pescador MVP is an MVP framework for GWT that builds on the standard MVP-related
classes provided by GWT.

It integrates several Java and GWT best practices. Features include: 

* A modular global application architecture.
* Dependency injection (using GIN/Guice).
* Command pattern for server calls (using gwt-dispatch) and caching of responses.
* Testing and building with a single command (via Maven).
* While maintaining compatibility with standard GWT classes for MVP, it provides:
    * A generics-based mechanism for associating places, activities
    and views.
    * Facilities for storing key-value data in places.
    * The ability to associate activities and views with other kinds of state
    indicators besides places. 

Of course, it also supports all the other goodies that come with GWT, such as:

* Declarative, modularized HTML and CSS that's easy to read and easy 
for designers to work with (via UiBinder).
* Folding out of messages for easy internationalization.
* A global event bus.
* Client bundles for access to resources (like images), making for less traffic
to the server.
* Logging.
* Vetting of generated HTML and URIs for unsafe strings.
* Etc.


# How to use

Add maven dependency
Alternately, include in build path


# Example application

There's an example app you can walk through
Git to download just example app
More doc in that dir
Maven for building and running


# Building, running and hacking the source


# How to compile, run and debug

## Maven

The .war file, including dependencies, can be built by running the
following command from the parent project directory (weather/):

$ mvn clean install

To start up the development mode server, go to the weather/weatherclient
directory and run:

$ mvn gwt:run

Create javadoc and test reports for the example/jsonp project, go to that
directory and run:

$ mvn site:site

The generated site will be at example/jsonp/target/site/project-info.html.

## Eclipse

The Google Eclipse and Maven plugins are required. To add as an Eclipse
project, first run this command from the top-level directory:

$ mvn eclipse:clean

Then go to File > Import > Import Existing Maven Projects.

Once the projects have been imported into the Eclipse workspace, there
are a few things that have to be set up manually:

* Include additional directories on the build path in project
configuration.
    * In pescadormvp and examples/jsonp projects, src/main/resources
    and src/test/resources should be included. 
    * In examples/jsonp, the directory target/generated-sources/gwt should
    also be included.
* It seems that messages (for internationalization) are not generated
automatically by Eclipse. Generate them by building with Maven directly.
* You can run and debug examples/jsonp from Eclipse as a Web Application.
* Maven and Eclipse don't always coordinate perfectly. Direct Maven
builds, doing Project > Clean from Eclipse, and right-clicking on the
project > Maven > Update Project... usually help.
* If while debugging some sources are not found, try refreshing projects
from Eclipse's Package Explorer.
