## Intro
Questify is a Q&A platform that allows users to post questions and receive answers, with an upvote/downvote system to rank responses. Built using modern web technologies, it ensures a seamless experience for users looking for knowledge exchange.

## Demo video
https://github.com/user-attachments/assets/7f4054fe-18da-4004-ac02-c8cd40b5c489


## Features
- Post questions & answers

  ![image](https://github.com/user-attachments/assets/bae8e942-db47-4739-b556-c935099332ba)

  ![image](https://github.com/user-attachments/assets/d31885fa-dacb-4987-bb37-fe1246017510)

  The homescreen after we add the question:
  
  ![image](https://github.com/user-attachments/assets/e2359a0d-baec-41c5-8b11-591afae38d48)

  Let's add an answer (comment) to the post:

  ![image](https://github.com/user-attachments/assets/4d6f7355-2591-4b01-b9e5-cb64d2b16490)
  ![image](https://github.com/user-attachments/assets/40f11ebe-e1e3-4976-a965-701e02fc82ee)
  ![image](https://github.com/user-attachments/assets/b7cf7c00-e457-4953-be94-dbbadd1cabff)

  
- Upvote & Downvote system
  ![image](https://github.com/user-attachments/assets/161a15cb-5f3b-45ae-9d4f-8a8e4b610823)

- Search by the title of the post
    ![image](https://github.com/user-attachments/assets/4f779ea6-e544-432e-8ed4-2d4578b37636)

- Filter by 3 options (by 1 tag, by an user, or only show "my posts")
    ![image](https://github.com/user-attachments/assets/ec620802-0d46-4198-b23d-323c63582557)
  
- User score system
  ![image](https://github.com/user-attachments/assets/022a7692-988d-45fc-a2c3-e4c8207574b2)
  - Each user starts with 0 points.
  - Users gain points when:
    - Their question is voted up (+2.5 points per vote),
    - Their answer is voted up (+5 points per vote).
  - Users lose points when:
    - Their question is voted down (-1.5 points per vote),
    - Their answer is voted down (-2.5 points per vote),
    - They down vote an answer of another user (-1.5 point).

- Admin mode (can ban users and delete any question or answer)
   
    ![image](https://github.com/user-attachments/assets/3c92c4d0-4091-43b1-a3f2-619e27faa950)
  
    ![image](https://github.com/user-attachments/assets/d1c9daca-bb9a-4bb1-9295-873593b829e1)
  
    ![image](https://github.com/user-attachments/assets/ce897cd7-eea9-42b0-8ba0-6036aa06d3c4)
  
  - At this moment, the user will receive an SMS and an email saying that they have been banned
    
    ![image](https://github.com/user-attachments/assets/12874150-970d-40e6-b1f7-e062b42b631b)



## Tech Stack
- Frontend: React + Next.js + TailwindCSS
- Backend: Spring Boot (Java)
- Database: PostgreSQL

## Database Diagram
![image](https://github.com/user-attachments/assets/12995f2a-bc9b-44dc-b63c-7bb4de271679)

## About us
We are two Computer Science students passionate about technology and creating projects that bring real change in the world. Below are the links to our personal websites.

- Simota Mihnea: [https://mihnea-simota-portofolio.netlify.app/](https://mihnea-simota-portofolio.netlify.app/)
- Muresan Raul: [https://raul-muresan-personal-website.vercel.app/](https://raul-muresan-personal-website.vercel.app/)
