import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;
import java.util.List;

import com.cohere.api.Cohere;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to PlateMate!");

        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
        ChatPost chatPost = new ChatPost(apiKey);

        String prompt = "Generate a simple 1-week meal plan for someone who is allergic to nuts.";

        String response = chatPost.getResponse(prompt);
        System.out.println("Generated Meal Plan:");
        System.out.println(response);
    }
}

//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to the Meal Plan Generator!");
//
//        // Your actual Cohere API key
//        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX"; // Replace with your real API key
//
//        System.out.println("API Key being passed: " + apiKey);
//
//        // Create an instance of ChatPost
//        ChatPost chatPost = new ChatPost(apiKey);
//
//        // Define the custom prompt
//        String prompt = "Give me a 1-week meal plan which adheres to these restrictions: dietary restrictions: Halal, allergies: fish, eggs, chia seeds, health goals: gain muscle. I want the meal plan to have daily breakfast, lunch, dinner, and a snack where each meal has a main dish, side, and a drink. I want you to return meals, recipes, ingredients, and nutritional values.";
//
//        // Call the API through ChatPost with the prompt
//        String response = chatPost.getResponse(prompt);
//
//        // Print the API response
//        System.out.println("Generated Meal Plan:");
//        System.out.println(response);
//    }
//}

//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to the Meal Plan Generator!");
//
//        // Your actual Cohere API key
//        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
//        System.out.println("API Key being passed: " + apiKey); // Debug output for API key
//
//        // Create an instance of ChatPost
//        ChatPost chatPost = new ChatPost(apiKey);
//        System.out.println("ChatPost object initialized."); // Debug output to confirm initialization
//
//        // Define the custom prompt
//        String prompt = "Give me a 1-week meal plan which adheres to these restrictions: " +
//                "dietary restrictions: Halal, allergies: fish, eggs, chia seeds, " +
//                "health goals: gain muscle. I want the meal plan to have daily breakfast, lunch, dinner, " +
//                "and a snack where each meal has a main dish, side, and a drink. " +
//                "I want you to return meals, recipes, ingredients, and nutritional values.";
//        System.out.println("Prompt created: " + prompt); // Debug output for prompt
//
//        // Call the API through ChatPost with the prompt
//        try {
//            String response = String.valueOf(chatPost.getResponse(prompt));
//            System.out.println("Response received: " + response); // Debug output for response
//        } catch (Exception e) {
//            System.err.println("An error occurred while fetching the response: " + e.getMessage());
//            e.printStackTrace(); // Print the stack trace for detailed debugging
//        }
//    }
//}

//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to the Meal Plan Generator!");
//
//        // Your actual Cohere API key
//        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
//
//        // Create an instance of ChatPost
//        ChatPost chatPost = new ChatPost(apiKey);
//
//        // Define the custom prompt
//        String prompt = "Give me a 1-week meal plan which adheres to these restrictions: " +
//                "dietary restrictions: Halal, allergies: fish, eggs, chia seeds, " +
//                "health goals: gain muscle. I want the meal plan to have daily breakfast, lunch, dinner, " +
//                "and a snack where each meal has a main dish, side, and a drink. " +
//                "I want you to return meals, recipes, ingredients, and nutritional values.";
//
//        // Call the API through ChatPost with the prompt
//        String response = chatPost.getResponse(prompt);
//
//        // Print the API response
//        System.out.println("Generated Meal Plan:");
//        System.out.println(response);
//    }
//}

//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to the Meal Plan Generator!");
//
//        // Your actual Cohere API key
//        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
//
//        // Create an instance of ChatPost
//        ChatPost chatPost = new ChatPost(apiKey);
//
//        // Define the custom prompt
//        String prompt = "Give me a 1-week meal plan which adheres to these restrictions: " +
//                "dietary restrictions: Halal, allergies: fish, eggs, chia seeds, " +
//                "health goals: gain muscle. I want the meal plan to have daily breakfast, lunch, dinner, " +
//                "and a snack where each meal has a main dish, side, and a drink. " +
//                "I want you to return meals, recipes, ingredients, and nutritional values.";
//
//        // Call the API through ChatPost with the prompt
//        String response = String.valueOf(chatPost.getResponse(prompt));
//
//        // Print the API response
//        System.out.println("Generated Meal Plan:");
//        System.out.println(response);
//    }
//}


//import src.ChatPost;
//
//public class Main {
//    public static void main(String[] args) {
//        // Replace "your_api_key_here" with your actual API key
//        ChatPost chatPost = new ChatPost("your_api_key_here");
//
//        String dietaryRestrictions = "Halal";
//        String allergies = "fish, eggs, chia seeds";
//        String healthGoals = "gain muscle";
//
//        String mealPlan = chatPost.getMealPlan(dietaryRestrictions, allergies, healthGoals);
//        System.out.println("Generated Meal Plan:");
//        System.out.println(mealPlan);
//    }
//}