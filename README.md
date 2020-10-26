
# NOTHS - Checkout Task

#### Install and Run
Please ensure Maven and Java 11 are installed on your machine. Simply run:

    mvn clean install && java -jar target/noths*.jar

#### Initial Thoughts
The first goal was to identify the domain objects pertinent to the design of this system. In the context of an enterprise, we would ideally have a glossary established - alas, for the purposes of the technical test, I opted for:

- Product (rather than *Item*)
- Basket
- Promotion
- Checkout

#### Design Pattern
The technical test stresses the need for a flexible solution, as promotions are subject to change. Unfortunately, I quickly realised that there was a temporal dimension to our system of discounts - an implicit order in which the discounts must be executed against a given basket. This led me to implement the **Chain of Responsibility** design pattern, whereby the application of one promotion precedes the next application, until the chain is exhausted. Promotions would be chained in the order demanded by the business.

There were two common 'promotion' abstractions provided by the technical test:

 1. Buy X products to get them at a cheaper price;
 2. Spend X to get a percentage off your entire basket.

These naturally formed the basis for the concrete implementations. The values which were prescribed by the technical test were considered *configuration*, and thus I opted to inject them the respective objects as constructor arguments, rather than have them hardcoded.

#### Approach
After settling on the Chain of Responsibility design pattern, I took a TDD approach to the design of the system. I like to begin from the outer layers, and work inwards - so I started with the **Checkout** class, and with each necessary dependency, went one layer deeper (though, in this example application, it was largely restricted to *orchestration* and *business logic* layers!) 

#### Notable Omissions
Resisting the temptation of over-engineering a solution, some notable omissions include:

- Any industry-standard framework (e.g. Spring Boot) - most gravitate towards web-based applications, and didn't want to make assumptions in this regard. In a real checkout system, there would be clearly defined *inputs* and *outputs* (e.g. HTTP endpoints), though I opted to forego these because of time/over-engineering. 
- Any IoC container, such as Spring. In a production-ready application, **Product**, **Checkout** and **Promotional** instances would be *singleton scoped*, whilst inherently stateful objects, such as a **Basket** instance, would be instantiated and scoped at the session level, as these naturally mutate over the lifecycle of a user session. 
- Any sort of containerisation (e.g. Docker). You'll need Java 11 on your machine to run this.

**Other Tidbits**

- In a real application, we would almost certainly need some form of session affinity in place, to ensure users continue to hit the same instance.
- Some object instances are deliberately immutable (such as **Product** instances) - I can't imagine a need for these to change at runtime. 
- Opted to create a **Basket** class, to house the list of **Product**s, and track the running total. From a layered architectural perspective, I saw the **Checkout** class as a service, managing and orchestrating interactions from an input (which happened to be the inputs hard-coded in the ApplicationLauncher - this is just for the demo!). Wanted to separate these concerns.
- Rather than perform *null checks* in every **Promotional** instance, chose to instead implement the *Null Object Pattern* (the **EmptyPromotion** instance), and have this instance called as the last promotion in the chain of responsibility. This means, for every promotion we apply, we don't have to check if the next promotion in the chain is *null* or not.
- Minor one - every time we add a product to the basket, we add to the running total, to prevent any implicit temporal method coupling for the client (e.g. having to call 'calculateTotal' before 'applyDiscounts').
- Ideally, would have injected in a **Basket** instance into the **Checkout** service (of course, didn't want to break the rules!). As a result, means I've taken a bit of a hybrid mockist-classicalist approach to testing at times. One isn't right or wrong - consistency is key!

