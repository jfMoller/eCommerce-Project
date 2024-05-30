# **eCommerce-Project**

## **Introduction**
I wanted to build a fullstack website in order to improve my understanding of e-commerce systems; the end result could be useful as a starting point for other e-commerce applications.

## **Description**
The project includes an SPA with Vue 3 (Composition API) & TypeScript, using Pinia for global storage and Tailwind CSS for styling and responsive design; the backend was built with Java Spring Boot & Neo4J.

The app lets you register and modify your account, as well as place orders of available products, it also includes an integrated admin interface where products and orders can be managed. 

Some advanced features were developed, such as calculations of the available product stock for ongoing orders, a full account lifecycle with automatic re-authentication when making edits and a param based search bar where filters and keywords can be combined to find products, as well as others.

By working on this project I've gained an increased understanding of the complexity and challenges involved in desiging an e-commerce system.

## **UI examples**
![Home page](https://github.com/jfMoller/eCommerce-Project/blob/main/images/home_page.png?raw=true)
![Product search](https://github.com/jfMoller/eCommerce-Project/blob/main/images/product_search.png?raw=true)
![Ongoing order](https://github.com/jfMoller/eCommerce-Project/blob/main/images/ongoing_order.png?raw=true)
![Admin UI](https://github.com/jfMoller/eCommerce-Project/blob/main/images/admin_ui.png?raw=true)
![Admin orders](https://github.com/jfMoller/eCommerce-Project/blob/main/images/admin_orders.png?raw=true)

## **How to run locally**

1.	Clone the project
```
git clone https://github.com/jfMoller/MariaDB-Editor.git
```

2.	Install local dependencies in the project directory
```
cd client
    npm i
```
3.	Setup a free AuraDB (Neo4j) database

    3.1. Go to [Neo4j](https://neo4j.com/cloud/platform/aura-graph-database/) and create a free instance.

    3.2 Paste your credentials in the application.yml folder
```
cd server/src/main/resources

spring:
    neo4j:
     uri: YOUR_NEO4J_URI
        authentication:
            username: neo4j
            password: YOUR_NEO4J_PASSWORD  
```
4.	Start the server
```
Navigate to server/src/main/java/me/code/springboot_neo4j

Run Application.java
```

5.	Run the client
```
cd client
    npm run dev
```