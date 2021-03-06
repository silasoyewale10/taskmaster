# AUTHOR: SILAS OYEWALE
## TITLE: ANDROID APPLICATION
### DESCRIPTION AND SUMMARY:
The main page should be built out to match the wireframe. In particular, 
it should have a heading at the top of the page, an image to mock the 
“my tasks” view, and buttons at the bottom of the page to allow going 
to the “add tasks” and “all tasks” page.

# Home Page
Contains settings, three hardcoded tasks, add task and details. 

## Add a Task
On the “Add a Task” page, allow users to type in details about a new task,
specifically a title and a body. When users click the “submit” button, 
show a “submitted!” label on the page.

All Tasks
The all tasks page should just be an image with a back button; it needs no functionality.

## SCREENSHOTS

# Home Page
<img src="/screenshots/lab-36-homepage.png"
width=150; margin-right= 10px;/>

# Notification Received When App Is Not in Use
<img src="/screenshots/lab-38-notification.png"
width=150; margin-right= 10px;/>


# Notification Drop Down
<img src="/screenshots/lab-38-homepage.png"
width=150; margin-right= 10px;/>

### DAILY CHANGE LOG:

#### Day 1
I added the homepage, the add task page and the all task page. The homepage has the
buttons to go to both the add task page and the all task page. The all task page is empty for now. 
#### Day 2
I added the settings page to change the username displayed on the homepage. I also added three tasks to be picked from. Each task leads you to a task page that shows the title of the chosen task. 
#### Day 3
I added the recycleview to be able to click on the list of tasks. When you click on each task, it dshould take you to a page displaying the task and lorem ipsum.
Also, I deleted the radio buttons from yesterday since I now have the tasks as a recyled view. I moved my tasks to left top corner to make more room for my recycle view.
### Day 4
I added database i.e. persistent storage and Room. Whe you click on the tasks, it takes you to a detail page that shows the title, body, and state of the clicked task.
### Day 5
I added recyclerview to the all task page and on the click of each task, it shows a toast of title, body, and state.
### Day 6
Added dynamo db. Able to store and retrieve data from the AWS dynamo database. 
### Day 7
I added login, logout, and sign up page.
### Day 8
I added s3. I can upload text to s3.
### Day 9
I added image to s3 bucket
### Day 10
I can send notification from pinpoint.
