# IBM Code Engine Example

This is a simple example of using IBM's [Code Engine](https://www.ibm.com/products/code-engine) to show how to create an application and functions to showcase how easy it is to get started in the serverless world.  The example uses Node and Python for the functions and PHP for the application.  

This is a portal (application) that calls 4 functions to display the current weather in a locale, TV highlights for Italy, Serie A football standings, and a quote in English.

It is important to note that currently functions must reside in the IBM Container Registry while applications and jobs can reside outside the container registry.

I have other repositories that also showcase Code Engine doing other tasks.

---
## Getting started

1. An [IBM Cloud account](https://cloud.ibm.com/login) is required.  Signup is free, and the example can run for free too using the Code Engine free tier.
2. The [IBM Cloud CLI](https://www.ibm.com/products/cli) needs to be downloaded and installed on your system.
3. The Code Engine and Countainer registry plugins for the CLI need to be installed.
```
ibmcloud plugin install code-engine
ibmcloud plugin install container-registry
```
---
## Creating the necessary resources
1. Logon to the IBM Cloud
```
ibmcloud login -u <username>
```
2. List resource groups
```
ibmcloud resource groups
```
3. Choose one of the resource groups fromt he last command
```
ibmcloud target -g <resource group>
```
4. List available regions
```
ibmcloud regions
```
5. Select a region to host the Code Engine project and resources
```
ibmcloud target -r <region name>
```
---
## Code Engine Time
1.  Create a Code Engine project
```
ibmcloud ce project create --name Portal
```
2. Select the project to use
```
ibmcloud ce project select --name Portal
```
3. Add a GitHub 
