## Questify
**Questify** is a **Q&A platform** designed for users to ask questions, share knowledge, and engage in meaningful discussions. Built with **Spring Boot**, **React**, and **PostgreSQL**, it leverages a **microservice architecture** to ensure a seamless and efficient experience for **knowledge exchange**.

## Demo video
https://github.com/user-attachments/assets/7f4054fe-18da-4004-ac02-c8cd40b5c489

## Features
**Authentication and security**
- Users can create an account by providing **username**, **password**, **email**, and **phone number**.
- Upon **registration** or **login**, they receive a secure **token**, which is used throughout their browsing session to validate interactions with the app’s **microservice**.

**Viewing and interacting with questions**
- Users can browse **recent questions**, added by other users.
- Each question includes, **title**, **text**, multiple **tags**, and **images** (stored as **URLs**).
- Users can **upvote** or **downvote** the question, affecting both:
  - The question's **score**
  - The author's **score**

**Posting and managing content**
- Users can **post questions** with **title**, **description**, **tags**, and **images**.
- Questions have three possible **statuses**:
  - **Sent** (newly posted)
  - **In progress** (currently receiving comments)
  - **Solved** (marked by the author, no one can add or edit anything)
- Once a question is marked as **solved**, it becomes **locked** and it can be only deleted from that point on.

**Search and filtering**
- Users can filter the question by:
  - **Title** (this also includes subtitles)
  - **Username** (the author of the post)
  - **Tags**
  - Their own **questions**

**Admin features**
- These are special users with privileges:
  - **Ban / Unban users**
  - Banned users can no longer use the app and they will receive an **SMS notification** that they've been banned.
  - Ability to **edit** or **delete** any question.


## Visual overview of the Features

### Post Questions & Answers

- **Add a question** to the platform by providing a title, description, and tags.
  
  ![Post Question](https://github.com/user-attachments/assets/bae8e942-db47-4739-b556-c935099332ba)

- **View the question** on the home screen after it has been added.

  ![Home Screen](https://github.com/user-attachments/assets/e2359a0d-baec-41c5-8b11-591afae38d48)

- **Add an answer (comment)** to the posted question to engage in the conversation.

  ![Add Answer](https://github.com/user-attachments/assets/4d6f7355-2591-4b01-b9e5-cb64d2b16490)
  ![Answer Example](https://github.com/user-attachments/assets/40f11ebe-e1e3-4976-a965-701e02fc82ee)
  ![Another Answer](https://github.com/user-attachments/assets/b7cf7c00-e457-4953-be94-dbbadd1cabff)

---

### Upvote & Downvote System

- **Vote on posts** to indicate their usefulness or relevance. Upvoting or downvoting affects both the question's and author's scores.

  ![Upvote Downvote](https://github.com/user-attachments/assets/161a15cb-5f3b-45ae-9d4f-8a8e4b610823)

---

### Search by Post Title

- **Search for specific questions** using keywords from the title to quickly find relevant posts.

  ![Search Post](https://github.com/user-attachments/assets/4f779ea6-e544-432e-8ed4-2d4578b37636)

---

### Filter by Tags, User, or "My Posts"

- **Filter questions** based on specific tags, authors, or limit results to **your own posts**.

  ![Filter Options](https://github.com/user-attachments/assets/ec620802-0d46-4198-b23d-323c63582557)

---

### User Score System

- **User scores** are tracked based on interactions with the platform:
  - **Start with 0 points**.
  - Gain points for:
    - Upvotes on your question (+2.5 points per vote).
    - Upvotes on your answer (+5 points per vote).
  - Lose points for:
    - Downvotes on your question (-1.5 points per vote).
    - Downvotes on your answer (-2.5 points per vote).
    - Downvoting another user's answer (-1.5 points per vote).

  ![User Score](https://github.com/user-attachments/assets/022a7692-988d-45fc-a2c3-e4c8207574b2)

---

### Admin Mode

- **Admin features** allow privileged users to:
  - **Ban** or **Unban** users.
  - **Delete any question or answer**, ensuring content moderation.

  ![Admin Dashboard](https://github.com/user-attachments/assets/3c92c4d0-4091-43b1-a3f2-619e27faa950)
  
  ![Admin Control](https://github.com/user-attachments/assets/697b6466-2ddf-4149-a00c-abc2e75d7dd9)
  
  ![Admin Action](https://github.com/user-attachments/assets/ce897cd7-eea9-42b0-8ba0-6036aa06d3c4)

- When a user is banned, they will receive both an **SMS** and **email notification** informing them of the ban.

  ![Ban Notification](https://github.com/user-attachments/assets/12874150-970d-40e6-b1f7-e062b42b631b)

---

## Tech Stack

- **Frontend**: React + Next.js + TailwindCSS
- **Backend**: Spring Boot (Java)
- **Database**: PostgreSQL

---

## Database Diagram

- The following diagram represents the structure of the database.

  ![Database Diagram](https://github.com/user-attachments/assets/12995f2a-bc9b-44dc-b63c-7bb4de271679)

## About us
We are two Computer Science students passionate about technology and creating projects that bring real change in the world. Below are the links to our personal websites.

- Simota Mihnea: [https://mihnea-simota-portofolio.netlify.app/](https://mihnea-simota-portofolio.netlify.app/)
- Muresan Raul: [https://raul-muresan-personal-website.vercel.app/](https://raul-muresan-personal-website.vercel.app/)
