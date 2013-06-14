# Pescador MVP

Pescador MVP is a modular MVP framework for GWT that builds on the standard MVP-related
classes provided by GWT.

It integrates several Java and GWT best practices. Features include: 

- A modular global application architecture.
- Dependency injection (using GIN/Guice).
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

Instructions on how to use Pescador MVP are in the [Javadoc](http://andrewgreen.github.io/pescadormvp/pescadormvp/apidocs/index.html) itself.

You may also wish to walk through the example application, in the [examples/jsonp](/AndrewGreen/pescadormvp/tree/master/examples/jsonp) folder. [Here](http://andrewgreen.github.io/pescadormvp/examples/jsonp/app.html) is how the example app looks compiled and running in your browser.

Pescador MVP was developed by Andrew Green at the [Instituto Mora](http://www.mora.edu.mx) as part of the [Pescador](http://lais.mora.edu.mx/huellasdeluz/#contenido;id=MXIMHDL-AcercaDelSitio-Pescador) system.


# How to use [TODO]

Add maven dependency
Alternately, include in build path


# Example application

There's an example app you can walk through. See example/jsonp/README.md
for more information.


# Building, running and hacking the source

# How to compile, run and debug

## Maven

The full project, including the example application, can be built by running the
following command from the parent project directory:

$ mvn clean install

To start up the development mode server for the example app, go to the
examples/jsonp directory and run:

$ mvn gwt:run

To create javadoc and test reports for the example/jsonp project, go to that
directory and run [TODO: fix, update this]:

$ mvn gwt:test site:site

The generated site will be at example/jsonp/target/site/project-info.html.

Alternately, to just perform tests and generate the test report, again from
the example/jsonp directory, run:

$ mvn -DtestTimeOut=120 gwt:test surefire-report:report 

Notes:
- gwt:test doesn't work immediately after a mvn clean has been run. mvn install
  must have been called first.
- surefire-report:report doesn't generate the CSS needed for a decent report;
  site:site has to be run first.
- The option -DtestTimeOut=120 allows for a longer timeout for the dedicated JVM
  used for tests requiring full GWT/browser context. 

GWT-based tests are configured to run in HtmlUnit. To run in a real browser,
change the gwt.test.mode property in example/jsonp/pom.xml.

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
