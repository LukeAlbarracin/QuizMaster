# QuizMaster (Frontend)

## A Quizlet inspired flashcard application which uses NLP to generate questions
My project is a full-stack app written in Java.

## Context
For one of my CS125 class at the University of Illinois Urbana-Champaign we were given creative freedom for our final project. The only major constraint was that it had to be written for Android. Previously I had begun writing basic programs to help aid my practice in certain classes. In addition, I was frequently using the well-known app called Quizlet to help me study. So for my project, I decided to create an app that uses Natural Language Processing (NLP) to convert facts into questions.

## Technology Details

### Frontend
The frontend is implemented as an Android application in Java. It uses the [Volley Library](https://github.com/google/volley) to perform HTTP requests.

### Backend
The backend uses the [Google Cloud Platform (GCP)](https://cloud.google.com/docs) and also uses [Spring Boot](https://spring.io/projects/spring-boot). More importantly, it uses the [Apache OpenNLP library](https://opennlp.apache.org/) to perform sentence analysis to send information back to the client. The backend can be found [here](https://github.com/LukeAlbarracin/opennlp-java-backend/blob/master/README.md).

## References
- [Guide to OpenNLP](https://www.baeldung.com/apache-open-nlp)
