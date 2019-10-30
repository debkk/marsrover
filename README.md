# marsrover exercise
code exercise for https://github.com/hawkescom/marsrover

## Summary
Use the [NASA API] to build a project in GitHub that calls the Mars Rover API and selects a picture on a given day. We want your application to download and store each image locally.

## Acceptance Criteria
  * Use list of dates below to pull the images were captured on that day by reading in a text ﬁle:
    * 02/27/17
    * June 2, 2018
    * Jul-13-2016
    * April 31, 2018
  * Language needs to be Java.
  * We should be able to run and build (if applicable) locally after you submit it
  * Include relevant documentation (.MD, etc.) in the repo
  * Bonus - Unit Tests, Static Analysis, Performance tests or any other things you feel are important for Deﬁnition of Done
  * Double Bonus - Have the app display the image in a web browser
  * Triple Bonus – Have it run in a Docker or K8s (Preferable)

## Vision statement/story
As a Mars enthusiest I want to be able to programatically download pictures from the Mars Rover for a given earth date using the NASA provided API and be able to display them on my webpage. I should be able to cache these pictures locally so that they can be viewed without the NASA api being available. I should be able to pre-load a list of pictures from a seed list file of dates of several date formats

## Solution Design and Details
In keeping with the design principle of separatation of duties, solution consists of two projects, one **marsrover-server** for the server side handling of photos, and the other **marsrover-client** for the display of photos served via an API.

### marsrover-server
Purpose of this project is to serve and access Mars Rover photos. It is a simple Java 8 Spring Boot application using quality of life annotations from lombok. 
See project readme for details

### marsover-client
Purpose of this project is to utilize the server-side api to display photos. It is a simple React with redux single-page app. For simplicity and speed I'm using [create-react-app] to bootstrap.
See project readme for details.

[create-react-app]: https://github.com/facebook/create-react-app
[NASA API]: https://api.nasa.gov/
