![thuwio789e](https://user-images.githubusercontent.com/33996847/42687450-78e46ce6-86b6-11e8-806c-8ee2502688b1.jpg)

# Campus Recruitment
This was final year project perfomed by 8th sem students of KSSEM in the academic year 2017-2018 which comes under VTU. 

## About Project

→ This project is about letting the candidates know about the companies visiting their colleges and drives they can attend.<br />
→ We collect the information of students like thier marks and skills and calculate their chances of getting placed in a company using one   method.<br />
→ Students can also view thier seniors or batch mates interview experiences, drives information that they should apply for.<br />
→ Placement officer can keep track of student details in a circular pie chart to keep track of placed and not placed students.<br />

## Platform used

→ Google Firebase was used in this app, as we wanted reliable, secure, efficient application.<br />
→ We have also focued on Authentication, Database, and Cloud Messaging for push notifications.<br /> <br />
→ Authentication, only a verified candidate can use our app. After the candidate has created an account, we will immediately send a verification id to entered email id, from there they need to verify thier account.<br />
First we need to add a dependency in our `gradle:app` file : <br /> <br />
`implementation 'com.google.firebase:firebase-auth:16.0.2'` <br />

→ Database, was used to store all the details of candidate that was entered in our app. So we made use of Firestore.<br />
Second add the Firestore database dependency in our `gradle:app` file : <br /> <br />
`compile 'com.google.firebase:firebase-firestore:17.0.2'` <br />


![home-top-illo-layer-apps_1x](https://user-images.githubusercontent.com/33996847/42687515-ab295810-86b6-11e8-9d69-b9fc61a8046c.png)
