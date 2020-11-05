# java-ducks-pizza
Online ordering system for a fictional pizza restaurant

Database: This contains a number of classes that use Hibernate to interact with a PostGreSQL database.  I implemented some of the Daos using HQL, and some with Criterias in order practice with each.  Were this an enterprise-level application, it would be far better to pick one and stick with it.

PizzaBoot: Contains an MVC application generated using Spring Boot that is the web interface used by customers for entering orders.

PizzaRestService: REST service that allows PizzaBoot to interact with the database.

OrderingSystem and PizzaMVC are no longer in use and shall be deleted.
