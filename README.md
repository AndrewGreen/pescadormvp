# Pescador MVP

Pescador MVP is a modular MVP framework for GWT that builds on the standard MVP-related
classes provided by GWT.

It integrates several Java and GWT best practices. Features include: 

- A modular global application architecture.
- Dependency injection (via GIN/Guice).
- Command pattern for server calls (using gwt-dispatch) and caching of responses,
  including for JSONP calls.
- Testing and building with a single command (via Maven).
- While maintaining compatibility with standard GWT classes for MVP, it provides:
    - A generics-based mechanism for associating places, activities, views and
      components
    - Facilities for storing key-value data in places.
    - The ability to associate activities and views with other kinds of state
      indicators besides places. 

Of course, it also supports all the other goodies that come with GWT 
(declarative HTML and CSS via UiBinder, client bundles, logging, etc.). Plus, it
includes resources for creating internal links (to places in the same GWT app)
that are also native HTML links, and some miscellaneous utilities.

For information on how to use Pescador MVP, see the [walk-through of the example
application](http://andrewgreen.github.io/pescadormvp/site/apidocs/index.html?mx/org/pescadormvp/examples/jsonp/client/package-summary.html) and the [overview](http://andrewgreen.github.io/pescadormvp/site/apidocs/index.html?mx/org/pescadormvp/core/package-summary.html). Code for the example app is in the [examples/jsonp](examples/jsonp) folder. [Here](http://andrewgreen.github.io/pescadormvp/site/examples/jsonp/live/index.html) is how the example app looks compiled and running in your browser.

Pescador MVP was developed by Andrew Green at the [Audiovisual Laboratory for Social Research](http://lais.mora.edu.mx/huellasdeluz/#contenido;id=MXIMHDL-AcercaDelSitio-LAIS) of the  [Instituto Mora](http://www.mora.edu.mx). It was originally developed as part of the [Pescador](http://lais.mora.edu.mx/huellasdeluz/#contenido;id=MXIMHDL-AcercaDelSitio-Pescador) system.


# How to use [TODO]

Add maven dependency
Alternately, include in build path


# Building, running and hacking the source

# How to compile, run and debug

## Maven

The full project, including the example application, can be built by running the
following command from the parent project directory:

$ mvn clean install

GWT-based tests are configured to run in HtmlUnit. To run in a real browser,
change the mode parameter in the gwt-maven-plugin configuration section of
in example/jsonp/pom.xml.

To generate a Maven site at target/staging/index.html, complete with javadocs
and surefire test reports, run:

$ mvn site site:stage 

To start up the development mode server for the example app, go to the
examples/jsonp directory and run:

$ mvn gwt:run


## Eclipse

The Google Eclipse and Maven plugins are required. To add as an Eclipse
project, first run this command from the top-level directory:

$ mvn eclipse:clean

Then go to File > Import > Import Existing Maven Projects.

Once the projects have been imported into the Eclipse workspace, there
are a few things that have to be set up manually:

- Include additional directories on the build path in project
  configuration.
    - In pescadormvp and examples/jsonp projects, src/main/resources
    and src/test/resources should be included. 
    - In examples/jsonp, the directory target/generated-sources/gwt should
    also be included.
- Messages (for internationalization) are not generated automatically by Eclipse.
  Generate them by building with Maven directly.
- You can run and debug examples/jsonp from Eclipse as a Web Application.
- Maven and Eclipse don't always coordinate perfectly. Direct Maven
  builds, doing Project > Clean from Eclipse, and right-clicking on the
  projects and choosing Refresh or Maven > Update Project... usually help.
- If while debugging some sources are not found, try refreshing projects
  from Eclipse's Package Explorer.
