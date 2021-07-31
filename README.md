# NutritionAdvisor


  <details open="open">
    <summary><h3 style="display: inline-block">Table of Contents</h3></summary>
    <summary>Table of Contents</summary>
    <ol>
      <li>
        <a href="#foreword">Foreword</a>
      </li>
      <li>
        <a href="#about-the-project">About The Project</a>
      </li>
      <li>
        <a href="#how-to-use">How to use?</a>
        <ul>
          <li><a href="#user-choice-screen">User choice screen</a></li>
          <li><a href="#Add-user-screen">Add user screen</a></li>
          <li><a href="#The-user-screen">The user screen</a></li>
          <li><a href="#The-food-screen">The food screen</a></li>
        </ul>
      </li>
      <li><a href="#Technical-information">Technical information</a></li>
    </ol>
  </details>

  <p align="center">
    <img src="https://i.imgur.com/JZl5k3Y.gif" />
  </p>

### Foreword
As a self-taught java developer, I felt quite confident with the Java basics I learned with the content provided by the University of Helsinki’s Java course. During the study, I made countless exercises, some simple, other very complex, many of them were done independently of any help. But all of them were exercises designed by someone else. Someone who made sure that the execution of the exercise wouldn’t exceed the content of the course. That’s why I decided to create something that would be fully independent. That would be created solely according to my vision from the first to the last step. I consciously decided to include within it topics I wasn’t capable of doing yet at that point. I wanted the program to not only be another project in my portfolio but a learning experience. I didn’t have at my disposal anything besides Google search engine and my curiosity.

After long deliberation, I decided that it will be a dietary program. I already had knowledge of the topic, I knew such a program could be useful to myself and that it would help me to face new challenges, making me a better Java developer. 

### About the project

The idea for the program stemmed from the particular dietary approach that is simple to follow, precise in its methods and effective in its results. Most of the diets are bombarding us with an endless amount of details and nuances. Following them require us to submit our lives, thoughts and time solely to cooking and food consumption. While such a strict approach could sometimes bring quick results, it’s very ineffective in a long term. Cooking fancy low-calory meals every day and abstaining from eating in social situations is doable in the period of 2-3 months, sometimes it is even fun but can we live our lives like that forever? Most of us don’t. We prefer spending time with family, friends, our hobbies or simply Netflix instead of cooking another low-carb cheesecake for 2 hours.

The secret to an effective diet is to forget about all the irrelevant details that consume a lot of our time and contribute near to nothing to the end result. Scientific research clearly shows that diet is a numbers game: it doesn’t matter if you eat low-carb, high-carb or only during particular hours. In the end, it’s a simple equation: do you burn more calories than you consume? If yes you can lose weight while eating pizza and chocolate. To stay healthy and preserve muscles one must also consume some reasonable minimum amount of all macronutrients: carbohydrates, fats and proteins. All other things are irrelevant.

Another important part of losing, gaining or maintaining your weight is tracking your progress. That should become your weekly habit because you need feedback to control your weight. Tracking your body weight is not sufficient, however. One could lose muscles; his weight would go down but his body composition would significantly worsen. The opposite is also possible. That’s where body fat measurement comes in. But don’t worry: it’s very simple. Research showed that a particular ratio between height and waist circumference is a very precise tool to estimate the body fat. So you just need to make sure that you measure your weight and waist circumference 1-2 times per week and you adjust your diet accordingly.

So here comes the Nutrition Advisor. Update your weight and waist circumference at least once a week and allow the program to do the thinking and calculating for you. Thanks to its functionality it will give you feedback to help you define your goals. It also estimates all your dietary requirements to make following the diet possibly easy for you to follow. Restricting its prescriptions to only a few numbers gives you freedom. You can keep following your diet regardless if it’s free Sunday that you can spend cooking, busy and long day at work where you can eat only fast foods or a social gathering with your friends.
 
 You may ask: „How am I going to track all those foods that I eat? I have no idea how many calories or proteins they have”. Don’t worry; Nutrition Advisor does that as well. Just make sure you enter right away any food you eat into the program It will calculate and track everything for you. You will know how much you have eaten and how much more you can eat.

### How to use? 

  <h4 align="center">User choice screen</h4>
  <p align="center">
  <img src="https://i.imgur.com/hsTVcun.png" />
</p>

**User choice screen** is a screen where you can choose your profile. Since the program can be used by many users it allows each of them to create their own profiles. At the bottom of the screen new profile might be created. In the middle of the screen, there is a functionality to delete a profile from the list of previously created profiles. By double-clicking the name, the user opens a previously-created profile.

  <h4 align="center">Add user screen</h4>
  <p align="center">
  <img src="https://i.imgur.com/CoxzvjW.png" />
</p>

**Add user screen** is an interface to add a new user to the profiles database. The user must enter his name, height, current weight and waist circumference, dietary goals and lifestyle. Based on that information program creates new profile with body composition statistics and initial dietary advice

  <h4 align="center">The user screen</h4>
  <p align="center">
  <img src="https://i.imgur.com/xW9rjrY.png" />
</p>

**The user screen** presents the user with basic information about himself. There is also a field for the user to update his weight and waist circumference which he should do at least once a week. Below is dietary advice which evaluates his current body composition and suggests further plans. Later the user can modify his dietary plans which can change over time. Next, the menu shows the upper limit of calories he can consume and the lower limit of macronutrients he should consume. At the bottom of the screen, there are buttons for manual adjustment of the calories and macronutrients to consume. Since the lifestyle might change from active to sedentary for example, the user should adjust requirements accordingly. Each body is also different and if you don’t progress toward your goals after two weeks of tracking perhaps you should manually adjust your intake: lower demand if you fail to lose weight, raise demand if you fail to gain weight or adjust accordingly if you fail to maintain your weight.

  <h4 align="center">The food screen</h4>
  <p align="center">
  <img src="https://i.imgur.com/nXgRCeu.png" />
</p>

**The food screen** is the screen responsible for tracking user’s everyday food consumption. In the upper part of the screen, the user can search for the particular product. After choosing the product from the result list with a double-click user enters the weight of the consumed product. The table below shows the total nutritious value of the chosen food. After that user can confirm it with the „Add button” and add meal to the daily food list which is placed in the bottom part of the interface. Food will appear in the list of "foods eaten today"(the list resets itself every day). The user can delete the food from the list as well. At the bottom of the screen, progress bars show how much nutritional needs of the user have been met on a given day. It’s important to keep in mind that the required calories intake is a rigid number hence the user should aim to accurately reach(and not exceed) the number every day but other macronutrients are just a minimum required value. There is no problem in exceeding the numbers as long as total calories intake isn’t affected

### Technical information

 The program is written in Java 11 and JavaFX. Food search engine and all the information about foods are taken through the REST API provided by **U.S. Department of Agriculture, Agricultural Research Service. FoodData Central, 2019.** http://fdc.nal.usda.gov  All the user information are stored in the local MySQL database.

<p align="center">
  <img src="https://i.imgur.com/mLiB70y.png" />
</p>
