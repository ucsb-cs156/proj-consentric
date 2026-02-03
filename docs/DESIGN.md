# proj-consentric: `docs/DESIGN.md`

In this file, we document the initial design for the `consentric` app.

## Overall purpose

The purpose of `consentric` is to provide a way for researchers studying teaching and learning (i.e. performing pedagogical research) to offer student
an opportunity to give or withhold) informed consent for participating in a research study, in a way that:

* Upholds one of the three pillars of informed consent, the "Voluntariness" principle by having a PI from outside the instructor's institution be the holder of the informed consent information until the course is over,
* Upholds the requirements of FERPA that Personally Identifiable Information (PII) pertaining to a student may not be shared outside the institution.

To explain the principle behind the app, we will use the personas of Alice and Bob, who are both Principal Investigators on a research team, but from
different universities:

* Alice is at University A, and is the instructor of a course in which the students are to be recruited as subjects.
* Bob is at University B, and will be the holder of informed consent information.

A key point is that there will be two *separate* instances of the webapp, one hosted at University A, and the other at University B.
* The instance of the web app hosted at University A will have a list of the students in the University A course taught by Prof. Alice, with their names and emails.  Since this is FERPA protected information, so it can only be hosted on a server at University A.
* The instance of the web app hosted at University B will contain *encrypted* names and emails that can only be decrypted with a key held by Prof. Alice.  Therefore neither Bob nor anyone at University B can access any FERPA protected information.

Note that the informed consent decisions, along with any other data that needs to be kept confidential from Prof. Alice is hosted on the instance
of the app at University B, and is associated with identifiers that Alice can tie back to individual students, but Bob cannot.

This basic setup can be used for two use cases:
* Case 1: Alice doesn't get access to informed consent decisions or pre-/post- survey data until the course is over.
  This preserves student autonomy and voluntariness, and mitigates the risk that students in Alice's class will
  feel coerced into giving consent because of the power that Prof. Alice has over them (i.e., their course grade)
* Case 2: Alice *never* gets access to the informed consent decisions or pre-/post- survey data, either *ever*,
  or until it has all been anonymized. 

## About the name

The name `consentric` is a play on the words "consent" (as in "informed consent", which is necessary to obtain from human subjects in research experiments)
and "concentric".

## Problem Statement

Given:
* Alice and Bob are co-PIs on research project about students in SE courses.
* Alice teaches a SE course at university 
* Alice wants Bob to handle the informed consent for the course without violating FERPA
* Students are not required to give informed consent for the research, but they are required to fill out the informed consent form and the pre/post surveys for the research.

Desired outcomes:
* Bob never receives FERPA protected information at any stage.
* Alice can verify that every student in the class fills out the informed consent form
* Alice does not have access to any other information (e.g. informed consent decisions or survey responses) until the course is over.
* It is possible for Alice, after the course is over, to connect students' responses with other data from the course, anonymize the data, and present it to the other researchers.
* Only students in the course should be able to complete the informed consent survey.
* If a student completes the survey multiple times, their new answer supersedes the old answer. (This allows them to "change their mind" about giving informed consent, which is required by IRB protocols.)
* Alice must be able to know who did/did-not fill out these surveys before the course grades are assigned, but must not be able to learn who did/did-not opt-in to participate in the research, or see the answers to the survey questions.
* Ideally, in the end, all of the research data can be anonymized in a way that data for a single student can be connected, but individual identities cannot be determined.
* If the previous bullet cannot be done perfectly, it should still be the case that the number of people that can tie data to individual identities should be minimized,and it should be made as difficult as possible.

## Proposed Solution

1. The team constructs two webapps: one for Alice and one for Bob.
2. Alice's web app allows her to:
    a. Upload a student roster with names and emails.
    b. Download a student roster that has a custom link to Bob's web app for each student, containing a single parameter. That parameter is a token that is randomly generated for each student.
    c. Alice's web app also generates a public/private key pair. Alice uploads the public key to Bob's web app.
    d. Alice's web app then sends individual emails to the students with an individualized link to Bob's web app that passes the token as a parameter.
3. Students now access Bob's web app with their individual links.
    a. Bob's web app looks up the token to find the associated course.
    b. Bob's web app then asks the student for their name, email and informed consent decision.
    c. Bob's web app takes the name and email, encrypts it using Alice's public key, and stores the encrypted name and email along with the consent decision.
    d. It also passes the encrypted name and email to any further surveys (e.g. Qualtrics surveys) associated with the research. 
4. Alice can now log in to Bob's system and get a download of a CSV file with token, encrypted(name), encrypted(email), and the date/time that the student filled out the survey. She cannot access the informed consent decision, nor the answers to any survey questions. She can use this to remind students that have not yet filled out the informed consent survey with follow up emails, and can assign participation grades.
5. Bob can obtain all of the research data in an anonymized fashion. Bob never has access to the plaintext email or name because they are encrypted on the client side (frontend, in the student's web browser, using Alice's public key) before being sent to the backend of Bob's system.
6. When the course is over, Alice will have a mapping from token to student name/email. Alice can provide Bob with any other course artifacts (e.g. assignments, github data, grades, CATME scores) needed for the research, but using the token as the identifier.
7. One detail is that if any of this data contains, for example, student CATME contents that contain names, project names, etc.that would de-anonymize the data, it may need to be redacted first.
8. Bob can then use the tokens to filter out data from non-consenting students, associate all data that needs to be associated, and assign a new set of identifiers to all of the data. At this point, the entire data set is anonymized, and can be made available to all researchers. It contains only data from consenting students, and all data is anonymized.

## Possible Flaws

1. The new data does not contain the tokens that Alice used, nor encrypted name, nor encrypted email. So anonymizing the data will be straightforward for Alice. Alice could, however conceivably, deanonymize one student at a time if she is able to match up artifacts in the data set with artifacts from her course. 
2. Beyond this class, Alice may teach these students again. Whatever Alice learns about their demographics from this study may “infect” her opinion of those students in future classes.

## Possible mitigations:

* For (2): Specifically mention this risk to students in the consent form. 
* For both: Rely on PI discretion and ethical behavior—i.e. Alice's assertion that she will not do this.
* For both: If that is insufficient, have the data set analyzed by the other members of the PI team (excluding Alice), and share only high level aggregated data with her. (Note that Alice and Bob will switch roles when Bob's course data is analyzed; in principle, each PI on the team will take a turn in Alice's role, or Bob's role.)
