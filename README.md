# LittlePay Customers Travel Data

## Overview
Customers travelling on public transport, when choose contact-less ticketing, can simply Tap On the bus while boarding
with their contact-less card or device and then simply Tap Off again while leaving.

This project 
- consumes a CSV file with multiple TAP-ON and TAP-OFF details of various customers along with specifics of when,where and how long
- reads, processes and performs required calculations
- produces a CSV file with 1 travel record per customer containing details such as to, fro, amount charged, status of trip etc

## Project details and Component Diagrams
- Confluence : <link to detail/design page>
- JIRA : <link to EPIC/STORY>

## API Details
The work flow of customer-trip has 1 API

#### 1. Generate Customers Travel Data

- POST
- /littlepay/v1/customer-trip/
- Consumes CSV file as MultipartFile
- Produces CSV file with customers travel data
- Produces Error response(code and message) in case of exception

The api consumes CSV file with multiple tap details of various customers and performs below steps :
- Generates transaction-id for logging
- Checks that file-size wouldn't exceed the specified size of 10MB(configurable, can be changed in application.yml)
- Reads CSV records and maps to model 'CustomerTapInfo'
- Processes the data and decides on status based on business logic
- Uses DROOLS as rule-engine to calculate amount-to-be-charged to customer for their travel
- Generates a model 'CustomerTrip' which aligns to required output CSV format
- Produces CSV data containing travel detail per customer and their charge amount


#### GIT Repo
- Repo : https://github.com/mkoganti/customer-trip-v1.git

#### Maven Cmd
- mvn clean install
- mvn clean install -DskipTests

### Pointers to check before committing
- Unit-tests are written
- Test coverage minimum of 80%
- README is updated
- Meaningful logging has been added
- Design docs updated

